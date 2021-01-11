/*
 * Project Abraxas (a.k.a core-utils)
 * http://www.elex-project.com/
 * Copyright (c) 2019. Elex. All Rights Reserved.
 */

package com.elex_project.abraxas;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;

/**
 * 스톱워치
 * Totally Rewritten @ 2019-04-28
 *
 * @author Elex
 */
public final class StopWatch {

	private static final byte STATE_RUNNING = 0x00;
	//private static final byte STATE_STOPPED = 0x01;
	private static final byte STATE_PAUSED = 0x0f;

	private long baseElapsedTime;
	private long startedTime;

	private byte state = STATE_PAUSED;

	private final boolean useNanoTime;

	/**
	 * Stop Watch with milli precision
	 *
	 * @return stop watch
	 */
	public static StopWatch newStopWatch() {
		return new StopWatch();
	}

	/**
	 * Stop Watch with a nano precision
	 *
	 * @return stop watch
	 */
	public static StopWatch newNanoStopWatch() {
		return new StopWatch(true);
	}

	/**
	 * 생성하면 시간 측정이 시작된다.
	 */
	private StopWatch() {
		this(false);
	}

	private StopWatch(final boolean useNanoTime) {
		this.useNanoTime = useNanoTime;
		start();
	}

	private long now() {
		if (useNanoTime) {
			return System.nanoTime();
		} else {
			return System.currentTimeMillis();
		}
	}

	/**
	 * 소요 시간을 반환한다.
	 *
	 * @return 밀리초 or 나노초
	 */
	public long getElapsed() {
		if (isRunning()) {
			return (now() - startedTime) + baseElapsedTime;
		} else {
			return baseElapsedTime;
		}
	}

	@NotNull
	public Duration getDuration() {
		if (useNanoTime){
			return Duration.ofNanos(getElapsed());
		} else {
			return Duration.ofMillis(getElapsed());
		}
	}

	/**
	 * 측정을 시작한다.
	 * 이미 측정을 시작한 경우에는 아무 효과가 없다.
	 */
	public void start() {
		if (isPaused()) {
			this.baseElapsedTime = 0;
			this.startedTime = now();
			this.state = STATE_RUNNING;
		}
	}

	/**
	 * 일시 중지
	 * 이미 중지된 상태라면 아무 일도 일어나지 않는다.
	 */
	public void pause() {
		if (isRunning()) {
			this.baseElapsedTime = getElapsed();
			this.state = STATE_PAUSED;
		}
	}

	/**
	 * 일시 중지 상태에서 재개한다.
	 * 일시 중지 상태가 아닌 때에는 아무 일도 일어나지 않는다.
	 */
	public void resume() {
		if (isPaused()) {
			this.startedTime = now();
			this.state = STATE_RUNNING;
		}
	}

	public boolean isRunning() {
		return this.state == STATE_RUNNING;
	}

	public boolean isPaused() {
		return this.state == STATE_PAUSED;
	}
}
