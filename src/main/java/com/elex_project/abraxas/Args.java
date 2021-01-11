/*
 * Project Abraxas (a.k.a core-utils)
 * http://www.elex-project.com/
 * Copyright (c) 2020. Elex. All Rights Reserved.
 */

package com.elex_project.abraxas;

import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * CLI 매개변수 파서
 *
 * <p>
 * 지원 포맷
 * <li>option</li>
 * <li>-option</li>
 * <li>--option</li>
 * <li>-option:value</li>
 * <li>-option=value</li>
 * <li>--option=value</li>
 *
 * <pre>
 * {@code
 * public static void main(String... args){
 *     Args parsedArgs = Args.parse(args);
 *     parsedArgs.get("message").orElse("Not exists.")
 * }
 * }
 * </pre>
 *
 * @author Elex
 */
@ToString
public final class Args {
	private final String[] args;
	private final Map<String, String> map;

	/**
	 * Parse args
	 *
	 * @param args arguments from main method
	 * @return parsed args
	 */
	public static Args parse(@NotNull final String... args) {
		return new Args(args);
	}

	private Args(@NotNull final String... args) {
		this.args = args;
		this.map = new HashMap<>();

		parse();
	}

	private void parse() {
		for (int i = 0; i < length(); i++) {
			String str = get(i).replaceFirst("^[-]+", Stringz.EMPTY_STRING);
			String[] strs = str.split("[=:]");
			if (strs.length > 1) {
				this.map.put(strs[0], strs[1]);
			} else {
				this.map.put(strs[0], null);
			}
		}
	}

	/**
	 * how many args?
	 *
	 * @return int
	 */
	private int length() {
		return args.length;
	}

	/**
	 * get raw arg
	 *
	 * @param idx index
	 * @return string
	 */
	private String get(final int idx) {
		return args[idx];
	}

	/**
	 * 매개변수 중 해당 키가 포함되어 있는지 확인.
	 *
	 * @param key 매개변수 키
	 * @return 있으면 참.
	 */
	public boolean has(@NotNull final String key) {
		return this.map.containsKey(key);
	}

	/**
	 * 매개변수로 전달된 키의 값
	 *
	 * @param key 매개변수 키
	 * @return Optional
	 */
	@NotNull
	public Optional<String> get(@NotNull final String key) {
		return Optional.of(this.map.get(key));
	}

	/**
	 * 매개변수로 전달된 키의 값
	 *
	 * @param key 매개변수 키
	 * @return 매개변수로 전달된 값 없이 키만 있는 경우(ex. '--opt'), null일 수 있음.
	 */
	@Nullable
	public String getOrNull(@NotNull final String key) {
		return this.map.get(key);
	}

	/**
	 * 전달된 키 셋.
	 *
	 * @return 키 셋
	 */
	public Set<String> keys() {
		return this.map.keySet();
	}
}
