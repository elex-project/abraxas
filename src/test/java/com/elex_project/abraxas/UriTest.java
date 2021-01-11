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

class UriTest {
	private static final String[] SAMPLES = {
			"http://www.my.work.com/",
			"http://www.my.uni.edu/in+fo/matriculation/enroling.html",
			"http://info.my.org/About Us/Phonebook",
			"http://www.library.my.town.va.us/Catalogue/76523471236%2Fwen44--4.98",
			"http://www.my.org/462F4F2D4241522A314159265358979323846",
			"http://www.w3.org:8000/안녕/test",
			"http://www.myu.edu/org/admin/people#andy",
			"http://info.my.org/AboutUs/Index/Phonebook?dobbins",
			"http://www.w3.org/RDB/EMP?*%20where%20name%%3Ddobbins",
			"http://www.baeldung.com?key1=value+1&key2=value%40%21%242&key3=value%253"
	};
	@Test
	void encode() {
		for (String uri : SAMPLES){
			Console.writeLine(Uri.encode(uri));
		}
	}

	@Test
	void decode() {
	}

	@Test
	void newUri() {
		String uri = new Uri.Builder().scheme("https").host("example.com").path("api").path("v1")
				.query("query", "Hello").query("sort", "desc")
				.fragment("section_1")
				.toString();
		Console.writeLine(uri);
	}

	@Test
	void parse() {
		final String sample1 = "https://user@example.com:8080/api/v1?query=Hello&sort=desc#section_1";
		Uri uri = Uri.parse(sample1);
		Console.writeLine("URI: " + uri.toString());
	}

}