/*
 * Project Abraxas (a.k.a core-utils)
 * http://www.elex-project.com/
 * Copyright (c) 2019. Elex. All Rights Reserved.
 */

package com.elex_project.abraxas;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * 배열 유틸리티
 *
 * @author Elex
 */
public final class Arrayz {
	public static int[] EMPTY_INT_ARRAY = {};
	public static Object[] EMPTY_OBJECT_ARRAY = {};

	private Arrayz() {
	}

	/**
	 * serial int array
	 *
	 * @param from inclusive
	 * @param to   inclusive
	 * @return int array
	 */
	public static int[] ofRange(final int from, final int to) {
		final int[] array = new int[to - from + 1];
		for (int i = 0; i < array.length; i++) {
			array[i] = from + i;
		}
		return array;
	}

	/**
	 * serial int array begins with 0
	 *
	 * @param to inclusive
	 * @return int array
	 */
	public static int[] ofRange(final int to) {
		return ofRange(0, to);
	}

	/**
	 * 배열이 널이거나 배열의 크기가 0이면, 그 배열은 비어있다.
	 *
	 * @param array 배열
	 * @param <T>   배열 타입
	 * @return 빈 배열이면 참
	 */
	public static <T> boolean isEmpty(final T @Nullable [] array) {
		return null == array || array.length == 0;
	}

	public static boolean isEmpty(final byte @Nullable [] array) {
		return null == array || array.length == 0;
	}

	public static boolean isEmpty(final short @Nullable [] array) {
		return null == array || array.length == 0;
	}

	public static boolean isEmpty(final int @Nullable [] array) {
		return null == array || array.length == 0;
	}

	public static boolean isEmpty(final long @Nullable [] array) {
		return null == array || array.length == 0;
	}

	public static boolean isEmpty(final float @Nullable [] array) {
		return null == array || array.length == 0;
	}

	public static boolean isEmpty(final double @Nullable [] array) {
		return null == array || array.length == 0;
	}

	public static boolean isEmpty(final boolean @Nullable [] array) {
		return null == array || array.length == 0;
	}

	public static boolean isEmpty(final char @Nullable [] array) {
		return null == array || array.length == 0;
	}

	/**
	 * 중복 요소가 제거된 합집합.
	 *
	 * @param arrays 배열
	 * @param <T>    배열 타입
	 * @return 셋
	 */
	@NotNull
	@SafeVarargs
	public static <T> Set<T> union(final T @NotNull []... arrays) {
		return concatIgnoreDuplicate(arrays);
	}

	/**
	 * 중복 요소가 제거된 합집합.
	 *
	 * @param arrays 배열
	 * @return 셋
	 */
	@NotNull
	public static Set<Byte> union(final byte @NotNull []... arrays) {
		return concatIgnoreDuplicate(arrays);
	}

	/**
	 * 중복 요소가 제거된 합집합.
	 *
	 * @param arrays 배열
	 * @return 셋
	 */
	@NotNull
	public static Set<Short> union(final short @NotNull []... arrays) {
		return concatIgnoreDuplicate(arrays);
	}

	/**
	 * 중복 요소가 제거된 합집합.
	 *
	 * @param arrays 배열
	 * @return 셋
	 */
	@NotNull
	public static Set<Integer> union(final int @NotNull []... arrays) {
		return concatIgnoreDuplicate(arrays);
	}

	/**
	 * 중복 요소가 제거된 합집합.
	 *
	 * @param arrays 배열
	 * @return 셋
	 */
	@NotNull
	public static Set<Long> union(final long @NotNull []... arrays) {
		return concatIgnoreDuplicate(arrays);
	}

	/**
	 * 중복 요소가 제거된 합집합.
	 *
	 * @param arrays 배열
	 * @return 셋
	 */
	@NotNull
	public static Set<Float> union(final float @NotNull []... arrays) {
		return concatIgnoreDuplicate(arrays);
	}

	/**
	 * 중복 요소가 제거된 합집합.
	 *
	 * @param arrays 배열
	 * @return 셋
	 */
	@NotNull
	public static Set<Double> union(final double @NotNull []... arrays) {
		return concatIgnoreDuplicate(arrays);
	}

	/**
	 * 중복 요소가 제거된 합집합.
	 *
	 * @param arrays 배열
	 * @return 셋
	 */
	@NotNull
	public static Set<Character> union(final char @NotNull []... arrays) {
		return concatIgnoreDuplicate(arrays);
	}

