package AcademicOffice;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CurrentCoursesController implements Initializable, StudentUpdater {
	@FXML
	private TextField courseIdTextField, courseCreditsTextField;
	@FXML
	private TextField courseProgressTextField, nextExerciseDueTextField;
	@FXML
	private ComboBox< String > courseComboBox;
	@FXML
	private TableView< Exercise > exercisesTable;
	@FXML
	private TableColumn< Course, String > nameColumn, weightColumn;
	@FXML
	private TableColumn< Course, String > dueColumn, sentColumn, gradeColumn;
	@FXML
	private TableView< Exam > examsTable;
	@FXML
	private TableColumn< Exam, String > examAttemptColumn, examDateColumn, examGradeColumn;
	@FXML
	private ProgressBar progressBar;
	@FXML
	private Button addExerciseButton, editExerciseButton, deleteExerciseButton;
	@FXML
	private Button addExamButton, editExamButton, deleteExamButton;
	private Student student;
	private Course currentCourse;
	private FieldsObject fieldsObject;
	private boolean cancelAction;

	@Override
	public void initialize(URL location , ResourceBundle resources) {
		student = Main.student;
		nameColumn.setCellValueFactory( new PropertyValueFactory<>( "ExerciseName" ) );
		weightColumn.setCellValueFactory( new PropertyValueFactory<>( "ExerciseWeight" ) );
		dueColumn.setCellValueFactory( new PropertyValueFactory<>( "ExerciseDue" ) );
		sentColumn.setCellValueFactory( new PropertyValueFactory<>( "ExerciseSent" ) );
		gradeColumn.setCellValueFactory( new PropertyValueFactory<>( "ExerciseGrade" ) );
		examAttemptColumn.setCellValueFactory( new PropertyValueFactory<>( "ExamAttempt" ) );
		examDateColumn.setCellValueFactory( new PropertyValueFactory<>( "ExamDate" ) );
		examGradeColumn.setCellValueFactory( new PropertyValueFactory<>( "ExamGrade" ) )
		;
		setCurrentCoursesInComboBox( );
		setButtonsToDisable( );
	}

	@FXML
	private void switchCurrentCourse() {
		String currentCourseName = courseComboBox.getSelectionModel( ).getSelectedItem( );
		setCurrentCourse( currentCourseName );
		updateScene( );
	}

	@FXML
	private void addExercise() {
		String name = "resources/addExercise_pop.fxml";
		String stageTitle = "Add Exercise";
		openPopupWindow( name , stageTitle );
	}

	@FXML
	private void deleteExercise() {
		String name = "resources/deleteExercise_pop.fxml";
		String stageTitle = "Delete Exercise";
		openPopupWindow( name , stageTitle );
	}

	@FXML
	private void editExercise() {
		String name = "resources/editExercise_pop.fxml";
		String stageTitle = "Edit Exercise";
		openPopupWindow( name , stageTitle );
	}

	@FXML
	private void addExam() {
		String name = "resources/addExam_pop.fxml";
		String stageTitle = "Add Exam";
		openPopupWindow( name , stageTitle );
	}

	@FXML
	private void editExam() {
		String name = "resources/editExam_pop.fxml";
		String stageTitle = "Edit Exam";
		openPopupWindow( name , stageTitle );
	}

	@FXML
	private void deleteExam() {
		String name = "resources/deleteExam_pop.fxml";
		String stageTitle = "Delete Exam";
		openPopupWindow( name , stageTitle );
	}

	private void openPopupWindow(String name , String stageTitle) {
		if( currentCourse == null )
			return;
		Platform.runLater( new PopupWindowThread( name , stageTitle ) );
	}

	private void resetCancelAction() {
		cancelAction = false;
	}

	private void setFields(PopupController controller) {
		// options: exam: add/edit/delete or  exercise: add/edit/delete
		if( controller instanceof ExamPopupController && controller.getDeleteWindow( ) ) {
			removeExam( );
		} else if( controller instanceof ExamPopupController && controller.getEditWindow( ) ) {
			updateExamFields( );
		} else if( controller instanceof ExamPopupController ) {
			setExamFields( );
		} else if( controller instanceof ExercisePopupController && controller.getDeleteWindow( ) ) {
			removeExercise( );
		} else if( controller instanceof ExercisePopupController && controller.getEditWindow( ) ) {
			updateExerciseFields( );
		} else if( controller instanceof ExercisePopupController ) {
			setExercisesFields( );
		}
	}

	private void setExercisesFields() {
		Exercise newExercise = new Exercise( );
		int weightInt = Integer.parseInt( fieldsObject.getExerciseWeight( ) );
		int grade = Integer.parseInt( fieldsObject.getExerciseGrade( ) );

		newExercise.setName( fieldsObject.getExerciseName( ) );
		newExercise.setWeight( weightInt );
		newExercise.setGrade( fieldsObject.getExerciseSent( ).equals( Exercise.UNSENT ) ? - 1 : grade );
		newExercise.setSent( fieldsObject.getExerciseSent( ).equals( Exercise.SENT ) );
		newExercise.setDue( fieldsObject.getExerciseDue( ) );
		newExercise.initialize( );

		Main.dataBaseHandler.addExerciseToDataBase( newExercise , currentCourse );
		updateScene( );
	}

	private void setExamFields() {
		final boolean ADD_EXAM_TO_DB = true;
		setExamFieldsInDataBase( ADD_EXAM_TO_DB );
	}

	private void updateExamFields() {
		final boolean ADD_EXAM_TO_DB = false;
		setExamFieldsInDataBase( ADD_EXAM_TO_DB );
	}

	private void setExamFieldsInDataBase(boolean addExam) {
		Exam newExam = new Exam( );
		int gradeInt = ( int ) Double.parseDouble( fieldsObject.getExamGrade( ) );
		boolean passed = fieldsObject.getExamPassed( ).equals( Exam.PASSED );

		newExam.setAttempt( fieldsObject.getExamAttempt( ) );
		newExam.setExamDate( fieldsObject.getExamDate( ) );
		newExam.setGrade( passed ? gradeInt : - 1 );
		newExam.setPassed( passed );
		newExam.setId( currentCourse.getId( ) );
		newExam.initialize( );

		if( addExam )
			Main.dataBaseHandler.addExamToDataBase( newExam , currentCourse );
		else
			Main.dataBaseHandler.updateExam( newExam , currentCourse );

		updateScene( );
		if( passed )
			askForChangingCourseStatus( );
	}

	private void updateExerciseFields() {
		Exercise newExercise = new Exercise( );
		int weightInt = ( int ) Double.parseDouble( fieldsObject.getExerciseWeight( ) );
		int grade = Integer.parseInt( fieldsObject.getExerciseGrade( ) );

		newExercise.setName( fieldsObject.getExerciseName( ) );
		newExercise.setDue( fieldsObject.getExerciseDue( ) );
		newExercise.setWeight( weightInt );
		newExercise.setGrade( fieldsObject.getExerciseSent( ).equals( Exercise.SENT ) ? grade : - 1 );
		newExercise.setSent( fieldsObject.getExerciseSent( ).equals( Exercise.SENT ) );
		newExercise.setId( currentCourse.getId( ) );
		newExercise.initialize( );

		Main.dataBaseHandler.updateExercise( newExercise , currentCourse );
		updateScene( );
	}

	private void removeExercise() {
		String exerciseName = fieldsObject.getExerciseName( );
		Main.dataBaseHandler.deleteExercise( exerciseName , currentCourse );
		currentCourse.deleteExercise( exerciseName );
		updateScene( );
	}

	private void removeExam() {
		String examAttempt = fieldsObject.getExamAttempt( );
		Main.dataBaseHandler.deleteExam( examAttempt , currentCourse );
		updateScene( );
	}

	private void setCurrentCourse(String currentCourseName) {
		ArrayList< Course > currentCoursesArrayList = getCurrentCourses( );

		for ( Course course : currentCoursesArrayList ) {
			if( course.getName( ).equals( currentCourseName ) ) {
				currentCourse = course;
				setButtonsToEnable( );
				return;
			}
		}
	}

	private void askForChangingCourseStatus() {
		String name = "resources/changeCourseStatus_pop.fxml";
		String stageTitle = "Change Course Status";
		resetCancelAction( );
		Stage popupStage = new Stage( );
		FXMLLoader loader = new FXMLLoader( getClass( ).getResource( name ) );
		Parent layout = getLayout( loader );
		ChangeCourseStatusController popupController = loader.getController( );
		popupController.setStage( popupStage );

		setStageSetting( popupStage , layout , stageTitle );
		if( popupController.getFieldsObject( ).getChangeCourseStatus( ) ) {
			currentCourse.setStatus( Course.COMPLETED );
			Main.dataBaseHandler.updateCourse( currentCourse );
			updateTables( );
		}

	}

	private void updateScene() {
		courseIdTextField.setText( currentCourse.getId( ) );
		courseCreditsTextField.setText( String.valueOf( currentCourse.getCredits( ) ) );
		setNextExerciseDueDate( );
		updateProgressBar( );
		updateTables( );
	}

	//-----Scene updates--------//

	private void updateTables() {
		ArrayList< Exercise > exercisesArrayList = currentCourse.getExerciseArrayList( );
		exercisesTable.setItems( FXCollections.observableList( exercisesArrayList ) );

		ArrayList< Exam > examArrayList = currentCourse.getExamsArrayList( );
		examsTable.setItems( FXCollections.observableList( examArrayList ) );
	}

	private void updateProgressBar() {
		float numberOfExercises = getNumberOfExercises( );
		int numberOfSentExercises = getNumberOfExercisesSent( );
		double result;

		if( numberOfExercises == 0 ) {
			updateProgressFields( 0 );
		} else {
			result = ( numberOfSentExercises / numberOfExercises );
			updateProgressFields( result );
		}
	}

	private float getNumberOfExercises() {
		return currentCourse.getExerciseArrayList( ).size( );
	}

	private int getNumberOfExercisesSent() {
		int numberOfSentExercises = 0;
		for ( Exercise exercise : currentCourse.getExerciseArrayList( ) ) {
			numberOfSentExercises += exercise.getSent( ) ? 1 : 0;
		}
		return numberOfSentExercises;
	}

	private void updateProgressFields(double result) {
		final int normalize = 100;
		courseProgressTextField.setText( ( int ) ( result * normalize ) + "%" );
		progressBar.setProgress( result );
	}

	public void updateStudent() {
		setCurrentCoursesInComboBox( );
	}

	private void setNextExerciseDueDate() {
		ArrayList< Exercise > exerciseArrayList = currentCourse.getExerciseArrayList( );
		if( exerciseArrayList.size( ) == 0 )
			return;

		DateFormatHandler currentDate = new DateFormatHandler( DateFormatHandler.getCurrentDate( ) );
		DateFormatHandler closetDate = null;

		//searching a date who's after today date
		for ( Exercise exercise : exerciseArrayList ) {
			DateFormatHandler testDate = new DateFormatHandler( exercise.getDue( ) );
			if( testDate.isAfter( currentDate ) && ! exercise.getSent( ) ) {
				closetDate = testDate;
				break;
			}
		}
		//no date is after today's date
		if( closetDate == null ) {
			setNoValidDueDate( );
			return;
		}
		//searching a date who's the closest to today date
		for ( Exercise exercise : exerciseArrayList ) {
			DateFormatHandler testDate = new DateFormatHandler( exercise.getDue( ) );
			if( testDate.isAfter( currentDate ) && testDate.isBefore( closetDate ) )
				closetDate = testDate;
		}
		setValidDueDate( closetDate.getDateAsString( ) );

	}

	private void setValidDueDate(String date) {
		nextExerciseDueTextField.setText( date );
	}

	private void setNoValidDueDate() {
		nextExerciseDueTextField.setText( "---" );
	}

	private void setCurrentCoursesInComboBox() {
		courseComboBox.getItems( ).clear( );
		ArrayList< Course > currentCoursesArrayList = getCurrentCourses( );

		for ( Course course : currentCoursesArrayList ) {
			courseComboBox.getItems( ).add( course.getName( ) );
		}
	}

	private void setButtonsToDisable() {
		addExerciseButton.setDisable( true );
		editExerciseButton.setDisable( true );
		deleteExerciseButton.setDisable( true );

		addExamButton.setDisable( true );
		editExamButton.setDisable( true );
		deleteExamButton.setDisable( true );
	}

	private void setButtonsToEnable() {
		addExerciseButton.setDisable( false );
		editExerciseButton.setDisable( false );
		deleteExerciseButton.setDisable( false );

		addExamButton.setDisable( false );
		editExamButton.setDisable( false );
		deleteExamButton.setDisable( false );
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

	private boolean userDidNotCancel() {
		return ! cancelAction;
	}

	private ArrayList< Course > getCurrentCourses() {
		ArrayList< Course > courseArrayList = student.getCourseArrayList( );
		ArrayList< Course > resultArrayList = new ArrayList<>( );

		for ( Course course : courseArrayList )
			if( course.getStatus( ).equals( Course.IN_STUDY ) )
				resultArrayList.add( course );

		return resultArrayList;
	}

	private class PopupWindowThread implements Runnable {
		private String name, stageTitle;

		private PopupWindowThread(String name , String stageTitle) {
			this.name = name;
			this.stageTitle = stageTitle;

		}

		public void run() {
			resetCancelAction( );
			Stage popupStage = new Stage( );
			FXMLLoader loader = new FXMLLoader( getClass( ).getResource( name ) );
			Parent layout = getLayout( loader );
			PopupController popupController = loader.getController( );
			popupController.setStage( popupStage );

			setStageSetting( popupStage , layout , stageTitle );

			if( userDidNotCancel( ) ) {
				fieldsObject = popupController.getFieldsObject( );
				setFields( popupController );
			}
		}
	}
}
