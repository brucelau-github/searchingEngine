package analysis;

import java.util.Scanner;

public class ScannerAnalysis implements analyzable {
	private InvertedItem invertedItems = new InvertedItem();
	private final String wordSplitter = "\\s+";
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
	@Override
	public void print(){
		System.out.print(invertedItems);
	}

}
