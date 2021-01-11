/*
 * Project Abraxas (a.k.a core-utils)
 * http://www.elex-project.com/
 * Copyright (c) 2017. Elex. All Rights Reserved.
 */

package com.elex_project.abraxas;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Math
 *
 * @author Eelx
 */
public final class Mathz {
	private Mathz() {

	}

	/**
	 * 밑이 base인 로그 연산
	 * eg. log_2 (100) = logBase(100, 2)
	 * <br>
	 * Math.log(x) / Math.log(base)
	 *
	 * @param x    x
	 * @param base 밑
	 * @return log_base (x)
	 */
	public static double logBase(final double x, final double base) {
		return Math.log(x) / Math.log(base);
	}

	/**
	 * 주어진 좌표가 원의 내부에 포함되어 있는가
	 *
	 * @param x1     점
	 * @param y1     점
	 * @param x0     원의 중심
	 * @param y0     원의 중심
	 * @param radius 원의 반지름
	 * @return 포함?
	 */
	public static boolean isCircleContainsPoint(final float x1, final float y1, final float x0, final float y0, final float radius) {
		double r = Math.sqrt(Math.pow(x1 - x0, 2) + Math.pow(y1 - y0, 2));
		return r < radius;
	}

	/**
	 * 주어진 좌표가 중심점을 기준으로 어느 각도만큼 회전해 있는지를 판정한다.
	 * 중심점의 x축 양의 방향이 기준각도 0도이며, 시계 방향의 값이다.
	 *
	 * @param x1 좌표 x
	 * @param y1 좌표 y
	 * @param x0 중심점 x
	 * @param y0 중심점 y
	 * @return 0~359 사이의 값이다.
	 */
	public static float calcAngleOfPoint(final float x1, final float y1, final float x0, final float y0) {
		float angle = (float) radianToDegree(Math.atan(Math.abs(y1 - y0) / Math.abs(x1 - x0)));
		switch (calcSectionOfPoint(x1, y1, x0, y0)) {
			case 1:
				return angle;
			case 2:
				return 180 - angle;
			case 3:
				return 180 + angle;
			case 4:
				return 360 - angle;
		}
		return angle;
	}

	/**
	 * 주어진 좌표가 중심 점을 기준으로 어느 평면에 위치하는지를 판정한다.
	 *
	 * @param x1 좌표 x
	 * @param y1 좌표 y
	 * @param x0 중심점 x
	 * @param y0 중심점 y
	 * @return 1~4 중 하나의 값이다.
	 */
	@Contract(pure = true)
	public static byte calcSectionOfPoint(final float x1, final float y1, final float x0, final float y0) {
		if (x1 >= x0) {
			if (y1 >= y0) {
				return 1;
			} else {
				return 4;
			}
		} else {
			if (y1 >= y0) {
				return 2;
			} else {
				return 3;
			}
		}
	}

	/**
	 * 라디안을 도로 변환
	 *
	 * @param rad 라디안
	 * @return 각도
	 */
	@Contract(pure = true)
	public static double radianToDegree(final double rad) {
		return rad * 180 / Math.PI;
	}

	public static float radianToDegree(final float rad) {
		return (float) (rad * 180 / Math.PI);
	}

	/**
	 * 도를 라디안으로 변환
	 *
	 * @param deg 각도
	 * @return 라디안
	 */
	@Contract(pure = true)
	public static double degreeToRadian(final double deg) {
		return deg * Math.PI / 180;
	}

	public static float degreeToRadian(final float deg) {
		return (float) (deg * Math.PI / 180);
	}

	/**
	 * 값의 범위를 제한
	 *
	 * @param amount 어떤 값
	 * @param low    범위의 최소 값
	 * @param high   범위의 최대 값
	 * @return 범위 내의 어떤 값
	 */
	public static byte constrain(final byte amount, final byte low, final byte high) {
		return amount < low ? low : (amount > high ? high : amount);
	}

	/**
	 * 값의 범위를 제한
	 *
	 * @param amount 어떤 값
	 * @param low    범위의 최소 값
	 * @param high   범위의 최대 값
	 * @return 범위 내의 어떤 값
	 */
	public static short constrain(final short amount, final short low, final short high) {
		return amount < low ? low : (amount > high ? high : amount);
	}

	/**
	 * 값의 범위를 제한
	 *
	 * @param amount 어떤 값
	 * @param low    범위의 최소 값
	 * @param high   범위의 최대 값
	 * @return 범위 내의 어떤 값
	 */
	public static int constrain(final int amount, final int low, final int high) {
		return amount < low ? low : (amount > high ? high : amount);
	}

	/**
	 * 값의 범위를 제한
	 *
	 * @param amount 어떤 값
	 * @param low    범위의 최소 값
	 * @param high   범위의 최대 값
	 * @return 범위 내의 어떤 값
	 */
	public static long constrain(final long amount, final long low, final long high) {
		return amount < low ? low : (amount > high ? high : amount);
	}

