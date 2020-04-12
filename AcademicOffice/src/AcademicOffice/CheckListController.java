package academicoffice;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CheckListController implements Initializable, StudentUpdater {
	final ObservableList< Task > todayTasks = FXCollections.observableArrayList( ),
		previousTasks = FXCollections.observableArrayList( );
	private Student student;
	@FXML
	private CheckListView< Task > todayList;
	@FXML
	private CheckListView< Task > previousList;
	private ArrayList< Task > todayTasksArrayList;
	private ArrayList< Task > previousTasksArrayList;
	private boolean cancelAction;

	@Override
	public void initialize(URL location , ResourceBundle resources) {
		student = Main.student;
		refreshTasks( );
	}

	private void refreshTasks() {
		updateStudent( );
		todayList.getItems( ).clear( );
		previousList.getItems( ).clear( );

		todayTasks.addAll( todayTasksArrayList );
		previousTasks.addAll( previousTasksArrayList );

		todayList.setItems( todayTasks );
		previousList.setItems( previousTasks );

		markFinishedTasks( todayList , todayTasksArrayList );
		markFinishedTasks( previousList , previousTasksArrayList );

		addListenersToList( todayList );
		addListenersToList( previousList );
	}

	public void updateStudent() {
		todayTasksArrayList = student.getTodayTasksArrayList( );
		previousTasksArrayList = student.getPreviousTasksArrayList( );
	}


	private void addListenersToList(CheckListView< Task > currentList) {
		final boolean UPDATE_TO_CHECK = true, UPDATE_TO_UNCHECK = false;
		currentList.getCheckModel( ).getCheckedItems( ).addListener( ( ListChangeListener< Task > ) c -> {
			c.next( );
			if( c.wasAdded( ) ) {
				Task task = c.getAddedSubList( ).get( 0 );
				Main.dataBaseHandler.updateTask( task , UPDATE_TO_CHECK );
			} else if( c.wasRemoved( ) ) {
				Task task = c.getRemoved( ).get( 0 );
				Main.dataBaseHandler.updateTask( task , UPDATE_TO_UNCHECK );
			}
		} );
	}

	private void markFinishedTasks(CheckListView< Task > currentList , ArrayList< Task > currentTasksArrayList) {
		for ( int i = 0 ; i < currentTasksArrayList.size( ) ; i++ ) {
			if( currentTasksArrayList.get( i ).isChecked( ) ) {
				currentList.getCheckModel( ).check( i );
			}
		}
	}

	@FXML
	void addNewTask() {
		resetCancelAddition( );

		String name = "resources/addTask_pop.fxml";
		FXMLLoader loader = new FXMLLoader( getClass( ).getResource( name ) );
		Stage popupStage = new Stage( );
		Parent layout = getLayout( loader );

		TaskPopupController taskPopupController = loader.getController( );
		taskPopupController.setStage( popupStage );

		setStageSetting( popupStage , layout , "Add Task" );

		if( userDidNotCancel( ) ) {
			FieldsObject fieldsObject = taskPopupController.getFieldsObject( );
			createNewTask( fieldsObject );
			refreshTasks( );
		}
	}

	private void createNewTask(FieldsObject fieldsObject) {
		String taskContent = fieldsObject.getTaskContent( );
		Task newTask = new Task.Builder( taskContent , student.getId( ) )
			.isTodayTask( true )
			.date( DateFormatHandler.getCurrentDate( ) )
			.isChecked( false )
			.build( );
		Main.dataBaseHandler.addTaskToDateBase( newTask , student );
	}

	private void resetCancelAddition() {
		cancelAction = false;
	}

	private boolean userDidNotCancel() {
		return ! cancelAction;
	}

	private void setStageSetting(Stage stage , Parent layout , String title) {
		stage.setTitle( title );
		stage.setResizable( false );
		stage.initModality( Modality.WINDOW_MODAL );
		stage.setScene( new Scene( layout ) );
		stage.setOnCloseRequest( we -> cancelAction = true );
		stage.showAndWait( );
	}

	private Parent getLayout(FXMLLoader loader) {
		Parent layout = null;
		try {
			layout = loader.load( );
			layout.requestFocus( );
		} catch ( IOException e ) {
			e.printStackTrace( );
			System.exit( 1 );
		}
		return layout;
	}
}
