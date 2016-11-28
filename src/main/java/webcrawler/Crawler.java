package webcrawler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
		String Domain = "http://www.wikipedia.com";
		int depth = 0;
		private Queue<String> urlQueue = new LinkedList<String>();
		private ArrayList<String> visitedUrl = new ArrayList<String>();
		Document doc;
		
		String httpHeaderUserAgent = "Mozilla/5.0 (X11; Linux x86_64; rv:45.0) Gecko/20100101 Firefox";
		public Crawler(){
			
		}
		
		public void crawl(){
			try {
				urlQueue.add(Domain);
				while(!urlQueue.isEmpty()){
					String url = urlQueue.remove();
					doc = Jsoup.connect(url).userAgent(httpHeaderUserAgent).get();
					visit(url);
					Elements links = doc.select("a[href]");
					for(Element link : links){
						String newUrl = link.attr("abs:href");
						if(!visitedUrl.contains(newUrl)) {
							urlQueue.add(newUrl);
						}
					}
					
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private void visit(String url) {
			// TODO Auto-generated method stub
			
		}
		
}
