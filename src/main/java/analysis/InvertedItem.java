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