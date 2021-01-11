/*
 * Project Abraxas (a.k.a core-utils)
 * http://www.elex-project.com/
 * Copyright (c) 2017. Elex. All Rights Reserved.
 */

package com.elex_project.abraxas;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

/**
 * 숫자 유틸리티
 * <p>
 * Created by Elex on 2016-06-28.
 *
 * @author Elex
 */
public final class Numberz {
	public static final int BYTE_SIZE = 1;
	public static final int SHORT_SIZE = Short.SIZE / Byte.SIZE;
	public static final int INT_SIZE = Integer.SIZE / Byte.SIZE;
	public static final int LONG_SIZE = Long.SIZE / Byte.SIZE;

	private Numberz() {
	}

	/**
	 * 반전
	 *
	 * @param val
	 * @return
	 */
	public static byte flip(final byte val) {
		return (byte) (~val);
	}

	/**
	 * 반전
	 *
	 * @param val
	 * @return
	 */
	public static short flip(final short val) {
		return (short) (~val);
	}

	/**
	 * 반전
	 *
	 * @param val
	 * @return
	 */
	public static int flip(final int val) {
		return ~val;
	}

	/**
	 * 반전
	 *
	 * @param val
	 * @return
	 */
	public static long flip(final long val) {
		return ~val;
	}

	/**
	 * 정수를 바이트배열로 변환
	 *
	 * @param i 정수
	 * @return 바이트 배열
	 */
	@NotNull
	@Contract(pure = true)
	public static byte[] toBytes(final int i) {
		byte[] result = new byte[INT_SIZE];

		result[0] = (byte) (i >> 24);
		result[1] = (byte) (i >> 16);
		result[2] = (byte) (i >> 8);
		result[3] = (byte) (i /*>> 0*/);

		return result;
	}


	/**
	 * 바이트 배열을 정수로
	 *
	 * @param bytes 배열
	 * @return 정수
	 */
	public static int toInt(final byte[] bytes) throws IllegalArgumentException {
		if (bytes.length != INT_SIZE) throw new IllegalArgumentException();

		return bytes[0] << 24 |
				(bytes[1] & 0xFF) << 16 |
				(bytes[2] & 0xFF) << 8 |
				(bytes[3] & 0xFF);
	}

	/**
	 * 바이트 배열을 정수로
	 *
	 * @param bytes 8바이트 배열
	 * @return 정수
	 */
	@Contract(pure = true)
	public static long toLong(@NotNull final byte[] bytes) throws IllegalArgumentException {
		if (bytes.length != LONG_SIZE) throw new IllegalArgumentException();

		return (long) bytes[0] << 56 |
				(long) (bytes[1] & 0xFF) << 48 |
				(long) (bytes[2] & 0xFF) << 40 |
				(long) (bytes[3] & 0xFF) << 32 |
				(long) (bytes[4] & 0xFF) << 24 |
				(long) (bytes[5] & 0xFF) << 16 |
				(long) (bytes[6] & 0xFF) << 8 |
				(long) (bytes[7] & 0xFF);
	}

	/**
	 * 바이트 배열의 일부를 정수화 시킨다.
	 *
	 * @param array
	 * @param startIdx
	 * @param size     1 ~ 4
	 * @return
	 * @throws IllegalArgumentException  size는 1 ~ 4만 사용할 것.
	 * @throws IndexOutOfBoundsException 배열의 인덱스를 넘어서지 않도록 주의할 것.
	 */
	public static int toInt(final byte[] array, final int startIdx, final int size)
			throws IllegalArgumentException, IndexOutOfBoundsException {
		if (size == 4) {
			return array[startIdx] << 24 |
					(array[startIdx + 1] & 0xFF) << 16 |
					(array[startIdx + 2] & 0xFF) << 8 |
					(array[startIdx + 3] & 0xFF);
		} else if (size == 3) {
			return (array[startIdx]) << 16 |
					(array[startIdx + 1] & 0xFF) << 8 |
					(array[startIdx + 2] & 0xFF);
		} else if (size == 2) {
			return (array[startIdx]) << 8 |
					(array[startIdx + 1] & 0xFF);
		} else if (size == 1) {
			return (array[startIdx]);
		} else {
			throw new IllegalArgumentException();
		}

	}

