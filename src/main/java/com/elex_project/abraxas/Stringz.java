/*
 * Project Abraxas (a.k.a core-utils)
 * http://www.elex-project.com/
 * Copyright (c) 2017. Elex. All Rights Reserved.
 */

package com.elex_project.abraxas;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 문자열 처리 유틸리티
 * Created by Elex on 2016-06-27.
 *
 * @author Elex
 */
@Slf4j
public final class Stringz {

	/**
	 * {@value}
	 */
	public static final String UTF_8 = "UTF-8";
	/**
	 * {@value}
	 */
	public static final String UTF_16 = "UTF-16";
	/**
	 * {@value}
	 */
	public static final String ASCII = "US-ASCII";
	/**
	 * empty string
	 */
	public static final String EMPTY_STRING = "";
	/*
	 * "UTF-8", 안드로이드 19 이상에서 가능.
	 */
	//public static final Charset UTF_8 = StandardCharsets.UTF_8;
	/**
	 * "\n"
	 */
	public static final CharSequence NEW_LINE = "\n";
	public static final CharSequence SPACE = " ";
	/**
	 * {@value}
	 */
	public static final String UNKNOWN = "알 수 없음";
	private static final Pattern PATTERN_EMAIL_ADDRESS
			= Pattern.compile(
			"[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
					"\\@" +
					"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
					"(" +
					"\\." +
					"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
					")+"
	);
	private static final Pattern PATTERN_BINARY = Pattern.compile("^[01]*$");
	private static final Pattern PATTERN_HEX = Pattern.compile("^[0-9a-fA-F]*$");


	private Stringz() {
	}

	public static Charset utf8() {
		try {
			return StandardCharsets.UTF_8;
		} catch (NoClassDefFoundError e) {
			return Charset.forName(UTF_8);
		}
	}

	public static Charset utf16() {
		try {
			return StandardCharsets.UTF_16;
		} catch (NoClassDefFoundError e) {
			return Charset.forName(UTF_16);
		}
	}

	public static Charset ascii() {
		try {
			return StandardCharsets.US_ASCII;
		} catch (NoClassDefFoundError e) {
			return Charset.forName(ASCII);
		}
	}

	/**
	 * 문자열을 바이트 배열로 변환한다. utf-8을 사용한다.
	 * 인코딩 미지원 예외시에는 빈 배열을 반환.
	 *
	 * @param s 문자열
	 * @return 바이트배열.
	 */

	public static byte @NotNull [] toBytes(@NotNull final String s) {
		try {
			return s.getBytes(StandardCharsets.UTF_8);
		} catch (NoClassDefFoundError e) {
			try {
				return s.getBytes(UTF_8);
			} catch (UnsupportedEncodingException e1) {
				return Bytez.EMPTY_BYTES;
			}
		}
	}

	/**
	 * 바이트 배열을 문자열로 변환. UTF-8 인코딩을 사용.
	 * 인코딩 미지원 예외시에는 빈 문자열을 반환.
	 *
	 * @param b 바이트 배열
	 * @return 문자열.
	 */
	public static String fromBytes(final byte @NotNull [] b) {
		try {
			return new String(b, StandardCharsets.UTF_8);
		} catch (NoClassDefFoundError e) {
			try {
				return new String(b, UTF_8);
			} catch (UnsupportedEncodingException e1) {
				return EMPTY_STRING;
			}
		}
	}

	/**
	 * 문자열 배열을 콤마로 구분된 문자열로 변환한다.
	 *
	 * @param stringArray 문자열 배열
	 * @return 콤마로 구분된 문자열
	 */
	public static String toCommaSeparatedString(@NotNull final String[] stringArray) {
		return join(", ", stringArray);
	}

