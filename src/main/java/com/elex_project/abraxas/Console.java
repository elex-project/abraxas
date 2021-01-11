/*
 * Project Abraxas (a.k.a core-utils)
 * http://www.elex-project.com/
 * Copyright (c) 2020. Elex. All Rights Reserved.
 */

package com.elex_project.abraxas;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Console I/O
 *
 * @author Elex
 */
public final class Console {

	//private static Scanner scanner;

	private Console() {

	}

	public static void write(final boolean val) {
		System.out.print(val);
	}

	public static void write(final byte val) {
		System.out.print(val);
	}

	public static void write(final char val) {
		System.out.print(val);
	}

	public static void write(final char[] val) {
		System.out.print(val);
	}

	public static void write(final short val) {
		System.out.print(val);
	}

	public static void write(final int val) {
		System.out.print(val);
	}

	public static void write(final long val) {
		System.out.print(val);
	}

	public static void write(final float val) {
		System.out.print(val);
	}

	public static void write(final double val) {
		System.out.print(val);
	}

	public static void write(final String val) {
		System.out.print(val);
	}

	public static void write(final Object val) {
		System.out.print(val);
	}

	public static void write(@NotNull final byte[] val) {
		System.out.print("[" + val.length + "] " + Bytez.toHex(":", val));
	}

	public static void write(final String format, final Object... params) {
		Formatter formatter = new Formatter();
		formatter.format(format, params);
		System.out.print(formatter);
	}

	public static void writeLine(final boolean val) {
		System.out.println(val);
	}

	public static void writeLine(final byte val) {
		System.out.println(val);
	}

	public static void writeLine(final char val) {
		System.out.println(val);
	}

	public static void writeLine(final char[] val) {
		System.out.println(val);
	}

	public static void writeLine(final short val) {
		System.out.println(val);
	}

	public static void writeLine(final int val) {
		System.out.println(val);
	}

	public static void writeLine(final long val) {
		System.out.println(val);
	}

	public static void writeLine(final float val) {
		System.out.println(val);
	}

	public static void writeLine(final double val) {
		System.out.println(val);
	}

	public static void writeLine(final String val) {
		System.out.println(val);
	}

	public static void writeLine(final Object val) {
		System.out.println(val);
	}

	public static void writeLine() {
		System.out.println();
	}

	public static void writeLine(@NotNull final byte[] val) {
		writeLine("[{}] {}", val.length, Bytez.toHex(":", val));
	}

	public static void writeLine(@NotNull final int[] val) {
		System.out.println(Numberz.join(", ", val));
	}

	public static void writeLine(@NotNull final String[] val) {
		System.out.println(Stringz.join(", ", val));
	}

	/**
	 *
	 * @param format eg. Hello, {}. I'm {} year-old.
	 * @param params params
	 * @throws IllegalFormatException argument length mismatches
	 */
	public static void writeLine(@NotNull final String format, final Object... params)
			throws IllegalFormatException {
		System.out.println(Stringz.format(format, params));
	}

	public static void writeLines(@NotNull final String... lines)
			throws IllegalFormatException {
		for (String line : lines) {
			System.out.println(line);
		}
	}

	public static void writeLine(final Throwable throwable) {
		System.err.println(getStackTrace(throwable));
	}

	@NotNull
	public static String getStackTrace(@NotNull Throwable e) {

		try (StringWriter str = new StringWriter();
		     PrintWriter writer = new PrintWriter(str)) {
			e.printStackTrace(writer);
			return str.toString();

		} catch (IOException ex) {
			return Stringz.EMPTY_STRING;
		}

	}

	public static String readLine()
			throws NoSuchElementException, IllegalStateException {
		return new Scanner(System.in).nextLine();
	}

	public static String readLine(@NotNull final String message)
			throws NoSuchElementException, IllegalStateException {
		write(message + " ");
		return new Scanner(System.in).nextLine();
	}

	public static String readLine(@NotNull final String message, final String defaultValue)
			throws NoSuchElementException, IllegalStateException {
		write(message + " ");
		String val = new Scanner(System.in).nextLine();
		return (Stringz.isEmpty(val)) ? defaultValue : val;
	}

	public static String readPattern(@NotNull final Pattern pattern)
			throws NoSuchElementException, IllegalStateException {
		return new Scanner(System.in).next(pattern);
	}

