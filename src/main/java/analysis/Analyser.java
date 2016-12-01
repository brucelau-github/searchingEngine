package analysis;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Analyser {

	protected final String wordSplitter = "[\\s\'\":,.]+";
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
}