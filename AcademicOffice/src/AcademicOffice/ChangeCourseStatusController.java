package AcademicOffice;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangeCourseStatusController extends PopupController implements Initializable {

	private boolean change;
	@FXML
	private Button yesButton, noButton;

	@Override
	FieldsObject getFieldsObject() {
		FieldsObject fieldsObject = new FieldsObject( );
		fieldsObject.setChangeCourseStatus( change );
		close( );
		return fieldsObject;
	}

	@Override
	public void initialize(URL url , ResourceBundle resourceBundle) {
		yesButton.setOnAction( e -> {
			change = true;
			getFieldsObject( );
		} );
		noButton.setOnAction( e -> {
			change = false;
			getFieldsObject( );
		} );
	}
}
