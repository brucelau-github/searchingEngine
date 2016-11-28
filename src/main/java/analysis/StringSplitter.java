package analysis;

public class StringSplitter implements analyzable {
	private final String wordSplitter = "\\s+";
	private InvertedItem invertedItems = new InvertedItem();
	@Override
	public void analyse(String doc) {
		String[] words = doc.split(wordSplitter);
		for(int i = 0 ; i < words.length; i ++){
			invertedItems.addTerm(words[i], i);
		}

	}
	@Override
	public void print(){
		System.out.print(invertedItems);
	}

}