	/**
	 * 차집합. 배열 1의 요소 중 배열 2의 요소인 것을 제거한다.
	 *
	 * @param array1 배열 1
	 * @param array2 배열 2
	 * @param <T>    배열 타입
	 * @return 셋
	 */
	@NotNull
	public static <T> Set<T> subtract(final T @Nullable [] array1, final T @Nullable [] array2) {
		Set<T> set = new HashSet<>();
		if (isEmpty(array1)) return set;
		if (isEmpty(array2)) {
			set.addAll(asList(array1));
			return set;
		}

		T[] tmp = java.util.Arrays.copyOf(array2, array2.length);
		java.util.Arrays.sort(tmp);
		for (T item : array1) {
			if (java.util.Arrays.binarySearch(tmp, item) < 0) {
				set.add(item);
			}
		}
		return set;
	}

	/**
	 * 차집합. 배열 1의 요소 중 배열 2의 요소인 것을 제거한다.
	 *
	 * @param array1 배열 1
	 * @param array2 배열 2
	 * @return 셋
	 */
	@NotNull
	public static Set<Byte> subtract(final byte @Nullable [] array1, final byte @Nullable [] array2) {
		Set<Byte> set = new HashSet<>();
		if (isEmpty(array1)) return set;
		if (isEmpty(array2)) {
			set.addAll(asList(array1));
			return set;
		}

		byte[] tmp = java.util.Arrays.copyOf(array2, array2.length);
		java.util.Arrays.sort(tmp);
		for (byte item : array1) {
			if (java.util.Arrays.binarySearch(tmp, item) < 0) {
				set.add(item);
			}
		}
		return set;
	}

	/**
	 * 차집합. 배열 1의 요소 중 배열 2의 요소인 것을 제거한다.
	 *
	 * @param array1 배열 1
	 * @param array2 배열 2
	 * @return 셋
	 */
	@NotNull
	public static Set<Short> subtract(final short @Nullable [] array1, final short @Nullable [] array2) {
		Set<Short> set = new HashSet<>();
		if (isEmpty(array1)) return set;
		if (isEmpty(array2)) {
			set.addAll(asList(array1));
			return set;
		}

		short[] tmp = java.util.Arrays.copyOf(array2, array2.length);
		java.util.Arrays.sort(tmp);
		for (short item : array1) {
			if (java.util.Arrays.binarySearch(tmp, item) < 0) {
				set.add(item);
			}
		}
		return set;
	}

	/**
	 * 차집합. 배열 1의 요소 중 배열 2의 요소인 것을 제거한다.
	 *
	 * @param array1 배열 1
	 * @param array2 배열 2
	 * @return 셋
	 */
	@NotNull
	public static Set<Integer> subtract(final int @Nullable [] array1, final int @Nullable [] array2) {
		Set<Integer> set = new HashSet<>();
		if (isEmpty(array1)) return set;
		if (isEmpty(array2)) {
			set.addAll(asList(array1));
			return set;
		}

		int[] tmp = java.util.Arrays.copyOf(array2, array2.length);
		java.util.Arrays.sort(tmp);
		for (int item : array1) {
			if (java.util.Arrays.binarySearch(tmp, item) < 0) {
				set.add(item);
			}
		}
		return set;
	}

	/**
	 * 차집합. 배열 1의 요소 중 배열 2의 요소인 것을 제거한다.
	 *
	 * @param array1 배열 1
	 * @param array2 배열 2
	 * @return 셋
	 */
	@NotNull
	public static Set<Long> subtract(final long @Nullable [] array1, final long @Nullable [] array2) {
		Set<Long> set = new HashSet<>();
		if (isEmpty(array1)) return set;
		if (isEmpty(array2)) {
			set.addAll(asList(array1));
			return set;
		}

		long[] tmp = java.util.Arrays.copyOf(array2, array2.length);
		java.util.Arrays.sort(tmp);
		for (long item : array1) {
			if (java.util.Arrays.binarySearch(tmp, item) < 0) {
				set.add(item);
			}
		}
		return set;
	}

	/**
	 * 차집합. 배열 1의 요소 중 배열 2의 요소인 것을 제거한다.
	 *
	 * @param array1 배열 1
	 * @param array2 배열 2
	 * @return 셋
	 */
	@NotNull
	public static Set<Float> subtract(final float @Nullable [] array1, final float @Nullable [] array2) {
		Set<Float> set = new HashSet<>();
		if (isEmpty(array1)) return set;
		if (isEmpty(array2)) {
			set.addAll(asList(array1));
			return set;
		}

		float[] tmp = java.util.Arrays.copyOf(array2, array2.length);
		java.util.Arrays.sort(tmp);
		for (float item : array1) {
			if (java.util.Arrays.binarySearch(tmp, item) < 0) {
				set.add(item);
			}
		}
		return set;
	}

