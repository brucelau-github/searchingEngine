package query;


public class QueryItem {

    private String title;
	private String summary;
    private String url;
    public void setTitle(String title) {
		this.title = title;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public void setUrl(String url) {
		this.url = url;
	}
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