package analysis;

import java.util.Scanner;

public class ScannerAnalysis extends Analyser {
	public ScannerAnalysis(String docID) {
		super(docID);
		// TODO Auto-generated constructor stub
	}
	private int currentPostion = 0;
	@Override
	public void analyse() {
		Scanner sc = new Scanner(doc).useDelimiter(wordSplitter);
		while(sc.hasNext()){
			addTerm(sc.next(), currentPostion);
			currentPostion++;
		}
		sc.close();
	}
}
