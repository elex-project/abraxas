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

import java.util.ArrayList;
import java.util.List;

/**
 * Tree
 *
 * @param <T>
 * @author Elex
 */
public class TreeNode<T> implements TreeItem<T> {
	private TreeItem<T> parent;
	private final List<TreeItem<T>> children;
	private T value;

	public TreeNode(T value) {
		this(value, null);
	}

	public TreeNode(T value, TreeItem<T> parent) {
		this(value, parent, new ArrayList<>());
	}

	public TreeNode(T value, TreeItem<T> parent, List<TreeItem<T>> children) {
		this.parent = parent;
		this.value = value;
		this.children = children;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getValue() {
		return value;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(T value) {
		this.value = value;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public TreeItem<T> getParent() {
		return parent;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setParent(TreeItem<T> parent) {
		this.parent = parent;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public TreeItem<T> getRoot() {
		TreeItem<T> item = this;
		//TreeItem p;
		while (true) {
			if (item.isTreeRoot()) return item;
			item = item.getParent();
		}
		//return item;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isTreeRoot() {
		return null == this.parent;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getDepth() {
		int depth = 0;
		TreeItem<T> p = this.getParent();
		while (p != null) {
			depth++;
			p = p.getParent();
		}
		return depth;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasChild(TreeItem<T> child) {
		return this.children.contains(child);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasChild() {
		return this.children.size() > 0;
	}
	/**
	 * {@inheritDoc}
	 */
	@NotNull
	@Override
	public List<TreeItem<T>> getChildren() {
		return children;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void appendChildren(@NotNull TreeItem<T>[] children) {
		for (TreeItem<T> child : children) {
			appendChild(child);
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeChild(TreeItem<T> child) {
		if (this.children.contains(child)) {
			child.setParent(null);
			this.children.remove(child);
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeChildren() {
		for (TreeItem<T> child : this.children) {
			child.setParent(null);
			this.children.remove(child);
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeChildren(@NotNull TreeItem<T>[] children) {
		for (TreeItem<T> child : children) {
			removeChild(child);
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void moveTo(@NotNull TreeItem<T> parent) {
		this.parent.removeChild(this);
		parent.appendChild(this);
	}
	/**
	 * {@inheritDoc}
	 */
	@Nullable
	@Override
	public TreeItem<T> getNextSibling() throws IndexOutOfBoundsException {
		try {
			int index = this.parent.getChildren().indexOf(this);
			return this.parent.getChildren().get(index + 1);
		} catch (ClassCastException | NullPointerException e) {
			return null;
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Nullable
	@Override
	public TreeItem<T> getPrevSibling() throws IndexOutOfBoundsException {
		try {
			int index = this.parent.getChildren().indexOf(this);
			return this.parent.getChildren().get(index - 1);
		} catch (ClassCastException | NullPointerException e) {
			return null;
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public TreeItem<T> getFirstChild() throws IndexOutOfBoundsException {
		return this.children.get(0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void walk(@NotNull TreeWalker<T> treeWalker) {
		treeWalker.onVisit(this);
		for (TreeItem<T> child : this.getChildren()) {
			child.walk(treeWalker);
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void appendChild(@NotNull TreeItem<T> child) {
		child.setParent(this);
		this.children.add(child);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void appendChild(int idx, @NotNull TreeItem<T> child) {
		child.setParent(this);
		this.children.add(idx, child);
	}
}
