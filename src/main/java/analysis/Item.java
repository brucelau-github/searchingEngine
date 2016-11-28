package analysis;

import java.util.ArrayList;

public class Item {
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
	public String getKeyWord() {
		return keyWord;
	}
	public String getPos() {
		StringBuffer sb = new StringBuffer();
		for(int p:pos){
			sb.append(":"+p);
		}
		return sb.toString();
	}
	
}
