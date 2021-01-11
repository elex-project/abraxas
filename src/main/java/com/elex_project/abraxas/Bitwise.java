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
 * Bitwise calculation
 * @param <T> type
 * @author Elex
 */
public interface Bitwise<T extends Number> {
	public String toBinaryString();

	public String toDecimalString();

	public String toHexString();

	public String toOctalString();

	public void set(int idx, boolean val);

	public void set(int idx);

	public void set();

	public void clear(int idx);

	public void clear();

	public boolean getBitAt(int pos);

	public T value();

	public void value(byte v);

	public void value(short v);

	public void value(int v);

	public void value(long v);

	public void add(byte a);

	public void add(short a);

	public void add(int a);

	public void add(long a);

	public void and(byte a);

	public void or(byte a);

	public void xor(byte a);

	public void and(short a);

	public void or(short a);

	public void xor(short a);

	public void and(int a);

	public void or(int a);

	public void xor(int a);

	public void and(long a);

	public void or(long a);

	public void xor(long a);

	public void flip();

	public void flip(int idx);

	public void complementOf2();

	public void reverse();

	public int size();

	public void shiftLeft(int n);

	public void shiftRight(int n);

	public void rotateLeft(int n);

	public void rotateRight(int n);
}
