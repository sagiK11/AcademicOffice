package AcademicOffice;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class Exam {
	final static String PASSED = "passed", FAILED = "failed";
	private String attempt, id;
	private String date;
	private Integer grade;
	private Boolean isPassed;
	private SimpleStringProperty examAttempt;
	private SimpleStringProperty examId;
	private SimpleIntegerProperty examGrade;
	private SimpleStringProperty examDate;
	private SimpleStringProperty examPassed;


	public String getAttempt() {
		return attempt;
	}

	public void setAttempt(String attempt) {
		this.attempt = attempt;
	}

	public String geExamDate() {
		return date;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public boolean isPassed() {
		return isPassed;
	}

	public void setPassed(boolean passed) {
		isPassed = passed;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void initialize() {
		this.examAttempt = new SimpleStringProperty( attempt );
		this.examDate = new SimpleStringProperty( date );
		this.examGrade = new SimpleIntegerProperty( grade );
		this.examPassed = new SimpleStringProperty( isPassed.toString( ) );
		this.examId = new SimpleStringProperty( id );
	}

	public String getExamAttempt() {
		return examAttempt.get( );
	}

	public String getExamId() {
		return examId.get( );
	}

	public Integer getExamGrade() {
		return examGrade.get( );
	}

	public String getExamDate() {
		return examDate.get( );
	}

	public void setExamDate(String date) {
		this.date = date;
	}

	public String getExamPassed() {
		return examPassed.get( );
	}

}
