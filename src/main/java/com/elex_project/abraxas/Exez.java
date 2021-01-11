/*
 * Project Abraxas (a.k.a core-utils)
 * http://www.elex-project.com/
 * Copyright (c) 2019. Elex. All Rights Reserved.
 */

package com.elex_project.abraxas;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

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
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@NotNull
	public static String exec(@NotNull final String command) throws IOException, InterruptedException {
		//Runtime runtime = Runtime.getRuntime();
		Process process = Runtime.getRuntime().exec(command);
		process.waitFor();

		String str = IOz.readStringFrom(process.getInputStream());
		process.destroy();

		return str;
	}

	/**
	 * 셧다운 훅
	 *
	 * @param th
	 * @see Runtime#addShutdownHook(Thread)
	 */
	public static void addShutdownHook(final Thread th) {
		Runtime.getRuntime().addShutdownHook(th);
	}

	/**
	 * @param th
	 * @see Runtime#removeShutdownHook(Thread)
	 */
	public static void removeShutdownHook(final Thread th) {
		Runtime.getRuntime().removeShutdownHook(th);
	}
}
