package query;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import io.netty.util.internal.PriorityQueue;

@Service
public class QueryService {
	private QueryDAO queryDao = new QueryDAO();
	private String[] keywords;
	private List<QueryItem> items = new ArrayList<QueryItem>();
	List<String> allKeys = new ArrayList<String>();
	List<String> paragraphedKeys = new ArrayList<String>();
	List<String> unrelatedKeys = new ArrayList<String>();

	public List<QueryItem> getQueryReslt(String keyword) {
		if (!items.isEmpty())
			items.clear();
		if (keyword.contains(" ")) {
			keywords = keyword.split(" ");
			for (String key : keywords) {
				getInvertedItem(key);
			}

			HeapSort heap = new HeapSort();
			String[] arrayKeys = (String[]) allKeys.toArray();
			heap.heapsort(arrayKeys);
			fillItems(arrayKeys);

		}
		if (!keyword.contains(" ")) {
			fillItems(queryDao.getDocs(keyword), keyword);
		}
		return items;
	}

	private void getInvertedItem(String keyword) {
		List<String> keys = queryDao.getDocs(keyword);
		allKeys.addAll(keys);

	}

	private void fillItems(List<String> docs, String keyword) {
		if (docs == null || docs.isEmpty()) {
			items.clear();
			return;
		}
		for (String doc : docs) {
			String[] tmp = doc.split(":");
			String docID = tmp[0];
			String docContent = queryDao.getDocContent(docID);
			String docUrl = queryDao.getDocURL(docID);
			items.add(new QueryItem(docContent.substring(1, 50), docContent, docUrl));
		}

	}

	private void fillItems(String[] arrayKeys) {
		if (arrayKeys == null || arrayKeys.length == 0) {
			items.clear();
			return;
		}
		unique(arrayKeys);
		for (String doc : arrayKeys) {
			String[] tmp = doc.split(":");
			String docID = tmp[0];
			String docContent = queryDao.getDocContent(docID);
			String docUrl = queryDao.getDocURL(docID);
			items.add(new QueryItem(docContent.substring(1, 50), docContent, docUrl));
		}

	}

	private void unique(String[] arrayKeys) {
		ArrayList<String> docIDs = new ArrayList<String>();
		for (String key : arrayKeys) {
			String[] keys = key.split(":");
			String docID = keys[0];
			if (!docIDs.contains(docID)) {
				docIDs.add(docID);
			}

		}

	}

	private void highlight() {
		for (QueryItem it : items) {
			for (String k : keywords) {
				it.setSummary(it.getSummary().replaceAll("(?i)" + k, "<strong>" + k + "</strong>"));
			}
		}
	}

	class InvertedItem {
		private long docID;
		private long freque;
		private long[] position;
		public InvertedItem(long docID, long freque, long[] position) {
			this.docID = docID;
			this.freque = freque;
			this.position = position;
		}
		public InvertedItem(String docID, String freque, String position) {
			this.docID = Long.parseLong(docID);
			this.freque = Long.parseLong(freque);
			String[] pos = position.split("-");
			this.position = new long[pos.length];
			int i = 0 ;
			for(String p : pos ){
				this.position[i++] = Long.parseLong(p);
			}
		}
		public InvertedItem(String key) {
			String[]  tmp = key.split(":");
			this_constr(tmp[0],tmp[1],tmp[2]);
		}
		private void this_constr(String docID, String freque, String position) {
			this.docID = Long.parseLong(docID);
			this.freque = Long.parseLong(freque);
			String[] pos = position.split("-");
			this.position = new long[pos.length];
			int i = 0 ;
			for(String p : pos ){
				this.position[i++] = Long.parseLong(p);
			}
			
		}
		public long getDocID() {
			return docID;
		}
		public void setDocID(long docID) {
			this.docID = docID;
		}
		public long getFreque() {
			return freque;
		}
		public void setFreque(long freque) {
			this.freque = freque;
		}
		public long[] getPosition() {
			return position;
		}
		public void setPosition(long[] position) {
			this.position = position;
		}

	}
}
