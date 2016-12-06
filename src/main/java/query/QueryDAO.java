package query;

import database.RedisDAO;
import util.AppConfig;

import java.util.List;

import com.lambdaworks.redis.KeyScanCursor;
import com.lambdaworks.redis.ScanArgs;
import com.lambdaworks.redis.api.sync.RedisCommands;

public class QueryDAO extends RedisDAO {
	private final AppConfig appConfig = AppConfig.newInstance();
	private final int LIMIT = Integer.parseInt(appConfig.getProperty("SCAN_COUNT"));
    private final int AUTOCOMPLETE_AMOUNT = Integer.parseInt(appConfig.getProperty("AUTOCOMPLETE_AMOUNT"));
	private final String PREFIX_DOC = "DOC:";
	private final String KEY = "KEY:";
	private final String DOC_URL = ":URL";
	private final String TRIE_STRUCTURE = "TRIE_STRUCTURE";
	private final String TRIE_NODE = "TRIE_NODE:";
	
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

	public List<String> queryKeys(String term) {
		ScanArgs scarg = new ScanArgs();
		scarg.limit(LIMIT);
		scarg.match(KEY + "*" + term + "*");
		
		KeyScanCursor<String> scanCouror = syncCommands.scan(scarg);
		List<String> keys = scanCouror.getKeys();
		while(keys.size()<AUTOCOMPLETE_AMOUNT){
			scanCouror = syncCommands.scan(scanCouror,scarg);
			if(scanCouror.getCursor().equalsIgnoreCase("0")) {
				break;
			}
			keys.addAll(scanCouror.getKeys());
		}
		return keys;
	}
	public String getTrie() {
		return syncCommands.get(TRIE_STRUCTURE);
	}


	public List<String> getDocsWithTrieID(long trieNodeID) {
		return syncCommands.zrevrange(TRIE_NODE+trieNodeID, 0, -1);
	}
	public boolean hasTrie() {
		return syncCommands.get(TRIE_STRUCTURE) == null ? false:true;
	}
}
