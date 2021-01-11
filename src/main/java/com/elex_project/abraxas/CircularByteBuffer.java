/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2018, Elex
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.elex_project.abraxas;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.EventListener;

/**
 * FIFO 형식으로 바이트를 저장하고 읽을 수 있는 환형 바이트 버퍼.
 * 버퍼가 가득차면 용량을 알아서 늘인다.
 * <p/>
 * <strike>모든 put&get 메서드는 넌블로킹 방식임.</strike>
 * <p/>
 * 스레드에 안전함.
 */
public class CircularByteBuffer {

	private static final int DEFAULT_CAPACITY = 1024;
	/**
	 * 바이트 버퍼
	 */
	private byte[] buffer;
	private final byte[] tmp2 = new byte[2];
	private final byte[] tmp4 = new byte[4];
	private final byte[] tmp8 = new byte[8];

	/**
	 * 초기 버퍼 크기, 버퍼가 부족하면 초기 크기만큼 더 늘린다.
	 */
	private final int initialCapacity;
	/**
	 * 읽기 가능한 크기
	 */
	private int available;
	/**
	 * 읽기 커서
	 */
	private int idxGet;
	/**
	 * 쓰기 커서
	 */
	private int idxPut;

	private ArrayList<InputListener> listeners;

	public CircularByteBuffer() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * 버퍼 크기는 동적으로 변경할 수 없음.
	 *
	 * @param capacity 1024
	 */
	public CircularByteBuffer(int capacity) {
		this.initialCapacity = capacity;
		this.buffer = new byte[capacity];
		//this.tmp = new byte[capacity];
	}

	/**
	 * 데이터의 초기화
	 */
	public synchronized void clear() {
		idxGet = idxPut = available = 0;
		Arrayz.fill(buffer, (byte) 0x00);

	}

	/**
	 * 한 바이트 읽기
	 *
	 * @return 읽을 데이터가 없으면 -1
	 */
	public synchronized int get() {
		if (available == 0) {
			return -1;
		}

		byte value = buffer[idxGet];
		idxGet = (idxGet + 1) % buffer.length;
		available--;
		return value;

	}

	/**
	 * 버퍼의 크기만큼 읽기
	 *
	 * @param dst 버퍼
	 * @return 읽어들인 실제 데이터의 바이트 크기. 읽을 데이터가 없었다면 0
	 */
	public synchronized int get(byte[] dst) {
		return get(dst, 0, dst.length);
	}

	/**
	 * 버퍼의 크기만큼 읽기
	 *
	 * @param dst 버퍼
	 * @param off 옵셋
	 * @param len 길이
	 * @return 읽어들인 실제 데이터의 바이트 크기. 읽을 데이터가 없었다면 0
	 */
	public synchronized int get(byte[] dst, int off, int len) {
		if (available == 0) {
			return 0;
		}

		// limit is last index to read + 1
		int limit = idxGet < idxPut ? idxPut : buffer.length;
		int count = Math.min(limit - idxGet, len);
		Arrayz.copy(buffer, idxGet, dst, off, count);
		idxGet += count;

		if (idxGet == buffer.length) {
			// Array end reached, check if we have more
			int count2 = Math.min(len - count, idxPut);
			if (count2 > 0) {
				Arrayz.copy(buffer, 0, dst, off + count, count2);
				idxGet = count2;
				count += count2;
			} else {
				idxGet = 0;
			}
		}
		available -= count;
		return count;

	}

	private synchronized void increaseCapacity(final int newSize) {
		this.buffer = Arrayz.copyOf(this.buffer, newSize);


	}

	public synchronized byte getByte() throws IndexOutOfBoundsException {
		if (available() >= Numberz.BYTE_SIZE) {
			return (byte) get();
		} else {
			throw new IndexOutOfBoundsException();
		}
	}

	public synchronized short getShort() throws IndexOutOfBoundsException {
		if (available() >= Numberz.SHORT_SIZE) {
			//byte[] a = new byte[NumberUtils.SHORT_SIZE];
			synchronized (tmp2) {
				int nRead = get(tmp2, 0, tmp2.length);
				if (nRead == Numberz.SHORT_SIZE) {
					return Numberz.toShort(tmp2);
				} else {
					throw new IndexOutOfBoundsException();
				}
			}

		} else {
			throw new IndexOutOfBoundsException();
		}
	}

