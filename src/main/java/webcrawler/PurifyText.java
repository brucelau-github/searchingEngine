package webcrawler;

public class PurifyText {
	protected String[] search = {
	        "\\r",                                           // Non-legal carriage return
	        "[\\n\\t]+",                                      // Newlines and tabs
	        "<head\\\\b[^>]*>.*?<\\\\head>",                     //<head>
	        "<script\\b[^>]*>.*?<\\script>",                // <script>s -- which strip_tags supposedly has problems with
	        "<style\\b[^>]*>.*?<\\style>",                 //  <style>s -- which strip_tags supposedly has problems with
	        "<i\\b[^>]*>(.*?)<\\>",                        // <i>
	        "<em\\b[^>]*>(.*?)<\\em>",                     //  <em>
	        "(<ul\\b[^>]*>|<\\ul>)",                       //  <ul> and <ul>
	        "(<ol\\b[^>]*>|<\\ol>)",                       //  <ol> and <ol>
	        "(<dl\\b[^>]*>|<\\dl>)",                       //  <dl> and <dl>
	        "<li\\b[^>]*>(.*?)<\\li>",                     //  <li> and <li>
	        "<dd\\b[^>]*>(.*?)<\\dd>",                     //  <dd> and <dd>
	        "<dt\\b[^>]*>(.*?)<\\dt>",                       //<dt> and <dt>
	        "<li\\b[^>]*>",                                //  <li>
	        "<hr\\b[^>]*>",                                 // <hr>
	        "<div\\b[^>]*>",                                // <div>
	        "(<table\\b[^>]*>|<\\table>)",                  // <table> and <table>
	        "(<tr\\b[^>]*>|<\\tr>)",                       //  <tr> and <tr>
	        "<td\\b[^>]*>(.*?)<\\td>",                      // <td> and <td>
	        "<span>.+?<\\span>",  							//<span class="_html2text_ignore">...<span>
	        "<(img)\\b[^>]*alt=\"([^>\"]+)\"[^>]*>",      //    <img> with alt tag
	};
	protected String[] replace = {
	        "",                              // Non-legal carriage return
	        " ",                             // Newlines and tabs
	        "",                              // <head>
	        "",                              // <script>s -- which strip_tags supposedly has problems with
	        "",                              // <style>s -- which strip_tags supposedly has problems with
	        "_\\1_",                         // <i>
	        "_\\1_",                         // <em>
	        "\n\n",                          // <ul> and </ul>
	        "\n\n",                          // <ol> and </ol>
	        "\n\n",                          // <dl> and </dl>
	        "\t* \\1\n",                     // <li> and </li>
	        " \\1\n",                        // <dd> and </dd>
	        "\t* \\1",                       // <dt> and </dt>
	        "\n\t* ",                        // <li>
	        "\n-------------------------\n", // <hr>
	        "<div>\n",                       // <div>
	        "\n\n",                          // <table> and </table>
	        "\n",                            // <tr> and </tr>
	        "\t\t\\1\n",                     // <td> and </td>
	        "",                              // <span class="_html2text_ignore">...</span>
	        "[\\2]",                         // <img> with alt tag
	};
	public PurifyText() {
		super();
	}
    public String purify(String html){
    	
    	for(int i = 0 ; i < search.length; i++) {
    		html.replaceAll(search[i], replace[i]);
    	}
    	return html;
    }
    
}
