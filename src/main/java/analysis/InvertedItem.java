package analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InvertedItem {
	public Map<String,Item> keywords;
	
	class Item {
		private String keyWord;
		private int freq;
		private ArrayList<Integer> pos = new ArrayList<Integer>();
		Item(String term) {
		 this.keyWord = term;
		 freq = 0;
		}
		Item(String term,int position) {
			this(term);
			addPostion(position);
		}
		public void addFreq(){
			freq++;
		}
		public void addPostion(int position){
			pos.add(position);
		}
		public int getFreq(){
			return freq;
		}
		public String toString(){
			StringBuffer p = new StringBuffer();
			for(int pint : pos){
				p.append(pint + ",");
			}
			return "term: "+keyWord + " freq: " + freq + " position: " + p.toString(); 
		}
	}
	public InvertedItem() {
		keywords = new HashMap<>();
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
	
}