	/**
	 * 바이트 배열의 일부를 정수화 시킨다.
	 *
	 * @param array
	 * @param startIdx
	 * @param size     1 ~ 8
	 * @return
	 * @throws IllegalArgumentException  size는 1 ~ 8만 사용할 것.
	 * @throws IndexOutOfBoundsException 배열의 인덱스를 넘어서지 않도록 주의할 것.
	 */
	public static long toLong(final byte[] array, final int startIdx, final int size)
			throws IllegalArgumentException, IndexOutOfBoundsException {
		if (size == 8) {
			return (long) array[startIdx] << 56 |
					(long) (array[startIdx + 1] & 0xFF) << 48 |
					(long) (array[startIdx + 2] & 0xFF) << 40 |
					(long) (array[startIdx + 3] & 0xFF) << 32 |
					(long) (array[startIdx + 4] & 0xFF) << 24 |
					(long) (array[startIdx + 5] & 0xFF) << 16 |
					(long) (array[startIdx + 6] & 0xFF) << 8 |
					(long) (array[startIdx + 7] & 0xFF);
		} else if (size == 7) {
			return (long) (array[startIdx]) << 48 |
					(long) (array[startIdx + 1] & 0xFF) << 40 |
					(long) (array[startIdx + 2] & 0xFF) << 32 |
					(long) (array[startIdx + 3] & 0xFF) << 24 |
					(long) (array[startIdx + 4] & 0xFF) << 16 |
					(long) (array[startIdx + 5] & 0xFF) << 8 |
					(long) (array[startIdx + 6] & 0xFF);
		} else if (size == 6) {
			return (long) (array[startIdx]) << 40 |
					(long) (array[startIdx + 1] & 0xFF) << 32 |
					(long) (array[startIdx + 2] & 0xFF) << 24 |
					(long) (array[startIdx + 3] & 0xFF) << 16 |
					(long) (array[startIdx + 4] & 0xFF) << 8 |
					(long) (array[startIdx + 5] & 0xFF);
		} else if (size == 5) {
			return (long) (array[startIdx]) << 32 |
					(long) (array[startIdx + 1] & 0xFF) << 24 |
					(long) (array[startIdx + 2] & 0xFF) << 16 |
					(long) (array[startIdx + 3] & 0xFF) << 8 |
					(long) (array[startIdx + 4] & 0xFF);
		} else if (size == 4) {
			return (long) (array[startIdx]) << 24 |
					(long) (array[startIdx + 1] & 0xFF) << 16 |
					(long) (array[startIdx + 2] & 0xFF) << 8 |
					(long) (array[startIdx + 3] & 0xFF);
		} else if (size == 3) {
			return (long) (array[startIdx]) << 16 |
					(long) (array[startIdx + 1] & 0xFF) << 8 |
					(long) (array[startIdx + 2] & 0xFF);
		} else if (size == 2) {
			return (long) (array[startIdx]) << 8 |
					(long) (array[startIdx + 1] & 0xFF);
		} else if (size == 1) {
			return (long) (array[startIdx] & 0xFF);
		} else {
			throw new IllegalArgumentException();
		}

	}

	/**
	 * 정수를 바이트배열로 변환
	 *
	 * @param i 정수
	 * @return 바이트 배열
	 */
	@NotNull
	@Contract(pure = true)
	public static byte[] toBytes(final long i) {
		byte[] result = new byte[LONG_SIZE];

		result[0] = (byte) (i >> 56);
		result[1] = (byte) (i >> 48);
		result[2] = (byte) (i >> 40);
		result[3] = (byte) (i >> 32);
		result[4] = (byte) (i >> 24);
		result[5] = (byte) (i >> 16);
		result[6] = (byte) (i >> 8);
		result[7] = (byte) (i /*>> 0*/);

		return result;
	}

