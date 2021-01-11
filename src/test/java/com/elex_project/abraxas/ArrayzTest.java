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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;
class ArrayzTest {
	private final int[] array1 = {1,3,5,7,9};
	private final int[] array2 = {1,2,7,10};

	@Test
	void union() {
		Set<Integer> array = Arrayz.union(array1, array2);
		Console.writeLine(array);
	}

	@Test
	void subtract() {
		Set<Integer> array = Arrayz.subtract(array1, array2);
		Console.writeLine(array);
	}

	@Test
	void intersect() {
		Set<Integer> array = Arrayz.intersect(array1, array2);
		Console.writeLine(array);
	}

	@Test
	void rangeOf() {
		for (int i : Arrayz.ofRange(5, 10)) {
			Console.writeLine(i);
		}
	}

	@Test
	void asList() {
	}

	@Test
	void testAsList() {
	}

	@Test
	void concat() {
	}

	@Test
	void testConcat() {
	}


	@Test
	void concatIgnoreDuplicate() {
	}

	@Test
	void testConcatIgnoreDuplicate() {
	}

	@Test
	void removeElement() {
	}

	@Test
	void testRemoveElement() {
	}

	@Test
	void sort() {
	}

	@Test
	void testSort() {
	}

	@Test
	void flip() {
	}

	@Test
	void iterate() {
	}

	@Test
	void testIterate() {
	}


	@Test
	void ofRange() {
	}

	@Test
	void testOfRange() {
	}

	@Test
	void isEmpty() {
	}

	@Test
	void testIsEmpty() {
	}



	@Test
	void fill() {
	}

	@Test
	void copy() {
		int[] array = {1,2,3,4,5,6,7,8,9};
		int[] dest1 = new int[10];
		int[] dest2 = new int[10];

		System.arraycopy(array,2,dest1, 4, 5);
		Arrayz.copy(array, 2, dest2, 4,5);

		Assertions.assertArrayEquals(dest1, dest2);
		Console.writeLine(dest1);
		Console.writeLine(dest2);
	}
}