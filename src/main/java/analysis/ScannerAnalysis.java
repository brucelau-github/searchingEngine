package analysis;

import java.util.Scanner;

public class ScannerAnalysis extends Analyser {
	public ScannerAnalysis(int docID) {
		super(docID);
		// TODO Auto-generated constructor stub
	}
	private int currentPostion = 0;
	@Override
	public void analyse(String doc) {
		Scanner sc = new Scanner(doc).useDelimiter(wordSplitter);
		while(sc.hasNext()){
			invertedItems.addTerm(sc.next(), currentPostion);
			currentPostion++;
		}
		sc.close();
	}
}
