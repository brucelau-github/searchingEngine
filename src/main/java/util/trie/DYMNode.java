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
 * @author Brian Harris (brian_bcharris_net)
 * @author changed by Jianye Liu brucelau.email@gmail.com
 */
package util.trie;
/**
 * Utility class for finding a best match to a word.
 * 
 * @author Brian Harris (brian_bcharris_net)
 */
public class DYMNode implements Comparable<DYMNode> {
	public TrieNode node;
	public String word;
	public int distance;
	public boolean wordExists;

	public DYMNode(TrieNode node, int distance, String word, boolean wordExists) {
		this.node = node;
		this.distance = distance;
		this.word = word;
		this.wordExists = wordExists;
	}

	// break ties of distance with frequency
	public int compareTo(DYMNode n) {
		if (distance == n.distance)
			return n.node.occurances - node.occurances;
		return distance - n.distance;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null)
			return false;

		if (!(o instanceof DYMNode))
			return false;

		DYMNode n = (DYMNode) o;
		return distance == n.distance && n.node.occurances == node.occurances;
	}

	@Override
	public int hashCode() {
		int hash = 31;
		hash += distance * 31;
		hash += node.occurances * 31;
		return hash;
	}

	@Override
	public String toString() {
		return word + ":" + distance;
	}
}