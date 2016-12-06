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

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import util.AppConfig;

@Component
//analyse one document, we should have another class to analyse multi class.
public class InvertedIndex { 
	final AppConfig appConfig = AppConfig.newInstance();
	private final int REPEATEDTIME = Integer.parseInt(appConfig.getProperty("ANALYSIS_AMOUNT_PER_TIME"));
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
	}
}


