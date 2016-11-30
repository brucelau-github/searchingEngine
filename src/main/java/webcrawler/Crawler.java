package webcrawler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	String Domain = "http://www.uwindsor.ca";
	int depth = 0;
	private Queue<String> urlQueue = new LinkedList<String>();
	private ArrayList<String> visitedUrl = new ArrayList<String>();
	Document doc;

	String httpHeaderUserAgent = "Mozilla/5.0 (X11; Linux x86_64; rv:45.0) Gecko/20100101 Firefox";
	private DocDAO docDao = new DocDAO();

	public Crawler() {

	}

	public static void main(String[] args) {
		Crawler cr = new Crawler();
		cr.crawl();
	}

	public void crawl() {
		int counter = 0;
		urlQueue.add(Domain);
		while (!urlQueue.isEmpty()) {
			try {
				String url = urlQueue.remove();
//				Thread.sleep(2000);
				doc = Jsoup.connect(url).userAgent(httpHeaderUserAgent).get();
				visit(doc.html(),url);
				Elements links = doc.select("a[href]");
				for (Element link : links) {
					String newUrl = link.attr("abs:href");
					if (!visitedUrl(newUrl)) {
						urlQueue.add(newUrl);
					}
				}
				counter++;
				if (counter > 10) {
					break;
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("unvisiable ");
			}
		}
	}

	private boolean visitedUrl(String newUrl) {
		return docDao.isVistedUrl(newUrl);
	}

	private void visit(String text,String url) {
		docDao.save(text,url);
	}

}
