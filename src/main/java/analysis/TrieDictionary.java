package analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import util.trie.DYMNode;
import util.trie.Distance;
import util.trie.TrieImpl;
import util.trie.TrieNode;

public class TrieDictionary extends TrieImpl {
	private AnalyserDAO aDao = new AnalyserDAO();

	public TrieDictionary(boolean caseSensitive) {
		super(caseSensitive);
	}

	public int insert(String word, int currentPostion) {
		if (word == null)
			return 0;

		int i = root.insert(caseSensitive ? word : word.toLowerCase(), 0, currentPostion);

		if (i == 1)
			size++;

		return i;
	}

	public void saveNode() {
		// TODO Auto-generated method stub

	}

	public String dumpTrieNode() {
		StringBuilder tmp = new StringBuilder();
		root.dumpTrieNode(tmp, "|");
		return tmp.toString();
	}

	public String dumpTrieNodeChar() {
		StringBuffer tmp = new StringBuffer();
		root.dumpTrieNodeChar(tmp, "|");
		return tmp.toString();
	}

	public String dumpTrie() {
		StringBuilder toString = new StringBuilder();
		toString.append(TrieNode.counter.get() + "|");
		root.dumpTrie(toString, "|");
		return toString.toString();
	}

	public void load(String trieStructure) {
		String edgeToken = "\\|";
		LinkedList<String> edgeTrie = new LinkedList<String>(Arrays.asList(trieStructure.split(edgeToken)));
		int nodeSize = Integer.parseInt(edgeTrie.remove());
		TrieNode.counter.set(nodeSize);
		TrieNode[] nodes = new TrieNode[nodeSize];
		for (int i = 0; i < nodeSize; i++) {
			String tmp = aDao.getTrieChar(i + 1);
			String[] tmp1 = tmp.split(":");
			char c = tmp1[0].charAt(0);
			boolean isWord = Boolean.valueOf(tmp1[1]);
			nodes[i] = new TrieNode(c, i + 1, isWord);
		}
		for (String e : edgeTrie) {
			String[] node = e.split("-");
			int parent = Integer.parseInt(node[0]);
			int child = Integer.parseInt(node[1]);
			TrieNode.addChildren(nodes[parent - 1], nodes[child - 1]);

		}
		root = nodes[0];

	}
	public void loadTest(String trieStructure,String[] chars) {
		String edgeToken = "\\|";
		LinkedList<String> edgeTrie = new LinkedList<String>(Arrays.asList(trieStructure.split(edgeToken)));
		int nodeSize = Integer.parseInt(edgeTrie.remove());
		TrieNode.counter.set(nodeSize);
		TrieNode[] nodes = new TrieNode[nodeSize];
		for (int i = 0; i < nodeSize; i++) {
			String tmp = chars[i];
			String[] tmp1 = tmp.split(":");
			char c = tmp1[0].charAt(0);
			boolean isWord = Boolean.valueOf(tmp1[1]);
			nodes[i] = new TrieNode(c,i+1, isWord);
		}
		for (String e : edgeTrie) {
			String[] node = e.split("-");
			int parent = Integer.parseInt(node[0]);
			int child = Integer.parseInt(node[1]);
			TrieNode.addChildren(nodes[parent - 1], nodes[child - 1]);

		}
		root = nodes[0];

	}
	public void clearFrequecy() {
		root.clearOccurance();

	}

	public long getTrieNodeID(String keyword) {
		TrieNode n = root.lookup(caseSensitive ? keyword : keyword.toLowerCase(), 0);
		if (n == null)
			return -1;
		return n.getNodeID();
	}

	public String[] multiBestMatch(String word, int resultLength) {

		if (!caseSensitive)
			word = word.toLowerCase();
		if(word.isEmpty()) return new String[]{""};
		// we store candidate nodes in a pqueue in an attempt to find the
		// optimal
		// match ASAP which can be useful for a necessary early exit
		// lookup the node.
		TrieNode curNode = root.lookup(word, 0);

		PriorityQueue<DYMNode> pq = new PriorityQueue<DYMNode>();

		DYMNode best = new DYMNode(curNode, Distance.LD(word, word), word, false);
		DYMNode cur = best;
		List<String> words = new ArrayList<String>();
		TrieNode tmp;

		while (words.size() < resultLength) {

			if (cur.node.children != null) {
				for (char c : cur.node.children.keySet()) {
					tmp = cur.node.children.get(c);
					String tWord = cur.word + c;
					int distance = Distance.LD(tWord, word);

					// only add possibly better matches to the pqueue
					if (tmp.isWord)
						pq.add(new DYMNode(tmp, distance, tWord, true));
					else
						pq.add(new DYMNode(tmp, distance, tWord, false));

				}
			}

			DYMNode n = pq.poll();

			if (n == null)
				break;
			cur = n;
			if (n.wordExists)
				// if n is more optimal, set it as best
				words.add(n.word);
		}
		return words.toArray(new String[0]);
	}
}
