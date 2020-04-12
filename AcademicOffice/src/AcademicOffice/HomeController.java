package academicoffice;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable, StudentUpdater {

	@FXML
	private Label studentNameLabel, studentMajorLabel;
	@FXML
	private TableView< Course > fallSemesterTable;
	@FXML
	private TableView< Course > winterSemesterTable;
	@FXML
	private TableView< Course > summerSemesterTable;
	@FXML
	private TableColumn< Course, String > fallNameColumn, fallStatusColumn;
	@FXML
	private TableColumn< Course, String > winterNameColumn, winterStatusColumn;
	@FXML
	private TableColumn< Course, String > summerNameColumn, summerStatusColumn;
	@FXML
	private ProgressBar progressBar;
	private Student student;

	@Override
	public void initialize(URL location , ResourceBundle resources) {
		student = Main.student;
		setCurrentYearCourses( );
		if( student != null )
			updateStudent( );
	}

	private void setCurrentYearCourses() {
		fallNameColumn.setCellValueFactory( new PropertyValueFactory<>( "CourseName" ) );
		fallStatusColumn.setCellValueFactory( new PropertyValueFactory<>( "CourseStatus" ) );
		winterNameColumn.setCellValueFactory( new PropertyValueFactory<>( "CourseName" ) );
		winterStatusColumn.setCellValueFactory( new PropertyValueFactory<>( "CourseStatus" ) );
		summerNameColumn.setCellValueFactory( new PropertyValueFactory<>( "CourseName" ) );
		summerStatusColumn.setCellValueFactory( new PropertyValueFactory<>( "CourseStatus" ) );
	}

	public void updateStudent() {
		studentNameLabel.setText( Main.student.getName( ) );
		studentMajorLabel.setText( Main.student.getMajor( ) );
		updateTables( );
	}

	private void updateTables() {
		List< Course > fallCoursesArrayList = student.getFallCourses( );
		List< Course > winterCoursesArrayList = student.getWinterCourses( );
		List< Course > summerCoursesArrayList = student.getSummerCourses( );
		double result = 0;
		float currentYearCoursesNumber = fallCoursesArrayList.size( ) + winterCoursesArrayList.size( ) +
			summerCoursesArrayList.size( );
		int currentYearCoursesCompleted = getCompletedCourses( fallCoursesArrayList ) +
			getCompletedCourses( winterCoursesArrayList ) +
			getCompletedCourses( summerCoursesArrayList );

		if( currentYearCoursesCompleted == 0 ) {
			updateProgressFields( 0 );
		} else {
			result = ( currentYearCoursesCompleted / currentYearCoursesNumber );
			updateProgressFields( result );
		}

		fallSemesterTable.setItems( FXCollections.observableList( fallCoursesArrayList ) );
		winterSemesterTable.setItems( FXCollections.observableList( winterCoursesArrayList ) );
		summerSemesterTable.setItems( FXCollections.observableList( summerCoursesArrayList ) );
	}


	private int getCompletedCourses(List< Course > coursesArrayList) {
		int coursesCompletedNumber = 0;

		for ( Course course : coursesArrayList ) {
			if( course.getStatus( ).equals( Course.COMPLETED ) )
				coursesCompletedNumber++;
		}
		return coursesCompletedNumber;
	}

	private void updateProgressFields(double result) {
		progressBar.setProgress( result );
	}
}
