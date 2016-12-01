package query;

import database.RedisDAO;

import java.util.List;

import com.lambdaworks.redis.api.sync.RedisCommands;

public class QueryDAO extends RedisDAO {
	private final String PREFIX_DOC = "DOC:";
	private final String KEY = "KEY:";
	private final String DOC_UNANALYZED = "DOC_UNANALYZED";
	private final String VISITED_URL = "VISITED_URL";
	private final String DOC_URL = ":URL";
	
	String docID;
	
	public QueryDAO() {
	}


	public List<String> getDocs(String key) {
		return syncCommands.zrevrange(KEY+key, 0, -1);
	}
	public String getDocURL(String docID) {
		return syncCommands.get(PREFIX_DOC+docID+DOC_URL);
	}
	public String getDocContent(String docID) {
		return syncCommands.get(PREFIX_DOC+docID);
	}
}