	/**
	 * 차집합. 배열 1의 요소 중 배열 2의 요소인 것을 제거한다.
	 *
	 * @param array1 배열 1
	 * @param array2 배열 2
	 * @return 셋
	 */
	@NotNull
	public static Set<Double> subtract(final double @Nullable [] array1, final double @Nullable [] array2) {
		Set<Double> set = new HashSet<>();
		if (isEmpty(array1)) return set;
		if (isEmpty(array2)) {
			set.addAll(asList(array1));
			return set;
		}

		double[] tmp = java.util.Arrays.copyOf(array2, array2.length);
		java.util.Arrays.sort(tmp);
		for (double item : array1) {
			if (java.util.Arrays.binarySearch(tmp, item) < 0) {
				set.add(item);
			}
		}
		return set;
	}

	/**
	 * 차집합. 배열 1의 요소 중 배열 2의 요소인 것을 제거한다.
	 *
	 * @param array1 배열 1
	 * @param array2 배열 2
	 * @return 셋
	 */
	@NotNull
	public static Set<Character> subtract(final char @Nullable [] array1, final char @Nullable [] array2) {
		Set<Character> set = new HashSet<>();
		if (isEmpty(array1)) return set;
		if (isEmpty(array2)) {
			set.addAll(asList(array1));
			return set;
		}

		char[] tmp = java.util.Arrays.copyOf(array2, array2.length);
		java.util.Arrays.sort(tmp);
		for (char item : array1) {
			if (java.util.Arrays.binarySearch(tmp, item) < 0) {
				set.add(item);
			}
		}
		return set;
	}

	/**
	 * 교집합.
	 *
	 * @param array1 배열 1
	 * @param array2 배열 2
	 * @param <T>    타입
	 * @return 셋
	 */
	@NotNull
	public static <T> Set<T> intersect(final T @Nullable [] array1, final T @Nullable [] array2) {
		Set<T> set = new HashSet<>();
		if (isEmpty(array1) || isEmpty(array2)) return set;

		T[] tmp = java.util.Arrays.copyOf(array2, array2.length);
		java.util.Arrays.sort(tmp);
		for (T item : array1) {
			if (java.util.Arrays.binarySearch(tmp, item) >= 0) {
				set.add(item);
			}
		}
		return set;
	}

	/**
	 * 교집합.
	 *
	 * @param array1 배열 1
	 * @param array2 배열 2
	 * @return 셋
	 */
	@NotNull
	public static Set<Byte> intersect(final byte @Nullable [] array1, final byte @Nullable [] array2) {
		Set<Byte> set = new HashSet<>();
		if (isEmpty(array1) || isEmpty(array2)) return set;

		byte[] tmp = java.util.Arrays.copyOf(array2, array2.length);
		java.util.Arrays.sort(tmp);
		for (byte item : array1) {
			if (java.util.Arrays.binarySearch(tmp, item) >= 0) {
				set.add(item);
			}
		}
		return set;
	}

	/**
	 * 교집합.
	 *
	 * @param array1 배열 1
	 * @param array2 배열 2
	 * @return 셋
	 */
	@NotNull
	public static Set<Short> intersect(final short @Nullable [] array1, final short @Nullable [] array2) {
		Set<Short> set = new HashSet<>();
		if (isEmpty(array1) || isEmpty(array2)) return set;

		short[] tmp = java.util.Arrays.copyOf(array2, array2.length);
		java.util.Arrays.sort(tmp);
		for (short item : array1) {
			if (java.util.Arrays.binarySearch(tmp, item) >= 0) {
				set.add(item);
			}
		}
		return set;
	}

	/**
	 * 교집합.
	 *
	 * @param array1 배열 1
	 * @param array2 배열 2
	 * @return 셋
	 */
	@NotNull
	public static Set<Integer> intersect(final int @Nullable [] array1, final int @Nullable [] array2) {
		Set<Integer> set = new HashSet<>();
		if (isEmpty(array1) || isEmpty(array2)) return set;

		int[] tmp = java.util.Arrays.copyOf(array2, array2.length);
		java.util.Arrays.sort(tmp);
		for (int item : array1) {
			if (java.util.Arrays.binarySearch(tmp, item) >= 0) {
				set.add(item);
			}
		}
		return set;
	}

	/**
	 * 교집합.
	 *
	 * @param array1 배열 1
	 * @param array2 배열 2
	 * @return 셋
	 */
	@NotNull
	public static Set<Long> intersect(final long @Nullable [] array1, final long @Nullable [] array2) {
		Set<Long> set = new HashSet<>();
		if (isEmpty(array1) || isEmpty(array2)) return set;

		long[] tmp = java.util.Arrays.copyOf(array2, array2.length);
		java.util.Arrays.sort(tmp);
		for (long item : array1) {
			if (java.util.Arrays.binarySearch(tmp, item) >= 0) {
				set.add(item);
			}
		}
		return set;
	}

