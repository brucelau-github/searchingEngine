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
package analysis;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PurifyingHTML implements Purifable {
	StringBuffer html = new StringBuffer();
	Document doc;
	public PurifyingHTML(String source){
		doc = Jsoup.parse(source);
	}
	
	@Override
	public String purify() {
		
		extractInnerText("title");
		extractAttribute("meta","content");
		extractAttribute("img","alt");
		extractInnerText("a");
		extractInnerText("span");
		extractInnerText("li");
		extractInnerText("h1");
		extractInnerText("h2");
		extractInnerText("h3");
		extractInnerText("h4");
		extractInnerText("h5");
		extractInnerText("p");
		replaceUnicode();
		return html.toString().trim();
	}


	private void extractInnerText(String tag) {
		
		Elements selects = doc.select(tag);
		for(Element e : selects){
			html.append(" "+ e.ownText());
		}
		html.append("\n");
	}

	private void extractAttribute(String tag, String attribute) {
		Elements selects = doc.select(tag + "["+attribute+"]");
		for(Element e : selects){
			html.append(" "+e.attr(attribute));
		}
		html.append("\n");
	}
	private void replaceUnicode(){
		String phtml = html.toString();
		phtml=phtml.replaceAll("[^\\n\\r\\t\\p{Print}]", "");
		html = new StringBuffer(phtml);
	}

}
