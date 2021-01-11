package com.elex_project.abraxas;

import org.jetbrains.annotations.NotNull;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.regex.Pattern;

/**
 * Regular Expression and Glob
 *
 * @author Elex
 */
public final class RegExz {

	private RegExz() {
	}

	public static boolean matches(@NotNull Pattern pattern, String input) {
		return pattern.matcher(input).matches();
	}

	/**
	 * Glob path matcher
	 *
	 * @param glob glob pattern
	 * @param path path
	 * @return matches?
	 */
	public static boolean matches(String glob, Path path) {
		PathMatcher matcher = FileSystems.getDefault().getPathMatcher(glob);
		return matcher.matches(path);
	}

	/**
	 * Glob matcher
	 *
	 * @param glob  glob pattern
	 * @param input text
	 * @return matches?
	 */
	public static boolean matches(String glob, String input) {
		return matches(Pattern.compile(regexFromGlob(glob)), input);
	}

	/**
	 * *        없는 것을 포함한 어떠한 수의 문자라도 일치<br>
	 * ?	    어떠한 하나의 문자를 일치<br>
	 * [abc]	대괄호 안의 하나의 문자를 일치<br>
	 * [a-z]	대괄호 안의 범위에 속하는 하나의 문자를 일치<br>
	 *
	 * @param glob
	 * @return
	 */
	@NotNull
	public static Pattern compileGlob(@NotNull String glob) {
		return Pattern.compile(regexFromGlob(glob));
	}

	@NotNull
	public static Pattern compile(@NotNull String regex) {
		return Pattern.compile(regex);
	}

	@NotNull
	public static Pattern compile(@NotNull String regex, int flags) {
		return Pattern.compile(regex, flags);
	}

	@NotNull
	public static String regexFromGlob(@NotNull String glob) {
		return regexFromGlob1(glob, true);
	}

	@NotNull
	public static String regexFromGlobWithoutBoundary(@NotNull String glob) {
		return regexFromGlob1(glob, false);
	}

	@NotNull
	private static String regexFromGlob1(@NotNull String glob, boolean withBoundary) {
		StringBuilder sb = new StringBuilder();
		if (withBoundary) sb.append('^');
		for (int i = 0; i < glob.length(); ++i) {
			final char c = glob.charAt(i);
			switch (c) {
				case '*':
					sb.append(".*");
					break;
				case '?':
					sb.append('.');
					break;
				case '.':
					sb.append("\\.");
					break;
				case '\\':
					sb.append("\\\\");
					break;
				default:
					sb.append(c);
			}
		}
		if (withBoundary) sb.append('$');
		return sb.toString();
	}

	@NotNull
	private static String regexFromGlob2(@NotNull String line) {
		//LOG.info("got line [" + line + "]");
		line = line.trim();
		int strLen = line.length();
		StringBuilder sb = new StringBuilder(strLen);
		// Remove beginning and ending * globs because they're useless
		if (line.startsWith("*")) {
			line = line.substring(1);
			strLen--;
		}
		if (line.endsWith("*")) {
			line = line.substring(0, strLen - 1);
			strLen--;
		}
		boolean escaping = false;
		int inCurlies = 0;
		for (char currentChar : line.toCharArray()) {
			switch (currentChar) {
				case '*':
					if (escaping)
						sb.append("\\*");
					else
						sb.append(".*");
					escaping = false;
					break;
				case '?':
					if (escaping)
						sb.append("\\?");
					else
						sb.append('.');
					escaping = false;
					break;
				case '.':
				case '(':
				case ')':
				case '+':
				case '|':
				case '^':
				case '$':
				case '@':
				case '%':
					sb.append('\\');
					sb.append(currentChar);
					escaping = false;
					break;
				case '\\':
					if (escaping) {
						sb.append("\\\\");
						escaping = false;
					} else
						escaping = true;
					break;
				case '{':
					if (escaping) {
						sb.append("\\{");
					} else {
						sb.append('(');
						inCurlies++;
					}
					escaping = false;
					break;
				case '}':
					if (inCurlies > 0 && !escaping) {
						sb.append(')');
						inCurlies--;
					} else if (escaping)
						sb.append("\\}");
					else
						sb.append("}");
					escaping = false;
					break;
				case ',':
					if (inCurlies > 0 && !escaping) {
						sb.append('|');
					} else if (escaping)
						sb.append("\\,");
					else
						sb.append(",");
					break;
				default:
					escaping = false;
					sb.append(currentChar);
			}
		}
		return sb.toString();
	}
}
