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

class EnvTest {

	@Test
	void getUUID() {
	}

	@Test
	void getOsName() {
		Console.writeLine(Env.getOsName());
	}

	@Test
	void getOsVersion() {
		Console.writeLine(Env.getOsVersion());
	}

	@Test
	void getOsArch() {
	}

	@Test
	void isWindows() {
	}

	@Test
	void getJavaName() {
		Console.writeLine(Env.getJavaName());
	}

	@Test
	void getJavaVendor() {
		Console.writeLine(Env.getJavaVendor());
	}

	@Test
	void getJavaVersion() {
		Console.writeLine(Env.getJavaVersion());
	}

	@Test
	void getJVMVersion() {
		Console.writeLine("Ver: %d", Env.getJVMVersion());
	}

	@Test
	void getUserName() {
	}

	@Test
	void getUserDir() {
		Console.writeLine(Env.getUserDir());
	}

	@Test
	void getUserHome() {
		Console.writeLine(Env.getUserHome());
	}

	@Test
	void isKorean() {
	}

	@Test
	void getLocale() {
	}

	@Test
	void localeToString() {
	}
}