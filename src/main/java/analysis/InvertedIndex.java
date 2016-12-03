package analysis;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
//analyse one document, we should have another class to analyse multi class.
public class InvertedIndex { 
	private final int REPEATEDTIME = 100;
	private String doc = "";
	private AnalyserDAO aDao = new AnalyserDAO();
	
	private Analyser analyse;
	private Purifable p;
	String docID;
	public InvertedIndex(){
		
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
		analyse = new Analyser(docID,doc);
		analyse.analyse();
		analyse.saveDoc();
		analyse.save();
	}

    public static void main(String[] args) {
    	
    	InvertedIndex inv = new InvertedIndex();
    	
    	inv.run();
    	

    }
    @Scheduled(fixedRate = 5000)
	public void run() {
    	int  i = 0;
		while(retrieveFile()){
			purifyHtml();
			analyseAndSave();
			if(i++ > REPEATEDTIME) {
				break;
			}

		}
		aDao.disconnect();
	}
}


