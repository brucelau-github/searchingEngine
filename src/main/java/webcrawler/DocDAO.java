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
package webcrawler;

import database.RedisDAO;

public class DocDAO extends RedisDAO {
	private final String PREFIX_DOC = "DOC:";
	private final String DOC_KEY = "DOC_ID";
	private final String DOC_UNANALYZED = "DOC_UNANALYZED";
	private final String VISITED_URL = "VISITED_URL";
	private final String DOC_URL = ":URL";
	private final String DOMAIN_TO_VISIT = "DOMAIN_TO_VISIT";
	private final String DOMAIN_VISITED = "DOMAIN_VISITED";
	
	String docID;
	
	public DocDAO() {

	}

	public boolean isVistedUrl(String url){
		return syncCommands.sismember(VISITED_URL, url);
		
	}
	public long getNewDocID() {
		if(syncCommands.keys(DOC_KEY).isEmpty()) {
			syncCommands.set(DOC_KEY, "0");
			return 0;
		}
		return syncCommands.incr(DOC_KEY);
	}

	public void saveDoc(String text,String docID) {
		syncCommands.set(PREFIX_DOC + docID, text);
		syncCommands.rpush(DOC_UNANALYZED, docID);
		
	}

	public void saveUrl(String url,String docID) {
		syncCommands.set(PREFIX_DOC+docID+DOC_URL, url);
		syncCommands.sadd(VISITED_URL, url);
		
	}

	public boolean isVisitedDomain(String newHostName) {
		if (syncCommands.keys(DOMAIN_VISITED).isEmpty()) return false;
		return syncCommands.sismember(DOMAIN_VISITED, newHostName);
	}

	public void saveNewDomain(String newHostName) {
		syncCommands.sadd(DOMAIN_TO_VISIT, newHostName);
		
	}

	public void saveVisitedDomain(String currentDomain) {
		syncCommands.sadd(DOMAIN_VISITED, currentDomain);
	}

	public boolean isAddedDomain(String newHostName) {
		return syncCommands.sismember(DOMAIN_TO_VISIT, newHostName);
	}

	public void linkAllUrlWithDomain(String currentDomain) {
		syncCommands.rename(VISITED_URL, VISITED_URL+":"+currentDomain);
	}

	public String getNextDomain() {
		return syncCommands.spop(DOMAIN_TO_VISIT);
	}

	public boolean isExistVisitedURL() {
		return !(syncCommands.keys(VISITED_URL)).isEmpty();
	}

	public void reomveVisitedDomain(String currentDomain) {
		syncCommands.srem(DOMAIN_VISITED, currentDomain);
		
	}
	
}
