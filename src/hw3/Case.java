// Sai Rajuladevi, srajulad
package hw3;

import javafx.beans.property.*;

public class Case implements Comparable<Case>{
	private StringProperty caseDate = new SimpleStringProperty();
	private StringProperty caseTitle = new SimpleStringProperty();
	private StringProperty caseType = new SimpleStringProperty();
	private StringProperty caseNumber = new SimpleStringProperty();
	private StringProperty caseLink = new SimpleStringProperty();
	private StringProperty caseCategory = new SimpleStringProperty();
	private StringProperty caseNotes = new SimpleStringProperty();

	Case(){ // Default constructor -> never used, but good to have
		caseDate.set("");
		caseTitle.set("");
		caseType.set("");
		caseNumber.set("");
		caseLink.set("");
		caseCategory.set("");
		caseNotes.set("");
	}

	Case(String caseDate, String caseTitle, String caseType, String caseNumber, 
			String caseLink, String caseCategory, String caseNotes){ // Constructor with params, will be used a lot
		this.caseDate.set(caseDate.trim()); // trim every argument to ensure no errors
		this.caseTitle.set(caseTitle.trim());
		this.caseType.set(caseType.trim());
		this.caseNumber.set(caseNumber.trim());
		this.caseLink.set(caseLink.trim()); 
		this.caseCategory.set(caseCategory.trim());
		this.caseNotes.set(caseNotes.trim());
	}
	// All the get, set, and property methods for the Beans class functionality
	public final String getCaseDate() { return caseDate.get(); }
	public final void setCaseDate(String date) { this.caseDate.set(date.trim()); }
	public final StringProperty caseDateProperty() { return caseDate; }
	
	public final String getCaseTitle() { return caseTitle.get(); }
	public final void setCaseTitle(String title) { this.caseTitle.set(title.trim()); }
	public final StringProperty caseTitleProperty() { return caseTitle; }
	
	public final String getCaseType() { return caseType.get(); }
	public final void setCaseType(String type) { this.caseType.set(type.trim()); }
	public final StringProperty caseTypeProperty() { return caseType; }

	public final String getCaseNumber() { return caseNumber.get(); }
	public final void setCaseNumber(String number) { this.caseNumber.set(number.trim()); }
	public final StringProperty caseNumberProperty() { return caseNumber; }

	public final String getCaseLink() { return caseLink.get(); }
	public final void setCaseLink(String link) { this.caseLink.set(link.trim()); }
	public final StringProperty caseLinkProperty() { return caseLink; }
	
	public final String getCaseCategory() { return caseCategory.get(); }
	public final void setCaseCategory(String category) { this.caseCategory.set(category.trim()); }
	public final StringProperty caseCategoryProperty() { return caseCategory; }

	public final String getCaseNotes() { return caseNotes.get(); }
	public final void setCaseNotes(String notes) { this.caseNotes.set(notes.trim()); }
	public final StringProperty caseNotesProperty() { return caseNotes; }

	// compare the CaseDates in reverse to sort from most recent
	@Override
	public int compareTo(Case c) {
		// TODO Auto-generated method stub
		return c.getCaseDate().compareTo(this.getCaseDate());
	}
	
	// ToString general method
	@Override
	public String toString() {
		return this.getCaseNumber();
	}




}
