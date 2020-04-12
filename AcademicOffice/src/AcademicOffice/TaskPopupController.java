package academicoffice;

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
		String taskContent = taskContentTextArea.getText( );
		FieldsObject fieldsObject = new FieldsObject.Builder( )
			.taskContent( taskContent )
			.build( );
		close( );
		return fieldsObject;
	}

	@Override
	public void initialize(URL url , ResourceBundle resourceBundle) {
		taskContentTextArea.requestFocus( );
	}
}
