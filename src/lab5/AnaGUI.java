// Sai Rajuladevi, srajulad
package lab5;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**AnaGUI starts the GUI application. It sets up the GUI components and 
 * attaches the handlers for three buttons - Find, Clear, and Exit
 */

public class AnaGUI extends Application{

	BorderPane root = new BorderPane();
	Button findButton, clearButton, exitButton; //these three buttons need to have handlers attached to them
	Label messageLabel;
	TextField userWord;
	TextArea anagramsTextArea;

	Anagrammar ag = new Anagrammar();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ag.loadWords("words.txt");
		primaryStage.setTitle("Anagrammar");
		setupScreen();
		Scene scene = new Scene(root, 250, 250);
		primaryStage.setScene(scene);
		primaryStage.show();

		// add exit button handler
		exitButton.setOnAction(new EventHandler<ActionEvent> (){
			@Override
			public void handle(ActionEvent event) {
				primaryStage.close();
			}
		}) ;
	}


	//setup all GUI components
	private void setupScreen() {

		GridPane textGrid = new GridPane(); 
		GridPane buttonGrid = new GridPane();
		Label textEnter = new Label("Enter word");
		Label textAnagram = new Label("Anagrams");
		Label welcomeMessage = new Label("Find Anagrams");
		messageLabel = new Label("");
		findButton = new Button("Find");
		clearButton = new Button("Clear");
		exitButton = new Button("Exit");
		anagramsTextArea = new TextArea();
		userWord = new TextField();

		textGrid.setVgap(10);
		textGrid.setHgap(10);
		buttonGrid.setHgap(10);

		textGrid.add(welcomeMessage, 0, 0, 2, 1);
		GridPane.setHalignment(welcomeMessage, HPos.CENTER);
		textGrid.add(textEnter, 0, 1);
		textGrid.add(textAnagram, 0, 2);
		textGrid.add(messageLabel, 0, 3, 2, 1);
		textGrid.add(userWord, 1, 1);
		textGrid.add(anagramsTextArea, 1, 2);

		buttonGrid.add(findButton, 0, 0);
		buttonGrid.add(clearButton, 1, 0);
		buttonGrid.add(exitButton, 2, 0);

		// add handlers
		findButton.setOnAction(new FindButtonHandler()); 
		clearButton.setOnAction(new ClearButtonHandler());


		userWord.setPrefSize(150, 10);
		anagramsTextArea.setPrefSize(150, 100);
		findButton.setPrefSize(70, 20);
		clearButton.setPrefSize(70, 20);
		exitButton.setPrefSize(70, 20);

		root.setTop(textGrid);
		root.setCenter(buttonGrid);

		textGrid.setAlignment(Pos.CENTER);
		buttonGrid.setAlignment(Pos.CENTER);

	}

	public class FindButtonHandler implements EventHandler <ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			anagramsTextArea.clear();

			ag.loadWords("words.txt");
			ag.findAnagrams(userWord.getText());

			if(userWord.getText().equals("")) {
				messageLabel.setText("Please enter some input");
			}

			else {
				for(String anagram : ag.anagramArray) {
					anagramsTextArea.appendText(anagram);
					anagramsTextArea.appendText("\n");
				}
				if(ag.isInDictionary) {
					if(ag.hasAnagrams) {
						messageLabel.setText(userWord.getText() + " found in words list\n" 
								+ String.valueOf(ag.anagramArray.length) + " anagram(s) found for " + userWord.getText());
					}
					else {
						messageLabel.setText(userWord.getText() + " found in words list\n"
								+ "No anagrams found for " + userWord.getText());

					}
				}
				else {
					if(ag.hasAnagrams) {
						messageLabel.setText(userWord.getText() + " not found in words list\n" 
								+ String.valueOf(ag.anagramArray.length) + " anagram(s) found for " + userWord.getText());
					}
					else {
						messageLabel.setText(userWord.getText() + " not found in words list\n"
								+ "No anagrams found for " + userWord.getText());
					}
				}
			}
		}
	}

	public class ClearButtonHandler implements EventHandler <ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			messageLabel.setText("");
			userWord.clear();
			anagramsTextArea.clear();
		}
	}

}
