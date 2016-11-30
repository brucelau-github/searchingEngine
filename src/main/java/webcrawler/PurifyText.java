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
