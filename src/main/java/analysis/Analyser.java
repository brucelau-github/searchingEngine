package analysis;

public abstract class Analyser implements analyzable {

	protected InvertedItem invertedItems;
	protected final String wordSplitter = "\\s+";

	public Analyser(int docID) {
		invertedItems = new InvertedItem(docID);
	}

	@Override
	public void print() {
		System.out.print(invertedItems);
	}

	public void save() {
		invertedItems.mergeToDataBase();
	}

}