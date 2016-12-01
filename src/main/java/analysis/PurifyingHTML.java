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
		return html.toString();
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
