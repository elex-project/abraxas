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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BitzTest {

	@Test
	void isSet() {
		int val = 365;
		Console.writeLine(Numberz.toBinaryStringPadded(val));

		assertTrue(Bitz.isSet(val, 0));
		assertFalse(Bitz.isSet(val, 1));
		assertTrue(Bitz.isSet(val, 2));
		assertTrue(Bitz.isSet(val, 3));
		assertFalse(Bitz.isSet(val, 4));
	}
	@Test
	void isSetArray() {
		byte[] val = {0x1e, 0x09};
		Console.writeLine(Bytez.toBinary(val));

		assertTrue(Bitz.isSet(val, 0));
		assertFalse(Bitz.isSet(val, 1));
		assertFalse(Bitz.isSet(val, 2));
		assertTrue(Bitz.isSet(val, 3));

		assertFalse(Bitz.isSet(val, 8));
		assertTrue(Bitz.isSet(val, 9));
		assertTrue(Bitz.isSet(val, 10));
		assertTrue(Bitz.isSet(val, 11));
	}

	@Test
	void setBit() {
	}

	@Test
	void flipBit() {
		int val = 365;
		Console.writeLine(Numberz.toBinaryStringPadded(val));
		val =Bitz.flipBit(val, 0);
		Console.writeLine(Numberz.toBinaryStringPadded(val));
	}
}