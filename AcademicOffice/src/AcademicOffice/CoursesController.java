package AcademicOffice;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class CoursesController extends PopupController implements Initializable, StudentUpdater {
	@FXML
	private TableView< Course > coursesTable;
	@FXML
	private TableColumn< Course, String > coursesColumn, idColumn;
	@FXML
	private TableColumn< Course, String > creditsColumn, statusColumn;
	@FXML
	private TableColumn< Course, String > finalGradeColumn, semesterColumn;
	@FXML
	private TextField averageTextField, creditsTextField;
	private Student student;
	private boolean cancelAction;

	@FXML
	public void popAddNewCourseWindow() {
		resetCancelAddition( );

		String name = "resources/addCourse_pop.fxml";
		FXMLLoader loader = new FXMLLoader( getClass( ).getResource( name ) );
		Stage popupStage = new Stage( );
		Parent layout = getLayout( loader );

		CoursePopupController coursePopupController = loader.getController( );
		coursePopupController.setStage( popupStage );

		setStageSetting( popupStage , layout , "Add Course" );

		if( userDidNotCancel( ) ) {
			FieldsObject fieldsObject = coursePopupController.getFieldsObject( );
			setCourseFields( fieldsObject );
		}
	}


	public void setCourseFields(FieldsObject fieldsObject) {
		boolean courseCompleted = fieldsObject.getCourseStatus( ).equals( Course.COMPLETED );
		int credInt, gradeInt;

		credInt = Integer.parseInt( fieldsObject.getCourseCredits( ) );
		gradeInt = Integer.parseInt( fieldsObject.getCourseGrade( ) );

		Course newCourse = new Course( );
		newCourse.setName( fieldsObject.getCourseName( ) );
		newCourse.setId( fieldsObject.getCourseId( ) );
		newCourse.setCredits( credInt );
		newCourse.setGrade( courseCompleted ? gradeInt : - 1 );
		newCourse.setStatus( fieldsObject.getCourseStatus( ) );
		newCourse.setStudentId( student.getId( ) );
		newCourse.setSemester( fieldsObject.getCourseSemester( ) );
		newCourse.initialize( );

		Main.dataBaseHandler.addCourseToDateBase( newCourse , student );
		updateTable( );
	}

	private void setTotalCreditsInScene() {
		creditsTextField.setText( String.valueOf( student.getTotalCredits( ) ) );
	}

	private void setAverageInScene() {
		averageTextField.setText( String.valueOf( student.getAverage( ) ) );
	}

	@FXML
	private void popDeleteCourseWindow() {
		resetCancelAddition( );

		String name = "resources/deleteCourse_pop.fxml";
		FXMLLoader loader = new FXMLLoader( getClass( ).getResource( name ) );
		Stage popupStage = new Stage( );
		Parent layout = getLayout( loader );

		CoursePopupController coursePopupController = loader.getController( );
		coursePopupController.setStage( popupStage );

		setStageSetting( popupStage , layout , "Delete Course" );

		if( userDidNotCancel( ) ) {
			Integer idToDelete = coursePopupController.getIdToDelete( );
			Main.dataBaseHandler.deleteCourse( idToDelete );
			updateTable( );
		}
	}


	@Override
	public void initialize(URL location , ResourceBundle resources) {
		student = Main.student;
		coursesColumn.setCellValueFactory( new PropertyValueFactory<>( "CourseName" ) );
		idColumn.setCellValueFactory( new PropertyValueFactory<>( "CourseId" ) );
		creditsColumn.setCellValueFactory( new PropertyValueFactory<>( "CourseCredits" ) );
		finalGradeColumn.setCellValueFactory( new PropertyValueFactory<>( "CourseGrade" ) );
		statusColumn.setCellValueFactory( new PropertyValueFactory<>( "CourseStatus" ) );
		semesterColumn.setCellValueFactory( new PropertyValueFactory<>( "CourseSemester" ) );
	}

	public void updateStudent() {
		updateTable( );
	}

	private void resetCancelAddition() {
		cancelAction = false;
	}

	private boolean userDidNotCancel() {
		return ! cancelAction;
	}

	private void updateTable() {
		ArrayList< Course > courseArrayList = student.getCourseArrayList( );
		coursesTable.setItems( FXCollections.observableList( courseArrayList ) );
		setAverageInScene( );
		setTotalCreditsInScene( );
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
		} catch ( IOException e ) {
			e.printStackTrace( );
			System.exit( 1 );
		}
		return layout;
	}

	@Override
	FieldsObject getFieldsObject() {
		return null;
	}

}
