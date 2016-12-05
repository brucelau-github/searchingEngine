package analysis;

import java.util.Arrays;
import java.util.LinkedList;

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
	public String dumpTrieNode(){
		StringBuilder tmp = new StringBuilder();
		root.dumpTrieNode(tmp,"|");
		return tmp.toString();
	}
	public String dumpTrie(){
		StringBuilder toString = new StringBuilder();
		toString.append(TrieNode.counter.get()+"|");
		root.dumpTrie(toString,"|");
		return toString.toString();
	}
	public void load(String trieStructure){
		String edgeToken="\\|";
		LinkedList<String> edgeTrie = new LinkedList<String>(Arrays.asList(trieStructure.split(edgeToken)));
		int nodeSize = Integer.parseInt(edgeTrie.remove());
		TrieNode.counter.set(nodeSize);
		TrieNode[] nodes= new TrieNode[nodeSize];
		for(int i=0;i<nodeSize;i++){
			nodes[i] = new TrieNode(aDao.getTrieChar(i+1),i+1);
		}
		for(String e:edgeTrie){
			String[] node=e.split("-");
			int parent = Integer.parseInt(node[0]);
			int child = Integer.parseInt(node[1]);
			TrieNode.addChildren(nodes[parent-1],nodes[child-1]);
			
		}
		root = nodes[0];
		
	}

	public void clearFrequecy() {
		root.clearOccurance();
		
	}
}