	public static String readPattern(@NotNull final String message, @NotNull final Pattern pattern)
			throws NoSuchElementException, IllegalStateException {
		write(message + " ");
		return new Scanner(System.in).next(pattern);
	}

	public static String readPattern(@NotNull final String pattern)
			throws NoSuchElementException, IllegalStateException {
		return new Scanner(System.in).next(pattern);
	}

	public static String readPattern(@NotNull final String message, @NotNull final String pattern)
			throws NoSuchElementException, IllegalStateException {
		write(message + " ");
		return new Scanner(System.in).next(pattern);
	}

	public static boolean readBoolean()
			throws InputMismatchException, NoSuchElementException, IllegalStateException {
		return new Scanner(System.in).nextBoolean();
	}

	public static boolean readBoolean(@NotNull final String message)
			throws NoSuchElementException, IllegalStateException {
		write(message + " ");
		try {
			return new Scanner(System.in).nextBoolean();
		} catch (InputMismatchException e) {
			return readBoolean(message);
		}
	}

	public static boolean readBoolean(@NotNull final String message, boolean defaultValue)
			throws NoSuchElementException, IllegalStateException {
		write(message + " ");
		try {
			return new Scanner(System.in).nextBoolean();
		} catch (InputMismatchException e) {
			return defaultValue;
		}
	}

	public static byte readByte()
			throws InputMismatchException, NoSuchElementException, IllegalStateException {
		return new Scanner(System.in).nextByte();
	}

	public static byte readByte(@NotNull final String message)
			throws NoSuchElementException, IllegalStateException {
		write(message + " ");
		try {
			return new Scanner(System.in).nextByte();
		} catch (InputMismatchException e) {
			return readByte(message);
		}
	}

	public static byte readByte(final int radix)
			throws InputMismatchException, IllegalArgumentException, NoSuchElementException, IllegalStateException {
		return new Scanner(System.in).nextByte(radix);
	}

	public static byte readByte(@NotNull final String message, final int radix)
			throws IllegalArgumentException, NoSuchElementException, IllegalStateException {
		write(message + " ");
		try {
			return new Scanner(System.in).nextByte(radix);
		} catch (InputMismatchException e) {
			return readByte(message, radix);
		}
	}

	public static short readShort()
			throws InputMismatchException, NoSuchElementException, IllegalStateException {
		return new Scanner(System.in).nextShort();
	}

	public static short readShort(@NotNull final String message)
			throws NoSuchElementException, IllegalStateException {
		write(message + " ");
		try {
			return new Scanner(System.in).nextShort();
		} catch (InputMismatchException e) {
			return readShort(message);
		}
	}

	public static short readShort(final int radix)
			throws InputMismatchException, IllegalArgumentException, NoSuchElementException, IllegalStateException {
		return new Scanner(System.in).nextShort(radix);
	}

	public static short readShort(@NotNull final String message, final int radix)
			throws IllegalArgumentException, NoSuchElementException, IllegalStateException {
		write(message + " ");
		try {
			return new Scanner(System.in).nextShort(radix);
		} catch (InputMismatchException e) {
			return readShort(message, radix);
		}
	}

	public static int readInt()
			throws InputMismatchException, NoSuchElementException, IllegalStateException {
		return new Scanner(System.in).nextInt();
	}

	public static int readInt(@NotNull final String message)
			throws NoSuchElementException, IllegalStateException {
		write(message + " ");
		try {
			return new Scanner(System.in).nextInt();
		} catch (InputMismatchException e) {
			return readInt(message);
		}
	}

	public static int readInt(final int radix)
			throws InputMismatchException, IllegalArgumentException, NoSuchElementException, IllegalStateException {
		return new Scanner(System.in).nextInt(radix);
	}

	public static int readInt(@NotNull final String message, final int radix)
			throws IllegalArgumentException, NoSuchElementException, IllegalStateException {
		write(message + " ");
		try {
			return new Scanner(System.in).nextInt(radix);
		} catch (InputMismatchException e) {
			return readInt(message, radix);
		}
	}

	public static int readInt(@NotNull final String message, final int radix, final int defVal)
			throws IllegalArgumentException, NoSuchElementException, IllegalStateException {
		write(message + " ");
		try {
			return new Scanner(System.in).nextInt(radix);
		} catch (InputMismatchException e) {
			return defVal;
		}
	}

	public static long readLong()
			throws InputMismatchException, NoSuchElementException, IllegalStateException {
		return new Scanner(System.in).nextLong();
	}

