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

import java.util.List;

/**
 * 트리 아이템
 *
 * @param <T> 트리 노드의 값 자료형
 */
public interface TreeItem<T> {
	/**
	 * 노드의 값
	 *
	 * @return 값
	 */
	public T getValue();

	/**
	 * 노드의 값을 지정
	 *
	 * @param val 값
	 */
	public void setValue(T val);

	/**
	 * 부모 트리
	 *
	 * @return 루트 아이템인 경우 부모는 널
	 */
	@Nullable
	public TreeItem<T> getParent();

	/**
	 * 부모 지정
	 *
	 * @param parent 부모
	 */
	public void setParent(@Nullable TreeItem<T> parent);

	/**
	 * 현재 노드의 최상위 루트 노드를 반환
	 *
	 * @return 루트 노드
	 */
	@Nullable
	public TreeItem<T> getRoot();

	/**
	 * 부모 노드가 null이면, 루트 노드이다.
	 *
	 * @return true / false
	 */
	public boolean isTreeRoot();

	/**
	 * 깊이
	 *
	 * @return 루트이면 0
	 */
	public int getDepth();

	/**
	 * 이 자식이 네 자식이면 참.
	 *
	 * @param child 노드
	 * @return ..
	 */
	public boolean hasChild(TreeItem<T> child);

	/**
	 * 무자식이면 false
	 *
	 * @return t/f
	 */
	public boolean hasChild();

	/**
	 * 자식 노드
	 *
	 * @return 자식 노드 리스트
	 */
	@NotNull
	public List<TreeItem<T>> getChildren();

	/**
	 * 자식 추가
	 *
	 * @param child 자식
	 */
	public void appendChild(TreeItem<T> child);

	/**
	 * 자식 추가
	 *
	 * @param idx   추가할 위치, 기존 데이터는 뒤로 밀림.
	 * @param child 데려올 자식
	 */
	public void appendChild(int idx, TreeItem<T> child);

	/**
	 * 자식 추가
	 *
	 * @param children 데려올 자식
	 */
	public void appendChildren(TreeItem<T>[] children);

	/**
	 * 자식 제거, 벼려진 자식의 부모 노드는 널이다.
	 *
	 * @param child 버릴 자식
	 */
	public void removeChild(TreeItem<T> child);

	/**
	 * 모든 자식을 버림
	 */
	public void removeChildren();

	/**
	 * 자식을 버림
	 *
	 * @param children children
	 */
	public void removeChildren(TreeItem<T>[] children);

	/**
	 * 노드 이동
	 *
	 * @param parent new parent node
	 */
	public void moveTo(@NotNull TreeItem<T> parent);

	/**
	 * 다음 형제 노드. 여차 저차한 이유로 널일 수 있음
	 *
	 * @return 노드
	 * @throws IndexOutOfBoundsException 다음 노드가 없음
	 */
	@Nullable
	public TreeItem<T> getNextSibling() throws IndexOutOfBoundsException;

	/**
	 * 이전 형제 노드. 여차 저차한 이유로 널일 수 있음
	 *
	 * @return 노드
	 * @throws IndexOutOfBoundsException 이전 노드가 없음
	 */
	@Nullable
	public TreeItem<T> getPrevSibling() throws IndexOutOfBoundsException;

	/**
	 * 첫 번째 자식
	 *
	 * @return 노드
	 * @throws IndexOutOfBoundsException 자식이 없음
	 */
	public TreeItem<T> getFirstChild() throws IndexOutOfBoundsException;

	/**
	 * 트리 탐색
	 *
	 * @param treeWalker 워커
	 * @see TreeWalker tree walker
	 */
	public void walk(TreeWalker<T> treeWalker);
}