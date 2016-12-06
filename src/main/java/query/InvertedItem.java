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
