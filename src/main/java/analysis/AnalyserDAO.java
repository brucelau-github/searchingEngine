package analysis;

import java.util.List;

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
	public String getDoc(long docID) {
		return syncCommands.get(PREFIX_DOC+docID);
	}
	public void saveItem(long docID, Item it) {
		syncCommands.zadd(KEY+it.getKeyWord(), it.getFreq(), String.valueOf(docID) + it.getPos());
		
	}
}