	/**
	 * 교집합.
	 *
	 * @param array1 배열 1
	 * @param array2 배열 2
	 * @return 셋
	 */
	@NotNull
	public static Set<Float> intersect(final float @Nullable [] array1, final float @Nullable [] array2) {
		Set<Float> set = new HashSet<>();
		if (isEmpty(array1) || isEmpty(array2)) return set;

		float[] tmp = java.util.Arrays.copyOf(array2, array2.length);
		java.util.Arrays.sort(tmp);
		for (float item : array1) {
			if (java.util.Arrays.binarySearch(tmp, item) >= 0) {
				set.add(item);
			}
		}
		return set;
	}

	/**
	 * 교집합.
	 *
	 * @param array1 배열 1
	 * @param array2 배열 2
	 * @return 셋
	 */
	@NotNull
	public static Set<Double> intersect(final double @Nullable [] array1, final double @Nullable [] array2) {
		Set<Double> set = new HashSet<>();
		if (isEmpty(array1) || isEmpty(array2)) return set;

		double[] tmp = java.util.Arrays.copyOf(array2, array2.length);
		java.util.Arrays.sort(tmp);
		for (double item : array1) {
			if (java.util.Arrays.binarySearch(tmp, item) >= 0) {
				set.add(item);
			}
		}
		return set;
	}

	/**
	 * 교집합.
	 *
	 * @param array1 배열 1
	 * @param array2 배열 2
	 * @return 셋
	 */
	@NotNull
	public static Set<Character> intersect(final char @Nullable [] array1, final char @Nullable [] array2) {
		Set<Character> set = new HashSet<>();
		if (isEmpty(array1) || isEmpty(array2)) return set;

		char[] tmp = java.util.Arrays.copyOf(array2, array2.length);
		java.util.Arrays.sort(tmp);
		for (char item : array1) {
			if (java.util.Arrays.binarySearch(tmp, item) >= 0) {
				set.add(item);
			}
		}
		return set;
	}

	/**
	 * @see java.util.Arrays#asList(Object[])
	 */
	@NotNull
	@Contract(pure = true)
	@SafeVarargs
	public static <T> List<T> asList(final T... array) {
		return java.util.Arrays.asList(array);
	}

	@NotNull
	public static List<Byte> asList(final byte... array) {
		List<Byte> list = new ArrayList<>();
		for (byte i : array) {
			list.add(i);
		}
		return list;
	}

	@NotNull
	public static List<Short> asList(final short... array) {
		List<Short> list = new ArrayList<>();
		for (short i : array) {
			list.add(i);
		}
		return list;
	}

	@NotNull
	public static List<Integer> asList(final int... array) {
		List<Integer> list = new ArrayList<>();
		for (int i : array) {
			list.add(i);
		}
		return list;
	}

	@NotNull
	public static List<Long> asList(final long... array) {
		List<Long> list = new ArrayList<>();
		for (long i : array) {
			list.add(i);
		}
		return list;
	}

	@NotNull
	public static List<Float> asList(final float... array) {
		List<Float> list = new ArrayList<>();
		for (float i : array) {
			list.add(i);
		}
		return list;
	}

	@NotNull
	public static List<Double> asList(final double... array) {
		List<Double> list = new ArrayList<>();
		for (double i : array) {
			list.add(i);
		}
		return list;
	}

	@NotNull
	public static List<Boolean> asList(final boolean... array) {
		List<Boolean> list = new ArrayList<>();
		for (boolean i : array) {
			list.add(i);
		}
		return list;
	}

	@NotNull
	public static List<Character> asList(final char... array) {
		List<Character> list = new ArrayList<>();
		for (char i : array) {
			list.add(i);
		}
		return list;
	}

	/**
	 * 배열을 합친다. 중복 요소는 중복된다.
	 *
	 * @param arrays 배열
	 * @param <T>    타입
	 * @return 리스트
	 */
	@NotNull
	@Contract("_ -> new")
	@SafeVarargs
	public static <T> List<T> concat(final T @NotNull []... arrays) {
		List<T> list = new ArrayList<>();
		for (T[] array : arrays) {
			list.addAll(asList(array));
		}
		return list;
	}

	/**
	 * 배열을 합친다. 중복 요소는 중복된다.
	 *
	 * @param arrays 배열
	 * @return 리스트
	 */
	@NotNull
	@Contract("_ -> new")
	public static List<Integer> concat(final int @NotNull []... arrays) {
		List<Integer> list = new ArrayList<>();
		for (int[] array : arrays) {
			list.addAll(asList(array));
		}
		return list;
	}

	/**
	 * 배열을 합친다. 중복 요소는 중복된다.
	 *
	 * @param arrays 배열
	 * @return 리스트
	 */
	@NotNull
	public static List<Byte> concat(final byte @NotNull []... arrays) {
		List<Byte> list = new ArrayList<>();
		for (byte[] array : arrays) {
			list.addAll(asList(array));
		}
		return list;
	}

