package AcademicOffice;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class SignUpController {
	private Stage stage;
	private Student student;
	@FXML
	private TextField userNameTextField, idTextField, majorTextField;
	@FXML
	private PasswordField passTextField, confirmPassTextField;
	@FXML
	private Label invalidPasswordLabel;

	@FXML
	private void signUp() {
		final int MIN_LEN = 5, MAX_LEN = 12;
		String userName = userNameTextField.getText( );
		String id = idTextField.getText( );
		String major = majorTextField.getText( );
		String pass = passTextField.getText( );
		String confirmPass = confirmPassTextField.getText( );

		if( pass.equals( confirmPass ) && pass.length( ) > MIN_LEN && pass.length( ) < MAX_LEN ) {
			Main.dataBaseHandler.addStudentToDataBase( userName , id , major , pass );
			closeStage( );
		} else
			invalidPasswordLabel.setText( "passwords does not match" );

	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Student getStudent() {
		return student;
	}

	private void closeStage() {
		if( stage != null )
			stage.close( );
	}
}
