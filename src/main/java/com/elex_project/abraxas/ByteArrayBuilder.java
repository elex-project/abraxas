/*
 * Project Abraxas (a.k.a core-utils)
 * http://www.elex-project.com/
 * Copyright (c) 2018. Elex. All Rights Reserved.
 */

package com.elex_project.abraxas;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Byte Array Builder
 *
 * @author Elex
 */
public final class ByteArrayBuilder implements Appendable {
	private final ByteArrayOutputStream buffer;

	public ByteArrayBuilder() {
		buffer = new ByteArrayOutputStream();
	}

	public ByteArrayBuilder append(final byte b) {
		buffer.write(b);
		return this;
	}

	/**
	 * @param b    바이트 값
	 * @param size 추가할 바이트 갯수
	 * @return
	 */
	public ByteArrayBuilder append(final byte b, final int size) {
		for (int i = 0; i < size; i++) {
			buffer.write(b);
		}
		return this;
	}

	public ByteArrayBuilder append(final byte[] b) {
		if (null == b) return this;

		buffer.write(b, 0, b.length);
		return this;
	}

	public ByteArrayBuilder append(final byte[] b, final int from, final int to) {
		if (null == b) return this;

		buffer.write(b, from, to);
		return this;
	}

	public ByteArrayBuilder append(final short s) {
		return append(Numberz.toBytes(s), 0, 2);
	}

	public ByteArrayBuilder append(final int i) {
		return append(Numberz.toBytes(i), 0, 4);
	}

	public ByteArrayBuilder append(final long l) {
		return append(Numberz.toBytes(l), 0, 8);
	}

	public ByteArrayBuilder appendHex(@NotNull final String hexStr) {
		final byte[] a = Bytez.fromHex(hexStr);
		if (null != a) {
			return append(a);
		} else {
			return this;
		}
	}

	public ByteArrayBuilder append(@NotNull final String str, final String charset) throws UnsupportedEncodingException {
		return append(str.getBytes(charset));
	}

	public ByteArrayBuilder append(@NotNull final String str, final Charset charset) {
		return append(str.getBytes(charset));
	}

	public ByteArrayBuilder append(@NotNull final String str) {
		byte[] a = new byte[0];
		try {
			a = str.getBytes(StandardCharsets.UTF_8);
		} catch (NoClassDefFoundError e) {
			try {
				a = str.getBytes(Stringz.UTF_8);
			} catch (UnsupportedEncodingException ex) {
				//
			}
		}
		return append(a);
	}

	public int size() {
		return buffer.size();
	}

	public void reset() {
		buffer.reset();
	}


	public byte @NotNull [] toByteArray() {
		return buffer.toByteArray();
	}

	public void writeTo(final OutputStream outputStream) throws IOException {
		buffer.writeTo(outputStream);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof ByteArrayBuilder) {
			return Arrayz.equals(this.toByteArray(), ((ByteArrayBuilder) obj).toByteArray());
		} else {
			return super.equals(obj);
		}
	}

	@Override
	public ByteArrayBuilder append(final CharSequence charSequence) {
		return append(charSequence, 0, charSequence.length());
	}

	@Override
	public ByteArrayBuilder append(final CharSequence charSequence, final int i, final int i1) {
		for (int x = i; x < i1; x++) {
			buffer.write(charSequence.charAt(x));
		}
		return this;
	}

	@Override
	public ByteArrayBuilder append(final char c) {
		buffer.write(c);
		return this;
	}
}