	/**
	 * 배열을 합친다. 중복 요소는 중복된다.
	 *
	 * @param arrays 배열
	 * @return 리스트
	 */
	@NotNull
	public static List<Short> concat(final short @NotNull []... arrays) {
		List<Short> list = new ArrayList<>();
		for (short[] array : arrays) {
			list.addAll(asList(array));
		}
		return list;
	}

	/**
	 * 배열을 합친다. 중복 요소는 중복된다.
	 *
	 * @param arrays 배열
	 * @return 리스트
	 */
	@NotNull
	public static List<Long> concat(final long @NotNull []... arrays) {
		List<Long> list = new ArrayList<>();
		for (long[] array : arrays) {
			list.addAll(asList(array));
		}
		return list;
	}

	/**
	 * 배열을 합친다. 중복 요소는 중복된다.
	 *
	 * @param arrays 배열
	 * @return 리스트
	 */
	@NotNull
	public static List<Float> concat(final float @NotNull []... arrays) {
		List<Float> list = new ArrayList<>();
		for (float[] array : arrays) {
			list.addAll(asList(array));
		}
		return list;
	}

	/**
	 * 배열을 합친다. 중복 요소는 중복된다.
	 *
	 * @param arrays 배열
	 * @return 리스트
	 */
	@NotNull
	public static List<Double> concat(final double @NotNull []... arrays) {
		List<Double> list = new ArrayList<>();
		for (double[] array : arrays) {
			list.addAll(asList(array));
		}
		return list;
	}

	/**
	 * 배열을 합친다. 중복 요소는 중복된다.
	 *
	 * @param arrays 배열
	 * @return 리스트
	 */
	@NotNull
	public static List<Boolean> concat(final boolean @NotNull []... arrays) {
		List<Boolean> list = new ArrayList<>();
		for (boolean[] array : arrays) {
			list.addAll(asList(array));
		}
		return list;
	}

	/**
	 * 배열을 합친다. 중복 요소는 중복된다.
	 *
	 * @param arrays 배열
	 * @return 리스트
	 */
	@NotNull
	public static List<Character> concat(final char @NotNull []... arrays) {
		List<Character> list = new ArrayList<>();
		for (char[] array : arrays) {
			list.addAll(asList(array));
		}
		return list;
	}

	/**
	 * 배열을 합친다. 중복 요소는 하나만 처리된다.
	 *
	 * @param arrays 배열
	 * @param <T>    타입
	 * @return 셋
	 */
	@NotNull
	@Contract("_ -> new")
	@SafeVarargs
	public static <T> Set<T> concatIgnoreDuplicate(final T @NotNull []... arrays) {
		return new HashSet<T>(concat(arrays));
	}

	/**
	 * 배열을 합친다. 중복 요소는 하나만 처리된다.
	 *
	 * @param arrays 배열
	 * @return 셋
	 */
	@NotNull
	@Contract("_ -> new")
	public static Set<Byte> concatIgnoreDuplicate(final byte @NotNull []... arrays) {
		return new HashSet<>(concat(arrays));
	}

	/**
	 * 배열을 합친다. 중복 요소는 하나만 처리된다.
	 *
	 * @param arrays 배열
	 * @return 셋
	 */
	@NotNull
	@Contract("_ -> new")
	public static Set<Short> concatIgnoreDuplicate(final short @NotNull []... arrays) {
		return new HashSet<>(concat(arrays));
	}

	/**
	 * 배열을 합친다. 중복 요소는 하나만 처리된다.
	 *
	 * @param arrays 배열
	 * @return 셋
	 */
	@NotNull
	@Contract("_ -> new")
	public static Set<Integer> concatIgnoreDuplicate(final int @NotNull []... arrays) {
		return new HashSet<>(concat(arrays));
	}

	/**
	 * 배열을 합친다. 중복 요소는 하나만 처리된다.
	 *
	 * @param arrays 배열
	 * @return 셋
	 */
	@NotNull
	@Contract("_ -> new")
	public static Set<Long> concatIgnoreDuplicate(final long @NotNull []... arrays) {
		return new HashSet<>(concat(arrays));
	}

	/**
	 * 배열을 합친다. 중복 요소는 하나만 처리된다.
	 *
	 * @param arrays 배열
	 * @return 셋
	 */
	@NotNull
	@Contract("_ -> new")
	public static Set<Float> concatIgnoreDuplicate(final float @NotNull []... arrays) {
		return new HashSet<>(concat(arrays));
	}

