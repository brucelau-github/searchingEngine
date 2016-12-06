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
package analysis;

import java.util.HashMap;
import java.util.Map;

import database.RedisDAO;

public class AnalyserDAO extends RedisDAO {
	private final String DOC_UNANALYZED = "DOC_UNANALYZED";
	private final String PREFIX_DOC = "DOC:";
	private final String TRIE_STRUCTURE = "TRIE_STRUCTURE";
	private final String TRIE_NODE = "TRIE_NODE:";
	private final String TRIE_NODE_C = "TRIE_NODE_C:";
	private final String KEY ="KEY:";
	public AnalyserDAO(){
		super();
	}
	public String getDocID(){
		return syncCommands.lpop(DOC_UNANALYZED);
	}
	public String getDoc(String docID) {
		return syncCommands.get(PREFIX_DOC+docID);
	}
	public void saveItem(String docID, Item it) {
		syncCommands.zadd(KEY+it.getKeyWord(), it.getFreq(), docID + ":" + it.getFreq()+ ":" +it.getPos());
		
	}
	public void saveDoc(String docID, String doc) {
		syncCommands.set(PREFIX_DOC+docID, doc);
		
	}
	public void saveTrie(String dumpTrie) {
		syncCommands.set(TRIE_STRUCTURE,dumpTrie);
	}
	public void saveTrieNodes(String dumpTrieNode, String docID) {
		//nodeID+" "+occurances+" "+docPositions+"|"
		Map<String,String> trieNodekeys = new HashMap<String,String>();
		String[] nodes = dumpTrieNode.split("\\|");
		for(String n:nodes){
			String[] nodeDetail=n.split("\\s");
			if(nodeDetail.length == 3)
			syncCommands.zadd(TRIE_NODE+nodeDetail[0], Integer.parseInt(nodeDetail[1]), docID + ":" + nodeDetail[1]+ ":" +nodeDetail[2]);
		}	
	}
	public String getTrie() {
		return syncCommands.get(TRIE_STRUCTURE);
	}
	public String getTrieChar(int nodeID) {
		return syncCommands.get(TRIE_NODE_C+nodeID);
	}
	public boolean hasTrie() {
		// TODO Auto-generated method stub
		return syncCommands.get(TRIE_STRUCTURE) == null ? false:true;
	}
	public void saveTrieNodeChar(String dumpTrieNodeChar) {
		//nodeID+" "+c
				Map<String,String> trieNodekeys = new HashMap<String,String>();
				String[] nodes = dumpTrieNodeChar.split("\\|");
				for(String n:nodes){
					String[] nodeDetail=n.split("\\s");
					trieNodekeys.put(TRIE_NODE_C+nodeDetail[0], nodeDetail[1]);
				}
				syncCommands.mset(trieNodekeys);
		
	}
}