	/**
	 * 값의 범위를 제한
	 *
	 * @param amount 어떤 값
	 * @param low    범위의 최소 값
	 * @param high   범위의 최대 값
	 * @return 범위 내의 어떤 값
	 */
	public static float constrain(final float amount, final float low, final float high) {
		return amount < low ? low : (amount > high ? high : amount);
	}

	/**
	 * 값의 범위를 제한
	 *
	 * @param amount 어떤 값
	 * @param low    범위의 최소 값
	 * @param high   범위의 최대 값
	 * @return 범위 내의 어떤 값
	 */
	public static double constrain(final double amount, final double low, final double high) {
		return amount < low ? low : (amount > high ? high : amount);
	}

	@Contract(pure = true)
	public static int min(@NotNull final int... numbers) {
		int min = numbers[0];
		for (int i : numbers) {
			min = Math.min(min, i);
		}
		return min;
	}

	@Contract(pure = true)
	public static int max(@NotNull final int... numbers) {
		int max = numbers[0];
		for (int i : numbers) {
			max = Math.max(max, i);
		}
		return max;
	}

	@Contract(pure = true)
	public static int avg(@NotNull final int... numbers) {
		int sum = 0;
		for (int i : numbers) {
			sum += i;
		}
		return sum / numbers.length;
	}

	@Contract(pure = true)
	public static long min(@NotNull final long... numbers) {
		long min = numbers[0];
		for (long i : numbers) {
			min = Math.min(min, i);
		}
		return min;
	}

	@Contract(pure = true)
	public static long max(@NotNull final long... numbers) {
		long max = numbers[0];
		for (long i : numbers) {
			max = Math.max(max, i);
		}
		return max;
	}

	@Contract(pure = true)
	public static long avg(@NotNull final long... numbers) {
		long sum = 0;
		for (long i : numbers) {
			sum += i;
		}
		return sum / numbers.length;
	}

	@Contract(pure = true)
	public static float min(@NotNull final float... numbers) {
		float min = numbers[0];
		for (float i : numbers) {
			min = Math.min(min, i);
		}
		return min;
	}

	@Contract(pure = true)
	public static float max(@NotNull final float... numbers) {
		float max = numbers[0];
		for (float i : numbers) {
			max = Math.max(max, i);
		}
		return max;
	}

	@Contract(pure = true)
	public static float avg(@NotNull final float... numbers) {
		float sum = 0;
		for (float i : numbers) {
			sum += i;
		}
		return sum / numbers.length;
	}

	@Contract(pure = true)
	public static double min(@NotNull final double... numbers) {
		double min = numbers[0];
		for (double i : numbers) {
			min = Math.min(min, i);
		}
		return min;
	}

	@Contract(pure = true)
	public static double max(@NotNull final double... numbers) {
		double max = numbers[0];
		for (double i : numbers) {
			max = Math.max(max, i);
		}
		return max;
	}

	@Contract(pure = true)
	public static double avg(@NotNull final double... numbers) {
		double sum = 0;
		for (double i : numbers) {
			sum += i;
		}
		return sum / numbers.length;
	}


	/**
	 * 어떤 범위 내의 값을 다른 범위 내의 값으로 변환
	 *
	 * @param x      어떤 값
	 * @param inMin  어떤 값의 최소 범위 값
	 * @param inMax  어떤 값의 최대 범위 값
	 * @param outMin 새로운 범위의 최소 값
	 * @param outMax 새로운 범위의 최대 값
	 * @return 새로운 범위 내의 어떤 값
	 */
	public static int remapRange(final int x, final int inMin, final int inMax, final int outMin, final int outMax) {
		return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
	}

	/**
	 * 어떤 범위 내의 값을 다른 범위 내의 값으로 변환
	 *
	 * @param x      어떤 값
	 * @param inMin  어떤 값의 최소 범위 값
	 * @param inMax  어떤 값의 최대 범위 값
	 * @param outMin 새로운 범위의 최소 값
	 * @param outMax 새로운 범위의 최대 값
	 * @return 새로운 범위 내의 어떤 값
	 */
	public static long remapRange(final long x, final long inMin, final long inMax, final long outMin, final long outMax) {
		return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
	}

	/**
	 * 어떤 범위 내의 값을 다른 범위 내의 값으로 변환
	 *
	 * @param x      어떤 값
	 * @param inMin  어떤 값의 최소 범위 값
	 * @param inMax  어떤 값의 최대 범위 값
	 * @param outMin 새로운 범위의 최소 값
	 * @param outMax 새로운 범위의 최대 값
	 * @return 새로운 범위 내의 어떤 값
	 */
	public static float remapRange(final float x, final float inMin, final float inMax, final float outMin, final float outMax) {
		return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
	}

	/**
	 * 어떤 범위 내의 값을 다른 범위 내의 값으로 변환
	 *
	 * @param x      어떤 값
	 * @param inMin  어떤 값의 최소 범위 값
	 * @param inMax  어떤 값의 최대 범위 값
	 * @param outMin 새로운 범위의 최소 값
	 * @param outMax 새로운 범위의 최대 값
	 * @return 새로운 범위 내의 어떤 값
	 */
	public static double remapRange(final double x, final double inMin, final double inMax, final double outMin, final double outMax) {
		return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
	}
}