	/**
	 * 배열을 합친다. 중복 요소는 하나만 처리된다.
	 *
	 * @param arrays 배열
	 * @return 셋
	 */
	@NotNull
	@Contract("_ -> new")
	public static Set<Double> concatIgnoreDuplicate(final double @NotNull []... arrays) {
		return new HashSet<>(concat(arrays));
	}

	/**
	 * 배열을 합친다. 중복 요소는 하나만 처리된다.
	 *
	 * @param arrays 배열
	 * @return 셋
	 */
	@NotNull
	@Contract("_ -> new")
	public static Set<Boolean> concatIgnoreDuplicate(final boolean @NotNull []... arrays) {
		return new HashSet<>(concat(arrays));
	}

	/**
	 * 배열을 합친다. 중복 요소는 하나만 처리된다.
	 *
	 * @param arrays 배열
	 * @return 셋
	 */
	@NotNull
	@Contract("_ -> new")
	public static Set<Character> concatIgnoreDuplicate(final char @NotNull []... arrays) {
		return new HashSet<>(concat(arrays));
	}

	/**
	 * 배열에서 요소 하나를 제거한다.
	 *
	 * @param array      원본 배열
	 * @param removedIdx 제거할 아이템의 인덱스
	 * @param <T>        타입
	 * @return 배열의 길이가 하나 줄어든 새로운 배열
	 */
	public static <T> T @NotNull [] removeElement(final T @NotNull [] array, final int removedIdx) {
		T[] newArray = java.util.Arrays.copyOf(array, array.length - 1);
		System.arraycopy(array, removedIdx + 1, newArray, removedIdx, array.length - 1 - removedIdx);

		return newArray;
	}

	/**
	 * 배열에서 요소 하나를 제거한다.
	 *
	 * @param array      원본 배열
	 * @param removedIdx 제거할 아이템의 인덱스
	 * @return 배열의 길이가 하나 줄어든 새로운 배열
	 */
	public static byte @NotNull [] removeElement(final byte @NotNull [] array, final int removedIdx) {
		byte[] newArray = java.util.Arrays.copyOf(array, array.length - 1);
		System.arraycopy(array, removedIdx + 1, newArray, removedIdx, array.length - 1 - removedIdx);
		return newArray;
	}

	/**
	 * 배열에서 요소 하나를 제거한다.
	 *
	 * @param array      원본 배열
	 * @param removedIdx 제거할 아이템의 인덱스
	 * @return 배열의 길이가 하나 줄어든 새로운 배열
	 */
	public static short @NotNull [] removeElement(final short @NotNull [] array, final int removedIdx) {
		short[] newArray = java.util.Arrays.copyOf(array, array.length - 1);
		System.arraycopy(array, removedIdx + 1, newArray, removedIdx, array.length - 1 - removedIdx);
		return newArray;
	}

	/**
	 * 배열에서 요소 하나를 제거한다.
	 *
	 * @param array      원본 배열
	 * @param removedIdx 제거할 아이템의 인덱스
	 * @return 배열의 길이가 하나 줄어든 새로운 배열
	 */
	public static int @NotNull [] removeElement(final int @NotNull [] array, final int removedIdx) {
		int[] newArray = java.util.Arrays.copyOf(array, array.length - 1);
		System.arraycopy(array, removedIdx + 1, newArray, removedIdx, array.length - 1 - removedIdx);
		return newArray;
	}

	/**
	 * 배열에서 요소 하나를 제거한다.
	 *
	 * @param array      원본 배열
	 * @param removedIdx 제거할 아이템의 인덱스
	 * @return 배열의 길이가 하나 줄어든 새로운 배열
	 */
	public static long @NotNull [] removeElement(final long @NotNull [] array, final int removedIdx) {
		long[] newArray = java.util.Arrays.copyOf(array, array.length - 1);
		System.arraycopy(array, removedIdx + 1, newArray, removedIdx, array.length - 1 - removedIdx);
		return newArray;
	}

	/**
	 * 배열에서 요소 하나를 제거한다.
	 *
	 * @param array      원본 배열
	 * @param removedIdx 제거할 아이템의 인덱스
	 * @return 배열의 길이가 하나 줄어든 새로운 배열
	 */
	public static float @NotNull [] removeElement(final float @NotNull [] array, final int removedIdx) {
		float[] newArray = java.util.Arrays.copyOf(array, array.length - 1);
		System.arraycopy(array, removedIdx + 1, newArray, removedIdx, array.length - 1 - removedIdx);
		return newArray;
	}

	/**
	 * 배열에서 요소 하나를 제거한다.
	 *
	 * @param array      원본 배열
	 * @param removedIdx 제거할 아이템의 인덱스
	 * @return 배열의 길이가 하나 줄어든 새로운 배열
	 */
	public static double @NotNull [] removeElement(final double @NotNull [] array, final int removedIdx) {
		double[] newArray = java.util.Arrays.copyOf(array, array.length - 1);
		System.arraycopy(array, removedIdx + 1, newArray, removedIdx, array.length - 1 - removedIdx);
		return newArray;
	}

