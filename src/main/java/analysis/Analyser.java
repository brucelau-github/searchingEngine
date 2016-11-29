package analysis;

import java.util.HashMap;
import java.util.Map;

public abstract class Analyser implements analyzable {

	protected final String wordSplitter = "[\\s]+";
	protected Map<String,Item> keywords = new HashMap<>();
	protected long docID = 0;
	protected AnalyserDAO dao = new AnalyserDAO();

	public Analyser(long docID) {
		this.docID = docID;
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
}