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

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @param <T> type of a response
 * @author Elex
 */
@Slf4j
public abstract class HttpRequest<T> {
	protected static final String CONTENT_TYPE = "Content-Type";
	protected static final String ACCEPT_CHARSET = "Accept-Charset";
	protected static final String ACCEPT_LANGUAGE = "Accept-Language";
	protected static final String USER_AGENT = "User-Agent";
	protected static final String CONNECTION = "Connection";

	private static ExecutorService executor;

	static {
		executor = Executors.newCachedThreadPool();
	}

	protected URL url;
	protected HttpURLConnection connection;
	protected boolean closeConnection = false;

	public HttpRequest(Uri uri) throws IOException {
		this(uri.toString());
	}

	public HttpRequest(String uri) throws IOException {
		this.url = new URL(uri);
		init();
	}

	protected void init() throws IOException {
		this.connection = (HttpURLConnection) this.url.openConnection();
		connection.setConnectTimeout(25000);
		connection.setInstanceFollowRedirects(false);
		connection.setUseCaches(false);
		connection.setRequestProperty(USER_AGENT, "Abraxas by Elex");
		try {
			connection.setRequestProperty(ACCEPT_CHARSET, StandardCharsets.UTF_8.name());
		} catch (NoClassDefFoundError e) {
			connection.setRequestProperty(ACCEPT_CHARSET, Stringz.UTF_8);
		}
	}

	public void setUserAgent(String userAgent) {
		connection.setRequestProperty(USER_AGENT, userAgent);
	}

	public void setRequestTimeout(int timeout) {
		this.connection.setReadTimeout(timeout);
	}

	public void setConnectTimeout(int timeout) {
		this.connection.setConnectTimeout(timeout);
	}

	public void setRequestHeaderProperty(String k, String v) {
		this.connection.setRequestProperty(k, v);
	}

	public void setContentType(String contentType) {
		this.connection.setRequestProperty(CONTENT_TYPE, contentType);
	}

	public void setFollowRedirect(boolean follow){
		this.connection.setInstanceFollowRedirects(follow);
	}
	public void setDisconnect(boolean disconnect) {
		this.closeConnection = disconnect;
		setRequestHeaderProperty(CONNECTION, disconnect ? "keep-alive" : "close");
	}

	public void sendAsync(final HttpResponseHandler<T> handler) {
		executor.execute(() -> {
			try {
				send(handler);
			} catch (IOException e) {
				log.error("Unable to send request.", e);
				handler.onException(e);
			}
		});

	}

	public abstract void send(HttpResponseHandler<T> handler) throws IOException;

	protected void processResponse(HttpResponseHandler<T> handler) throws IOException {
		if (null != handler) {
			try {
				handler.onResponse(
						connection.getResponseCode(),
						connection.getHeaderFields(),
						handler.transform(connection.getInputStream()));
			} catch (Throwable e) {
				handler.onException(e);
			}
		}

	}
}
