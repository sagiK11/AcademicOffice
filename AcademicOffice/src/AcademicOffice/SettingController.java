package AcademicOffice;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable, StudentUpdater {

	@FXML
	private TextField studentNameTextField, studentMajorTextField;
	@FXML
	private TextField oldPassTextField, oldPassConfirmTextField;
	@FXML
	private TextField newPassTextField, newPassConfirmTextField;
	@FXML
	private Label userPassFeedBackLabel, userInfoFeedBackLabel;
	private Student student;

	@Override
	public void initialize(URL location , ResourceBundle resources) {
		student = Main.student;
	}

	public void updateStudent() {
		studentNameTextField.setText( student.getName( ) );
		studentMajorTextField.setText( student.getMajor( ) );
	}

	@FXML
	private void updateStudentInfo() {
		String newName = studentNameTextField.getText( );
		String newMajor = studentMajorTextField.getText( );
		student.setName( newName );
		student.setMajor( newMajor );
		sendFeedBackToUser( userInfoFeedBackLabel );
		Main.dataBaseHandler.updateStudentInfo( newName , newMajor );
	}

	@FXML
	private void updateStudentPassword() {
		student.setName( studentNameTextField.getText( ) );
		student.setMajor( studentMajorTextField.getText( ) );

		if( OldPasswordConfirmationOK( ) ) {
			if( bothNewPasswordMatch( ) ) {
				updateNewPassword( );
				sendFeedBackToUser( userPassFeedBackLabel );
			} else {
				notifyUserForMissMatchNewPasswords( );
			}
		} else {
			notifyUserForMissMatchOldPasswords( );
		}
	}


	private boolean bothNewPasswordMatch() {
		String newPass = newPassTextField.getText( );
		String newPassConfirmation = newPassConfirmTextField.getText( );
		return newPass.equals( newPassConfirmation );
	}

	private boolean OldPasswordConfirmationOK() {
		String studentCurrentPass = student.getPassword( );
		String oldPass = oldPassTextField.getText( );
		String oldPassConfirmation = oldPassConfirmTextField.getText( );
		return studentCurrentPass.equals( oldPass ) && studentCurrentPass.equals( oldPassConfirmation );
	}

	private void updateNewPassword() {
		String newPassword = newPassTextField.getText( );
		Main.dataBaseHandler.updateStudentPassword( newPassword );
	}


	private void sendFeedBackToUser(Label label) {
		label.setText( "Changes Saved!" );
	}

	private void notifyUserForMissMatchNewPasswords() {
		userPassFeedBackLabel.setText( "unmatched new passwords" );
	}

	private void notifyUserForMissMatchOldPasswords() {
		userPassFeedBackLabel.setText( "unmatched old passwords" );
	}

}
