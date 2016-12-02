package query;

public class InvertedItem implements Comparable<InvertedItem> {
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
		int i = 0;
		for (String p : pos) {
			this.position[i++] = Long.parseLong(p);
		}
	}

	public InvertedItem(String key) {
		String[] tmp = key.split(":");
		this_constr(tmp[0], tmp[1], tmp[2]);
	}

	private void this_constr(String docID, String freque, String position) {
		this.docID = Long.parseLong(docID);
		this.freque = Long.parseLong(freque);
		String[] pos = position.split("-");
		this.position = new long[pos.length];
		int i = 0;
		for (String p : pos) {
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

	@Override
	public int compareTo(InvertedItem it) {
		if (this.docID == it.docID)
			return 0;
		return this.docID > it.docID ? 1 : -1;
	}

}
