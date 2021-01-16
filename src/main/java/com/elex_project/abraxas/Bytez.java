/*
 * Project Abraxas (a.k.a core-utils)
 * http://www.elex-project.com/
 * Copyright (c) 2017. Elex. All Rights Reserved.
 */

package com.elex_project.abraxas;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.StringJoiner;
import java.util.zip.*;

/**
 * 바이트 배열 유틸리티
 * <p>
 * Created by Elex on 2016-03-29.
 *
 * @author Elex
 */
public final class Bytez {
	static final byte[] EMPTY_BYTES = {};
	private static final String TAG = Bytez.class.getSimpleName();
	private final static char[] HEX_ARRAY = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e', 'f'};//"0123456789abcdef".toCharArray();
	private static final int[] CRC16_TABLE = {
			0x0000, 0xc0c1, 0xc181, 0x0140, 0xc301, 0x03c0, 0x0280, 0xc241,
			0xc601, 0x06c0, 0x0780, 0xc741, 0x0500, 0xc5c1, 0xc481, 0x0440,
			0xcc01, 0x0cc0, 0x0d80, 0xcd41, 0x0f00, 0xcfc1, 0xce81, 0x0e40,
			0x0a00, 0xcac1, 0xcb81, 0x0b40, 0xc901, 0x09c0, 0x0880, 0xc841,
			0xd801, 0x18c0, 0x1980, 0xd941, 0x1b00, 0xdbc1, 0xda81, 0x1a40,
			0x1e00, 0xdec1, 0xdf81, 0x1f40, 0xdd01, 0x1dc0, 0x1c80, 0xdc41,
			0x1400, 0xd4c1, 0xd581, 0x1540, 0xd701, 0x17c0, 0x1680, 0xd641,
			0xd201, 0x12c0, 0x1380, 0xd341, 0x1100, 0xd1c1, 0xd081, 0x1040,
			0xf001, 0x30c0, 0x3180, 0xf141, 0x3300, 0xf3c1, 0xf281, 0x3240,
			0x3600, 0xf6c1, 0xf781, 0x3740, 0xf501, 0x35c0, 0x3480, 0xf441,
			0x3c00, 0xfcc1, 0xfd81, 0x3d40, 0xff01, 0x3fc0, 0x3e80, 0xfe41,
			0xfa01, 0x3ac0, 0x3b80, 0xfb41, 0x3900, 0xf9c1, 0xf881, 0x3840,
			0x2800, 0xe8c1, 0xe981, 0x2940, 0xeb01, 0x2bc0, 0x2a80, 0xea41,
			0xee01, 0x2ec0, 0x2f80, 0xef41, 0x2d00, 0xedc1, 0xec81, 0x2c40,
			0xe401, 0x24c0, 0x2580, 0xe541, 0x2700, 0xe7c1, 0xe681, 0x2640,
			0x2200, 0xe2c1, 0xe381, 0x2340, 0xe101, 0x21c0, 0x2080, 0xe041,
			0xa001, 0x60c0, 0x6180, 0xa141, 0x6300, 0xa3c1, 0xa281, 0x6240,
			0x6600, 0xa6c1, 0xa781, 0x6740, 0xa501, 0x65c0, 0x6480, 0xa441,
			0x6c00, 0xacc1, 0xad81, 0x6d40, 0xaf01, 0x6fc0, 0x6e80, 0xae41,
			0xaa01, 0x6ac0, 0x6b80, 0xab41, 0x6900, 0xa9c1, 0xa881, 0x6840,
			0x7800, 0xb8c1, 0xb981, 0x7940, 0xbb01, 0x7bc0, 0x7a80, 0xba41,
			0xbe01, 0x7ec0, 0x7f80, 0xbf41, 0x7d00, 0xbdc1, 0xbc81, 0x7c40,
			0xb401, 0x74c0, 0x7580, 0xb541, 0x7700, 0xb7c1, 0xb681, 0x7640,
			0x7200, 0xb2c1, 0xb381, 0x7340, 0xb101, 0x71c0, 0x7080, 0xb041,
			0x5000, 0x90c1, 0x9181, 0x5140, 0x9301, 0x53c0, 0x5280, 0x9241,
			0x9601, 0x56c0, 0x5780, 0x9741, 0x5500, 0x95c1, 0x9481, 0x5440,
			0x9c01, 0x5cc0, 0x5d80, 0x9d41, 0x5f00, 0x9fc1, 0x9e81, 0x5e40,
			0x5a00, 0x9ac1, 0x9b81, 0x5b40, 0x9901, 0x59c0, 0x5880, 0x9841,
			0x8801, 0x48c0, 0x4980, 0x8941, 0x4b00, 0x8bc1, 0x8a81, 0x4a40,
			0x4e00, 0x8ec1, 0x8f81, 0x4f40, 0x8d01, 0x4dc0, 0x4c80, 0x8c41,
			0x4400, 0x84c1, 0x8581, 0x4540, 0x8701, 0x47c0, 0x4680, 0x8641,
			0x8201, 0x42c0, 0x4380, 0x8341, 0x4100, 0x81c1, 0x8081, 0x4040,
	};

