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

import org.w3c.dom.Document;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpPostRequest<T> extends HttpRequest<T>{
	private Map<String, String> arguments;
	private String body;
	public HttpPostRequest(final Uri uri) throws IOException {
		super(uri);
	}

	public HttpPostRequest(final String uri) throws IOException {
		super(uri);
	}

	@Override
	protected void init() throws IOException {
		super.init();
		this.connection.setRequestMethod(HttpMethod.POST.getName());
		this.connection.setDoInput(true);
		this.connection.setDoOutput(true);
		this.arguments = new HashMap<>();
	}

	/**
	 * 바디가 설정된 때에는 파라미터는 사용하지 않는다.
	 *
	 * @param content
	 */
	public void setBody(String content, String contentType) {
		this.body = content;
		setContentType(contentType);
	}
	/**
	 * 바디가 설정된 때에는 파라미터는 사용하지 않는다.
	 * 파라미터를 지정하면 바디는 null이 된다.
	 *
	 * @param k
	 * @param v
	 */
	public void putParameter(String k, String v) {
		this.body = null;
		this.arguments.put(k, v);
	}
	/**
	 * application/xml
	 *
	 * @param xml
	 * @throws TransformerException
	 */
	public void setBody(Document xml) throws TransformerException {
		this.body = IOz.convertXMLToString(xml);
		setContentType(Mime.XML.getMimeType()+"; charset=UTF-8");
	}

	@Override
	public void send(final HttpResponseHandler<T> handler) throws IOException {
		byte[] out = null;
		if (null == body) {
			out = Stringz.toBytes(Stringz.join("&","=", arguments));
			setContentType(Mime.URL_ENCODED.getMimeType()+"; charset=UTF-8");

		} else {
			out = Stringz.toBytes(body);

		}

		connection.setFixedLengthStreamingMode(out == null ? 0 : out.length);

		connection.connect();

		OutputStream os = connection.getOutputStream();
		if (null != out) os.write(out);
		os.flush();
		//os.close();

		processResponse(handler);

		//os.close();
		//connection.getInputStream().close();

		if (closeConnection) connection.disconnect();
	}
}
