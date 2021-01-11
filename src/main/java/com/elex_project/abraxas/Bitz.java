/*
 * Project Abraxas (a.k.a core-utils)
 * http://www.elex-project.com/
 * Copyright (c) 2017. Elex. All Rights Reserved.
 */

package com.elex_project.abraxas;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * 비트 플래그 유틸리티
 * <p>
 * 0xffffffff 에서 마지막 위치부터 인덱스 0.
 * Created by Elex on 2017-03-03.
 *
 * @author Elex
 */
public final class Bitz {

	private Bitz() {
	}

	/**
	 * 해당 비트가 설정 되었는지 여부
	 *
	 * @param val 비트셋
	 * @param bit 비트 인덱스
	 * @return true / false
	 */
	@Contract(pure = true)
	public static boolean isSet(final int val, final int bit) {
		int value = val;
		value >>= bit;
		value &= 0x01;
		return value != 0;
	}

	/**
	 * 해당 비트가 설정 되었는지 여부
	 *
	 * @param val 비트셋
	 * @param bit 비트 인덱스
	 * @return true / false
	 */
	public static boolean isSet(final byte val, final int bit) {
		byte value = val;
		value >>= bit;
		value &= 0x01;
		return value != 0;
	}

	/**
	 * 해당 비트가 설정 되었는지 여부
	 *
	 * @param val 비트셋
	 * @param bit 비트 인덱스
	 * @return true / false
	 */
	public static boolean isSet(final short val, final int bit) {
		short value = val;
		value >>= bit;
		value &= 0x01;
		return value != 0;
	}

	/**
	 * 해당 비트가 설정 되었는지 여부
	 *
	 * @param val 비트셋
	 * @param bit 비트 인덱스
	 * @return true / false
	 */
	public static boolean isSet(final long val, final int bit) {
		long value = val;
		value >>= bit;
		value &= 0x01;
		return value != 0;
	}

	/**
	 * 해당 비트가 설정 되었는지 여부
	 *
	 * @param val 비트셋
	 * @param bit 비트 인덱스
	 * @return true / false
	 */
	public static boolean isSet(final byte @NotNull [] val, final int bit) {
		byte xVal = val[val.length - 1 - bit / 8];
		int xBit = bit % 8;
		return isSet(xVal, xBit);
	}

	/**
	 * 해당 인덱스의 비트를 켠다.
	 *
	 * @param val 비트셋.
	 * @param bit 비트 인덱스
	 * @return 비트셋
	 */
	@Contract(pure = true)
	public static int setBit(final int val, final int bit) {
		int value = val;
		value |= (1 << bit);
		return value;// & 0xff;
	}

	/**
	 * 해당 인덱스의 비트를 켠다.
	 *
	 * @param val 비트셋.
	 * @param bit 비트 인덱스
	 * @return 비트셋
	 */
	public static byte setBit(final byte val, final int bit) {
		byte value = val;
		value |= (1 << bit);
		return value;// & 0xff;
	}

	/**
	 * 해당 인덱스의 비트를 켠다.
	 *
	 * @param val 비트셋.
	 * @param bit 비트 인덱스
	 * @return 비트셋
	 */
	public static short setBit(final short val, final int bit) {
		short value = val;
		value |= (1 << bit);
		return value;// & 0xff;
	}

	/**
	 * 해당 인덱스의 비트를 켠다.
	 *
	 * @param val 비트셋.
	 * @param bit 비트 인덱스
	 * @return 비트셋
	 */
	public static long setBit(final long val, final int bit) {
		long value = val;
		value |= (1 << bit);
		return value;// & 0xff;
	}

	/**
	 * 해당 인덱스의 비트를 켠다.
	 *
	 * @param val 비트셋.
	 * @param bit 비트 인덱스
	 * @return 비트셋
	 */
	public static byte[] setBit(final byte @NotNull [] val, final int bit) {
		int idx = val.length - 1 - bit / 8;
		byte xVal = val[idx];
		int xBit = bit % 8;
		val[idx] = setBit(xVal, xBit);
		return val;
	}