	/**
	 * 배열에서 요소 하나를 제거한다.
	 *
	 * @param array      원본 배열
	 * @param removedIdx 제거할 아이템의 인덱스
	 * @return 배열의 길이가 하나 줄어든 새로운 배열
	 */
	public static boolean @NotNull [] removeElement(final boolean @NotNull [] array, final int removedIdx) {
		boolean[] newArray = java.util.Arrays.copyOf(array, array.length - 1);
		System.arraycopy(array, removedIdx + 1, newArray, removedIdx, array.length - 1 - removedIdx);
		return newArray;
	}

	/**
	 * 배열에서 요소 하나를 제거한다.
	 *
	 * @param array      원본 배열
	 * @param removedIdx 제거할 아이템의 인덱스
	 * @return 배열의 길이가 하나 줄어든 새로운 배열
	 */
	public static char @NotNull [] removeElement(final char @NotNull [] array, final int removedIdx) {
		char[] newArray = java.util.Arrays.copyOf(array, array.length - 1);
		System.arraycopy(array, removedIdx + 1, newArray, removedIdx, array.length - 1 - removedIdx);
		return newArray;
	}

	public static void fill(final byte[] array, final byte value) {
		Arrays.fill(array, value);
	}

	public static void fill(final short[] array, final short value) {
		Arrays.fill(array, value);
	}

	public static void fill(final int[] array, final int value) {
		Arrays.fill(array, value);
	}

	public static void fill(final long[] array, final long value) {
		Arrays.fill(array, value);
	}

	public static void fill(final float[] array, final float value) {
		Arrays.fill(array, value);
	}

	public static void fill(final double[] array, final double value) {
		Arrays.fill(array, value);
	}

	public static void fill(final char[] array, final char value) {
		Arrays.fill(array, value);
	}

	public static void fill(final boolean[] array, final boolean value) {
		Arrays.fill(array, value);
	}

	public static void fill(final String[] array, final String value) {
		Arrays.fill(array, value);
	}

	public static <T> void fill(final T[] array, final T value) {
		Arrays.fill(array, value);
	}

	public static void copy(final byte[] array, final int idx, final byte[] dest, final int offset, final int size) {
		for (int i = 0; i < size; i++) {
			dest[offset + i] = array[idx + i];
		}
	}

	public static void copy(final short[] array, final int idx, final short[] dest, final int offset, final int size) {
		for (int i = 0; i < size; i++) {
			dest[offset + i] = array[idx + i];
		}
	}

	public static void copy(final int[] array, final int idx, final int[] dest, final int offset, final int size) {
		for (int i = 0; i < size; i++) {
			dest[offset + i] = array[idx + i];
		}
	}

	public static void copy(final long[] array, final int idx, final long[] dest, final int offset, final int size) {
		for (int i = 0; i < size; i++) {
			dest[offset + i] = array[idx + i];
		}
	}

	public static void copy(final float[] array, final int idx, final float[] dest, final int offset, final int size) {
		for (int i = 0; i < size; i++) {
			dest[offset + i] = array[idx + i];
		}
	}

	public static void copy(final double[] array, final int idx, final double[] dest, final int offset, final int size) {
		for (int i = 0; i < size; i++) {
			dest[offset + i] = array[idx + i];
		}
	}

	public static void copy(final char[] array, final int idx, final char[] dest, final int offset, final int size) {
		for (int i = 0; i < size; i++) {
			dest[offset + i] = array[idx + i];
		}
	}

	public static void copy(final boolean[] array, final int idx, final boolean[] dest, final int offset, final int size) {
		for (int i = 0; i < size; i++) {
			dest[offset + i] = array[idx + i];
		}
	}

	public static <T> void copy(final T[] array, final int idx, final T[] dest, final int offset, final int size) {
		System.arraycopy(array, idx, dest, offset, size);
	}

	public static boolean equals(final byte[] array, final byte[] another) {
		return Arrays.equals(array, another);
	}

	public static boolean equals(final short[] array, final short[] another) {
		return Arrays.equals(array, another);
	}

	public static boolean equals(final int[] array, final int[] another) {
		return Arrays.equals(array, another);
	}

	public static boolean equals(final long[] array, final long[] another) {
		return Arrays.equals(array, another);
	}

	public static boolean equals(final float[] array, final float[] another) {
		return Arrays.equals(array, another);
	}

	public static boolean equals(final double[] array, final double[] another) {
		return Arrays.equals(array, another);
	}

	public static boolean equals(final char[] array, final char[] another) {
		return Arrays.equals(array, another);
	}

