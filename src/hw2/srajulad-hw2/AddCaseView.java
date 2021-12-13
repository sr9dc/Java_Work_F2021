// Sai Rajuladevi, srajulad
package hw2;

import java.time.LocalDate;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class AddCaseView extends CaseView {

	AddCaseView(String header) {
		super("Add Case"); // updates the window name
	}

	@Override
	Stage buildView() {
		// TODO Auto-generated method stub
		caseDatePicker.setValue(LocalDate.now()); // default date value as specified in doc
		updateButton.setText("Add Case"); // change the updateButton text
		Scene scene = new Scene(updateCaseGridPane, this.CASE_WIDTH, this.CASE_HEIGHT); // Scene setup
		this.stage.setScene(scene); // set the scene
		return this.stage;
	}

}
