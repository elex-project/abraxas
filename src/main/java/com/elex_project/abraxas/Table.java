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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * 2차원 테이블
 *
 * @param <R> 행
 * @param <C> 열
 * @param <V> 값
 * @author Elex
 */
public class Table<R, C, V> {
	private final Map<R, Map<C, V>> table;

	public Table() {
		table = new HashMap<>();
	}

	/**
	 * 행렬의 값을 반환, 아무 것도 없으면 널.
	 *
	 * @param row 행
	 * @param col 열
	 * @return 값
	 */
	@Nullable
	public V getOrNull(R row, C col) {
		Map<C, V> r;
		if (null != (r = getOrNull(row))) {
			return r.get(col);
		}
		return null;
	}

	@NotNull
	public Optional<V> get(R row, C col) {
		return Optional.ofNullable(getOrNull(row, col));
	}

	/**
	 * 행을 가져옴
	 *
	 * @param row 행
	 * @return 행
	 */
	@Nullable
	public Map<C, V> getOrNull(R row) {
		return table.get(row);
	}

	@NotNull
	public Optional<Map<C, V>> get(R row) {
		return Optional.ofNullable(getOrNull(row));
	}

	/**
	 * 값을 지정
	 *
	 * @param row 행
	 * @param col 열
	 * @param val 값
	 */
	public void set(@NotNull R row, @NotNull C col, @Nullable V val) {
		Map<C, V> r;
		if (null == (r = table.get(row))) {
			r = new HashMap<>();
			table.put(row, r);
		}
		r.put(col, val);
	}

	/**
	 * 테이블 청소
	 */
	public void clear() {
		table.clear();
	}

	/**
	 * 행 청소
	 *
	 * @param row 행
	 */
	public void clear(R row) {
		Map<C, V> r;
		if (null != (r = getOrNull(row))) {
			r.clear();
		}
	}

	/**
	 * 행 인덱스
	 *
	 * @return 인덱스
	 */
	public Set<R> getRowKeys() {
		return table.keySet();
	}

	/**
	 * 열 인덱스
	 *
	 * @param row 행
	 * @return 행 인덱스
	 */
	@Nullable
	public Set<C> getColumnKeySet(R row) {
		Map<C, V> r;
		if (null != (r = getOrNull(row))) {
			return r.keySet();
		}
		return null;
	}

	/**
	 * 열 인덱스, 모든 열을 탐색해서 모은다.
	 *
	 * @return 열 인덱스
	 */
	public Set<C> getColumnKeySet() {
		Set<C> result = new HashSet<>();
		for (R r : getRowKeys()) {
			Set<C> columnKeys = getColumnKeySet(r);
			if (null != columnKeys) result.addAll(columnKeys);
		}
		return result;
	}

	/**
	 * 순차적으로 데이터 방문
	 *
	 * @param walker 워커
	 * @see TableWalker
	 */
	public void walk(@NotNull TableWalker<R, C, V> walker) {
		Set<R> rowKeys = getRowKeys();
		for (R row : rowKeys) {
			if (null != walker) {
				if (!walker.beforeVisitRow(row)) continue;
			}
			Set<C> columnKeys = getColumnKeySet(row);
			if (null != columnKeys) {
				for (C col : columnKeys) {
					if (null != walker) {
						if (!walker.beforeVisitCol(row, col)) continue;
					}
					V val = getOrNull(row, col);
					if (null != val && null != walker) {
						walker.onVisit(row, col, val);
					}
				}
			}
		}
	}


}