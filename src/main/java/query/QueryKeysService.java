package query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import analysis.TrieDictionary;
import util.AppConfig;;

@Service
public class QueryKeysService {
	private final AppConfig appConfig = AppConfig.newInstance();
	private QueryDAO queryDao = new QueryDAO();
	private final int AUTOCOMPLETE_AMOUNT = Integer.parseInt(appConfig.getProperty("AUTOCOMPLETE_AMOUNT"));
	private TrieDictionary dictionary = new TrieDictionary(false);
	
	public KeyItem[] queryKeys(String term) {

		List<String> keys = queryDao.queryKeys(term);
		String[] topShortestTen = keys.toArray(new String[0]);
		Arrays.sort(topShortestTen, new Comparator<String>() {

			@Override
			public int compare(String s1, String s2) {
				return s1.length() - s2.length();
			}

		});
		ArrayList<KeyItem> keysArray = new ArrayList<KeyItem>();

		for (int i = 0; i < topShortestTen.length && i < AUTOCOMPLETE_AMOUNT; i++) {
			String label = topShortestTen[i];
			label = trimKeys(label);
			keysArray.add(new KeyItem(label, label));
		}
		return keysArray.toArray(new KeyItem[0]);
	}

	private String trimKeys(String label) {
		// TODO Auto-generated method stub
		return label.replaceAll("KEY:", "");
	}

	public String[] queryKeysWithString(String term) {
		// TODO Auto-generated method stub
		List<String> keys = queryDao.queryKeys(term);
		String[] topShortestTen = keys.toArray(new String[0]);
		Arrays.sort(topShortestTen, new Comparator<String>() {

			@Override
			public int compare(String s1, String s2) {
				return s1.length() - s2.length();
			}

		});
		keys.clear();
		for (int i = 0; i < topShortestTen.length && i < AUTOCOMPLETE_AMOUNT; i++) {
			String label = topShortestTen[i];
			label = trimKeys(label);
			keys.add(label);
		}
		return keys.toArray(new String[0]);
	}

	public String[] queryManyKeys(String term) {
		// TODO Auto-generated method stub
		String[] terms = term.split("\\s+");
		if (terms.length == 1) {
			return queryKeysWithString(terms[0]);
		}

		String lastKey = terms[terms.length - 1];
		String previousterms = "";
		for (int j=0; j< (terms.length - 1); j++) {
			previousterms+= (" " + terms[j]);
		}
		String[] queryResult = queryKeysWithString(lastKey);
		for (int i = 0; i < queryResult.length; i++) {
			queryResult[i] = previousterms + " " + queryResult[i];
		}
		return queryResult;
	}
	
	public String[] queryManyKeysTire(String term) {
		// TODO Auto-generated method stub
		dictionary.load(queryDao.getTrie());
		String[] terms = term.split("\\s+");
		if (terms.length == 1) {
			return dictionary.multiBestMatch(terms[0],10);
		}

		String lastKey = terms[terms.length - 1];
		String previousterms = "";
		for (int j=0; j< (terms.length - 1); j++) {
			previousterms+= (" " + terms[j]);
		}
//		String[] queryResult = queryKeysWithString(lastKey);
		String[] queryResult = dictionary.multiBestMatch(lastKey,10);
		for (int i = 0; i < queryResult.length; i++) {
			queryResult[i] = previousterms + " " + queryResult[i];
		}
		return queryResult;
	}
	

}
