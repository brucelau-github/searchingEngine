package query;


public class QueryItem {

    private final String title;
	private final String summary;
    private final String url;
    public QueryItem(String title, String summary, String url) {
    	super();
    	this.title = title;
    	this.summary = summary;
    	this.url = url;
    }
	public String getTitle() {
		return title;
	}
	public String getSummary() {
		return summary;
	}
	public String getUrl() {
		return url;
	}
    
}