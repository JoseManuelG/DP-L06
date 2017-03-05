package forms;

import domain.Configuration;


public class ConfigurationForm {
	
	private String text;
	private Configuration configuration;
	
//Constructor
	public ConfigurationForm(){
		super();
	}
	
//attributes------------
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Configuration getConfiguration() {
		return configuration;
	}
	
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	
	
	
}