	/**
	 * 해당 인덱스의 비트를 끈다.
	 *
	 * @param val 비트셋.
	 * @param bit 비트 인덱스
	 * @return 비트셋
	 */
	@Contract(pure = true)
	public static int clearBit(final int val, final int bit) {
		int value = val;
		value &= ~(1 << bit);
		return value;// & 0xff;
	}

	/**
	 * 해당 인덱스의 비트를 끈다.
	 *
	 * @param val 비트셋.
	 * @param bit 비트 인덱스
	 * @return 비트셋
	 */
	public static byte clearBit(final byte val, final int bit) {
		byte value = val;
		value &= ~(1 << bit);
		return value;// & 0xff;
	}

	/**
	 * 해당 인덱스의 비트를 끈다.
	 *
	 * @param val 비트셋.
	 * @param bit 비트 인덱스
	 * @return 비트셋
	 */
	public static short clearBit(final short val, final int bit) {
		short value = val;
		value &= ~(1 << bit);
		return value;// & 0xff;
	}

	/**
	 * 해당 인덱스의 비트를 끈다.
	 *
	 * @param val 비트셋.
	 * @param bit 비트 인덱스
	 * @return 비트셋
	 */
	public static long clearBit(final long val, final int bit) {
		long value = val;
		value &= ~(1 << bit);
		return value;// & 0xff;
	}

	/**
	 * 해당 인덱스의 비트를 끈다.
	 *
	 * @param val 비트셋.
	 * @param bit 비트 인덱스
	 * @return 비트셋
	 */
	public static byte[] clearBit(final byte @NotNull [] val, final int bit) {
		int idx = val.length - 1 - bit / 8;
		byte xVal = val[idx];
		int xBit = bit % 8;
		val[idx] = clearBit(xVal, xBit);
		return val;
	}

	/**
	 * 해당 인덱스의 비트가 켜진 비트면 끄고, 꺼진 비트면 켠다.
	 *
	 * @param val 비트셋.
	 * @param bit 비트 인덱스
	 * @return 비트셋
	 */
	public static int flipBit(final int val, final int bit) {
		int value;
		if (isSet(val, bit)) {
			value = clearBit(val, bit);
		} else {
			value = setBit(val, bit);
		}
		return value;
	}

	/**
	 * 해당 인덱스의 비트가 켜진 비트면 끄고, 꺼진 비트면 켠다.
	 *
	 * @param val 비트셋.
	 * @param bit 비트 인덱스
	 * @return 비트셋
	 */
	public static byte flipBit(final byte val, final int bit) {
		byte value;
		if (isSet(val, bit)) {
			value = clearBit(val, bit);
		} else {
			value = setBit(val, bit);
		}
		return value;
	}

	/**
	 * 해당 인덱스의 비트가 켜진 비트면 끄고, 꺼진 비트면 켠다.
	 *
	 * @param val 비트셋.
	 * @param bit 비트 인덱스
	 * @return 비트셋
	 */
	public static short flipBit(final short val, final int bit) {
		short value;
		if (isSet(val, bit)) {
			value = clearBit(val, bit);
		} else {
			value = setBit(val, bit);
		}
		return value;
	}

	/**
	 * 해당 인덱스의 비트가 켜진 비트면 끄고, 꺼진 비트면 켠다.
	 *
	 * @param val 비트셋.
	 * @param bit 비트 인덱스
	 * @return 비트셋
	 */
	public static long flipBit(final long val, final int bit) {
		long value;
		if (isSet(val, bit)) {
			value = clearBit(val, bit);
		} else {
			value = setBit(val, bit);
		}
		return value;
	}

	/**
	 * 해당 인덱스의 비트가 켜진 비트면 끄고, 꺼진 비트면 켠다.
	 *
	 * @param val 비트셋.
	 * @param bit 비트 인덱스
	 * @return 비트셋
	 */
	public static byte[] flipBit(final byte @NotNull [] val, final int bit) {
		int idx = val.length - 1 - bit / 8;
		byte xVal = val[idx];
		int xBit = bit % 8;
		val[idx] = flipBit(xVal, xBit);
		return val;
	}
}
