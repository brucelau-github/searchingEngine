package analysis;

import java.util.ArrayList;

import database.*;
//analyse one document, we should have another class to analyse multi class.
public class InvertedIndex { 
	private String doc = "";
	private AnalyserDAO aDao = new AnalyserDAO();
	private Analyser analyse;
	public InvertedIndex(){
		
	}
	public void retreivedFile(){
		long docID = Long.parseLong(aDao.getDocID());
		analyse = new ScannerAnalysis(docID);
		doc = aDao.getDoc(docID);
	}

	public void analyseAndSave(){

		analyse.analyse(doc);
		analyse.save();
	}

    public static void main(String[] args) {
    	
    	InvertedIndex inv = new InvertedIndex();
    	inv.retreivedFile();
    	inv.analyseAndSave();
    	

    }
}


