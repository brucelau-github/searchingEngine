/*
 * Copyright 2011-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * @author Jianye Liu brucelau.email@gmail.com
 */
package util.trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * An implementation of a trie that dynamically grows and shrinks with word
 * insertions and removals and supports approximate matching of words using edit
 * distance and word frequency.
 * 
 * This trie implementation is not thread-safe because I didn't need it to be,
 * but it would be easy to change.
 * 
 * @original author Brian Harris (brian_bcharris_net)
 * @refracted by Jianye Liu
 */
public class TrieImpl implements Trie {

	// dummy node for root of trie
	protected TrieNode root;

	// current number of unique words in trie
	protected int size;

	// if this is a case sensitive trie
	protected boolean caseSensitive;

	/**
	 * @param caseSensitive
	 *            If this Trie should be case-insensitive to the words it
	 *            encounters. Case-insensitivity is accomplished by converting
	 *            String arguments to all functions to lower-case before
	 *            proceeding.
	 */
	public TrieImpl(boolean caseSensitive) {
		root = new TrieNode((char) 0);
		size = 0;
		this.caseSensitive = caseSensitive;
	}

	public int insert(String word) {
		if (word == null)
			return 0;

		int i = root.insert(caseSensitive ? word : word.toLowerCase(), 0);

		if (i == 1)
			size++;

		return i;
	}

	public boolean remove(String word) {
		if (word == null)
			return false;

		if (root.remove(caseSensitive ? word : word.toLowerCase(), 0)) {
			size--;
			return true;
		}

		return false;
	}

	public int frequency(String word) {
		if (word == null)
			return 0;

		TrieNode n = root.lookup(caseSensitive ? word : word.toLowerCase(), 0);
		return n == null ? 0 : n.occurances;
	}

	public boolean contains(String word) {
		if (word == null)
			return false;

		return root.lookup(caseSensitive ? word : word.toLowerCase(), 0) != null;
	}

	public int size() {
		return size;
	}

	@Override
	public String toString() {
		return root.toString();
	}

	public String bestMatch(String word, long max_time) {

		if (!caseSensitive)
			word = word.toLowerCase();

		// we store candidate nodes in a pqueue in an attempt to find the
		// optimal
		// match ASAP which can be useful for a necessary early exit
		PriorityQueue<DYMNode> pq = new PriorityQueue<DYMNode>();

		DYMNode best = new DYMNode(root, Distance.LD("", word), "", false);
		DYMNode cur = best;
		TrieNode tmp;
		int count = 0;
		long start_time = System.currentTimeMillis();

		while (true) {
			if (count++ % 1000 == 0 && (System.currentTimeMillis() - start_time) > max_time)
				break;

			if (cur.node.children != null) {
				for (char c : cur.node.children.keySet()) {
					tmp = cur.node.children.get(c);
					String tWord = cur.word + c;
					int distance = Distance.LD(tWord, word);

					// only add possibly better matches to the pqueue
					if (distance <= cur.distance) {
						if (tmp.occurances == 0)
							pq.add(new DYMNode(tmp, distance, tWord, false));
						else
							pq.add(new DYMNode(tmp, distance, tWord, true));
					}
				}
			}

			DYMNode n = pq.poll();

			if (n == null)
				break;

			cur = n;
			if (n.wordExists)
				// if n is more optimal, set it as best
				if (n.distance < best.distance
						|| (n.distance == best.distance && n.node.occurances > best.node.occurances))
				best = n;
		}
		return best.word;
	}
}
	