	public synchronized int getInt() throws IndexOutOfBoundsException {
		if (available() >= Numberz.INT_SIZE) {
			//byte[] a = new byte[NumberUtils.INT_SIZE];
			synchronized (tmp4) {
				int nRead = get(tmp4, 0, tmp4.length);
				if (nRead == Numberz.INT_SIZE) {
					return Numberz.toInt(tmp4);
				} else {
					throw new IndexOutOfBoundsException();
				}
			}
		} else {
			throw new IndexOutOfBoundsException();
		}
	}

	public synchronized long getLong() throws IndexOutOfBoundsException {
		if (available() >= Numberz.LONG_SIZE) {
			//byte[] a = new byte[NumberUtils.LONG_SIZE];
			synchronized (tmp8) {
				int nRead = get(tmp8, 0, tmp8.length);
				if (nRead == Numberz.LONG_SIZE) {
					return Numberz.toLong(tmp8);
				} else {
					throw new IndexOutOfBoundsException();
				}
			}

		} else {
			throw new IndexOutOfBoundsException();
		}
	}

	/**
	 * 버퍼의 읽기 가능한 영역 내에서 특정 바이트의 위치(현재 커서 기준)를 반환.
	 *
	 * @param b 찾고자 하는 바이트
	 * @return 찾은 바이트의 위치, 없으면 -1.
	 */
	public synchronized int seek(final byte b) {
		int i = 0;
		while (i < available()) {
			int idx = idxGet + i;
			if (idx < buffer.length) {
				if (buffer[idx] == b) {
					return i;
				}
			} else {
				if (buffer[idx - buffer.length] == b) {
					return i;
				}
			}
			i++;
		}

		return -1;


	}

	public synchronized String getString(final byte terminalByte)
			throws IndexOutOfBoundsException {
		try {
			return getString(terminalByte, StandardCharsets.UTF_8);
		} catch (NoClassDefFoundError e) {
			try {
				return getString(terminalByte, Stringz.UTF_8);
			} catch (UnsupportedEncodingException e1) {
				throw new RuntimeException(e1);
			}
		}
	}

	/**
	 * 버퍼에서 문자열을 읽는다.
	 *
	 * @param terminalByte 문자열의 마지막 바이트. 종료 문자.
	 * @param encoding     인코딩.
	 * @return 버퍼에 종료 문자가 없는 때(seek() 반환 값이 0보다 작은 경우)에는 null을 반환.
	 * @throws IndexOutOfBoundsException
	 * @throws UnsupportedEncodingException
	 */
	public synchronized String getString(final byte terminalByte, final String encoding)
			throws IndexOutOfBoundsException, UnsupportedEncodingException {
		int pos = seek(terminalByte);
		if (pos >= 0) {
			final byte[] s = new byte[pos + 1];
			get(s);
			return new String(s, encoding);
		}
		return null;

	}

	public synchronized String getString(final byte terminalByte, final Charset encoding)
			throws IndexOutOfBoundsException {
		int pos = seek(terminalByte);
		if (pos >= 0) {
			final byte[] s = new byte[pos + 1];
			get(s);
			return new String(s, encoding);
		}
		return null;

	}


	/**
	 * 한 바이트 저장
	 *
	 * @param value 바이트
	 */
	public synchronized void put(final byte value) {
		if (free() <= 0) {
			if (idxPut == 0) idxPut = buffer.length;
			increaseCapacity(buffer.length + initialCapacity);
		}

		buffer[idxPut] = value;
		idxPut = (idxPut + 1) % buffer.length;
		available++;


		if (null != listeners) {
			for (InputListener listener : listeners) {
				if (listener.getTrigger() == value) {
					listener.onDataAvailable(value, CircularByteBuffer.this);
				}
			}
		}

		//return true;
	}


	/**
	 * 바이트 배열을 저장
	 *
	 * @param src 바이트 배열
	 */
	public void put(byte[] src) {
		put(src, 0, src.length);
	}

