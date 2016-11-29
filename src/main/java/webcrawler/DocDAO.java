package webcrawler;

import database.RedisDAO;
import com.lambdaworks.redis.api.sync.RedisCommands;

public class DocDAO extends RedisDAO {
	private final String PREFIX_DOC = "DOC:";
	private final String DOC_KEY = "DOC_ID";
	private final String DOC_UNANALYZED = "DOC_UNANALYZED";
	private final String VISITED_URL = "VISITED_URL";
	
	public DocDAO() {
	}

	public void save(String text) {
		String docID = String.valueOf(getNewDocID());
		syncCommands.set(PREFIX_DOC + docID, text);
		syncCommands.rpush(DOC_UNANALYZED, docID);
	}
	public void recordUrl(String url){
		syncCommands.sadd(VISITED_URL, url);
		
	}
	public boolean isVistedUrl(String url){
		return syncCommands.sismember(VISITED_URL, url);
		
	}
	private long getNewDocID() {
		if(!syncCommands.exists(DOC_KEY)) {
			syncCommands.set(DOC_KEY, "0");
		}
		return syncCommands.incr(DOC_KEY);
	}
	
}