	private Bytez() {

	}

	/**
	 * 주어진 크기의 임의의 바이트 배열을 생성한다.
	 *
	 * @param numBytes 배열 길이
	 * @return 랜덤 배열
	 */

	public static byte @NotNull [] generateRandomBytes(final int numBytes) {
		return Random.nextBytes(numBytes);
	}

	@Contract(pure = true)
	public static byte @NotNull [] generateSerialBytes(final int numBytes) {
		byte[] array = new byte[numBytes];
		for (int i = 0; i < array.length; i++) {
			array[i] = (byte) i;
		}
		return array;
	}


	/**
	 * 주어진 바이트 값으로 채워진 바이트 배열을 생성.
	 *
	 * @param b    배열을 채울 값
	 * @param size 배열 크기
	 * @return 바이트 배열
	 */
	public static byte @NotNull [] newByteArray(final byte b, final int size) {
		return new ByteArrayBuilder().append(b, size).toByteArray();
	}

	public static boolean isEquals(final byte[] a, final byte[] b) {
		return java.util.Arrays.equals(a, b);
	}

	public static boolean isEquals(final short[] a, final short[] b) {
		return java.util.Arrays.equals(a, b);
	}

	public static boolean isEquals(final int[] a, final int[] b) {
		return java.util.Arrays.equals(a, b);
	}

	public static boolean isEquals(final long[] a, final long[] b) {
		return java.util.Arrays.equals(a, b);
	}

	public static boolean isEquals(final float[] a, final float[] b) {
		return java.util.Arrays.equals(a, b);
	}

	public static boolean isEquals(final double[] a, final double[] b) {
		return java.util.Arrays.equals(a, b);
	}

	public static boolean isEquals(final boolean[] a, final boolean[] b) {
		return java.util.Arrays.equals(a, b);
	}

	public static boolean isEquals(final char[] a, final char[] b) {
		return java.util.Arrays.equals(a, b);
	}

	public static <T> boolean isEquals(final T[] a, final T[] b) {
		return java.util.Arrays.equals(a, b);
	}

	/**
	 * hex to byte[]
	 *
	 * @param hex 16진수 표기의 문자열. 16진 문자가 아닌 부분은 제거된다.
	 * @return 바이트 배열.
	 * @throws IllegalArgumentException 문자열이 짝수 개가 아니면
	 *                                  길이가 0인 경우에는 길이가 0인 바이트 배열을 반환. 문자열이 짝수 개가 아니면 null.
	 */
	@NotNull
	public static byte[] fromHex(@NotNull final String hex) throws IllegalArgumentException {
		return fromHex(hex
				.replaceAll("[^0-9a-fA-F]+", Stringz.EMPTY_STRING)
				.toCharArray());
	}

	/**
	 * hex to byte[]
	 *
	 * @param hex 16진수 표기의 문자열
	 * @return 바이트 배열.
	 * @throws IllegalArgumentException 문자열이 짝수 개가 아니면
	 *                                  길이가 0인 경우에는 길이가 0인 바이트 배열을 반환. 문자열이 짝수 개가 아니면 null.
	 */
	@NotNull
	public static byte[] fromHex(@NotNull final char[] hex) throws IllegalArgumentException {

		final int len = hex.length;

		if ((len & 0x01) != 0) {
			throw new IllegalArgumentException("Length of hex must be even.");
		}

		final byte[] out = new byte[len >> 1];

		// two characters form the hex value.
		for (int i = 0, j = 0; j < len; i++) {
			int f = toDigit(hex[j]) << 4;
			j++;
			f = f | toDigit(hex[j]);
			j++;
			out[i] = (byte) (f & 0xFF);
		}

		return out;
	}

