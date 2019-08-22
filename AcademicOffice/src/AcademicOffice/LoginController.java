package AcademicOffice;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class LoginController {
	@FXML
	private TextField userNameTextField, idTextField, passTextField;
	@FXML
	private Label invalidStudent;
	private Student student;
	private Stage stage;

	@FXML
	private void logIn() {
		String userName = userNameTextField.getText( );
		String id = idTextField.getText( );
		String pass = passTextField.getText( );
		try {
			student = Main.dataBaseHandler.getStudent( userName , id , pass );
			closeStage( );
		} catch ( UnknownStudentException e ) {
			invalidStudent.setText( "Invalid data" );
			System.out.println( "unknown student at login" );
		}
	}

	@FXML
	public void signUpWindowPopup() throws Exception {
		Stage signUpStage = new Stage( );
		String name = "resources/studentSignUp_pop.fxml";

		setStageProperties( signUpStage );

		FXMLLoader loader = new FXMLLoader( getClass( ).getResource( name ) );
		Parent root = loader.load( );

		SignUpController signUpController = loader.getController( );
		signUpController.setStage( signUpStage );

		signUpStage.setScene( new Scene( root ) );
		signUpStage.initModality( Modality.WINDOW_MODAL );
		signUpStage.setOnCloseRequest( we -> System.exit( 1 ) );
		signUpStage.showAndWait( );
	}

	private void setStageProperties(Stage signUpStage) {
		signUpStage.setTitle( "Sign up" );
		signUpStage.getIcons( ).add( new Image( Main.class.getResourceAsStream( "icon.png" ) ) );
		signUpStage.setResizable( false );
	}

	public Student getStudent() {
		return student;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	private void closeStage() {
		if( stage != null )
			stage.close( );
	}
}
