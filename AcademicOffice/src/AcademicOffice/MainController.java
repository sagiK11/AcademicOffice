package academicoffice;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {
	private final int SCENE_NUMBER = 6;
	@FXML
	private BorderPane borderPane;
	@FXML
	private Button checkListButton, homeButton, currentCoursesButton, chatButton;
	@FXML
	private Button signOutButton, coursesButton, settingButton;
	private StudentUpdater[] studentUpdaterArray;
	private FXMLLoader[] fxmlLoadersArray;
	private Button[] buttonsArray;
	private AnchorPane[] currentSceneArray;

	@FXML
	private void switchScene(ActionEvent event) {
		Object source = event.getSource( );
		int i = 0;

		for ( ; i < SCENE_NUMBER ; ++ i )
			if( source == buttonsArray[ i ] ) {
				studentUpdaterArray[ i ].updateStudent( );
				borderPane.setCenter( currentSceneArray[ i ] );
				return;
			}
		signOutAndExit( );
	}

	private void loadScenes() {
		int i = 0;
		FXMLLoader loader;

		for ( ; i < SCENE_NUMBER ; ++ i ) {
			loader = fxmlLoadersArray[ i ];
			try {
				currentSceneArray[ i ] = loader.load( );
				studentUpdaterArray[ i ] = loader.getController( );
				studentUpdaterArray[ i ].updateStudent( );
			} catch ( IOException e ) {
				e.printStackTrace( );
			}
		}
		setOpeningSceneToHomeScene( );
	}

	private void setOpeningSceneToHomeScene() {
		int homeSceneIndex = 0;
		borderPane.setCenter( currentSceneArray[ homeSceneIndex ] );
	}

	@Override
	public void initialize(URL location , ResourceBundle resources) {
		fxmlLoadersArray = new FXMLLoader[ SCENE_NUMBER ];
		fxmlLoadersArray[ 0 ] = new FXMLLoader( getClass( ).getResource( "resources/home_scene.fxml" ) );
		fxmlLoadersArray[ 1 ] = new FXMLLoader( getClass( ).getResource( "resources/checkList_scene.fxml" ) );
		fxmlLoadersArray[ 2 ] = new FXMLLoader( getClass( ).getResource( "resources/currentCourses_scene.fxml" ) );
		fxmlLoadersArray[ 3 ] = new FXMLLoader( getClass( ).getResource( "resources/chat_scene.fxml" ) );
		fxmlLoadersArray[ 4 ] = new FXMLLoader( getClass( ).getResource( "resources/courses_scene.fxml" ) );
		fxmlLoadersArray[ 5 ] = new FXMLLoader( getClass( ).getResource( "resources/setting_scene.fxml" ) );

		buttonsArray = new Button[ SCENE_NUMBER + 1 ];
		buttonsArray[ 0 ] = homeButton;
		buttonsArray[ 1 ] = checkListButton;
		buttonsArray[ 2 ] = currentCoursesButton;
		buttonsArray[ 3 ] = chatButton;
		buttonsArray[ 4 ] = coursesButton;
		buttonsArray[ 5 ] = settingButton;
		buttonsArray[ 6 ] = signOutButton;

		studentUpdaterArray = new StudentUpdater[ SCENE_NUMBER ];
		currentSceneArray = new AnchorPane[ SCENE_NUMBER ];
		loadScenes( );
	}


	private void signOutAndExit() {
		Main.dataBaseHandler.closeConnection( );
		Platform.exit( );
	}

}