	/**
	 * 16진수 문자를 숫자로 변환.
	 *
	 * @param ch 16진 문자
	 * @return 숫자
	 * @throws IllegalArgumentException 문자를 숫자로 변환할 수 없으면 예외 발생
	 * @see Character#digit(char, int)
	 */
	private static int toDigit(final char ch) throws IllegalArgumentException {
		final int digit = Character.digit(ch, 16);
		if (digit == -1) {
			throw new IllegalArgumentException("Weird HEX char : " + ch);
		}
		return digit;
	}

	/**
	 * 바이트를 2진수 표현 문자열로
	 *
	 * @param b 바이트
	 * @return 문자열
	 */
	@NotNull
	public static String toBinary(final byte b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 7; i >= 0; i--) {
			if (Bitz.isSet(b, i)) {
				sb.append(1);
			} else {
				sb.append(0);
			}
		}
		return sb.toString();
	}

	/**
	 * 바이트 배열을 2진수 표현으로.
	 *
	 * @param bytes 바이트 배열. 널이거나 빈 배열이면 빈 문자열을 반환
	 * @return 문자열
	 */
	@NotNull
	public static String toBinary(@Nullable final byte... bytes) {
		if (Arrayz.isEmpty(bytes)) {
			return Stringz.EMPTY_STRING;
		}

		StringBuilder sb = new StringBuilder();
		for (byte v : bytes) {
			sb.append(toBinary(v));
		}
		return sb.toString();
	}

	/**
	 * 바이트를 16진수 표현으로
	 *
	 * @param b 바이트
	 * @return 문자열. 앞에 0x가 붙는다.
	 */
	@NotNull
	public static String toHex(final byte b) {

		return toHex(b, true);
	}

	@NotNull
	public static String toHex(final byte b, boolean withPrefix) {

		StringBuilder sb = new StringBuilder();
		if (withPrefix) sb.append("0x");
		String hexNumber = "0" + Integer.toHexString(0xff & b);
		sb.append(hexNumber.substring(hexNumber.length() - 2));
		return sb.toString();
	}

	/**
	 * byte[] to hex
	 *
	 * @param bytes 바이트 배열
	 * @return 16진수 표현의 문자열. 매개변수의 길이가 0이면 빈 문자열을 반환.
	 */
	@NotNull
	public static String toHex(@NotNull final byte... bytes) {
		return toHex("", bytes);
	}

	/**
	 * byte[] to hex
	 *
	 * @param separator 구분자. 구분자가 널이면 바이트 표현들이 따닥따닥 붙는다.
	 * @param bytes     바이트 배열
	 * @return 16진수 표현의 문자열. 매개변수의 길이가 0이면 빈 문자열을 반환.
	 */
	@NotNull
	public static String toHex(@Nullable final String separator, @Nullable final byte... bytes) {
		if (Arrayz.isEmpty(bytes)) {
			return Stringz.EMPTY_STRING;
		}

		final int byteLength = bytes.length;
		char[] buffer = new char[2];
		StringJoiner joiner = new StringJoiner((null == separator) ? Stringz.EMPTY_STRING : separator);
		for (int i = 0; i < byteLength; i++) {
			final int byteHex = bytes[i] & 0xFF;
			buffer[0] = HEX_ARRAY[byteHex >>> 4];
			buffer[1] = HEX_ARRAY[byteHex & 0x0F];
			joiner.add(String.valueOf(buffer));
		}
		return joiner.toString();
	}

	/**
	 * byte[] to String
	 * 바이트 배열을 콤마로 구분된 문자열로 반환한다.
	 *
	 * @param ba 바이트 배열
	 * @return 매개변수 길이가 0이면 빈 문자열을 반환.
	 * @see #toHex(byte)
	 */
	@NotNull
	public static String toString(@Nullable final byte... ba) {
		if (Arrayz.isEmpty(ba)) {
			return Stringz.EMPTY_STRING;
		}
		final String delimiter = ", ";
		try {
			StringJoiner joiner = new StringJoiner(delimiter);
			for (byte b : ba) {
				joiner.add(toHex(b));
			}
			return joiner.toString();
		} catch (NoClassDefFoundError e){
			StringBuilder sb = new StringBuilder();
			for (byte b : ba) {
				sb.append(toHex(b)).append(delimiter);
			}
			sb.delete(sb.length() - delimiter.length(), sb.length());
			return sb.toString();
		}


	}

	public static byte[] fromByteBuffer(final @NotNull ByteBuffer buffer){
		return buffer.array();
	}

	@NotNull
	public static ByteBuffer toByteBuffer(final byte[] bytes){
		return ByteBuffer.wrap(bytes);
	}

	@NotNull
	public static ByteBuffer toByteBuffer(final byte[] bytes, final int offset, final int length){
		return ByteBuffer.wrap(bytes, offset, length);
	}

	/**
	 * Bitwise Flip
	 *
	 * @param val
	 * @return
	 */
	@Nullable
	@Contract(pure = true)
	public static byte[] flip(@Nullable final byte[] val) {
		if (null == val) return null;

		byte[] value = new byte[val.length];
		for (int i = 0; i < val.length; i++) {
			value[i] = Numberz.flip(val[i]);
		}
		return value;
	}

	/**
	 * 배열의 순서를 뒤집는다.
	 *
	 * @param ba 바이트 배열. final; 값이 변경되지 않음. 널이 입력되면 널을 돌려준다.
	 * @return 역순으로 뒤집힌 바이트 배열. 매개변수 길이가 0이면 길이가 0인 배열을 반환.
	 */
	@Contract(pure = true)
	@Nullable
	public static byte[] reverse(@Nullable final byte[] ba) {
		if (null == ba) return null;
		if (ba.length == 0) {
			return EMPTY_BYTES;
		}

		byte[] reversed = new byte[ba.length];
		for (int i = 0; i < ba.length; i++) {
			reversed[i] = ba[ba.length - 1 - i];
		}
		return reversed;
	}

	/**
	 * 배열 요소의 위치를 바꾼다. 시작은 끝으로, 끝은 시작으로.
	 * {@link #reverse(byte[])}와의 가장 큰 차이점은, 매개변수로 주어진 배열의 값이 바뀐다는 것이다.
	 *
	 * @param ba 바이트 배열. 값이 변경되며 변경된 값이 반환됨.
	 * @return 매개변수로 전달 받은 그 바이트 배열. 매개변수가 널이면 널, 길이가 0이나 1이면 그대로 반환.
	 */
	@Nullable
	@Contract("_ -> param1")
	public static byte[] swap(@Nullable final byte[] ba) {
		if (null == ba) return null;
		if (ba.length == 0 || ba.length == 1) {
			return ba;
		}

		for (int i = 0; i < ba.length / 2; i++) {
			byte b = ba[i];
			ba[i] = ba[ba.length - 1 - i];
			ba[ba.length - 1 - i] = b;
		}

		return ba;
	}

	/**
	 * 바이트 배열 여러 개를 하나로 합친다.
	 *
	 * @param arrays
	 * @return
	 */
	@NotNull
	public static byte[] concatenate(@NotNull final byte[]... arrays) {
		int newLength = 0;
		for (byte[] a : arrays) {
			newLength += a.length;
		}
		byte[] c = new byte[newLength];
		int pos = 0;
		for (byte[] a : arrays) {
			System.arraycopy(a, 0, c, pos, a.length);
			pos += a.length;
		}
		return c;
	}

	/**
	 * 바이트 배열의 부분 배열을 구한다.
	 *
	 * @param original 바이트 배열
	 * @param idx      부분 배열의 시작 인덱스
	 * @param length   부분 배열의 길이. 0보다 커야 한다.
	 * @return 부분 바이트 배열
	 * @throws ArrayIndexOutOfBoundsException 인덱스 확인 바람
	 * @throws IllegalArgumentException       length가 음수?
	 */
	@NotNull
	@Contract(pure = true)
	public static byte[] subArray(@NotNull final byte[] original, final int idx, final int length) {

		int to = idx + length;
		if (to > original.length - 1) {
			to = original.length;
		}
		return java.util.Arrays.copyOfRange(original, idx, to);
	}

	/**
	 * 배열의 첫 인덱스부터 주어진 갯수만큼의 부분 배열을 가져온다.
	 * 길이가 맞지 않으면 잘리거나 0으로 채워진다.
	 *
	 * @param array
	 * @param length 부분 배열의 길이. 0보다 커야 한다.
	 * @return
	 */
	@NotNull
	@Contract(pure = true)
	public static byte[] subArray(@NotNull final byte[] array, final int length) {
		return java.util.Arrays.copyOf(array, length);
	}

	/**
	 * 배열의 뒤에서부터주어진 갯수만큼을 부분 배열로 가져온다.
	 *
	 * @param array  배열
	 * @param length 부분 배열의 길이. 0보다 커야 한다.
	 * @return
	 */
	@NotNull
	@Contract(pure = true)
	public static byte[] subArrayFromEnd(@NotNull final byte[] array, final int length) {
		int idx = array.length - length;
		if (idx < 0) {
			idx = 0;
		}
		return java.util.Arrays.copyOfRange(array, idx, array.length);
	}

	public static long crc32(final byte[] data) {
		Checksum crc32 = new CRC32();
		crc32.update(data, 0, data.length);
		return crc32.getValue();
	}

	public static long crc32(final byte[] data, final int offset, final int length) {
		Checksum crc32 = new CRC32();
		crc32.update(data, offset, length);
		return crc32.getValue();
	}

	public static long crc32(final InputStream inputStream) throws IOException {
		Checksum crc32 = new CRC32();
		try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
			int read = 0;
			byte[] buffer = new byte[1024];
			while ((read = bufferedInputStream.read(buffer)) > 0) {
				crc32.update(buffer, 0, read);
			}
		}

		return crc32.getValue();
	}

	public static long adler32(final byte[] data) {
		Checksum adler32 = new Adler32();
		adler32.update(data, 0, data.length);
		return adler32.getValue();
	}

	public static long adler32(final byte[] data, final int offset, final int length) {
		Checksum adler32 = new Adler32();
		adler32.update(data, offset, length);
		return adler32.getValue();
	}

	public static long adler32(final InputStream inputStream) throws IOException {
		Checksum adler32 = new Adler32();
		try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
			int read = 0;
			byte[] buffer = new byte[1024];
			while ((read = bufferedInputStream.read(buffer)) > 0) {
				adler32.update(buffer, 0, read);
			}
		}

		return adler32.getValue();
	}

	/**
	 * Simple 1-byte Checksum
	 *
	 * @param bytes
	 * @return
	 */
	public static byte checksum1(byte... bytes) {
		int sum = 0;
		for (byte b : bytes) {
			sum += b;
			//sum += Math.abs(b);
		}
		byte checksum = (byte) sum;
		checksum = Numberz.flip(checksum);
		checksum += 1;
		return checksum;
	}

	/**
	 * 8비트 xor 연산
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static byte parity(final byte a, final byte b) {
		return (byte) (a ^ b);
	}

	/**
	 * 8비트 xor 연산
	 * <p>
	 * parity((byte)1, (byte)2, (byte)3);
	 * parity((byte)0x00, new byte[]{0x00,0x01});
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static byte parity(final byte a, @NotNull final byte... b) {
		byte x = a;
		for (byte v : b) {
			x = parity(x, v);
		}
		return x;
	}

	public static byte parity(@NotNull final byte[] b) {
		return parity((byte) 0x00, b);
	}

	@NotNull
	public static byte[] parity16(@NotNull final byte[] b) {
		byte[] x;
		if (b.length % 2 == 0) {
			x = b;
		} else {
			// 배열이 홀수 개인 경우에 맨 뒤에 0을 채워 짝수 개로 만든다.
			x = java.util.Arrays.copyOf(b, b.length + 1);
		}
		byte[] parity = newByteArray((byte) 0x00, 2);
		for (int i = 0; i < x.length; i += 2) {
			parity[0] = (byte) (parity[0] ^ x[i]);
			parity[1] = (byte) (parity[1] ^ x[i + 1]);
		}
		return parity;
	}

	public static short crc16(@NotNull final byte[] data) {
		int crc = 0x0000;
		for (byte b : data) {
			crc = (crc >>> 8) ^ CRC16_TABLE[(crc ^ b) & 0xff];
		}

		return (short) crc;
	}

	public static short crc16Modbus(final byte[] data) {
		return crc16Modbus(data, data.length);
	}

	public static short crc16Modbus(final byte[] data, final int dataLength) {
		CRC16Modbus crc16 = new CRC16Modbus();
		crc16.update(data, 0, dataLength);
		return (short) crc16.getValue();
	}

	private static class CRC16Modbus {
		private int sum = 0xFFFF;

		private CRC16Modbus() {
		}

		public int getValue() {
			return sum;
		}

		public void reset() {
			sum = 0xFFFF;
		}

		public void update(final byte[] b, final int off, final int len) {
			for (int i = off; i < off + len; ++i) {
				update(b[i]);
			}
		}

		public void update(final int b) {
			sum = sum >> 8 ^ CRC16_TABLE[(sum ^ b & 0xFF) & 0xFF];
		}

	}

	/**
	 * 자바 내장 베이스64 인코더를 사용
	 *
	 * @param src
	 * @return
	 * @since 1.8
	 */
	public static String toBase64(final byte[] src) {
		return Base64.getEncoder().encodeToString(src);
	}

	/**
	 * 자바 내장 베이스64 디코더를 사용
	 *
	 * @param src
	 * @return
	 * @since 1.8
	 */
	public static byte[] fromBase64(final String src) {
		return Base64.getDecoder().decode(src);
	}

	@NotNull
	@Contract(pure = true)
	public static byte[] salt(@NotNull final byte[] a, @NotNull final byte[] salt) {
		return salt(a, salt, 1);
	}

	@NotNull
	@Contract(pure = true)
	public static byte[] salt(@NotNull final byte[] a, @NotNull final byte[] salt, final int repeat) {
		byte[] buffer = new byte[a.length];
		int c = 0;
		int j = 0;
		while (c < repeat) {
			for (int i = 0; i < buffer.length; i++) {
				buffer[i] = (byte) (a[i] ^ salt[j]);
				j++;
				if (j >= salt.length) j = 0;
			}
			c++;
		}
		return buffer;
	}

	/**
	 * 배열 요소의 위치를 무작위로 섞는다.
	 * 원본 배열이 변경됨.
	 *
	 * @param bytes
	 */
	public static void shuffle(@NotNull final byte[] bytes) {
		byte tmp;
		int idxA, idxB;
		for (int i = 0; i < bytes.length; i++) {
			idxA = Random.nextInt(bytes.length);
			idxB = Random.nextInt(0, bytes.length - 1, idxA);
			tmp = bytes[idxA];
			bytes[idxA] = bytes[idxB];
			bytes[idxB] = tmp;
		}
	}

	public static byte[] ipAddress(@NotNull final InetAddress inetAddress) {
		return inetAddress.getAddress();
	}

	public static byte[] ipAddress(String ipAddress) throws UnknownHostException {
		return InetAddress.getByName(ipAddress).getAddress();
	}

	@NotNull
	public static InetAddress ipAddress(final byte[] ipAddress) throws UnknownHostException {
		return InetAddress.getByAddress(ipAddress);
	}

	@TestOnly
	static byte[] obfuscate(final String str) {
		byte[] original = Stringz.toBytes(str);
		byte[] output = new byte[original.length + 1];
		byte iv = Random.nextByte();
		output[0] = iv;
		for (int i = 0; i < original.length; i++) {
			if (i == 0) {
				output[i + 1] = (byte) (original[i] ^ iv);
			} else {
				output[i + 1] = (byte) (original[i] ^ output[i + 1 - 1]);
			}
		}
		return output;
	}

	public static String defuscate(final byte[] bytes) {
		byte[] output = new byte[bytes.length - 1];
		byte iv = bytes[0];
		for (int i = 0; i < output.length; i++) {
			if (i == 0) {
				output[i] = (byte) (bytes[i + 1] ^ iv);
			} else {
				output[i] = (byte) (bytes[i + 1] ^ bytes[i + 1 - 1]);
			}
		}
		return Stringz.fromBytes(output);
	}
}
