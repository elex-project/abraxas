/*
 * Project Abraxas (a.k.a core-utils)
 * http://www.elex-project.com/
 * Copyright (c) 2017. Elex. All Rights Reserved.
 */

package com.elex_project.abraxas;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * System Environments
 *
 * @author Elex
 */
public final class Env {
	/**
	 * .elex
	 */
	private static final String _ELEX = ".elex";
	/**
	 * .uuid
	 */
	private static final String _UUID = ".uuid";
	/**
	 * os.name
	 */
	private static final String OS_NAME = "os.name";
	/**
	 * os.version
	 */
	private static final String OS_VERSION = "os.version";
	/**
	 * os.arch
	 */
	private static final String OS_ARCH = "os.arch";
	/**
	 * java.runtime.name
	 */
	private static final String JAVA_RUNTIME_NAME = "java.runtime.name";
	/**
	 * java.vendor
	 */
	private static final String JAVA_VENDOR = "java.vendor";
	/**
	 * java.version
	 */
	private static final String JAVA_VERSION = "java.version";
	/**
	 * user.name
	 */
	private static final String USER_NAME = "user.name";
	/**
	 * user.dir
	 */
	private static final String USER_DIR = "user.dir";
	/**
	 * user.home
	 */
	private static final String USER_HOME = "user.home";

	private static String uuid;

	private Env() {

	}

	/**
	 * '사용자홈/{path}/.uuid' 파일에 저장해둔다.
	 *
	 * @return uuid
	 */
	public static String getUUID(final String folder) {
		if (null == uuid) {
			try {
				File dir = new File(Env.getUserHome(), folder);
				dir.mkdirs();
				File file = new File(dir, _UUID);

				try {
					uuid = IOz.readStringFrom(file);
				} catch (Exception e) {
					uuid = Stringz.generateUUID();
					try {
						IOz.writeStringTo(uuid, file);
					} catch (IOException ignore) {
					}
				}
			} catch (SecurityException e) {
				uuid = Stringz.generateUUID();
			}
		}
		return uuid;
	}

	public static String getOsName() {
		return System.getProperty(OS_NAME, Stringz.UNKNOWN);
	}

	public static String getOsVersion() {
		return System.getProperty(OS_VERSION, Stringz.EMPTY_STRING);
	}

	public static String getOsArch() {
		return System.getProperty(OS_ARCH, Stringz.EMPTY_STRING);
	}

	public static boolean isWindows() {
		return getOsName().toLowerCase().startsWith("windows");
	}

	/**
	 * @return eg. Java(TM) SE Runtime Environment
	 */
	public static String getJavaName() {
		return System.getProperty(JAVA_RUNTIME_NAME, Stringz.UNKNOWN);
	}

	/**
	 * @return eg. Oracle Corporation
	 */
	public static String getJavaVendor() {
		return System.getProperty(JAVA_VENDOR, Stringz.EMPTY_STRING);
	}

	/**
	 * Java 8 or lower: 1.6.0_23, 1.7.0, 1.7.0_80, 1.8.0_211
	 * Java 9 or higher: 9.0.1, 11.0.4, 12, 12.0.1
	 *
	 * @return version string
	 */
	public static String getJavaVersion() {
		return System.getProperty(JAVA_VERSION, Stringz.EMPTY_STRING);
	}

	public static int getJVMVersion() {
		final String[] vers = getJavaVersion().split("\\.");
		if (vers[0].equals("1")) {
			return Integer.parseInt(vers[1]);
		} else {
			return Integer.parseInt(vers[0]);
		}
	}

	public static String getUserName() {
		return System.getProperty(USER_NAME, Stringz.UNKNOWN);
	}

	/**
	 * @return eg. C:\Users\Elex\Workspace\java-lib\core-utils
	 */
	public static String getUserDir() {
		return System.getProperty(USER_DIR, Stringz.EMPTY_STRING);
	}

	/**
	 * @return eg. C:\Users\Elex
	 */
	public static String getUserHome() {
		return System.getProperty(USER_HOME, Stringz.EMPTY_STRING);
	}

	/**
	 * 한국어인가?
	 *
	 * @return t/f
	 */
	public static boolean isKorean() {
		Locale l = Locale.getDefault();
		return l.getLanguage().equals(Locale.KOREAN.getLanguage());
	}

	/**
	 * 디폴트 로케일의 문자열 표현
	 *
	 * @return eg. ko-KR, en-US
	 */
	@NotNull
	public static String getLocale() {
		return localeToString(Locale.getDefault());
	}

	/**
	 * 로케일 문자열
	 * 로케일을 직접 문자열로 찍으면 가로바 대신 언더바로 표현되는데,
	 * 이걸 쓰면 가로 바로 표현된다.
	 *
	 * @return eg. ko-KR, en-US
	 */
	@NotNull
	public static String localeToString(@NotNull final Locale locale) {
		return locale.getLanguage().toLowerCase() + '-' + locale.getCountry().toUpperCase();
	}

	/**
	 * new String[]{언어, 국가}
	 *
	 * @return eg. {ko, KR}
	 */
	@NotNull
	public static String[] getLocaleAsArray() {
		return getLocaleAsArray(Locale.getDefault());
	}

	@NotNull
	@Contract("_ -> new")
	public static String[] getLocaleAsArray(@NotNull final Locale locale) {
		return new String[]{locale.getLanguage().toLowerCase(), locale.getCountry().toUpperCase()};
	}

	/**
	 * 국가명을 한글로
	 *
	 * @return eg. 대한민국
	 */
	public static String getCountry() {
		return getCountry(Locale.getDefault());
	}

	public static String getCountry(@NotNull final Locale locale) {
		return getCountry(locale, Locale.KOREAN);
	}

	public static String getCountry(@NotNull final Locale locale, @NotNull final Locale inLocale) {
		return locale.getDisplayCountry(inLocale);
	}

	/**
	 * 언어명을 한글로
	 *
	 * @return eg. 한국어
	 */
	public static String getLanguage() {
		return getLanguage(Locale.getDefault());
	}

	public static String getLanguage(@NotNull final Locale locale) {
		return getLanguage(locale, Locale.KOREAN);
	}

	public static String getLanguage(@NotNull final Locale locale, @NotNull final Locale inLocale) {
		return locale.getDisplayLanguage(inLocale);
	}

	public static Locale[] getAvailableLocales() {
		return Locale.getAvailableLocales();
	}

}