	public static String capitalizeFirstLetter(final @Nullable String str) {
		if (isEmpty(str)) {
			return str;
		}
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public static String capitalizeFirstLetter(final @Nullable String str, final @NotNull Locale locale) {
		if (isEmpty(str)) {
			return str;
		}
		return str.substring(0, 1).toUpperCase(locale) + str.substring(1);
	}

	public static <K, V> String join(final @NotNull CharSequence c1, final @NotNull CharSequence c2, final @Nullable Map<K, V> map) {
		try {
			StringJoiner joiner = new StringJoiner(c1);
			for (K key : map.keySet()) {
				joiner.add(join(c2, key, map.get(key)));
			}
			return joiner.toString();

		} catch (NoClassDefFoundError e) {
			StringBuilder sb = new StringBuilder();
			for (K key : map.keySet()) {
				sb.append(join(c2, key, map.get(key))).append(c1);
			}
			sb.delete(sb.length() - c1.length(), sb.length());
			return sb.toString();
		}

	}

	public static <T> String join(final @NotNull CharSequence c, final @Nullable List<T> list) {
		if (null == list || list.size() == 0) {
			return Stringz.EMPTY_STRING;
		} else if (list.size() == 1) {
			if (list.get(0) instanceof CharSequence) {
				return list.get(0).toString();
			} else if (list.get(0) instanceof Number
					|| list.get(0) instanceof Boolean
					|| list.get(0) instanceof Character) {
				return String.valueOf(list.get(0));
			} else {
				return list.get(0).toString();
			}

		} else {
			try {
				// StringJoiner is available in Java 8+.
				StringJoiner sj = new StringJoiner(c);
				for (T s : list) {
					if (s instanceof CharSequence) {
						sj.add((CharSequence) s);
					} else if (s instanceof Number
							|| s instanceof Boolean
							|| s instanceof Character) {
						sj.add(String.valueOf(s));
					} else {
						sj.add(s.toString());
					}
				}
				return sj.toString();

			} catch (NoClassDefFoundError e) {
				StringBuilder sb = new StringBuilder();
				for (T s : list) {
					if (s instanceof CharSequence) {
						sb.append((CharSequence) s).append(c);
					} else if (s instanceof Number
							|| s instanceof Boolean
							|| s instanceof Character) {
						sb.append(String.valueOf(s)).append(c);
					} else {
						sb.append(s.toString()).append(c);
					}
				}
				sb.delete(sb.length() - c.length(), sb.length());

				return sb.toString();
			}
		}
	}

	public static <T> String join(final char c, final @Nullable List<T> list) {
		return join(String.valueOf(c), list);
	}

	public static <T> String join(final @NotNull CharSequence c, @Nullable final Set<T> set) {
		if (null == set || set.size() == 0) {
			return Stringz.EMPTY_STRING;
		} else {
			try {
				// StringJoiner is available in Java 8+.
				StringJoiner sj = new StringJoiner(c);
				for (T s : set) {
					if (s instanceof CharSequence) {
						sj.add((CharSequence) s);
					} else if (s instanceof Number
							|| s instanceof Boolean
							|| s instanceof Character) {
						sj.add(String.valueOf(s));
					} else {
						sj.add(s.toString());
					}
				}
				return sj.toString();

			} catch (NoClassDefFoundError e) {
				StringBuilder sb = new StringBuilder();
				for (T s : set) {
					if (s instanceof CharSequence) {
						sb.append((CharSequence) s).append(c);
					} else if (s instanceof Number
							|| s instanceof Boolean
							|| s instanceof Character) {
						sb.append(String.valueOf(s)).append(c);
					} else {
						sb.append(s.toString()).append(c);
					}
				}
				sb.delete(sb.length() - c.length(), sb.length());

				return sb.toString();
			}
		}
	}

	public static <T> String join(final char c, final @Nullable Set<T> set) {
		return join(String.valueOf(c), set);
	}

	/**
	 * 배열의 각 항목을 결합.
	 *
	 * @param c           구분자
	 * @param array 배열. 널을 입력받으면, 널을 반환.
	 * @return 문자열
	 */
	public static <T> String join(final @NotNull CharSequence c, final @NotNull T... array) {
		if (null == array || array.length == 0) {
			return Stringz.EMPTY_STRING;
		} else if (array.length == 1) {
			if (array[0] instanceof CharSequence) {
				return array[0].toString();
			} else if (array[0] instanceof Number
					|| array[0] instanceof Boolean
					|| array[0] instanceof Character) {
				return String.valueOf(array[0]);
			} else {
				return array[0].toString();
			}
		} else {
			try {
				// StringJoiner is available in Java 8+.
				StringJoiner sj = new StringJoiner(c);
				for (T s : array) {
					if (s instanceof CharSequence) {
						sj.add((CharSequence) s);
					} else if (s instanceof Number
							|| s instanceof Boolean
							|| s instanceof Character) {
						sj.add(String.valueOf(s));
					} else {
						sj.add(s.toString());
					}
				}
				return sj.toString();

			} catch (NoClassDefFoundError e) {
				StringBuilder sb = new StringBuilder();
				for (T s : array) {
					if (s instanceof CharSequence) {
						sb.append((CharSequence) s).append(c);
					} else if (s instanceof Number
							|| s instanceof Boolean
							|| s instanceof Character) {
						sb.append(String.valueOf(s)).append(c);
					} else {
						sb.append(s.toString()).append(c);
					}
				}
				sb.delete(sb.length() - c.length(), sb.length());

				return sb.toString();
			}

		}
	}

	public static <T> String join(final char c, final @NotNull T... array) {
		return join(String.valueOf(c), array);
	}

	public static boolean contains(final @NotNull String str, final @NotNull CharSequence ch) {
		return str.contains(ch);
	}

	/**
	 * 널 혹은, 트림한 결과 문자열 길이가 0이라면 true
	 *
	 * @param str 문자열
	 * @return 널 혹은, 트림한 결과 문자열 길이가 0이라면 true
	 */
	@Contract("null -> true")
	public static boolean isEmpty(@Nullable final String str) {
		return (null == str || str.trim().length() == 0);
	}

	/**
	 * Sample :
	 * 34556372-2fef-4be1-9aab-bdcfd9bdec0b
	 *
	 * @return 문자열
	 * @see UUID#randomUUID()
	 */
	@NotNull
	public static String generateUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 유효한 이메일 주소인가, 널 체크 포함됨
	 *
	 * @param emailAddress 이메일 주소
	 * @return 유효한 이메일 주소면 참.
	 */
	public static boolean isValidEmail(@NotNull final String emailAddress) {
		return !isEmpty(emailAddress) && PATTERN_EMAIL_ADDRESS.matcher(emailAddress).matches();
	}

	/**
	 * 문자열의 각 문자 순서를 뒤집는다.
	 *
	 * @param str 문자열
	 * @return 역순으로된 문자열
	 */
	@NotNull
	public static String reverse(@NotNull final String str) {
		/*StringBuilder stringBuilder = new StringBuilder();
		char[] a = str.toCharArray();
		for (int i = a.length - 1; i >= 0; i--) {
			stringBuilder.append(a[i]);
		}
		return stringBuilder.toString();*/
		return new StringBuilder(str).reverse().toString();
	}

	/**
	 * Extract char[] from a string
	 *
	 * @param s       string
	 * @param start   from
	 * @param end     to
	 * @param dest    output
	 * @param destoff offset
	 */
	public static void getChars(@NotNull final CharSequence s, final int start, final int end, final char @NotNull [] dest, int destoff) {
		Class<? extends CharSequence> c = s.getClass();

		if (c == String.class)
			((String) s).getChars(start, end, dest, destoff);
		else if (c == StringBuffer.class)
			((StringBuffer) s).getChars(start, end, dest, destoff);
		else if (c == StringBuilder.class)
			((StringBuilder) s).getChars(start, end, dest, destoff);
		else {
			for (int i = start; i < end; i++)
				dest[destoff++] = s.charAt(i);
		}
	}


	public static boolean isBinary(@Nullable final String str) {
		return (!isEmpty(str)
				&& str.length() % 8 == 0
				&& PATTERN_BINARY.matcher(str).matches());
	}

	public static boolean isHex(@Nullable final String str) {
		return (!isEmpty(str)
				&& str.length() % 2 == 0
				&& PATTERN_HEX.matcher(str).matches());
	}

	/**
	 * Returns whether the given CharSequence contains any printable characters.
	 *
	 * @param str ..
	 * @return ..
	 */
	public static boolean isGraphic(@NotNull final CharSequence str) {
		final int len = str.length();
		for (int cp, i = 0; i < len; i += Character.charCount(cp)) {
			cp = Character.codePointAt(str, i);
			int gc = Character.getType(cp);
			if (gc != Character.CONTROL
					&& gc != Character.FORMAT
					&& gc != Character.SURROGATE
					&& gc != Character.UNASSIGNED
					&& gc != Character.LINE_SEPARATOR
					&& gc != Character.PARAGRAPH_SEPARATOR
					&& gc != Character.SPACE_SEPARATOR) {
				return true;
			}
		}
		return false;
	}

	public static boolean isDigitsOnly(@NotNull final CharSequence str) {
		final int len = str.length();
		for (int cp, i = 0; i < len; i += Character.charCount(cp)) {
			cp = Character.codePointAt(str, i);
			if (!Character.isDigit(cp)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isPrintableAscii(final char c) {
		final int asciiFirst = 0x20;
		final int asciiLast = 0x7E;  // included
		return (asciiFirst <= c && c <= asciiLast) || c == '\r' || c == '\n';
	}

	public static boolean isPrintableAsciiOnly(@NotNull final CharSequence str) {
		final int len = str.length();
		for (int i = 0; i < len; i++) {
			if (!isPrintableAscii(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNumber(final @Nullable String str) {
		if (isEmpty(str)) return false;

		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isAllLowerCase(final @Nullable String str) {
		if (isEmpty(str)) {
			return false;
		}
		final int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isLowerCase(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isAllUpperCase(final @Nullable String str) {
		if (isEmpty(str)) {
			return false;
		}
		final int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isUpperCase(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Does a comma-delimited list 'delimitedString' contain a certain item?
	 * (without allocating memory)
	 *
	 * @param delimitedString ..
	 * @param delimiter       ..
	 * @param item            ..
	 * @return ..
	 */
	public static boolean delimitedStringContains(final String delimitedString, final char delimiter, final String item) {
		if (isEmpty(delimitedString) || isEmpty(item)) {
			return false;
		}

		int pos = -1;
		int length = delimitedString.length();
		while ((pos = delimitedString.indexOf(item, pos + 1)) != -1) {
			if (pos > 0 && delimitedString.charAt(pos - 1) != delimiter) {
				continue;
			}
			int expectedDelimiterPos = pos + item.length();
			if (expectedDelimiterPos == length) {
				// Match at end of string.
				return true;
			}
			if (delimitedString.charAt(expectedDelimiterPos) == delimiter) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 둘 이상의 공백을 하나로 합친다.
	 *
	 * @param src 원본 문자열.
	 * @return 공백이 압축된 문자열
	 */
	@NotNull
	public static String pack(@NotNull final String src) {
		return src.replaceAll("\\s+", SPACE.toString());
	}

	@Contract(pure = true)
	public static boolean startsWithOneOf(String string, @NotNull String... prefixes) {
		for (String prefix : prefixes) {
			if (string.startsWith(prefix)) {
				return true;
			}
		}

		return false;
	}

	@Contract(pure = true)
	public static boolean endsWithOneOf(String string, @NotNull String... prefixes) {
		for (String prefix : prefixes) {
			if (string.endsWith(prefix)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * XML to String
	 *
	 * @param doc XML Document
	 * @return String
	 */
	public static String xmlToString(@NotNull Document doc) {
		StringWriter sw = new StringWriter();
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, Stringz.UTF_8);

			transformer.transform(new DOMSource(doc), new StreamResult(sw));
		} catch (TransformerException ignore) {

		}

		return sw.toString();
	}

	public static String xmlToSimpleString(@NotNull Document doc) {
		StringWriter sw = new StringWriter();
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "no");
			transformer.setOutputProperty(OutputKeys.ENCODING, Stringz.UTF_8);

			transformer.transform(new DOMSource(doc), new StreamResult(sw));
		} catch (TransformerException ignore) {

		}

		return sw.toString();
	}

	public static String encodeToBase64(byte @NotNull [] src) {
		return Bytez.toBase64(src);
	}

	public static byte[] decodeFromBase64(@NotNull String src) {
		return Bytez.fromBase64(src);
	}

	public static Formatter formatter(final @NotNull String format, final Object... args) {
		return new Formatter().format(format, args);
	}

	/**
	 * 형식화 문자열
	 * 매개 변수를 삽입할 위치를 {}로 지정한다.
	 * @param format "{}"
	 * @param args 전달할 매개변수
	 * @return 문자열
	 */
	public static String format(final @NotNull String format, final Object... args) {
		final StringBuilder sb = new StringBuilder();
		final Matcher matcher = Pattern.compile("\\{}").matcher(format);
		int start = 0;
		int idx = 0;
		try {
			while (matcher.find()) {
				int from = matcher.start();
				int to = matcher.end();
				sb.append(format, start, from);
				if (null==args[idx]) {
					// do nothing
				} else if (args[idx] instanceof CharSequence) {
					sb.append(args[idx]);
				} else if (args[idx] instanceof Number) {
					sb.append(args[idx]);
				} else if (args[idx] instanceof byte[]) {
					sb.append(Bytez.toHex(":", (byte[])args[idx]));
				}else {
					sb.append(args[idx].toString());
				}
				start = to;
				idx++;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException(e);
		}
		sb.append(format, start, format.length());
		return sb.toString();
	}

	public static int countCharsIn(char ch, @NotNull String input) {
		int cnt = 0;
		for (int i = 0; i < input.length(); i++) {
			if (ch == input.charAt(i)) {
				cnt++;
			}
		}
		return cnt;
	}

}
