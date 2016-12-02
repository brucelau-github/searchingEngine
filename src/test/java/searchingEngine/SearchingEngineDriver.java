package searchingEngine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import query.HeapSort;
import query.InvertedItem;
import webcrawler.DomainCrawler;

public class SearchingEngineDriver {

	@Test
	public void test() {

		String pattern = "(.){0,2}(?i)" + "(spring)" + "(.){0,2}";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(
				"Last, but by no means least we look at Spring Boot, which some say is the best Java frameworks for web applications. Since it’s v1.0 release in 2014, Spring Boot has already joined Spring MVC in dominating the web framework landscape. With almost three in ten already using Spring Boot, it’s fast becoming the go to option for Spring users as well as those contemplating a microservices architecture. In fact, of those who do state they use a microservices architecture, an impressive 48% use Spring Boot. This is quite telling and significant so early on in the microservices era. We should mention Spring MVC too really, as it’s growing a few percent each year among our respondents and holds it’s rightful place in top spot of the web framework table. ");
		while (m.find()) {
			System.out.println(m.group(0));
		}
		DomainCrawler dc = new DomainCrawler("www.uwindsor.ca");
		assertTrue("my Class".toLowerCase().indexOf("class") != -1);
		// assertTrue("my class".matches("\\w*Class\\w*"));
	}

}