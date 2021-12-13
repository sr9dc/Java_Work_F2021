// Sai Rajuladevi, srajulad
package hw3;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class DeleteCaseView extends CaseView{
	// constructor
	DeleteCaseView(String header) {
		super("Delete Case"); // updates the window name
	}

	// overriden build view method
	@Override
	Stage buildView() {
		updateButton.setText("Delete Case"); // change updateButton text
		Scene scene = new Scene(updateCaseGridPane, this.CASE_WIDTH, this.CASE_HEIGHT); // Scene setup
		this.stage.setScene(scene); // set the scene
		return this.stage;
	}

}
