/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Elex
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

/**
 * @author Elex
 */
public final class Bitwise8 implements Bitwise<Byte> {
	private static final byte ONE = (byte) 1;
	private static final byte ZERO = (byte) 0;
	private static final int DATA_LENGTH = 8;

	private byte value;


	@Override
	public boolean equals(Object o) {
		if (o instanceof Bitwise) {
			return Bitwise32.equals(this, (Bitwise<?>) o);
		} else {
			return super.equals(o);
		}
	}

	@Override
	public String toBinaryString() {
		return Numberz.toBinaryStringPadded(value);
	}

	@Override
	public String toDecimalString() {
		return Byte.toString(value);
	}

	@Override
	public String toHexString() {
		return Numberz.toHexString(value);
	}

	public String toOctalString() {
		return Integer.toOctalString(value);
	}

	@Override
	public void set(int idx, boolean val) {
		if (val) {
			set(idx);
		} else {
			clear(idx);
		}
	}

	@Override
	public void set(int idx) {
		value |= (byte) (ONE << idx);
	}

	@Override
	public void set() {
		for (int i = 0; i < DATA_LENGTH; i++) {
			set(i, true);
		}
	}

	@Override
	public void clear(int idx) {
		value &= ~(ONE << idx);
	}

	@Override
	public void clear() {
		this.value = ZERO;
	}

	@Override
	public boolean getBitAt(int pos) {
		return Bitz.isSet(value, pos);
	}

	@Override
	public void value(byte v) {
		this.value = v;
	}

	@Override
	public void value(short v) {
		this.value = (byte) v;
	}

	@Override
	public void value(int v) {
		this.value = (byte) v;
	}

	@Override
	public void value(long v) {
		this.value = (byte) v;
	}

	public Byte value() {
		return value;
	}

	@Override
	public void add(byte a) {
		this.value += a;
	}

	@Override
	public void add(short a) {
		this.value += a;
	}

	@Override
	public void add(int a) {
		this.value += a;
	}

	@Override
	public void add(long a) {
		this.value += a;
	}

	@Override
	public void flip() {
		for (int i = 0; i < DATA_LENGTH; i++) {
			flip(i);
		}
	}


	@Override
	public void flip(int idx) {
		set(idx, !getBitAt(idx));
	}

	@Override
	public void complementOf2() {
		flip();
		add(ONE);
	}

	@Override
	public void reverse() {
		byte tmp = value;
		//clear();
		for (int j = 0; j < DATA_LENGTH; j++) {
			boolean b = Bitz.isSet(tmp, j);
			set(DATA_LENGTH - 1 - j, b);
		}
	}

	@Override
	public void and(byte a) {
		value &= a;
	}

	@Override
	public void or(byte a) {
		value |= a;
	}

	@Override
	public void xor(byte a) {
		value ^= a;
	}

	@Override
	public void and(short a) {
		value &= a;
	}

	@Override
	public void or(short a) {
		value |= a;
	}

	@Override
	public void xor(short a) {
		value ^= a;
	}

	@Override
	public void and(int a) {
		value &= a;
	}

	@Override
	public void or(int a) {
		value |= a;
	}

	@Override
	public void xor(int a) {
		value ^= a;
	}

	@Override
	public void and(long a) {
		value &= a;
	}

	@Override
	public void or(long a) {
		value |= a;
	}

	@Override
	public void xor(long a) {
		value ^= a;
	}

	@Override
	public int size() {
		return DATA_LENGTH;
	}

	@Override
	public void shiftLeft(int n) {
		value <<= n;
	}

	@Override
	public void shiftRight(int n) {
		value >>= n;
	}

	@Override
	public void rotateLeft(int n) {
		for (int i = 0; i < n; i++) {
			boolean b = getBitAt(DATA_LENGTH - 1);
			value <<= 1;
			set(0, b);
		}
	}

	@Override
	public void rotateRight(int n) {
		for (int i = 0; i < n; i++) {
			boolean b = getBitAt(0);
			value >>= 1;
			set(DATA_LENGTH - 1, b);
		}
	}
}