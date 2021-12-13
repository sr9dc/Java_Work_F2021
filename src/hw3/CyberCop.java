// Sai Rajuladevi, srajulad
package hw3;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class CyberCop extends Application{

	public static final String DEFAULT_PATH = "data"; //folder name where data files are stored
	public static final String DEFAULT_HTML = "/CyberCop.html"; //local HTML
	public static final String APP_TITLE = "Cyber Cop"; //displayed on top of app

	CCView ccView = new CCView();
	CCModel ccModel = new CCModel();

	CaseView caseView; //UI for Add/Modify/Delete menu option

	GridPane cyberCopRoot;
	Stage stage;

	static Case currentCase; //points to the case selected in TableView.

	public static void main(String[] args) {
		launch(args);
	}

	/** start the application and show the opening scene */
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		primaryStage.setTitle("Cyber Cop");
		cyberCopRoot = ccView.setupScreen();  
		setupBindings();
		Scene scene = new Scene(cyberCopRoot, ccView.ccWidth, ccView.ccHeight);
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		ccView.webEngine.load(getClass().getResource(DEFAULT_HTML).toExternalForm());
		primaryStage.show();
	}

	/** setupBindings() binds all GUI components to their handlers.
	 * It also binds disableProperty of menu items and text-fields 
	 * with ccView.isFileOpen so that they are enabled as needed
	 */
	void setupBindings() {
		//write your code here
		// Get file Bindings setup
		ccView.openFileMenuItem.disableProperty().bind(ccView.isFileOpen); 
		ccView.closeFileMenuItem.disableProperty().bind(ccView.isFileOpen.not()); 

		// Get input Bindings setup
		ccView.titleTextField.disableProperty().bind(ccView.isFileOpen.not());
		ccView.caseTypeTextField.disableProperty().bind(ccView.isFileOpen.not());
		ccView.yearComboBox.disableProperty().bind(ccView.isFileOpen.not());
		ccView.caseNumberTextField.disableProperty().bind(ccView.isFileOpen.not());
		ccView.searchButton.disableProperty().bind(ccView.isFileOpen.not());
		ccView.clearButton.disableProperty().bind(ccView.isFileOpen.not());

		// Get menu bindings setup
		ccView.addCaseMenuItem.disableProperty().bind(ccView.isFileOpen.not());
		ccView.modifyCaseMenuItem.disableProperty().bind(ccView.isFileOpen.not());
		ccView.deleteCaseMenuItem.disableProperty().bind(ccView.isFileOpen.not());


		// Bind the selected item in the Table for case selection
		ccView.caseTableView.getSelectionModel().selectedItemProperty().addListener(event->{
			currentCase = ccView.caseTableView.getSelectionModel().getSelectedItem();

			if(ccView.caseTableView.getItems().size() > 0 && currentCase != null) { // autofill boxes based on currentCase
				ccView.titleTextField.setText(currentCase.getCaseTitle());
				ccView.caseTypeTextField.setText(currentCase.getCaseType());
				ccView.yearComboBox.valueProperty().set(currentCase.getCaseDate().substring(0,4));
				ccView.caseNumberTextField.setText(currentCase.getCaseNumber());
			}
			ccView.webEngine.load(getClass().getResource(DEFAULT_HTML).toExternalForm()); // reset to original form
			if(currentCase != null) { // null check
				ccView.caseNotesTextArea.setText(currentCase.getCaseNotes()); // get case notes in
				if (currentCase.getCaseLink() == null || currentCase.getCaseLink().isBlank()) {  //if no link in data
					URL url = getClass().getClassLoader().getResource(DEFAULT_HTML);  //default html
					if (url != null) ccView.webEngine.load(url.toExternalForm());
				} else if (currentCase.getCaseLink().toLowerCase().startsWith("http")){  //if external link
					ccView.webEngine.load(currentCase.getCaseLink());
				} else {
					URL url = getClass().getClassLoader().getResource(currentCase.getCaseLink().trim());  //local link
					if (url != null) ccView.webEngine.load(url.toExternalForm());
				}
			}
		});


		// OpenFileMenuItemHandler
		ccView.openFileMenuItem.setOnAction(event ->{ //lambda expression
			FileChooser fileChooser = new FileChooser(); // file choosing
			fileChooser.setTitle("Select file");
			fileChooser.setInitialDirectory(new File(DEFAULT_PATH)); // set path
			fileChooser.getExtensionFilters().addAll( // setup file filters 
					new ExtensionFilter("CSV Files", "*.csv"),
					new ExtensionFilter("TSV Files", "*.tsv"));

			File file = null; // set to null before initializing 
			if((file = fileChooser.showOpenDialog(stage)) != null) { 
				ccModel.caseList.clear(); // reset for multiple open operations
				ccModel.caseMap.clear(); 
				ccModel.yearMap.clear();
				ccModel.yearList.clear();
				ccModel.readCases(file.getPath()); // use readCases method to populate caseList
				ccModel.buildYearMapAndList(); // populate the years map and list

				ccView.caseTableView.setItems(ccModel.caseList); // put Cases in tableView
				currentCase = ccView.caseTableView.getItems().get(0); // set currentCase pointer to first index

				if(ccView.caseTableView.getItems().size() > 0 && currentCase != null) { // autofill boxes based on currentCase
					currentCase = ccView.caseTableView.getItems().get(0);
					ccView.titleTextField.setText(currentCase.getCaseTitle());
					ccView.caseTypeTextField.setText(currentCase.getCaseType());
					ccView.yearComboBox.valueProperty().set(currentCase.getCaseDate().substring(0,4));
					ccView.caseNumberTextField.setText(currentCase.getCaseNumber());
				}

				// url viewing and notes setup for first Case in TableView				
				if(currentCase != null) { // null check
					ccView.caseNotesTextArea.setText(currentCase.getCaseNotes());
					if (currentCase.getCaseLink() == null || currentCase.getCaseLink().isBlank()) {  //if no link in data
						URL url = getClass().getClassLoader().getResource(DEFAULT_HTML);  //default html
						if (url != null) ccView.webEngine.load(url.toExternalForm());
					} else if (currentCase.getCaseLink().toLowerCase().startsWith("http")){  //if external link
						ccView.webEngine.load(currentCase.getCaseLink());
					} else {
						URL url = getClass().getClassLoader().getResource(currentCase.getCaseLink().trim());  //local link
						if (url != null) ccView.webEngine.load(url.toExternalForm());
					}
				}
			}

			// sort the yearList and display
			Collections.sort(ccModel.yearList);
			ccView.yearComboBox.setItems(ccModel.yearList);

			ccView.messageLabel.setText(String.valueOf(ccView.caseTableView.getItems().size()) + " cases."); // message label update

			stage.setTitle("Cyber Cop: " + file.getName()); // update window with filename
			ccView.isFileOpen.set(true); // controls entry in opening and closing files
		});

		// CloseFileMenuItemHandler
		ccView.closeFileMenuItem.setOnAction(event ->{
			// reset/clear all values
			ccView.titleTextField.clear();
			ccView.caseTypeTextField.clear();
			ccView.yearComboBox.valueProperty().set(null);
			ccView.caseNumberTextField.clear();
			ccView.messageLabel.setText("");
			ccView.caseNotesTextArea.clear();
			ccView.caseTableView.getItems().clear();
			ccView.webEngine.load(getClass().getResource(DEFAULT_HTML).toExternalForm()); // reset to original form

			stage.setTitle(APP_TITLE); // reset window title

			ccView.isFileOpen.set(false); // control entry again to allow opening
		});

		// ExitMenuItemHandler
		ccView.exitMenuItem.setOnAction(ActionEvent -> stage.close()); 

		// SearchButtonHandler
		ccView.searchButton.setOnAction(event ->{
			ObservableList<Case> caseList = // search for the cases using all input parameters from GUI
					FXCollections.observableList(ccModel.searchCases(ccView.titleTextField.getText(), 
							ccView.caseTypeTextField.getText(), ccView.yearComboBox.getValue(), ccView.caseNumberTextField.getText()));

			ccView.caseTableView.setItems(caseList); // update TableView

			if(ccView.caseTableView.getItems().size() > 0 && currentCase != null) { // autofill boxes based on first search result
				currentCase = ccView.caseTableView.getItems().get(0);
				ccView.titleTextField.setText(currentCase.getCaseTitle());
				ccView.caseTypeTextField.setText(currentCase.getCaseType());
				ccView.yearComboBox.valueProperty().set(currentCase.getCaseDate().substring(0,4));
				ccView.caseNumberTextField.setText(currentCase.getCaseNumber());
			}

			// reduce the years to filtered list in yearsComboBox
			// same algorithm as in CCModel.java
			Iterator<Case> casesIterator = caseList.iterator();

			ObservableMap<String, Case> caseMap = FXCollections.observableHashMap();		// map with caseNumber as key and Case as value
			ObservableMap<String, List<Case>> yearMap = FXCollections.observableHashMap();	// map with each year as a key and a list of all cases dated in that year as value. 
			ObservableList<String> yearList = FXCollections.observableArrayList();			// list of years to populate the yearComboBox in ccView

			// iterate through caseMap and populate initial caseMap
			while (casesIterator.hasNext()) {
				Case input = casesIterator.next(); 
				caseMap.put(input.getCaseNumber(), input); // put into caseMap
			}
			for (Map.Entry<String, Case> entry : caseMap.entrySet()) { // for each Entry, map the cases to the years
				Case value = (Case) entry.getValue(); // Case Entry
				String caseYear = value.getCaseDate().substring(0,4); // Corresponding year key

				if(yearMap.containsKey(caseYear)){ // if the year key exists, add to the entry list value
					yearMap.get(caseYear).add(value); 
				}
				else {
					List<Case> startCasesList = new ArrayList<>(); // otherwise initialize the List with first case for that year
					startCasesList.add(value);
					yearMap.put(caseYear, startCasesList);
				}
			}
			yearList.addAll(yearMap.keySet()); // add all the reduced years into the yearList
			Collections.sort(yearList); // and sort for clean viewing
			ccView.yearComboBox.setItems(yearList); // update comboBox with filtered years
			ccView.caseTableView.getSelectionModel().select(0); // programatically select to update view
			ccView.messageLabel.setText(String.valueOf(ccView.caseTableView.getItems().size()) + " cases."); // update message label with filtered list size
		});

		// ClearButtonHandler
		ccView.clearButton.setOnAction(event ->{ // clear inputs in GUI
			ccView.titleTextField.clear();
			ccView.caseTypeTextField.clear();
			ccView.yearComboBox.valueProperty().set(null);
			ccView.caseNumberTextField.clear();
			ccView.messageLabel.setText(""); // reset message Label
		});

		// AddButtonHandler
		ccView.addCaseMenuItem.setOnAction(event->{
			CaseView addCases = new AddCaseView("Add Case"); // set the window and name
			addCases.buildView().show(); // build and show the window

			addCases.updateButton.setOnAction(event2->{ // updateButtonEventHandler
				// get all inputs from the textFields
				String title = addCases.titleTextField.getText(); 
				String date = addCases.caseDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // date format
				String type = addCases.caseTypeTextField.getText();
				String number = addCases.caseNumberTextField.getText();
				String category = addCases.categoryTextField.getText();
				String link = addCases.caseLinkTextField.getText();
				String notes = addCases.caseNotesTextArea.getText();

				try {
					if(title.isEmpty() 
							|| date.isEmpty() 
							|| type.isEmpty() 
							|| number.isEmpty() ) {
						throw new DataException("Case must have date, title, type, and number");
					}

					else if(ccModel.caseMap.containsKey(number)) {
						throw new DataException("Duplicate case number");
					}
					else {
						// use case constructor to populate inputs
						Case addCase = new Case(date, title, type, number, link, category, notes);

						ccModel.caseList.add(addCase); // add case to the Model's caseList
						ccView.caseTableView.setItems(ccModel.caseList); // update the table
					}
				}
				catch (DataException d) {
				}

				ccView.messageLabel.setText(String.valueOf(ccView.caseTableView.getItems().size()) + " cases."); // update message label
			});

			addCases.closeButton.setOnAction(event2->{ // closeButtonEventHandler
				addCases.stage.close(); 
			});

			addCases.clearButton.setOnAction(event2->{ // clearButtonEventHandler
				// clear all GUI inputs
				addCases.titleTextField.clear();
				addCases.caseDatePicker.setValue(LocalDate.now()); // reset to sysdate
				addCases.caseTypeTextField.clear();
				addCases.caseNumberTextField.clear();
				addCases.categoryTextField.clear();
				addCases.caseLinkTextField.clear();
				addCases.caseNotesTextArea.clear();
			});
		});

		// ModifyButtonHandler
		ccView.modifyCaseMenuItem.setOnAction(event->{
			CaseView modifyCases = new ModifyCaseView("Modify Case"); // set the window and name

			// pull all arguments from the currentCase and populate inputs
			modifyCases.titleTextField.setText(currentCase.getCaseTitle());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // get date in correct format
			modifyCases.caseDatePicker.setValue(LocalDate.parse(currentCase.getCaseDate(), formatter)); // formatter in argument
			modifyCases.caseTypeTextField.setText(currentCase.getCaseType());
			modifyCases.caseNumberTextField.setText(currentCase.getCaseNumber());
			modifyCases.categoryTextField.setText(currentCase.getCaseCategory());
			modifyCases.caseLinkTextField.setText(currentCase.getCaseLink());
			modifyCases.caseNotesTextArea.setText(currentCase.getCaseNotes());

			modifyCases.buildView().show(); // Now, build and show the view with the arguments set above

			modifyCases.updateButton.setOnAction(event2->{ // updateButtonEventHandler
				String title = modifyCases.titleTextField.getText(); 
				String date = modifyCases.caseDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // date format
				String type = modifyCases.caseTypeTextField.getText();
				String number = modifyCases.caseNumberTextField.getText();


				try {
					if(title.isEmpty() 
							|| date.isEmpty() 
							|| type.isEmpty() 
							|| number.isEmpty() ) {
						throw new DataException("Case must have date, title, type, and number");
					}

					else if(ccModel.caseMap.containsKey(number)) {
						throw new DataException("Duplicate case number");
					}
					else {
						int currentCaseIndex = ccModel.caseList.indexOf(currentCase); // get the index location of current case

						// change the current case to reflect the set modifications 
						currentCase.setCaseTitle(modifyCases.titleTextField.getText());
						currentCase.setCaseDate(modifyCases.caseDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
						currentCase.setCaseType(modifyCases.caseTypeTextField.getText());
						currentCase.setCaseNumber(modifyCases.caseNumberTextField.getText());
						currentCase.setCaseCategory(modifyCases.categoryTextField.getText());
						currentCase.setCaseLink(modifyCases.caseLinkTextField.getText());
						currentCase.setCaseNotes(modifyCases.caseNotesTextArea.getText());

						ccModel.caseList.set(currentCaseIndex, currentCase); // update the modified currentCase  
						ccView.caseTableView.setItems(ccModel.caseList); // view the change in Table
						ccView.messageLabel.setText(String.valueOf(ccView.caseTableView.getItems().size()) + " cases."); // update message Label

						if(currentCase != null) { // update caseNotes and webURL again now with modification
							ccView.caseNotesTextArea.setText(currentCase.getCaseNotes());

							if (currentCase.getCaseLink() == null || currentCase.getCaseLink().isBlank()) {  //if no link in data
								URL url = getClass().getClassLoader().getResource(DEFAULT_HTML);  //default html
								if (url != null) ccView.webEngine.load(url.toExternalForm());
							} else if (currentCase.getCaseLink().toLowerCase().startsWith("http")){  //if external link
								ccView.webEngine.load(currentCase.getCaseLink());
							} else {
								URL url = getClass().getClassLoader().getResource(currentCase.getCaseLink().trim());  //local link
								if (url != null) ccView.webEngine.load(url.toExternalForm());
							}
						}
					}
				}
				catch (DataException d) {
				}
			});

			modifyCases.closeButton.setOnAction(event2->{ // closeButtonEventHandler
				modifyCases.stage.close();
			});

			modifyCases.clearButton.setOnAction(event2->{ // clearButtonEventHandler
				// clear everything
				modifyCases.titleTextField.clear();
				modifyCases.caseDatePicker.setValue(LocalDate.now());
				modifyCases.caseTypeTextField.clear();
				modifyCases.caseNumberTextField.clear();
				modifyCases.categoryTextField.clear();
				modifyCases.caseLinkTextField.clear();
				modifyCases.caseNotesTextArea.clear();
			});
		});

		// DeleteButtonHandler
		ccView.deleteCaseMenuItem.setOnAction(event->{
			CaseView deleteCases = new DeleteCaseView("Delete Case"); // set window and name

			// get all the inputs to populate window
			deleteCases.titleTextField.setText(currentCase.getCaseTitle());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			deleteCases.caseDatePicker.setValue(LocalDate.parse(currentCase.getCaseDate(), formatter));
			deleteCases.caseTypeTextField.setText(currentCase.getCaseType());
			deleteCases.caseNumberTextField.setText(currentCase.getCaseNumber());
			deleteCases.categoryTextField.setText(currentCase.getCaseCategory());
			deleteCases.caseLinkTextField.setText(currentCase.getCaseLink());
			deleteCases.caseNotesTextArea.setText(currentCase.getCaseNotes());

			deleteCases.buildView().show(); // now build and show the window

			deleteCases.updateButton.setOnAction(event2->{ // updateButtonEventHandler
				int currentCaseIndex = ccModel.caseList.indexOf(currentCase); // get the curentCase index location
				ccModel.caseList.remove(currentCaseIndex); // remove the case
				ccView.caseTableView.setItems(ccModel.caseList); // show the updated tableView
				ccView.caseTableView.getSelectionModel().select(currentCaseIndex-1); // programatically select to update view
				ccView.messageLabel.setText(String.valueOf(ccView.caseTableView.getItems().size()) + " cases."); // update messageLabel
			});

			deleteCases.closeButton.setOnAction(event2->{ // closeButtonEventHandler
				deleteCases.stage.close();
			});

			deleteCases.clearButton.setOnAction(event2->{ // clearButtonEventHandler
				// clear everything
				deleteCases.titleTextField.clear();
				deleteCases.caseDatePicker.setValue(LocalDate.now());
				deleteCases.caseTypeTextField.clear();
				deleteCases.caseNumberTextField.clear();
				deleteCases.categoryTextField.clear();
				deleteCases.caseLinkTextField.clear();
				deleteCases.caseNotesTextArea.clear();
			});

		});


		// caseCountChartMenuItem
		ccView.caseCountChartMenuItem.setOnAction(event->{
			ccView.showChartView(ccModel.yearMap);
		});

		// saveFileMenyUtem
		ccView.saveFileMenuItem.setOnAction(event->{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save file");
			fileChooser.setInitialDirectory(new File(DEFAULT_PATH));
			File file = fileChooser.showSaveDialog(stage);

			if(ccModel.writeCases(file.getAbsolutePath())) {
				ccView.messageLabel.setText(file.getName() + " saved");
			}
		});

	}
}