	public static long readLong(@NotNull final String message)
			throws NoSuchElementException, IllegalStateException {
		write(message + " ");
		try {
			return new Scanner(System.in).nextLong();
		} catch (InputMismatchException e) {
			return readLong(message);
		}
	}

	public static long readLong(final int radix)
			throws InputMismatchException, IllegalArgumentException, NoSuchElementException, IllegalStateException {
		return new Scanner(System.in).nextLong(radix);
	}

	public static long readLong(@NotNull final String message, final int radix)
			throws IllegalArgumentException, NoSuchElementException, IllegalStateException {
		write(message + " ");
		try {
			return new Scanner(System.in).nextLong(radix);
		} catch (InputMismatchException e) {
			return readLong(message, radix);
		}
	}

	public static long readLong(@NotNull final String message, final int radix, final long defVal)
			throws IllegalArgumentException, NoSuchElementException, IllegalStateException {
		write(message + " ");
		try {
			return new Scanner(System.in).nextLong(radix);
		} catch (InputMismatchException e) {
			return defVal;
		}
	}

	public static float readFloat()
			throws InputMismatchException, NoSuchElementException, IllegalStateException {
		return new Scanner(System.in).nextFloat();
	}

	public static float readFloat(@NotNull final String message)
			throws NoSuchElementException, IllegalStateException {
		write(message + " ");
		try {
			return new Scanner(System.in).nextFloat();
		} catch (InputMismatchException e) {
			return readFloat(message);
		}
	}

	public static float readFloat(@NotNull final String message, final float defVal)
			throws NoSuchElementException, IllegalStateException {
		write(message + " ");
		try {
			return new Scanner(System.in).nextFloat();
		} catch (InputMismatchException e) {
			return defVal;
		}
	}

	public static double readDouble()
			throws InputMismatchException, NoSuchElementException, IllegalStateException {
		return new Scanner(System.in).nextDouble();
	}

	public static double readDouble(@NotNull final String message)
			throws NoSuchElementException, IllegalStateException {
		write(message + " ");
		try {
			return new Scanner(System.in).nextDouble();
		} catch (InputMismatchException e) {
			return readDouble(message);
		}
	}

	public static double readDouble(@NotNull final String message, final double defVal)
			throws NoSuchElementException, IllegalStateException {
		write(message + " ");
		try {
			return new Scanner(System.in).nextDouble();
		} catch (InputMismatchException e) {
			return defVal;
		}
	}

	public static BigDecimal readBigDecimal()
			throws InputMismatchException, NoSuchElementException, IllegalStateException {
		return new Scanner(System.in).nextBigDecimal();
	}

	public static BigDecimal readBigDecimal(@NotNull final String message)
			throws NoSuchElementException, IllegalStateException {
		write(message + " ");
		try {
			return new Scanner(System.in).nextBigDecimal();
		} catch (InputMismatchException e) {
			return readBigDecimal(message);
		}
	}

	public static BigInteger readBigInteger()
			throws InputMismatchException, NoSuchElementException, IllegalStateException {
		return new Scanner(System.in).nextBigInteger();
	}

	public static BigInteger readBigInteger(@NotNull final String message)
			throws NoSuchElementException, IllegalStateException {
		write(message + " ");
		try {
			return new Scanner(System.in).nextBigInteger();
		} catch (InputMismatchException e) {
			return readBigInteger(message);
		}
	}

	public static BigInteger readBigInteger(final int radix)
			throws InputMismatchException, NoSuchElementException, IllegalStateException {
		return new Scanner(System.in).nextBigInteger(radix);
	}

	public static BigInteger readBigInteger(@NotNull final String message, final int radix)
			throws NoSuchElementException, IllegalStateException {
		write(message + " ");
		try {
			return new Scanner(System.in).nextBigInteger(radix);
		} catch (InputMismatchException e) {
			return readBigInteger(message, radix);
		}
	}

	/**
	 * Works only on a real console.
	 *
	 * @return
	 * @throws RuntimeException
	 */
	public static char[] readPassword() throws RuntimeException {
		try {
			return System.console().readPassword();
		} catch (NullPointerException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Works only on a real console.
	 *
	 * @param message
	 * @return
	 * @throws RuntimeException
	 */
	public static char[] readPassword(@NotNull final String message) throws RuntimeException {
		write(message + " ");
		return readPassword();
	}
}
