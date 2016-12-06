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
			sb.append("-"+p);
		}
		return sb.deleteCharAt(0).toString();
	}
	
}
