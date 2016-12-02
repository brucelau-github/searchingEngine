package webcrawler;

import java.util.LinkedList;
import java.util.Queue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Crawler {
	protected Queue<String> urlQueue = new LinkedList<String>();
	protected boolean stopSwitch = false;
	protected Document doc;
	protected String docID = "";
	protected String url = "http://www.uwindsor.ca";
	protected String httpHeaderUserAgent = "Mozilla/5.0 (X11; Linux x86_64; rv:45.0) Gecko/20100101 Firefox";
	protected DocDAO docDao = new DocDAO();

	public Crawler() {

	}

	public static void main(String[] args) {
		Crawler cr = new Crawler();
		cr.crawl();
	}

	@Scheduled(fixedRate = 60000)
	public void crawl() {
		beforeCrawl();
		urlQueue.add(url);
		while (!urlQueue.isEmpty()) {
			try {
				url = urlQueue.remove();
				doc = Jsoup.connect(url).userAgent(httpHeaderUserAgent).get();
				crawlerDo();
				Elements links = doc.select("a[href]");
				for (Element link : links) {
					String newUrl = link.attr("abs:href");
					if (!isCrawlable(newUrl)) {
						urlQueue.add(newUrl);
					}
				}
				if (stopSwitch) {
					break;
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("unvisiable ");
			}
		}
		afterCrawl();
	}

	protected void beforeCrawl() {
		
	}

	protected void afterCrawl() {
			
	}

	protected void crawlerDo() {
		docID = String.valueOf(docDao.getNewDocID());
		docDao.saveDoc(doc.html(),docID);
		docDao.saveUrl(url,docID);
		
	}

	protected boolean isCrawlable(String newUrl) {
		return docDao.isVistedUrl(newUrl);
	}

}
