package com.elex_project.abraxas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 윈도우즈 전용 유틸리티
 *
 * 2020-09-05: win-tools 프로젝트와 병합
 * @author Elex
 */
public final class Windowz {
	// tasklist
	private static final byte[] TASKLIST = {
			67, 55, 86, 37, 78, 34, 75, 56, 76
	};
	// taskkill /pid
	private static final byte[] TASKKILL_BY_PID = new byte[]{
			-70, -50, -81, -36, -73, -36, -75, -39, -75, -107, -70, -54, -93, -57, -25
	};

	private Windowz() {
	}

	public static List<WinTask> getTasks() throws IOException {
		List<WinTask> list = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				Runtime.getRuntime()
						.exec(Bytez.defuscate(TASKLIST)).getInputStream()))) {
			reader.lines().forEach(line -> {
				try {
					list.add(new WinTask(line));
				} catch (Throwable ignore) {
				}
			});
		}
		return list;
	}

	public static Stream<String> killTask(final WinTask task) throws IOException {
		return killTask(task.getPid());
	}

	static Stream<String> killTask(final long pid) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				Runtime.getRuntime()
						.exec(Bytez.defuscate(TASKKILL_BY_PID) + pid).getInputStream()))) {
			return reader.lines();
		}
	}

	public static final class WinTask {
		private final String name;
		private final long pid;

		private WinTask(final String s) throws NumberFormatException {
			String[] token = s.split("\\s+");
		/*for (String tok : token) {
			System.out.print(tok + "\t");
		}
		System.out.println();*/
			this.name = token[0];
			this.pid = Long.parseLong(token[1]);
		}

		public String getName() {
			return name;
		}

		public long getPid() {
			return pid;
		}

		@Override
		public String toString() {
			return name + ": " + pid;
		}
	}
}
