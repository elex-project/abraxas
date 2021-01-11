package com.elex_project.abraxas;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * Nullable Equals
 */
public final class Comparez {
	private Comparez() {
	}

	public static boolean equals(@Nullable Object a, @Nullable Object b) {
		if (null == a) {
			return null == b;
		} else {
			return a.equals(b);
		}
	}

	public static boolean equals(@Nullable String a, @Nullable String b) {
		if (null == a) {
			return null == b;
		} else {
			return a.equals(b);
		}
	}

	public static boolean equals(@Nullable Byte a, @Nullable Byte b) {
		if (null == a) {
			return null == b;
		} else {
			return a.equals(b);
		}
	}

	public static boolean equals(@Nullable Short a, @Nullable Short b) {
		if (null == a) {
			return null == b;
		} else {
			return a.equals(b);
		}
	}

	public static boolean equals(@Nullable Integer a, @Nullable Integer b) {
		if (null == a) {
			return null == b;
		} else {
			return a.equals(b);
		}
	}

	public static boolean equals(@Nullable Long a, @Nullable Long b) {
		if (null == a) {
			return null == b;
		} else {
			return a.equals(b);
		}
	}

	public static boolean equals(@Nullable Float a, @Nullable Float b) {
		if (null == a) {
			return null == b;
		} else {
			return a.equals(b);
		}
	}

	public static boolean equals(@Nullable Double a, @Nullable Double b) {
		if (null == a) {
			return null == b;
		} else {
			return a.equals(b);
		}
	}

	public static boolean equals(@Nullable Boolean a, @Nullable Boolean b) {
		if (null == a) {
			return null == b;
		} else {
			return a.equals(b);
		}
	}

	public static boolean equals(@Nullable Character a, @Nullable Character b) {
		if (null == a) {
			return null == b;
		} else {
			return a.equals(b);
		}
	}

	public static <T> boolean equals(@Nullable T[] a, @Nullable T[] b) {
		if (null == a) {
			return null == b;
		} else {
			return Arrayz.equals(a, b);
		}
	}

	public static boolean equals(byte a, byte b) {
		return a == b;
	}

	public static boolean equals(short a, short b) {
		return a == b;
	}

	public static boolean equals(int a, int b) {
		return a == b;
	}

	public static boolean equals(long a, long b) {
		return a == b;
	}

	public static boolean equals(float a, float b) {
		return a == b;
	}

	public static boolean equals(double a, double b) {
		return a == b;
	}

	public static boolean equals(boolean a, boolean b) {
		return a == b;
	}

	public static boolean equals(char a, char b) {
		return a == b;
	}
}
