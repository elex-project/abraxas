/*
 * Project Abraxas (a.k.a core-utils)
 * http://www.elex-project.com/
 * Copyright (c) 2019. Elex. All Rights Reserved.
 */

package com.elex_project.abraxas;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

/**
 * Executor
 * @author Elex
 */
public final class Exez {
	private Exez() {
	}

	/**
	 * 명령 실행
	 *
	 * @param command 명령
	 * @return 실행 결과를 반환
	 * @throws IOException e
	 * @throws InterruptedException e
	 */
	@NotNull
	public static List<String> exec(@NotNull final String command) throws IOException, InterruptedException {
		//Runtime runtime = Runtime.getRuntime();
		final Process process = Runtime.getRuntime().exec(command);
		process.waitFor();

		final List<String> str = IOz.readLinesFrom(process.getInputStream());
		process.destroy();

		return str;
	}

	/**
	 * 셧다운 훅
	 *
	 * @param th thread
	 * @see Runtime#addShutdownHook(Thread)
	 */
	public static void addShutdownHook(final Thread th) {
		Runtime.getRuntime().addShutdownHook(th);
	}

	/**
	 * @param th thread
	 * @see Runtime#removeShutdownHook(Thread)
	 */
	public static void removeShutdownHook(final Thread th) {
		Runtime.getRuntime().removeShutdownHook(th);
	}
}
