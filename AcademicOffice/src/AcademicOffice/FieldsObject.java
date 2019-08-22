package AcademicOffice;

import java.util.ArrayList;


public class FieldsObject {
	private String courseName, courseId;
	private String courseCredits;
	private String courseStatus;
	private String courseGrade;
	private String courseSemester;
	private String exerciseName, examAttempt;
	private String exerciseDue, examDate;
	private String exerciseGrade;
	private String examGrade;
	private String exerciseSent, examPassed;
	private String exerciseWeight;
	private String taskContent;
	private boolean changeCourseStatus;

	String getCourseName() {
		return courseName;
	}

	void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	String getCourseId() {
		return courseId;
	}

	void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	String getCourseGrade() {
		return courseGrade;
	}

	void setCourseGrade(String courseGrade) {
		this.courseGrade = courseGrade;
	}

	String getCourseCredits() {
		return courseCredits;
	}

	void setCourseCredits(String courseCredits) {
		this.courseCredits = courseCredits;
	}

	String getCourseStatus() {
		return courseStatus;
	}

	void setCourseStatus(String courseStatus) {
		this.courseStatus = courseStatus;
	}

	String getCourseSemester() {
		return courseSemester;
	}

	void setCourseSemester(String courseSemester) {
		this.courseSemester = courseSemester;
	}

	String getExerciseWeight() {
		return exerciseWeight;
	}

	void setExerciseWeight(String exerciseWeight) {
		this.exerciseWeight = exerciseWeight;
	}

	String getExerciseSent() {
		return exerciseSent;
	}

	void setExerciseSent(String exerciseSent) {
		this.exerciseSent = exerciseSent;
	}

	String getExamPassed() {
		return examPassed;
	}

	void setExamPassed(String examPassed) {
		this.examPassed = examPassed;
	}

	String getExerciseName() {
		return exerciseName;
	}

	void setExerciseName(String exerciseName) {
		this.exerciseName = exerciseName;
	}

	String getExamAttempt() {
		return examAttempt;
	}

	void setExamAttempt(String examAttempt) {
		this.examAttempt = examAttempt;
	}

	String getExerciseDue() {
		return exerciseDue;
	}

	void setExerciseDue(String exerciseDue) {
		this.exerciseDue = exerciseDue;
	}

	String getExamDate() {
		return examDate;
	}

	void setExamDate(String examDate) {
		this.examDate = examDate;
	}

	String getExerciseGrade() {
		return exerciseGrade;
	}

	void setExerciseGrade(String exerciseGrade) {
		this.exerciseGrade = exerciseGrade;
	}

	String getExamGrade() {
		return examGrade;
	}

	void setExamGrade(String examGrade) {
		this.examGrade = examGrade;
	}

	String getTaskContent() {
		return taskContent;
	}

	void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}

	boolean getChangeCourseStatus() {
		return changeCourseStatus;
	}

	void setChangeCourseStatus(boolean changeCourseStatus) {
		this.changeCourseStatus = changeCourseStatus;
	}

	String[] toArray() {
		ArrayList< String > tmp = new ArrayList<>( );
		if( courseName != null )
			tmp.add( courseName );
		if( courseCredits != null )
			tmp.add( courseCredits );
		if( courseId != null )
			tmp.add( courseId );
		if( courseStatus != null )
			tmp.add( courseStatus );
		if( examAttempt != null )
			tmp.add( examAttempt );
		if( examGrade != null )
			tmp.add( examGrade );
		if( examDate != null )
			tmp.add( examDate );
		if( examPassed != null )
			tmp.add( examPassed );
		if( exerciseName != null )
			tmp.add( exerciseName );
		if( exerciseDue != null )
			tmp.add( exerciseDue );
		if( exerciseGrade != null )
			tmp.add( exerciseGrade );
		if( exerciseSent != null )
			tmp.add( exerciseSent );

		String[] res = new String[ tmp.size( ) ];
		for ( int i = 0 ; i < tmp.size( ) ; i++ ) {
			res[ i ] = tmp.get( i );
		}
		return res;
	}
}