	/**
	 * 정수를 바이트배열로 변환
	 *
	 * @param i 정수
	 * @return 바이트 배열
	 */
	@NotNull
	@Contract(pure = true)
	public static byte[] toBytes(final short i) {
		byte[] result = new byte[SHORT_SIZE];

		result[0] = (byte) (i >> 8);
		result[1] = (byte) (i /*>> 0*/);

		return result;
	}

	@NotNull
	@Contract(value = "_ -> new", pure = true)
	public static byte[] toBytes(final byte i) {
		return new byte[]{i};
	}

	/**
	 * 바이트 배열을 정수로
	 *
	 * @param bytes 4바이트 배열
	 * @return 정수
	 */
	public static short toShort(@NotNull final byte[] bytes) {
		if (bytes.length == SHORT_SIZE) {
			return toShort(bytes[0], bytes[1]);
		} else if (bytes.length == 1) {
			return toShort((byte) 0x00, bytes[0]);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public static short toShort(final byte high, final byte low) {
		return (short) ((high & 0xFF) << 8 |
				(low & 0xFF));
	}

	@NotNull
	public static byte[] toBytes(final float f) {
		return toBytes(toInt(f));
	}

	@NotNull
	public static byte[] toBytes(final double d) {
		return toBytes(toLong(d));
	}

	/**
	 * 부동소수의 비트필드를 정수값으로 반환
	 *
	 * @param f
	 * @return
	 */
	public static int toInt(final float f) {
		return Float.floatToIntBits(f);
	}

	/**
	 * 부동소수의 비트필드를 정수값으로 반환
	 *
	 * @param d
	 * @return
	 */
	public static long toLong(final double d) {
		return Double.doubleToLongBits(d);
	}

	/**
	 * 정수 형태의 비트필드를 부동소수로 반환
	 *
	 * @param intAsBits
	 * @return
	 */
	public static float toFloat(final int intAsBits) {
		return Float.intBitsToFloat(intAsBits);
	}

	/**
	 * 정수 형태의 비트필드를 부동소수로 반환
	 *
	 * @param longAsBits
	 * @return
	 */
	public static double toDouble(final long longAsBits) {
		return Double.longBitsToDouble(longAsBits);
	}

	public static float toFloat(final byte[] array, final int startIdx, final int size) {
		return Float.intBitsToFloat(toInt(array, startIdx, size));
	}

	public static float toFloat(final byte[] array) {
		return toFloat(array, 0, 4);
	}

	public static double toDouble(final byte[] array, final int startIdx, final int size) {
		return Double.longBitsToDouble(toLong(array, startIdx, size));
	}

	public static double toDouble(final byte[] array) {
		return toDouble(array, 0, 8);
	}

	@NotNull
	public static String toHexString(final byte i) {
		return Bytez.toHex(i);
	}
	/**
	 * 16진 표기로 변환
	 *
	 * @param i 정수
	 * @return 문자열
	 */
	@NotNull
	public static String toHexString(final short i) {
		return Bytez.toHex(toBytes(i));
	}

	/**
	 * 16진 표기로 변환
	 *
	 * @param i 정수
	 * @return 문자열
	 */
	@NotNull
	public static String toHexString(final int i) {
		return Bytez.toHex(toBytes(i));
	}

	/**
	 * 16진 표기로 변환
	 *
	 * @param i 정수
	 * @return 문자열
	 */
	@NotNull
	public static String toHexString(final long i) {
		return Bytez.toHex(toBytes(i));
	}

	/**
	 * 2진 표기로 변환
	 *
	 * @param i 정수
	 * @return 문자열
	 */
	@NotNull
	@Contract(pure = true)
	public static String toBinaryString(final int i) {
		return Integer.toBinaryString(i);
	}

	@NotNull
	public static String toBinaryStringPadded(final int i) {
		char[] str = new char[INT_SIZE * 8];
		//Arrays.fill(str, '0');
		//String cnv = Integer.toBinaryString(i);
		for (int n = 0; n < str.length; n++) {
			str[str.length - 1 - n] = (Bitz.isSet(i, n)) ? '1' : '0';
		}
		return String.valueOf(str);
	}

	@NotNull
	public static String toBinaryStringPadded(final short i) {
		char[] str = new char[SHORT_SIZE * 8];
		//Arrays.fill(str, '0');
		for (int n = 0; n < str.length; n++) {
			str[str.length - 1 - n] = (Bitz.isSet(i, n)) ? '1' : '0';
		}
		return String.valueOf(str);
	}

	@NotNull
	public static String toBinaryStringPadded(final byte i) {
		char[] str = new char[BYTE_SIZE * 8];
		//Arrays.fill(str, '0');
		for (int n = 0; n < str.length; n++) {
			str[str.length - 1 - n] = (Bitz.isSet(i, n)) ? '1' : '0';
		}
		return String.valueOf(str);
	}

	@NotNull
	public static String toBinaryStringPadded(final long i) {
		char[] str = new char[LONG_SIZE * 8];
		//Arrays.fill(str, '0');
		for (int n = 0; n < str.length; n++) {
			str[str.length - 1 - n] = (Bitz.isSet(i, n)) ? '1' : '0';
		}
		return String.valueOf(str);
	}

	public static String toOctalString(final int val){
		return Integer.toOctalString(val);
	}
	public static String toOctalString(final long val){
		return Long.toOctalString(val);
	}
	/**
	 * 2진 표기 문자열을 정수로 변환
	 *
	 * @param binaryFormat 0과 1의 문자열
	 * @return 정수
	 */
	public static int fromBinaryString(final String binaryFormat) {
		return Integer.parseUnsignedInt(binaryFormat, 2);
	}

	public static short fromShortBinaryString(final String binaryFormat) {
		return (short) fromBinaryString(binaryFormat);
	}

	public static byte fromByteBinaryString(final String binaryFormat) {
		return (byte) fromBinaryString(binaryFormat);
	}

	public static long fromLongBinaryString(final String binaryFormat) {
		return Long.parseUnsignedLong(binaryFormat, 2);
	}

	/**
	 * Pack 2 int values into a long, useful as a return value for a range
	 *
	 * @param end   ..
	 * @param start ..
	 * @return ..
	 * @see #unpackFirst(long)
	 * @see #unpackLast(long)
	 */
	public static long packIntoLong(final int start, final int end) {
		return (((long) start) << 32) | end;
	}

	/**
	 * Get the start value from a range packed in a long by {@link #packIntoLong(int, int)}
	 *
	 * @param range ..
	 * @return ...
	 * @see #unpackLast(long)
	 * @see #packIntoLong(int, int)
	 */
	public static int unpackFirst(final long range) {
		return (int) (range >>> 32);
	}

	/**
	 * Get the end value from a range packed in a long by {@link #packIntoLong(int, int)}
	 *
	 * @param range ..
	 * @return ..
	 * @see #unpackFirst(long)
	 * @see #packIntoLong(int, int)
	 */
	public static int unpackLast(final long range) {
		return (int) (range & 0x00000000FFFFFFFFL);
	}

	public static int compare(final int n1, final int n2) {
		if (n1 > n2) return 1;
		if (n1 < n2) return -1;
		return 0;
	}

	@Contract(pure = true)
	public static boolean contains(@NotNull final int[] array, final int number) {
		for (int i : array) {
			if (i == number) return true;
		}
		return false;
	}

	@Contract(pure = true)
	public static boolean contains(@NotNull final short[] array, final short number) {
		for (short i : array) {
			if (i == number) return true;
		}
		return false;
	}

	@Contract(pure = true)
	public static boolean contains(@NotNull final byte[] array, final byte number) {
		for (byte i : array) {
			if (i == number) return true;
		}
		return false;
	}

	public static boolean contains(@NotNull final long[] array, final long number) {
		for (long i : array) {
			if (i == number) return true;
		}
		return false;
	}
	/**
	 *
	 * @param value
	 * @return
	 * @since 1.8
	 */
	public static long toUnsigned(final int value) {
		//return ((long) value & 0xffffffffL);
		return Integer.toUnsignedLong(value);
	}

	/**
	 *
	 * @param value
	 * @return
	 * @since 1.8
	 */
	public static int toUnsigned(final short value) {
		return Short.toUnsignedInt(value);
	}
	/**
	 *
	 * @param value
	 * @return
	 * @since 1.8
	 */
	public static long toUnsignedLong(final short value) {
		return Short.toUnsignedLong(value);
	}
	/**
	 *
	 * @param value
	 * @return
	 * @since 1.8
	 */
	public static int toUnsigned(final byte value) {
		return Byte.toUnsignedInt(value);
	}
	/**
	 *
	 * @param value
	 * @return
	 * @since 1.8
	 */
	public static long toUnsignedLong(final byte value) {
		return Byte.toUnsignedLong(value);
	}

	public static <T extends Number> String join(final CharSequence c, final List<T> list) {
		if (null == list || list.size() == 0) {
			return Stringz.EMPTY_STRING;
		} else if (list.size() == 1) {
			return String.valueOf(list.get(0));
		} else {
			try {
				// StringJoiner is available in Java 8.
				StringJoiner sj = new StringJoiner(c);
				for (T s : list) {
					sj.add(String.valueOf(s));
				}
				return sj.toString();

			} catch (NoClassDefFoundError e) {
				StringBuilder sb = new StringBuilder();
				for (T s : list) {
					sb.append(s).append(c);
				}
				sb.delete(sb.length() - c.length(), sb.length());

				return sb.toString();
			}
		}
	}

	public static <T extends Number> String join(final char c, final List<T> list) {
		return join(String.valueOf(c), list);
	}

	public static <T extends Number> String join(final CharSequence c, final Set<T> set) {
		if (null == set || set.size() == 0) {
			return Stringz.EMPTY_STRING;
		} else {
			try {
				// StringJoiner is available in Java 8.
				StringJoiner sj = new StringJoiner(c);
				for (T s : set) {
					sj.add(String.valueOf(s));
				}
				return sj.toString();

			} catch (NoClassDefFoundError e) {
				StringBuilder sb = new StringBuilder();
				for (T s : set) {
					sb.append(s).append(c);
				}
				sb.delete(sb.length() - c.length(), sb.length());

				return sb.toString();
			}
		}
	}

	public static <T extends Number> String join(final char c, final Set<T> set) {
		return join(String.valueOf(c), set);
	}

	@Deprecated
	public static String toCommaSeparatedString(@NotNull final int... array) {
		return join(", ", array);
	}

	@Deprecated
	public static String toCommaSeparatedString(@NotNull final long... array) {
		return join(", ", array);
	}

	@Deprecated
	public static String toCommaSeparatedString(@NotNull final float... array) {
		return join(", ", array);
	}

	@Deprecated
	public static String toCommaSeparatedString(@NotNull final double... array) {
		return join(", ", array);
	}

	public static String join(final CharSequence c, final int... numberArray) {
		if (null == numberArray || numberArray.length == 0) {
			return Stringz.EMPTY_STRING;
		} else if (numberArray.length == 1) {
			return String.valueOf(numberArray[0]);
		} else {
			try {
				// StringJoiner is available in Java 8.
				StringJoiner sj = new StringJoiner(c);
				for (int s : numberArray) {
					sj.add(String.valueOf(s));
				}
				return sj.toString();

			} catch (NoClassDefFoundError e) {
				StringBuilder sb = new StringBuilder();
				for (int s : numberArray) {
					sb.append(s).append(c);
				}
				sb.delete(sb.length() - c.length(), sb.length());

				return sb.toString();
			}

		}
	}

	public static String join(final CharSequence c, final long... numberArray) {
		if (null == numberArray || numberArray.length == 0) {
			return Stringz.EMPTY_STRING;
		} else if (numberArray.length == 1) {
			return String.valueOf(numberArray[0]);
		} else {
			try {
				// StringJoiner is available in Java 8.
				StringJoiner sj = new StringJoiner(c);
				for (long s : numberArray) {
					sj.add(String.valueOf(s));
				}
				return sj.toString();

			} catch (NoClassDefFoundError e) {
				StringBuilder sb = new StringBuilder();
				for (long s : numberArray) {
					sb.append(s).append(c);
				}
				sb.delete(sb.length() - c.length(), sb.length());

				return sb.toString();
			}

		}
	}

	public static String join(final CharSequence c, final float... numberArray) {
		if (null == numberArray || numberArray.length == 0) {
			return Stringz.EMPTY_STRING;
		} else if (numberArray.length == 1) {
			return String.valueOf(numberArray[0]);
		} else {
			try {
				// StringJoiner is available in Java 8.
				StringJoiner sj = new StringJoiner(c);
				for (float s : numberArray) {
					sj.add(String.valueOf(s));
				}
				return sj.toString();

			} catch (NoClassDefFoundError e) {
				StringBuilder sb = new StringBuilder();
				for (float s : numberArray) {
					sb.append(s).append(c);
				}
				sb.delete(sb.length() - c.length(), sb.length());

				return sb.toString();
			}

		}
	}

	public static String join(final CharSequence c, final double... numberArray) {
		if (null == numberArray || numberArray.length == 0) {
			return Stringz.EMPTY_STRING;
		} else if (numberArray.length == 1) {
			return String.valueOf(numberArray[0]);
		} else {
			try {
				// StringJoiner is available in Java 8.
				StringJoiner sj = new StringJoiner(c);
				for (double s : numberArray) {
					sj.add(String.valueOf(s));
				}
				return sj.toString();

			} catch (NoClassDefFoundError e) {
				StringBuilder sb = new StringBuilder();
				for (double s : numberArray) {
					sb.append(s).append(c);
				}
				sb.delete(sb.length() - c.length(), sb.length());

				return sb.toString();
			}

		}
	}

	public static String join(final char c, final int... numberArray) {
		if (null == numberArray || numberArray.length == 0) {
			return Stringz.EMPTY_STRING;
		} else if (numberArray.length == 1) {
			return String.valueOf(numberArray[0]);
		} else {
			StringBuilder sb = new StringBuilder();
			for (int s : numberArray) {
				sb.append(s).append(c);
			}
			sb.delete(sb.length() - 1, sb.length());

			return sb.toString();

		}
	}

	public static String join(final char c, final long... numberArray) {
		if (null == numberArray || numberArray.length == 0) {
			return Stringz.EMPTY_STRING;
		} else if (numberArray.length == 1) {
			return String.valueOf(numberArray[0]);
		} else {
			StringBuilder sb = new StringBuilder();
			for (long s : numberArray) {
				sb.append(s).append(c);
			}
			sb.delete(sb.length() - 1, sb.length());

			return sb.toString();

		}
	}

	public static String join(final char c, final float... numberArray) {
		if (null == numberArray || numberArray.length == 0) {
			return Stringz.EMPTY_STRING;
		} else if (numberArray.length == 1) {
			return String.valueOf(numberArray[0]);
		} else {
			StringBuilder sb = new StringBuilder();
			for (float s : numberArray) {
				sb.append(s).append(c);
			}
			sb.delete(sb.length() - 1, sb.length());

			return sb.toString();

		}
	}

	public static String join(final char c, final double... numberArray) {
		if (null == numberArray || numberArray.length == 0) {
			return Stringz.EMPTY_STRING;
		} else if (numberArray.length == 1) {
			return String.valueOf(numberArray[0]);
		} else {
			StringBuilder sb = new StringBuilder();
			for (double s : numberArray) {
				sb.append(s).append(c);
			}
			sb.delete(sb.length() - 1, sb.length());

			return sb.toString();

		}
	}
}
