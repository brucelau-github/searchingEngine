package analysis;

import java.util.Scanner;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import util.AppConfig;

@Component
//analyse one document, we should have another class to analyse multi class.
public class TrieAnalyserDriver { 
	final AppConfig appConfig = AppConfig.newInstance();
	private final int REPEATEDTIME = Integer.parseInt(appConfig.getProperty("ANALYSIS_AMOUNT_PER_TIME"));
	private String doc = "";
	private final AnalyserDAO aDao = new AnalyserDAO();
	
	private TrieDictionary dictionary = new TrieDictionary(false);
	private Purifable p;
	String docID;
	public TrieAnalyserDriver(){
		
	}
	public boolean retrieveFile(){
		docID = aDao.getDocID();
		if(docID == null) return false;
		doc = aDao.getDoc(docID);
		return true;
	}
	public void purifyHtml(){
		p = new PurifyingHTML(doc);
		doc = p.purify();
	}
	public void analyseAndSave(){
	    String tokenizer = "[^a-zA-Z]+";
		int currentPostion = 0;
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(doc.toLowerCase()).useDelimiter(tokenizer);
		while(sc.hasNext()){
			dictionary.insert(sc.next(),currentPostion);
			currentPostion++;
		}
		sc.close();
		
		aDao.saveTrieNodes(dictionary.dumpTrieNode(),docID);
		dictionary.clearFrequecy();
	}
	

    public static void main(String[] args) {
    	
    	TrieAnalyserDriver inv = new TrieAnalyserDriver();
    	
    	inv.run();
    	

    }
    @Scheduled(fixedRate = 5000)
	public void run() {
    	int  i = 0;
    	dictionary.load(aDao.getTrie());
		while(retrieveFile()){
			purifyHtml();
			analyseAndSave();
			if(i++ > REPEATEDTIME) {
				break;
			}
		}
		aDao.saveTrie(dictionary.dumpTrie());
	}
}