	/**
	 * 바이트 배열을 저장
	 *
	 * @param src 바이트 배열
	 * @param off 옵셋
	 * @param len 길이
	 */
	public synchronized void put(final byte[] src, final int off, final int len) {
		if (free() < len) {
			if (idxPut == 0) idxPut = buffer.length;
			increaseCapacity(Math.max(buffer.length + initialCapacity, buffer.length + len));
		}

		// limit is last index to put + 1
		int limit = idxPut < idxGet ? idxGet : buffer.length;
		int count = Math.min(limit - idxPut, len);
		System.arraycopy(src, off, buffer, idxPut, count);
		idxPut += count;

		if (idxPut == buffer.length) {
			// Array end reached, check if we have more
			int count2 = Math.min(len - count, idxGet);
			if (count2 > 0) {
				System.arraycopy(src, off + count, buffer, 0, count2);
				idxPut = count2;
				count += count2;
			} else {
				idxPut = 0;
			}
		}
		available += count;


		if (listeners != null) {
			for (InputListener listener : listeners) {
				byte trigger = listener.getTrigger();
				for (int i = 0; i < len; i++) {
					if (trigger == src[off + i]) {
						listener.onDataAvailable(trigger, CircularByteBuffer.this);
						break;
					}
				}
			}
		}

		//return count;
	}

	/**
	 * 다음에 읽게 될 첫 번째 바이트
	 *
	 * @return 읽을 데이터가 없으면 -1
	 */
	public synchronized int peek() {
		return available > 0 ? buffer[idxGet] : -1;

	}

	/**
	 * Skips the given count of bytes, but at most the currently available count.
	 *
	 * @return number of bytes actually skipped from this buffer (0 if no bytes are available)
	 */
	public synchronized int skip(int count) {
		if (count > available) {
			count = available;
		}
		idxGet = (idxGet + count) % buffer.length;
		available -= count;
		return count;

	}

	public synchronized int skipUntil(byte b) {
		int pos = seek(b);
		return skip(pos);

	}

	/**
	 * 버퍼의 최대 저장 공간 크기
	 */
	public synchronized int capacity() {
		return buffer.length;

	}

	/**
	 * 읽을 수 있는 데이터의 크기
	 */
	public synchronized int available() {
		return available;

	}

	/**
	 * 쓰기 가능한 데이터의 크기
	 */
	public synchronized int free() {
		return buffer.length - available;

	}

	@Override
	public String toString() {
		return "[" + idxGet + ", " + idxPut + "]\t" + Bytez.toHex(":", buffer);

	}


	/**
	 * 입력 데이터 모니터를 등록.
	 * 만일, 동일한 바이트의 트리거가 이미 등록되어 있다면 기존의 트리거는 제거된다.
	 *
	 * @param listener listener
	 */
	public void addListener(InputListener listener) {
		if (null == listeners) {
			listeners = new ArrayList<>();
		}
		for (InputListener l : listeners) {
			if (l.getTrigger() == listener.getTrigger()) {
				listeners.remove(l);
			}
		}
		listeners.add(listener);
	}

	public void removeListener(InputListener listener) {
		if (null != listeners) {
			listeners.remove(listener);
			if (listeners.size() == 0) {
				listeners = null;
			}
		}

	}

	public void removeListener(byte trigger) {
		if (null != listeners) {
			for (InputListener l : listeners) {
				if (l.getTrigger() == trigger) {
					listeners.remove(l);
				}
			}
			if (listeners.size() == 0) {
				listeners = null;
			}
		}
	}

	public void removeAllListeners() {
		if (null != listeners) listeners.clear();
		listeners = null;
	}

	/**
	 * 버퍼 입력 모니터
	 */
	public interface InputListener extends EventListener {
		/**
		 * 감시할 바이트 데이터
		 *
		 * @return 바이트
		 */
		public byte getTrigger();

		/**
		 * 버퍼에 트리거 바이트가 입력되면 (별도의 스레드에서) 호출된다.
		 *
		 * @param trigger
		 * @param buffer
		 */
		public void onDataAvailable(byte trigger, CircularByteBuffer buffer);
	}
}