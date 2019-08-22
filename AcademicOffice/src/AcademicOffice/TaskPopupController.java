package AcademicOffice;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class TaskPopupController extends PopupController implements Initializable {

	@FXML
	private TextArea taskContentTextArea;


	@FXML
	FieldsObject getFieldsObject() {
		FieldsObject fieldsObject = new FieldsObject( );
		String taskContent = taskContentTextArea.getText( );
		fieldsObject.setTaskContent( taskContent );
		close( );
		return fieldsObject;
	}

	@Override
	public void initialize(URL url , ResourceBundle resourceBundle) {
		taskContentTextArea.requestFocus( );
	}
}
