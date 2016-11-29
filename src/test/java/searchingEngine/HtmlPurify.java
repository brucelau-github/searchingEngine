package searchingEngine;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import webcrawler.PurifyText;

public class HtmlPurify {

	@Test
	public void test() {
		File input = new File("testdata/uwindsor.html");
//		File input = new File(".");
		System.out.println(input.getAbsolutePath());
		Document doc;
		try {
			doc = Jsoup.parse(input, "UTF-8", "http://www.uwindsor.ca.com/");
			PurifyText p = new PurifyText();
			System.out.println(p.purify(doc.html()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
