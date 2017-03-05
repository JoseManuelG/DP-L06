package utilities;

import java.util.List;

public class RecipeAjaxSearch {
	private List<String> ids;
	private List<String> tickers;
	private List<String> titles;
	private List<String> summaries;
	private List<String> lastModifieds;
	
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	public List<String> getTickers() {
		return tickers;
	}
	public void setTickers(List<String> tickers) {
		this.tickers = tickers;
	}
	public List<String> getTitles() {
		return titles;
	}
	public void setTitles(List<String> titles) {
		this.titles = titles;
	}
	public List<String> getSummaries() {
		return summaries;
	}
	public void setSummaries(List<String> summaries) {
		this.summaries = summaries;
	}
	public List<String> getLastModifieds() {
		return lastModifieds;
	}
	public void setLastModifieds(List<String> lastModifieds) {
		this.lastModifieds = lastModifieds;
	}

}