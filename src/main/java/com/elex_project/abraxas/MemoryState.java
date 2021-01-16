/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Elex
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.elex_project.abraxas;

import lombok.ToString;

import java.text.NumberFormat;

/**
 * JVM runtime memory info
 *
 * @author Elex
 */
@ToString
public final class MemoryState {
	private static final NumberFormat PERCENT_FORMAT = NumberFormat.getPercentInstance();
	private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();

	static {
		PERCENT_FORMAT.setMaximumFractionDigits(2);
		NUMBER_FORMAT.setMaximumFractionDigits(2);
	}

	private long totalMemory;
	private long freeMemory;
	private long maxMemory;

	public MemoryState() {
		update();
	}

	/**
	 * Reload memory info
	 */
	public void update() {
		this.totalMemory = Runtime.getRuntime().totalMemory();
		this.freeMemory = Runtime.getRuntime().freeMemory();
		this.maxMemory = Runtime.getRuntime().maxMemory();
	}

	public int getAvailableProcessors(){
		return Runtime.getRuntime().availableProcessors();
	}

	/**
	 * @return size in bytes
	 * @see Runtime#totalMemory()
	 */
	public long getTotalMemory() {
		return totalMemory;
	}

	/**
	 * @return size in bytes
	 * @see Runtime#freeMemory()
	 */
	public long getFreeMemory() {
		return freeMemory;
	}

	/**
	 * @return size in bytes
	 * @see Runtime#maxMemory()
	 */
	public long getMaxMemory() {
		return maxMemory;
	}

	/**
	 * @return usage 0.0 ~ 1.0
	 */
	public float getUsage() {
		return (float) (totalMemory - freeMemory) / (float) totalMemory;
	}

	public String getUsageAsString() {
		return PERCENT_FORMAT.format(getUsage());
	}

	public String getTotalMemoryInMega() {
		return NUMBER_FORMAT.format(getTotalMemory() / 1024D / 1024D);
	}

	public String getTotalMemoryInGiga() {
		return NUMBER_FORMAT.format(getTotalMemory() / 1024D / 1024D / 1024D);
	}
}
