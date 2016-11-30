package analysis;

import database.RedisDAO;

public class AnalyserDAO extends RedisDAO {
	private final String DOC_UNANALYZED = "DOC_UNANALYZED";
	private final String PREFIX_DOC = "DOC:";
	private final String KEY ="KEY:";
	public AnalyserDAO(){
		super();
	}
	public String getDocID(){
		return syncCommands.lpop(DOC_UNANALYZED);
	}
	public String getDoc(String docID) {
		return syncCommands.get(PREFIX_DOC+docID);
	}
	public void saveItem(String docID, Item it) {
		syncCommands.zadd(KEY+it.getKeyWord(), it.getFreq(), docID + it.getPos());
		
	}
	public void saveDoc(String docID, String doc) {
		syncCommands.set(PREFIX_DOC+docID, doc);
		
	}
}
