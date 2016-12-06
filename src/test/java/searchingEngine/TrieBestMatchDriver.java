package searchingEngine;

import static org.junit.Assert.*;

import org.junit.Test;

import analysis.TrieDictionary;

public class TrieBestMatchDriver {

	@Test
	public void test() {
		/*
		 * \0
		 * a    b
		 * d
		 * d
		 * i
		 * t
		 * i
		 *  f g 
		 * 
		 * 
		 * 
		 * 
		 */
		TrieDictionary td = new TrieDictionary(false);
		String trieStructure = "10|1-2|1-3|2-4|2-5|4-7|5-8|3-10|3-6|6-9|";
		String[] chars={(char)0+":false","a:false","b:false","d:fales","s:false","e:false","d:true","k:true","e:true","y:true"};
		td.loadTest(trieStructure, chars);
		String[] rest = td.multiBestMatch("b", 10);
	}

}
