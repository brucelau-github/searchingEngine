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

public class PurifyText {
	protected String[][] search = {
			{"<!DOCTYPE[^<>]*>", ""},           	//<!DOCTYPE
			{"<html[^<>]*>",""},            	//<html>
			{"</?head[^<>]*>",""},            	//<head>
			{"<meta[^<>]*>",""},
			{"<link[^<>]*>",""},
			{"<title>(.*)</title>","$1"},
			{"</?body[^<>]*>",""},							//body
			{"<a[^<>]*>(.*)</a>","$1"},							//body
			{"<!--[^<>]+-->",""},							//comments
			{"</?div[^<>]*>",""},							//div
			{"<style[^<>]*>[^<>]*</style>",""},            	//<style> text </style>
			{"<script[^<>]*>[^<>]*</script>",""},           	//<script> text </style>
			{"<noscript[^<>]*>.*</noscript>",""}, 
//			{"[\n\r\\s]+","\n"},           	//<script> text </style>
	};
	public PurifyText() {
		super();
	}
    public String purify(String html){
    	String phtml = html;
    	
    	for(int i = 0 ; i < search.length; i++) {
    		
    		phtml=phtml.replaceAll(search[i][0],search[i][1]);
    	}
    	return phtml;
    }
    
}
