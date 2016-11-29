package analysis;

import java.util.Scanner;

public class ScannerAnalysis extends Analyser {
	public ScannerAnalysis(long docID) {
		super(docID);
		// TODO Auto-generated constructor stub
	}
	private int currentPostion = 0;
	@Override
	public void analyse(String doc) {
		Scanner sc = new Scanner(doc).useDelimiter(wordSplitter);
		while(sc.hasNext()){
			addTerm(sc.next(), currentPostion);
			currentPostion++;
		}
		sc.close();
	}
	@Override
	public void print() {
		// TODO Auto-generated method stub
		
	}
}
