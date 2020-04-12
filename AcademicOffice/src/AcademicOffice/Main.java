package academicoffice;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
	static Student student;
	static DataBaseHandler dataBaseHandler;

	public static void main(String[] args) {
		launch( args );
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		establishConnectionToDataBase( );
		//establishStudentLoginDetails( );
		quickLoginForDebugging( );
		buildUserInterface( primaryStage );
	}

	private void quickLoginForDebugging() {
		try {
			student = dataBaseHandler.getStudent( "Sagi" , "1" , "p" );
		} catch ( UnknownStudentException e ) {
			e.printStackTrace( );
		}
	}

	private void establishStudentLoginDetails() throws IOException {
		Stage logInStage = new Stage( );
		String name = "resources/studentLogin_pop.fxml";

		logInStage.setTitle( "Log In" );
		logInStage.getIcons( ).add( new Image( Main.class.getResourceAsStream( "icon.png" ) ) );
		logInStage.setResizable( false );

		FXMLLoader loader = new FXMLLoader( getClass( ).getResource( name ) );
		Parent root = loader.load( );

		LoginController loginController = loader.getController( );
		loginController.setStage( logInStage );

		logInStage.setScene( new Scene( root ) );
		logInStage.initModality( Modality.WINDOW_MODAL );
		logInStage.setOnCloseRequest( we -> System.exit( 1 ) );
		logInStage.showAndWait( );
		student = loginController.getStudent( );
	}

	private void establishConnectionToDataBase() {
		dataBaseHandler = new DataBaseHandler( );
		dataBaseHandler.connect( );
	}

	private void buildUserInterface(Stage primaryStage) throws IOException {
		final int STAGE_WIDTH = 1000, STAGE_HEIGHT = 640;
		String name = "resources/root_scene.fxml";

		primaryStage.setTitle( "Academic Office" );
		primaryStage.getIcons( ).add( new Image( Main.class.getResourceAsStream( "icon.png" ) ) );
		primaryStage.setResizable( false );

		FXMLLoader loader = new FXMLLoader( getClass( ).getResource( name ) );
		Parent root = loader.load( );

		Scene rootScene = new Scene( root , STAGE_WIDTH , STAGE_HEIGHT );

		primaryStage.setScene( rootScene );
		primaryStage.show( );
	}

	@Override
	public void stop() {
		System.exit( 0 );
	}
}