	public static boolean equals(final boolean[] array, final boolean[] another) {
		return Arrays.equals(array, another);
	}

	public static <T> boolean equals(final T[] array, final T[] another) {
		return Arrays.equals(array, another);
	}

	public static void sort(final int[] array) {
		Arrays.sort(array);
	}

	public static void sort(final byte[] array) {
		Arrays.sort(array);
	}

	public static void sort(final short[] array) {
		Arrays.sort(array);
	}

	public static void sort(final long[] array) {
		Arrays.sort(array);
	}

	public static void sort(final float[] array) {
		Arrays.sort(array);
	}

	public static void sort(final double[] array) {
		Arrays.sort(array);
	}

	public static void sort(final char[] array) {
		Arrays.sort(array);
	}

	public static void sort(final String[] array) {
		Arrays.sort(array);
	}

	public static <T> void sort(final T[] array, Comparator<T> comparator) {
		Arrays.sort(array, comparator);
	}

	public static byte[] copyOf(final byte[] array, final int newSize) {
		return Arrays.copyOf(array, newSize);
	}

	public static short[] copyOf(final short[] array, final int newSize) {
		return Arrays.copyOf(array, newSize);
	}

	public static int[] copyOf(final int[] array, final int newSize) {
		return Arrays.copyOf(array, newSize);
	}

	public static long[] copyOf(final long[] array, final int newSize) {
		return Arrays.copyOf(array, newSize);
	}

	public static float[] copyOf(final float[] array, final int newSize) {
		return Arrays.copyOf(array, newSize);
	}

	public static double[] copyOf(final double[] array, final int newSize) {
		return Arrays.copyOf(array, newSize);
	}

	public static char[] copyOf(final char[] array, final int newSize) {
		return Arrays.copyOf(array, newSize);
	}

	public static boolean[] copyOf(final boolean[] array, final int newSize) {
		return Arrays.copyOf(array, newSize);
	}

	public static <T> T[] copyOf(final T[] array, final int newSize) {
		return Arrays.copyOf(array, newSize);
	}

	/**
	 * 맵의 키와 값을 서로 뒤집는다. 키는 값이 되고 값은 키가 된다.
	 *
	 * @param map 맵
	 * @param <K> 키
	 * @param <V> 값
	 * @return 맵
	 */
	@NotNull
	public static <V, K> Map<V, K> flip(@NotNull final Map<K, V> map) {
		final Map<V, K> newMap = new HashMap<>();
		for (final Map.Entry<K, V> pair : map.entrySet()) {
			newMap.put(pair.getValue(), pair.getKey());
		}
		return newMap;
	}

	/**
	 * 맵을 하나씩 순회한다.
	 *
	 * @param map      맵
	 * @param iterator 반복 이벤트 리스너
	 * @param <K>      키
	 * @param <V>      밸류
	 */
	public static <K, V> void iterate(@NotNull final Map<K, V> map, @Nullable final MapIterator<K, V> iterator) {
		for (Map.Entry<K, V> pair : map.entrySet()) {
			if (null != iterator) iterator.onVisit(pair.getKey(), pair.getValue());
		}
	}

	/**
	 * 리스트를 하나씩 순회한다.
	 *
	 * @param list     리스트
	 * @param iterator 반복 이벤트 리스너
	 * @param <T>      타입
	 */
	public static <T> void iterate(@NotNull final List<T> list, @Nullable final ListIterator<T> iterator) {
		for (T item : list) {
			if (null != iterator) iterator.onVisit(item);
		}
	}

	/**
	 * 리스트를 하나씩 순회한다.
	 *
	 * @param set      set
	 * @param iterator 반복 이벤트 리스너
	 * @param <T>      타입
	 */
	public static <T> void iterate(@NotNull final Set<T> set, @Nullable final ListIterator<T> iterator) {
		for (T item : set) {
			if (null != iterator) iterator.onVisit(item);
		}
	}

	/**
	 * 리스트를 하나씩 순회한다.
	 *
	 * @param array    array
	 * @param iterator 반복 이벤트 리스너
	 * @param <T>      타입
	 */
	public static <T> void iterate(@NotNull final T[] array, @Nullable final ListIterator<T> iterator) {
		for (T item : array) {
			if (null != iterator) iterator.onVisit(item);
		}
	}

	public interface MapIterator<K, V> {
		/**
		 * 맵 아이템마다 한 번씩 호출된다.
		 *
		 * @param k 키
		 * @param v 밸류
		 */
		public void onVisit(final K k, final V v);
	}

	public interface ListIterator<T> {
		/**
		 * 리스트 아이템 마다 한 번씩 호출된다.
		 *
		 * @param v 타입
		 */
		public void onVisit(final T v);
	}
}
