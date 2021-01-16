/*
 * Project Abraxas (a.k.a core-utils)
 * http://www.elex-project.com/
 * Copyright (c) 2017. Elex. All Rights Reserved.
 */

package com.elex_project.abraxas;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Random
 *
 * @author Elex
 */
public final class Random {
	private static final java.util.Random RANDOM;

	static {
		RANDOM = new java.util.Random();
	}

	private Random() {
	}

	public static void setSeed(long seed) {
		RANDOM.setSeed(seed);
	}

	public static void setSeed() {
		setSeed(Timez.currentTime());
	}

	public static void nextBytes(byte[] bytes) {
		RANDOM.nextBytes(bytes);
	}

	public static int nextInt() {
		return RANDOM.nextInt();
	}

	public static long nextLong() {
		return RANDOM.nextLong();
	}

	public static boolean nextBoolean() {
		return RANDOM.nextBoolean();
	}

	public static float nextFloat() {
		return RANDOM.nextFloat();
	}

	public static double nextDouble() {
		return RANDOM.nextDouble();
	}

	public static synchronized double nextGaussian() {
		return RANDOM.nextGaussian();
	}


	/**
	 * 임의의 바이트 배열
	 *
	 * @param size 배열의 길이
	 * @return 바이트 배열
	 */
	@NotNull
	public static byte[] nextBytes(int size) {
		byte[] bytes = new byte[size];
		nextBytes(bytes);
		return bytes;
	}

	public static byte nextByte() {
		return nextBytes(1)[0];
	}

	/**
	 * 0 ~ numOfCase-1 사이의 정수값
	 *
	 * @param numOfCase 경우의 수
	 * @return 정수
	 */
	public static int nextInt(int numOfCase) {
		return RANDOM.nextInt(numOfCase);
	}

	/**
	 * min ~ max 사이의 정수값
	 *
	 * @param min
	 * @param max
	 * @return
	 */
	public static int nextInt(final int min, final int max) {
		return nextInt(max - min + 1) + min;
	}

	public static int nextInt(final int min, final int max, @NotNull final int... except) {
		int num;
		do {
			num = nextInt(min, max);

		} while (Numberz.contains(except, num));

		return num;
	}

	public static <T> T nextObject(@NotNull T[] array) {
		return array[nextInt(array.length)];
	}

	public static <T> T nextObject(@NotNull List<T> list) {
		return list.get(nextInt(list.size()));
	}

	/**
	 * 아스키 문자 33~126까지 중 하나
	 *
	 * @return 임의의 문자
	 */
	public static char nextAscii() {
		return (char) nextInt(33, 126);
	}

	public static char nextChar() {
		return (char) nextInt();
	}

	@NotNull
	public static String nextChars(final int length) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(nextAscii());
		}
		return sb.toString();
	}

	/**
	 * 주어진 문자집합 중에서 임의의 문자만으로 구성된, 주어진 길이의 문자열을 반환한다.
	 *
	 * @param length
	 * @param seed   문자집합
	 * @return
	 */
	@NotNull
	public static String nextChars(final int length, @NotNull final String seed) {
		final char[] chars = seed.toCharArray();
		final StringBuilder sb = new StringBuilder();
		while (sb.length() < length) {
			sb.append(chars[nextInt(chars.length)]);
		}

		return sb.toString();
	}

	public static char nextCharAlphaNumeric() {
		char code;
		do {
			code = nextAscii();
		} while (!Character.isLetterOrDigit(code));
		return code;
	}

	public static char nextCharByPattern(final Pattern pattern) {
		char code;
		do {
			code = nextChar();
		} while (!pattern.matcher(String.valueOf(code)).matches());
		return code;
	}

	@NotNull
	public static String nextCharsAlphaNumeric(final int length) {
		final StringBuilder sb = new StringBuilder();
		while (sb.length() < length) {
			sb.append(nextCharAlphaNumeric());
		}
		return sb.toString();
	}

	@NotNull
	public static String nextCharsByPattern(final Pattern pattern, final int length) {
		final StringBuilder sb = new StringBuilder();
		while (sb.length() < length) {
			sb.append(nextCharByPattern(pattern));
		}
		return sb.toString();
	}
}
