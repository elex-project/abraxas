package com.elex_project.abraxas;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * 바이트 배열 패킷에서 순서대로 부분을 끊어낼 때 사용한다.
 *
 * @author Elex
 */
public final class ChunkedBytes {
	private final byte[] bytes;
	private int idx = 0;

	public ChunkedBytes(final byte[] bytes) {
		this.bytes = bytes;
	}

	public ChunkedBytes(final String hex) {
		this(Bytez.fromHex(hex));
	}

	/**
	 * 인덱스 포인터를 원하는 위치로 이동시킨다.
	 * @param pos 절대적인 위치
	 */
	public void seek(final int pos) {
		idx = pos;
	}

	/**
	 * 인덱스 포인터를 원하는 위치로 이동시킨다.
	 * @param pos 현재 위치로부터 상대적인 위치
	 */
	public void seekRelative(final int pos) {
		idx += pos;
	}
	/**
	 * 인덱스 위치의 바이트를 읽는다. 인덱스 포인터는 이동하지 않는다.
	 * @return byte value of that index
	 */
	public byte peek() {
		return bytes[idx];
	}

	/**
	 * 인덱스 위치 이후로 남은 바이트 길이
	 * @return remain
	 */
	public int available() {
		return bytes.length - idx;
	}

	/**
	 * 다음 바이트 배열을 읽는다.
	 * @param size
	 * @return byte array
	 */
	@NotNull
	@Contract(pure = true)
	public byte[] next(final int size) {
		byte[] out = Bytez.subArray(bytes, idx, size);
		idx += size;
		return out;
	}

	/**
	 * Next byte value
	 * @return byte
	 */
	public byte next() {
		byte out = bytes[idx];
		idx++;
		return out;
	}
}
