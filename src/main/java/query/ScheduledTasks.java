package query;

import java.text.SimpleDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import analysis.InvertedIndex;
import webcrawler.DomainCrawler;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    
    @Scheduled(fixedRate = 60000)
    public void webCrawler() {
    	DomainCrawler cr = new DomainCrawler("http://www.uwindsor.ca");
		cr.crawl();
        log.info("web crawler Module starts to run");
    }
    @Scheduled(fixedRate = 60000)
    public void invertedInedex() {
    	InvertedIndex inv = new InvertedIndex();
    	inv.run();
    	log.info("AnalyseModule starts to run");
    }
}