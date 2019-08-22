package AcademicOffice;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;


public class ExercisePopupController extends PopupController implements Initializable {
	@FXML
	private TextField nameTextField, weightTextField;
	@FXML
	private ComboBox< String > sentComboBox;
	@FXML
	private Slider gradeSlider;
	@FXML
	private Label invalidField;
	@FXML
	private DatePicker dueDatePicker;

	@Override
	public void initialize(URL location , ResourceBundle resources) {
		if( ! location.toString( ).contains( "delete" ) ) {
			sentComboBox.getItems( ).clear( );
			sentComboBox.getItems( ).add( Exercise.SENT );
			sentComboBox.getItems( ).add( Exercise.UNSENT );
			editWindow = location.toString( ).contains( "edit" );
		} else {
			deleteWindow = true;
		}
	}


	@FXML
	public FieldsObject getFieldsObject() {
		FieldsObject fieldsObject = new FieldsObject( );

		if( ! deleteWindow ) {
			fieldsObject.setExerciseName( getExerciseName( ) );
			fieldsObject.setExerciseWeight( getExerciseWeight( ) );
			fieldsObject.setExerciseDue( getExerciseDate( ) );
			fieldsObject.setExerciseSent( getExerciseIsSent( ) );
			fieldsObject.setExerciseGrade( getExerciseGrade( ) );

			if( ValidInputTester.validStringArrayInput( fieldsObject.toArray( ) ) )
				close( );
			else
				setInvalidFieldToast( );
		} else {
			if( ValidInputTester.validStringInput( getExerciseName( ) ) ) {
				fieldsObject.setExerciseName( getExerciseName( ) );
				close( );
			}
		}
		return fieldsObject;
	}

	private String getExerciseGrade() {
		return String.valueOf( getGrade( ) );
	}

	private String getExerciseName() {
		return nameTextField.getText( );
	}

	private String getExerciseWeight() {
		return weightTextField.getText( );
	}

	private String getExerciseDate() {
		return DateFormatHandler.convertToLocalDateFormat( dueDatePicker.getValue( ).toString( ) );
	}

	private String getExerciseIsSent() {
		return sentComboBox.getSelectionModel( ).getSelectedItem( );
	}

	public Integer getGrade() {
		return gradeSlider != null ? ( int ) gradeSlider.getValue( ) : null;
	}

	private void setInvalidFieldToast() {
		invalidField.setText( "Invalid input" );
	}

}
