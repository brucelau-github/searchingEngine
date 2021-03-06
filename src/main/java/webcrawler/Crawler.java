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
package webcrawler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public abstract class Crawler {
	protected Queue<String> urlQueue = new LinkedList<String>();
	protected boolean stopSwitch = false;
	protected Document doc;
	protected String docID = "";
	protected String url = "http://www.uwindsor.ca";
	protected String httpHeaderUserAgent = "Mozilla/5.0 (X11; Linux x86_64; rv:45.0) Gecko/20100101 Firefox";
	protected DocDAO docDao = new DocDAO();
	protected List<String> urlHistory = new ArrayList<String>();

	public Crawler() {

	}

	@Scheduled(fixedRate = 3600000)
	public void crawl() {
		beforeCrawl();
		urlQueue.add(url);
		while (!urlQueue.isEmpty() && !stopSwitch) {
			try {
				url = urlQueue.remove();
				doc = Jsoup.connect(url).userAgent(httpHeaderUserAgent).get();
				crawlerDo();
				Elements links = doc.select("a[href]");
				for (Element link : links) {
					String newUrl = clearnURL(link.attr("abs:href"));
					if (isCrawlable(newUrl)) {
						if (!urlHistory.contains(newUrl))
							urlQueue.add(newUrl);
							urlHistory.add(newUrl);
					}
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("unvisiable ");
			}
		}
		afterCrawl();
	}

	protected String clearnURL(String url) {
		return url.trim().replaceAll("#[\\w-]*$", "").replaceAll("/$", "");
	}

	protected abstract void beforeCrawl();

	protected abstract void afterCrawl();

	protected abstract void crawlerDo() ;

	protected abstract boolean isCrawlable(String newUrl);

}
