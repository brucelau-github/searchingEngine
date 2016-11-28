package analysis;

import java.util.ArrayList;

import database.*;
//analyse one document, we should have another class to analyse multi class.
public class InvertedIndex { 
	private String doc = "";
	private DAO dao;
	private analyzable analyse;
	public InvertedIndex(){
		dao = new RedisDAO();
		analyse = new ScannerAnalysis();
	}
	public void retreivedFile(){
		//to do :
		// check list "DocumentKeys" llen > 0;
		// lpop(DocumentKeys);
		// doc = get(docKey);
//		dao.set("1", "By late 2006, Google established a new headquarters for its AdWords division in Ann Arbor, Michigan.[300] In November 2006, Google opened offices on Carnegie Mellon's campus in Pittsburgh, focusing on shopping-related advertisement coding and smartphone applications and programs.[301][302] Other office locations in the U.S. include Atlanta, Georgia; Austin, Texas; Boulder, Colorado; Cambridge, Massachusetts; San Francisco, California; Seattle, Washington; Reston, Virginia, and Washington, D.C.[303]");
		doc = dao.get("1");
	}
	/* (non-Javadoc)
	 * @see analysis.analyzable#analyseDocument()
	 */

	public void analyse(){

		analyse.analyse(doc);
		
		analyse.print();
		
	}

    public static void main(String[] args) {
    	
    	InvertedIndex inv = new InvertedIndex();
    	inv.retreivedFile();
    	inv.analyse();
    	inv.dumpBackToDataBase();
    	

//        // key = word, value = set of filenames containing that word
//        ST<String, SET<String>> st = new ST<String, SET<String>>();
//
//        // create inverted index of all files
//        for (String filename : args) {
//            // StdOut.println(filename);
//            In in = new In(filename);
//            while (!in.isEmpty()) {
//                String word = in.readString();
//                if (!st.contains(word)) st.put(word, new SET<String>());
//                SET<String> set = st.get(word);
//                set.add(filename);
//            }
//        }
//
//        // read queries from standard input, one per line
//        while (!StdIn.isEmpty()) {
//            String query = StdIn.readString();
//            if (!st.contains(query)) StdOut.println("NOT FOUND");
//            else {
//                SET<String> set = st.get(query);
//                for (String filename : set) {
//                    StdOut.print(filename + " ");
//                }
//                StdOut.println();
//            }
//            StdOut.println();
//        }

    }
	private void dumpBackToDataBase() {
		// TODO Auto-generated method stub
		
	}

}


