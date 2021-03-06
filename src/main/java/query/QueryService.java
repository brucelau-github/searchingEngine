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
package query;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.SealedObject;

import org.springframework.stereotype.Service;

import io.netty.util.internal.PriorityQueue;

@Service
public class QueryService {
	private QueryDAO queryDao = new QueryDAO();
	private String[] keywords;
	private List<QueryItem> items = new ArrayList<QueryItem>();
	List<String> allKeys = new ArrayList<String>();
	InvertedItem[] firstItems;
	InvertedItem[] secondItems;
	List<String> firstkey = new ArrayList<String>();
	private boolean firstKey;

	public List<QueryItem> getQueryReslt(String keyword) {
		firstKey = true;
		if (!items.isEmpty()) {
			items.clear();
		}
		
		keywords = keyword.split(" ");
		for (String key : keywords) {
			getInvertedItem(key);
		}
		Arrays.sort(firstItems, new Comparator<InvertedItem>() {

			@Override
			public int compare(InvertedItem it1, InvertedItem it2) {
				return (int) (it2.getFreque() - it1.getFreque());
			}

		});
		fillItems();
		highlight();
		return items;
	}

	private void fillItems() {

		if (firstItems == null || firstItems.length == 0) {
			items.clear();
			return;
		}
		for (InvertedItem doc : firstItems) {
			String docID = String.valueOf(doc.getDocID());
			String docContent = queryDao.getDocContent(docID);
			String docUrl = queryDao.getDocURL(docID);
			items.add(new QueryItem(docContent.substring(0, docContent.indexOf("\n") + 1), docContent, docUrl));
		}
	}

	private void getInvertedItem(String keyword) {
		List<String> keys = queryDao.getDocs(keyword);
		if (firstKey) {
			fillClass(keys);
			firstKey = false;

		} else {
			compareItem(keys);
		}

	}

	private void compareItem(List<String> keys) {
		secondItems = new InvertedItem[keys.size()];
		for (int i = 0; i < keys.size(); i++) {
			secondItems[i] = new InvertedItem(keys.get(i));
		}
		Sort.heapsort(secondItems);

		int i = 0, j = 0;
		List<InvertedItem> tmp = new ArrayList<>();
		while (i < firstItems.length && j < secondItems.length) {
			if (firstItems[i].compareTo(secondItems[j]) > 0)
				j++;
			else if (firstItems[i].compareTo(secondItems[j]) < 0)
				i++;
			else {
				tmp.add(new InvertedItem(firstItems[i].getDocID(),
						firstItems[i].getFreque() + secondItems[j].getFreque(), firstItems[i].getPosition()));
				j++;
				i++;
			}
		}
		firstItems = new InvertedItem[tmp.size()];
		for (int t = 0; t < tmp.size(); t++) {
			firstItems[t] = tmp.get(t);
		}
	}

	private void fillClass(List<String> keys) {
		firstItems = new InvertedItem[keys.size()];
		for (int i = 0; i < keys.size(); i++) {
			firstItems[i] = new InvertedItem(keys.get(i));
		}
		Sort.heapsort(firstItems);
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
			String docContent = it.getSummary();
			for (String k : keywords) {
				docContent = replaceKeyWord(docContent, k);
			}
			it.setSummary(docContent);
		}
	}

	private String extractSentence(String p, String key) {
		String s = "...";
		String pattern = "(\\s\\b\\w*\\b\\s){0,20}(?i)(" + key + ")(\\s\\b\\w*\\b\\s){0,20}";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(p);
		while (m.find()) {
			s = s + m.group(0);
		}
		s = s.replaceAll("(?i)\\b" + key + "\\b", "<span class=\"highlight\">" + key + "</span>");
		return s.concat("...");

	}
	private String replaceKeyWord(String p, String key) {
		String s = p.replaceAll("(?i)\\b" + key + "\\b", "<span class=\"highlight\">" + key + "</span>");
		return s.concat("...");

	}
}
