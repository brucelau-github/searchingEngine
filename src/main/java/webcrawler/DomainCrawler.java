/**
 * Get a domain from redis SET DOMAIN:TO_VISIT
 * 
 * 1Get the index page of this domain
 * Save this page
 * Get all links on this page
 * Every link do
 * 	if the links is in this domain Enqueue this link
 * 	else 
 * 		if this link is not memenber of DOMAIN:TO_VISIT
 * 			 Save to Set SET DOMAIN:TO_VISIT
 * repeat 1.
 * 
 * 		
 */
package webcrawler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.AppConfig;

public class DomainCrawler extends Crawler {
	final AppConfig appConfig = AppConfig.newInstance();
	String currentDomain = "";
	String pattern = "https?://(\\w*\\.)*(\\w*\\.[a-zA-Z]+)";
	Pattern r = Pattern.compile(pattern);
	Matcher m;
	int DOMAIN_DEPTH = Integer.parseInt(appConfig.getProperty("DOMAIN_DEPTH"));
	int pageCounter = 0;

	public DomainCrawler() {

	}

	public DomainCrawler(String url) {
		m = r.matcher(url.toLowerCase());
		if (m.find())
			currentDomain = m.group(2);
		this.url = url.toLowerCase();
	}

	protected boolean isInDomain(String newurl) {
		boolean isInDomain = false;
		String newHostName = "";
		m = r.matcher(newurl.toLowerCase());
		if (m.find()) {
			newHostName = m.group(2);
			isInDomain = currentDomain.equals(newHostName);
			if (!isInDomain) {
				if (isNewDomain(newHostName))
					docDao.saveNewDomain(newHostName);
			}
		}
		return isInDomain;
	}

	private boolean isNewDomain(String newHostName) {
		// TODO Auto-generated method stub
		return !(docDao.isAddedDomain(newHostName) || docDao.isVisitedDomain(newHostName));
	}

	@Override
	protected boolean isCrawlable(String newUrl) {
		return isInDomain(newUrl);
	}

	@Override
	protected void afterCrawl() {
		if (docDao.isExistVisitedURL())
			docDao.linkAllUrlWithDomain(currentDomain);
		else docDao.reomveVisitedDomain(currentDomain);
	}

	@Override
	protected void beforeCrawl() {
		while (docDao.isVisitedDomain(currentDomain)) {
			currentDomain = docDao.getNextDomain();
			url = "http://www." + currentDomain;
		}
		docDao.saveVisitedDomain(currentDomain);
	}

	public static void main(String[] args) {
		DomainCrawler cr = new DomainCrawler("http://www.uwindsor.ca");
		cr.crawl();
	}

	@Override
	protected void crawlerDo() {
		docID = String.valueOf(docDao.getNewDocID());
		docDao.saveDoc(doc.html(), docID);
		docDao.saveUrl(url, docID);
		urlHistory.add(url);
		pageCounter++;
		if (pageCounter > DOMAIN_DEPTH)
			this.stopSwitch = true;
	}
}
