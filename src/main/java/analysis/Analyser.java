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
import java.util.Scanner;

public class Analyser {

	protected final String wordSplitter = "[^a-zA-Z]+";
	protected Map<String,Item> keywords = new HashMap<>();
	protected String docID;
	protected AnalyserDAO dao = new AnalyserDAO();
	protected String doc;

	public Analyser(String docID) {
		this.docID = docID;
	}

	public Analyser(String docID2, String Doc) {
		this.docID = docID2;
		this.doc = Doc;
	}

	protected void addTerm(String term,int position){
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

	protected void save() {
		for(Map.Entry<String, Item> entry : keywords.entrySet()){
			Item it = entry.getValue();
			dao.saveItem(docID, it);
		}
		
	}
	private int currentPostion = 0;
	public void analyse() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(doc.toLowerCase()).useDelimiter(wordSplitter);
		while(sc.hasNext()){
			addTerm(sc.next(), currentPostion);
			currentPostion++;
		}
		sc.close();
	}

	public void saveDoc() {
		dao.saveDoc(docID,doc);
		
	}
	public void close(){
		dao.disconnect();
	}
}