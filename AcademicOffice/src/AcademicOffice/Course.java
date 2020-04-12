package academicoffice;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Course {
	static final String COMPLETED = "Completed", IN_STUDY = "In Study";
	static final String REGISTERED = "Registered", FAILED = "Failed";
	private String name, id, status, studentId, semester;
	private Integer credits, finalGrade;

	private ArrayList< Exercise > exerciseArrayList = new ArrayList<>( );
	private ArrayList< Exam > examsArrayList = new ArrayList<>( );
	private SimpleStringProperty courseName;
	private SimpleStringProperty courseId;
	private SimpleIntegerProperty courseCredits;
	private SimpleIntegerProperty courseGrade;
	private SimpleStringProperty courseStatus;
	private SimpleStringProperty courseSemester;

	public static class Builder {
		private String name, id, status, studentId, semester;
		private Integer credits, finalGrade;

		Builder(String name , String id) {
			this.name = name;
			this.id = id;
		}

		Builder status(String status) {
			this.status = status;
			return this;
		}

		Builder studentId(String studentId) {
			this.studentId = studentId;
			return this;
		}

		Builder semester(String semester) {
			this.semester = semester;
			return this;
		}

		Builder credits(Integer credits) {
			this.credits = credits;
			return this;
		}

		Builder finalGrade(Integer finalGrade) {
			this.finalGrade = finalGrade;
			return this;
		}

		Course build() {
			Course course = new Course( );
			course.setName( this.name );
			course.setId( this.id );
			course.setSemester( this.semester );
			course.setStatus( this.status );
			course.setStudentId( this.studentId );
			course.setCredits( this.credits );
			course.setGrade( this.finalGrade );
			return course;
		}
	}

	private Course() {
	}

	public void initialize() {
		this.courseName = new SimpleStringProperty( name );
		this.courseId = new SimpleStringProperty( id );
		this.courseCredits = new SimpleIntegerProperty( credits );
		this.courseGrade = new SimpleIntegerProperty( finalGrade );
		this.courseStatus = new SimpleStringProperty( status );
		this.courseSemester = new SimpleStringProperty( semester );
	}

	public void setGrade(Integer finalGrade) {
		this.finalGrade = finalGrade;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	Integer getCredits() {
		return credits;
	}

	void setCredits(Integer credits) {
		this.credits = credits;
	}

	Integer getFinalGrade() {
		return finalGrade;
	}

	public String getCourseName() {
		return courseName.get( );
	}

	public String getCourseId() {
		return courseId.get( );
	}

	public Integer getCourseCredits() {
		return courseCredits.get( );
	}

	public Integer getCourseGrade() {
		return courseGrade.get( );
	}

	public String getCourseStatus() {
		return courseStatus.get( );
	}

	public String getCourseSemester() {
		return courseSemester.get( );
	}

	String getStatus() {
		return status;
	}

	void setStatus(String status) {
		this.status = status;
	}

	public String getStudentId() {
		return studentId;
	}

	void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	String getSemester() {
		return semester;
	}

	void setSemester(String semester) {
		this.semester = semester;
	}

	ArrayList< Exercise > getExerciseArrayList() {
		return exerciseArrayList;
	}

	void deleteExercise(String exerciseName) {

		for ( int i = 0 ; i < exerciseArrayList.size( ) ; i++ ) {
			String currentCourseName = exerciseArrayList.get( i ).getName( );
			if( currentCourseName.equals( exerciseName ) ) {
				exerciseArrayList.remove( i );
				return;
			}

		}

	}

	ArrayList< Exam > getExamsArrayList() {
		return examsArrayList;
	}

	void addNewExercise(Exercise exercise) {
		exerciseArrayList.add( exercise );
	}

	void updateExercise(Exercise exercise) {
		for ( int i = 0 ; i < exerciseArrayList.size( ) ; i++ ) {
			if( exerciseArrayList.get( i ).getName( ).equals( exercise.getName( ) ) ) {
				exerciseArrayList.remove( exerciseArrayList.get( i ) );
				exerciseArrayList.add( exercise );
				return;
			}
		}
	}

	void addNewExam(Exam exam) {
		examsArrayList.add( exam );
	}

	void deleteExam(String examAttempt) {
		for ( Exam exam : examsArrayList ) {
			if( exam.getAttempt( ).equals( examAttempt ) ) {
				examsArrayList.remove( exam );
				return;
			}
		}

	}

	void updateExam(Exam exam) {
		for ( int i = 0 ; i < examsArrayList.size( ) ; i++ ) {
			if( examsArrayList.get( i ).getAttempt( ).equals( exam.getAttempt( ) ) ) {
				examsArrayList.remove( examsArrayList.get( i ) );
				examsArrayList.add( exam );
				return;
			}
		}
	}

	public String toString() {
		return "Course name: " + name + "\nCourse id: " + id +
			"\nCourse Credits: " + credits + "\nGrade: " + finalGrade +
			"\nStatus: " + status + "\nSemester: " + semester;
	}


}
