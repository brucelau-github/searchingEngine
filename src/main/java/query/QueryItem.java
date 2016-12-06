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