package AcademicOffice;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ExamPopupController extends PopupController implements Initializable {
	@FXML
	private TextField attemptTextField;
	@FXML
	private DatePicker examDatePicker;
	@FXML
	private Slider gradeSlider;
	@FXML
	private ComboBox< String > passedComboBox;

	@Override
	public void initialize(URL location , ResourceBundle resourceBundle) {
		if( ! location.toString( ).contains( "delete" ) ) {
			passedComboBox.getItems( ).clear( );
			passedComboBox.getItems( ).add( "passed" );
			passedComboBox.getItems( ).add( "did'nt pass" );
			passedComboBox.getItems( ).add( "upcoming" );
			editWindow = location.toString( ).contains( "edit" );
		} else {
			deleteWindow = true;
		}
	}

	@FXML
	public FieldsObject getFieldsObject() {
		FieldsObject fieldsObject = new FieldsObject( );

		if( ! deleteWindow ) {
			fieldsObject.setExamAttempt( getExamAttempt( ) );
			fieldsObject.setExamDate( getExamDate( ) );
			fieldsObject.setExamGrade( getExamGrade( ) );
			fieldsObject.setExamPassed( getExamIsPass( ) );

			if( ValidInputTester.validStringArrayInput( fieldsObject.toArray( ) ) )
				close( );
		} else {

			if( ValidInputTester.validStringInput( getExamAttempt( ) ) ) {
				fieldsObject.setExamAttempt( getExamAttempt( ) );
				close( );
			}
		}
		return fieldsObject;
	}


	private String getExamAttempt() {
		return attemptTextField.getText( );
	}

	private String getExamDate() {
		String date = ( examDatePicker.getValue( ).toString( ) );
		return DateFormatHandler.convertToLocalDateFormat( date );
	}

	private String getExamGrade() {
		return String.valueOf( gradeSlider.getValue( ) );
	}

	private String getExamIsPass() {
		return passedComboBox.getSelectionModel( ).getSelectedItem( );
	}

}
