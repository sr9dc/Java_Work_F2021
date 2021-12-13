package hw3;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DataException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DataException(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Data Error");
		alert.setContentText(message);
		alert.showAndWait();
	}

}
