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

public class InvertedItem {
	private Map<String,Item> keywords;
	private long docId = 0;
	private AnalyserDAO dao;
	
	public InvertedItem(long docId) {
		keywords = new HashMap<>();
		this.docId = docId;
		dao = new AnalyserDAO();
	}
	
	public void addTerm(String term,int position){
		if(!keywords.containsKey(term)) {
			keywords.put(term, new Item(term,position));
		} else {
			Item termobj = (Item) keywords.get(term);
			termobj.addFreq();
			termobj.addPostion(position);
		}
	}
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for(Map.Entry<String, Item> entry : keywords.entrySet()){
			Item it = entry.getValue();
			sb.append(it.toString()+"\n");
		}
		return sb.toString();

	}

	public void mergeToDataBase() {
		for(Map.Entry<String, Item> entry : keywords.entrySet()){
			Item it = entry.getValue();
			dao.saveItem(docId, it.getKeyWord(), it.getFreq(), it.getPos());
		}
	}
	public long getDocumentID(){
		
		return this.docId;
	}
	
	
}