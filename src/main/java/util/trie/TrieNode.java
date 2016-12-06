package util.trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A node in a trie.
 * @author Brian Harris (brian_bcharris_net)
 */
public class TrieNode {
	public static final AtomicLong counter = new AtomicLong();
	char c;
	public int occurances;
	public boolean isWord = false;
	StringBuffer docPositions = new StringBuffer();
	long nodeID;
	public Map<Character, TrieNode> children;
	public TrieNode(char c,long nodeID,boolean isWord) {
		this.nodeID = nodeID;
		this.c = c;
		this.isWord = isWord;
		occurances = 0;
		children = null;
	}
	public TrieNode(char c,long nodeID) {
		this.nodeID = nodeID;
		this.c = c;
		occurances = 0;
		children = null;
	}
	TrieNode(char c) {
		this.nodeID = counter.incrementAndGet();
		this.c = c;
		occurances = 0;
		children = null;
	}
	
	int insert(String s, int pos) {
		if (s == null || pos >= s.length())
			return 0;
		
		// allocate on demand
		if (children == null)
			children = new HashMap<Character, TrieNode>();

		char c = s.charAt(pos);
		TrieNode n = children.get(c);

		// make sure we have a child with char c
		if (n == null) {
			n = new TrieNode(c);
			children.put(c, n);
		}
		
		// if we are the last node in the sequence of chars
		// that make up the string
		if (pos == s.length()-1) {
			n.occurances++;
			return n.occurances;
		} else
			return n.insert(s, pos+1);
	}
	public int insert(String s, int pos,int docPosition) {
		if (s == null || pos >= s.length())
			return 0;
		
		// allocate on demand
		if (children == null)
			children = new HashMap<Character, TrieNode>();

		char c = s.charAt(pos);
		TrieNode n = children.get(c);

		// make sure we have a child with char c
		if (n == null) {
			n = new TrieNode(c);
			children.put(c, n);
		}
		
		// if we are the last node in the sequence of chars
		// that make up the string
		if (pos == s.length()-1) {
			n.occurances++;
			n.isWord = true;
			if(n.docPositions.length() == 0) n.docPositions.append(docPosition);
			else n.docPositions.append("-" + docPosition);
			return n.occurances;
		} else
			return n.insert(s, pos+1,docPosition);
	}
	

	boolean remove(String s, int pos) {
		if (children == null || s == null)
			return false;
		
		char c = s.charAt(pos);
		TrieNode n = children.get(c);

		if (n == null)
			return false;

		boolean ret;
		if (pos == s.length()-1) {
			int before = n.occurances;
			n.occurances = 0;
			ret = before > 0;
		} else {
			ret = n.remove(s, pos+1);
		}
		
		// if we just removed a leaf, prune upwards
		if (n.children == null && n.occurances == 0) {
			children.remove(n.c);
			if (children.size() == 0)
				children = null;
		}
		
		return ret;
	}
	
	public TrieNode lookup(String s, int pos) {
		if (s == null)
			return null;
		
		if (pos >= s.length() || children == null)
			return null;
		else if (pos == s.length()-1)
			return children.get(s.charAt(pos));
		else {
			TrieNode n = children.get(s.charAt(pos)); 
			return n == null ? null : n.lookup(s, pos+1);
		}
	}
	public void dumpTrie(StringBuilder sb,String edgeToken) {
		if (children == null)
			return;
		for (TrieNode n : children.values()){
			sb.append(nodeID+"-"+n.nodeID+edgeToken);
			n.dumpTrie(sb,edgeToken);
		}
	}
	public void dumpTrieNode(StringBuilder sb,String edgeToken) {
		if(occurances > 0)
		sb.append(nodeID+" "+occurances+" "+docPositions+edgeToken);
		if (children == null)
			return;
		for (TrieNode n : children.values()){
			n.dumpTrieNode(sb,edgeToken);
		}
	}
	
	private void dump(StringBuilder sb, String prefix) {
		sb.append(prefix+c+"("+occurances+")"+"\n");
		
		if (children == null)
			return;
		
		for (TrieNode n : children.values())
			n.dump(sb, prefix+(c==0?"":c));
	}
	
	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder();
		dumpTrie(toString,"|");
		toString.append("\n");
		dumpTrieNode(toString,"\n");
		System.out.println(toString);
		return toString.toString();
	}
	public static void addChildren(TrieNode trieNode, TrieNode trieNode2) {
		if(trieNode.children == null)
			trieNode.children = new HashMap<Character, TrieNode>();
		trieNode.children.put(trieNode2.c, trieNode2);
		
	}
	public void clearOccurance() {
		occurances = 0;
		if (children == null)
			return;
		for (TrieNode n : children.values()){
			n.clearOccurance();
		}
		
	}
	public long getNodeID() {
		return nodeID;
	}
	public void dumpTrieNodeChar(StringBuffer sb, String edgeToken) {
			sb.append(String.valueOf(nodeID)+" "+ c + ":" + String.valueOf(isWord) +edgeToken);
			if (children == null)
				return;
			for (TrieNode n : children.values()){
				n.dumpTrieNodeChar(sb,edgeToken);
			}
	}
}
