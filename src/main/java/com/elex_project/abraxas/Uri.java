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

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * URI = scheme:[//authority]path[?query][#fragment]
 * <p>
 * authority = [userinfo@]host[:port]
 *
 * @author Elex
 * @see "https://en.wikipedia.org/wiki/URL"
 * @see "https://en.wikipedia.org/wiki/Percent-encoding"
 */
@Slf4j
@Getter
public final class Uri {
	private static final Pattern URI_PATTERN = Pattern
			.compile("(?<scheme>[^:/]+)://((?<user>[^@]+)@)?(?<host>[^:]+)(:(?<port>[0-9]+))?(/(?<path>[^?]+))?(\\?(?<query>[^#]+))?(#(?<fragment>.+))?");

	private String scheme = "http";
	private String user = null;
	private String host = "localhost";
	private int port = -1;
	private final List<String> path;
	private final Map<String, String> query;
	private String fragment = null;

	private Uri() {
		path = new ArrayList<>();
		query = new HashMap<>();
	}

	/**
	 * Alert! Do not encode the full uri!
	 *
	 * @param str str
	 * @return str
	 * @see java.net.URLEncoder#encode(String, String)
	 */
	public static String encode(final @NotNull String str) {
		try {
			return java.net.URLEncoder.encode(str, Stringz.utf8().name());
		} catch (UnsupportedEncodingException e) {
			log.error("Utf-8 not supported.", e);
			throw new RuntimeException();
		}
	}

	/**
	 * '+' is valid char for a path segment
	 *
	 * @param str
	 * @return
	 */
	private static String encodePath(final @NotNull String str) {
		try {
			return java.net.URLEncoder.encode(str, Stringz.utf8().name())
					.replaceAll("%2B", "+");
		} catch (UnsupportedEncodingException e) {
			log.error("Utf-8 not supported.", e);
			throw new RuntimeException();
		}
	}

	/**
	 * @param str
	 * @return
	 * @see java.net.URLDecoder#decode(String, String)
	 */
	public static String decode(final @NotNull String str) {
		try {
			return java.net.URLDecoder.decode(str, Stringz.utf8().name());
		} catch (UnsupportedEncodingException e) {
			log.error("Utf-8 not supported.", e);
			throw new RuntimeException();
		}
	}

	public static class Builder {
		private final Uri uri;

		public Builder() {
			this.uri = new Uri();
		}

		public Builder scheme(final @NotNull String scheme) {
			uri.scheme = scheme;
			return this;
		}

		public Builder user(final @Nullable String user) {
			uri.user = user;
			return this;
		}

		public Builder host(final @NotNull String host) {
			uri.host = host;
			return this;
		}

		public Builder port(final int port) {
			uri.port = port;
			return this;
		}

		/**
		 * Append path segment
		 *
		 * @param path segment
		 * @return
		 */
		public Builder path(final @NotNull String path) {
			if (Stringz.contains(path, "/")) {
				for (final String seg : path.split("/")) {
					path(seg);
				}
			} else {
				uri.path.add(path);
			}
			return this;
		}

		public Builder encodedPath(final @NotNull String path) {
			if (Stringz.contains(path, "/")) {
				for (final String seg : path.split("/")) {
					encodedPath(seg);
				}
			} else {
				uri.path.add(encodePath(path));
			}
			return this;
		}

		public Builder query(final @NotNull String name, final @Nullable String value) {
			uri.query.put(name, value);
			return this;
		}

		public Builder encodedQuery(final @NotNull String name, final @Nullable String value) {
			uri.query.put(encode(name), (null == value) ? null : encode(value));
			return this;
		}

		public Builder fragment(final @Nullable String fragment) {
			uri.fragment = fragment;
			return this;
		}

		public Uri build() {
			return uri;
		}
	}

	/**
	 * Start creating new Uri
	 * set the values with builder pattern, then call the toString() when done.
	 *
	 * @return uri
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * parse uri string int uri object
	 *
	 * @param uri uri
	 * @return uri
	 */
	public static Uri parse(final @NotNull String uri) {
		final Matcher matcher = URI_PATTERN.matcher(uri);
		log.info(matcher.toString());
		if (!matcher.matches()) throw new IllegalArgumentException("URI pattern mismatches.");

		final Uri object = new Uri();
		object.scheme = matcher.group("scheme");
		object.user = matcher.group("user");
		object.host = matcher.group("host");
		if (null != matcher.group("port")) {
			object.port = Integer.parseInt(matcher.group("port"));
		}
		if (null != matcher.group("path")) {
			for (String seg : matcher.group("path").split("/")) {
				object.path.add(seg);
			}
		}
		if (null != matcher.group("query")) {
			for (String q : matcher.group("query").split("&")) {
				String[] pair = q.split("=");
				object.query.put(pair[0], pair[1]);
			}
		}
		object.fragment = matcher.group("fragment");
		return object;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(scheme).append("://");
		if (null != user) sb.append(user).append("@");
		sb.append(host);
		if (port > 0) sb.append(":").append(port);
		for (final String seg : this.path) {
			sb.append("/").append(seg);
		}
		if (query.size() > 0) {
			sb.append("?");
			final StringJoiner joiner = new StringJoiner("&");
			for (String key : query.keySet()) {
				joiner.add(key + "=" + query.get(key));
			}
			sb.append(joiner);
		}
		if (null != fragment) sb.append("#").append(fragment);
		return sb.toString();
	}


	public URI toURI() {
		return URI.create(toString());
	}
}
