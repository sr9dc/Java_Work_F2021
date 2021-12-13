// Sai Rajuladevi, srajulad
package hw2;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ModifyCaseView extends CaseView{

	ModifyCaseView(String header) {
		super("Modify Case"); // updates the window name
	}

	@Override
	Stage buildView() {
		// TODO Auto-generated method stub
		updateButton.setText("Modify Case"); // change the updateButton text
		Scene scene = new Scene(updateCaseGridPane, this.CASE_WIDTH, this.CASE_HEIGHT); // Scene setup
		this.stage.setScene(scene); // set the scene
		return this.stage;
	}

}
