package query;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class QueryService {
	private QueryDAO queryDao = new QueryDAO();
	private String[] keywords;
	private List<QueryItem> items = new ArrayList<QueryItem>();
	List<String> attachedKeys = new ArrayList<String>();
	List<String> paragraphedKeys = new ArrayList<String>();
	List<String> unrelatedKeys = new ArrayList<String>();

	public List<QueryItem> getQueryReslt(String keyword) {
		if(!items.isEmpty()) items.clear();
		if(keyword.contains(" ")) {
			keywords = keyword.split(" ");
			for(String key:keywords) {
				getInvertedItem(key);
			}
		}
		if(!keyword.contains(" ")) {
			fillItems(queryDao.getDocs(keyword),keyword);
		}
		return items;
	}

	private void getInvertedItem(String keyword) {
		List<String> keys = queryDao.getDocs(keyword);
		combineAND(keys);
		
	}

	private void combineAND(List<String> keys) {
		// TODO Auto-generated method stub
		
	}

	private void fillItems(List<String> docs, String keyword) {
		if(docs == null || docs.isEmpty()) {
			items.clear();
			return;
		}
		for(String doc: docs) {
			String[] tmp = doc.split(":");
			String docID = tmp[0];
			String docContent = queryDao.getDocContent(docID);
			docContent = docContent.replaceAll("(?i)"+keyword, "<strong>" +  keyword + "</strong>");
			String docUrl = queryDao.getDocURL(docID);
			items.add(new QueryItem(docContent.substring(1,50), docContent, docUrl));
		}
		
	}
	

}
