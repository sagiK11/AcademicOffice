package AcademicOffice;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class CoursePopupController extends PopupController implements Initializable {

	@FXML
	private TextField nameTextField, idTextField, creditsTextField, courseIdTextField, semesterTextField;
	@FXML
	private ComboBox< String > statusComboBox;
	@FXML
	private Label nameInvalidInput, idInvalidInput, creditsInvalidInput;
	@FXML
	private Label statusInvalidInput, invalidCourseIdLabel;
	@FXML
	private Slider gradeSlider;
	private Integer id;

	@Override
	public void initialize(URL location , ResourceBundle resources) {

		if( ! location.toString( ).contains( "delete" ) ) {
			statusComboBox.getItems( ).clear( );
			statusComboBox.getItems( ).add( Course.COMPLETED );
			statusComboBox.getItems( ).add( Course.IN_STUDY );
			statusComboBox.getItems( ).add( Course.REGISTERED );
			statusComboBox.getItems( ).add( Course.FAILED );

		}

	}

	@FXML
	FieldsObject getFieldsObject() {
		FieldsObject fieldsObject = new FieldsObject( );
		fieldsObject.setCourseName( getCourseName( ) );
		fieldsObject.setCourseCredits( getCourseCredits( ) );
		fieldsObject.setCourseId( getCourseId( ) );
		fieldsObject.setCourseStatus( getCourseStatus( ) );
		fieldsObject.setCourseGrade( getCourseGrade( ) );
		fieldsObject.setCourseSemester( getCourseSemester( ) );
		if( validInput( fieldsObject ) ) {
			close( );
		}
		return fieldsObject;
	}

	private String getCourseGrade() {
		return String.valueOf( getGrade( ) );
	}

	private String getCourseCredits() {
		return creditsTextField.getText( );
	}

	private String getCourseName() {
		return nameTextField.getText( );
	}

	private String getCourseId() {
		return idTextField.getText( );
	}

	private String getCourseSemester() {
		return semesterTextField.getText( );
	}

	private String getCourseStatus() {
		return statusComboBox.getSelectionModel( ).getSelectedItem( );
	}

	private boolean validInput(FieldsObject fieldsObject) {
		clearAllErrors( );
		if( courseNameError( fieldsObject.getCourseName( ) ) )
			toastError( nameInvalidInput );
		else if( courseIdError( fieldsObject.getCourseId( ) ) )
			toastError( idInvalidInput );
		else if( courseCreditsError( fieldsObject.getCourseCredits( ) ) )
			toastError( creditsInvalidInput );
		else if( ! courseStatusError( fieldsObject.getCourseStatus( ) ) )
			toastError( statusInvalidInput );
		else
			return true;
		return false;

	}


	private boolean courseNameError(String courseName) {
		return ValidInputTester.courseNameError( courseName );
	}

	private boolean courseIdError(String courseId) {
		return ValidInputTester.numberFormatError( courseId );
	}

	private boolean courseCreditsError(String courseCredits) {
		return ValidInputTester.numberFormatError( courseCredits );
	}

	private boolean courseStatusError(String courseStatus) {
		return ValidInputTester.validStringInput( courseStatus );
	}

	private void clearAllErrors() {
		creditsInvalidInput.setText( "" );
		idInvalidInput.setText( "" );
		nameInvalidInput.setText( "" );
		statusInvalidInput.setText( "" );
	}

	private void toastError(Label label) {
		final String INVALID_INPUT = "Invalid Input";
		label.setText( INVALID_INPUT );
	}

	public void deleteCourse() {
		try {
			id = Integer.parseInt( courseIdTextField.getText( ) );
		} catch ( NumberFormatException e ) {
			invalidCourseIdLabel.setText( "invalid id" );
			return;
		}

		close( );
	}

	Integer getIdToDelete() {
		return id;
	}

	public int getGrade() {
		return ( int ) gradeSlider.getValue( );
	}

}
