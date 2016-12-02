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

public class DomainCrawler extends Crawler {
	
	String currentDomain = "";
	String pattern = "https?://(\\w*\\.)+[a-zA-Z]+";
	Pattern r = Pattern.compile(pattern);
	Matcher m;
	
	public DomainCrawler(){
		
	}
	public DomainCrawler(String url) {
		m = r.matcher(url.toLowerCase());
		if(m.find()) currentDomain = m.group(0);
		this.url = url.toLowerCase();
	}

	protected boolean isInDomain(String newurl){
		boolean isInDomain = false;
		String newHostName = "";
		m = r.matcher(newurl.toLowerCase());
		if(m.find()) {
			newHostName = m.group(0);
			isInDomain = currentDomain.equals(newHostName);
			if(isInDomain) {
				if(!docDao.isVisitedDomain(newHostName)&&!docDao.isAddedDomain(newHostName))
				docDao.saveNewDomain(newHostName);
			}
		}
		return isInDomain;
	}
	
	@Override
	protected boolean isCrawlable(String newUrl) {
		return isInDomain(newUrl) && docDao.isVistedUrl(newUrl);
	}
	
	@Override
	protected void afterCrawl() {
		
	}
	
	@Override
	protected void beforeCrawl() {
		docDao.saveVisitedDomain(currentDomain);
	}
	public static void main(String[] args) {
		DomainCrawler cr = new DomainCrawler("http://www.uwindsor.ca");
		cr.crawl();
	}
}
