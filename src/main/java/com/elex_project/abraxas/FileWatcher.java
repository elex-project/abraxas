/*
 * Project Abraxas (a.k.a core-utils)
 * http://www.elex-project.com/
 * Copyright (c) 2019. Elex. All Rights Reserved.
 */

package com.elex_project.abraxas;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

/**
 * 파일 와쳐
 */
public final class FileWatcher {

	private WatchKey watchKey;
	private WatchService watchService;
	private Worker worker;
	private boolean isRunning = false;

	/**
	 * @param path 감시 대상
	 * @throws IOException
	 */
	public FileWatcher(final @NotNull Path path) throws IOException {
		this();
		addWatchPoint(path);
	}

	public FileWatcher(final String folder) throws IOException {
		this(Paths.get(folder));
	}

	/**
	 * addWatchPoint()를 실행해서 감시 폴더를 지정하시오.
	 *
	 * @throws IOException
	 */
	public FileWatcher() throws IOException {
		//watchService 생성
		watchService = FileSystems.getDefault().newWatchService();
	}

	public void addWatchPoint(@NotNull final Path path) throws IOException {
		//해당 디렉토리 경로에 와치서비스와 이벤트 등록
		path.register(watchService,
				StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_DELETE,
				StandardWatchEventKinds.ENTRY_MODIFY,
				StandardWatchEventKinds.OVERFLOW);
	}

	public void addWatchPointWithSubDirectories(final Path path) throws Exception {
		Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes basicFileAttributes) throws IOException {
				addWatchPoint(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	public void start(final Handler handler) {
		if (isRunning) return;

		worker = new Worker(handler);
		worker.start();
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void stop() {
		isRunning = false;
		try {
			if (null != worker) worker.join();
		} catch (InterruptedException e) {
			//L.e(TAG, e);
		}
	}

	public interface Handler {
		public void onCreated(final Path path);

		public void onDeleted(final Path path);

		public void onModified(final Path path);

		public void onOverflow(final Path path);
	}

	private class Worker extends Thread {
		private Handler handler;

		private Worker(Handler handler) {
			this.handler = handler;
		}

		@Override
		public void run() {
			isRunning = true;
			//L.v(TAG, "Worker is running");
			while (isRunning) {
				try {
					//이벤트가 오길 대기(Blocking)
					watchKey = watchService.take();
				} catch (InterruptedException e) {
					//L.e(TAG, e);
				}

				//이벤트들을 가져옴
				List<WatchEvent<?>> events = watchKey.pollEvents();
				for (WatchEvent<?> event : events) {
					//이벤트 종류
					WatchEvent.Kind<?> kind = event.kind();
					//경로
					Path path = (Path) event.context();
					if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
						handler.onCreated(path);
						//L.v(TAG, "생성; " + path.toAbsolutePath().toString());
					} else if (kind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
						handler.onDeleted(path);
						//L.v(TAG, "제거; " + path.toAbsolutePath().toString());
					} else if (kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
						handler.onModified(path);
						//L.v(TAG, "수정; " + path.toAbsolutePath().toString());
					} else if (kind.equals(StandardWatchEventKinds.OVERFLOW)) {
						handler.onOverflow(path);
						//L.v(TAG, "오버플로; " + path.toAbsolutePath().toString());
					} else {
						//L.v(TAG, "모름; " + path.toAbsolutePath().toString());
					}
				}

				if (!watchKey.reset()) {
					try {
						watchService.close();
						isRunning = false;
					} catch (IOException e) {
						//L.e(TAG, e);
					}
				}

			}
		}
	}
}
