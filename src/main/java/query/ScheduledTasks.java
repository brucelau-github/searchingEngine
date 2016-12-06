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