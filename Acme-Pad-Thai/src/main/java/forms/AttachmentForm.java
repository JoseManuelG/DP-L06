package forms;

import domain.LearningMaterial;


public class AttachmentForm {
	
	private String text;
	private LearningMaterial learningMaterial;
	
//Constructor
	public AttachmentForm(){
		super();
	}
	
//attributes------------
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public LearningMaterial getLearningMaterial() {
		return learningMaterial;
	}
	
	public void setLearningMaterial(LearningMaterial learningMaterial) {
		this.learningMaterial = learningMaterial;
	}
	
	
	
}
