package AcademicOffice;

import java.util.ArrayList;
import java.util.Calendar;


public class Student {
	private String name;
	private String id;
	private String major;
	private String password;
	private int totalCredits;
	private double average;
	private ArrayList< Course > courseArrayList;
	private ArrayList< Task > tasksArrayList;

	public Student() {
		final int COURSE_NUMBER = 25;
		courseArrayList = new ArrayList<>( COURSE_NUMBER );
		tasksArrayList = new ArrayList<>( );
		name = "Missing Name";
		id = "Missing id";
		major = "Missing Major";
		totalCredits = 0;
	}

	public Student(String name , String major) {
		this.name = name;
		this.major = major;
	}


	private void updateAverage() {
		int sum = 0;
		for ( Course course : courseArrayList )
			sum += course.getFinalGrade( ) == - 1 ? 0 : course.getFinalGrade( );
		average = sum / completedCourses( );
	}

	private int completedCourses() {
		int completedCourses = 0;
		for ( Course course : courseArrayList )
			completedCourses += course.getStatus( ).equals( Course.COMPLETED ) ? 1 : 0;
		return completedCourses == 0 ? 1 : completedCourses;
	}

	private void updateTotalCredits() {
		totalCredits = 0;
		for ( Course course : courseArrayList ) {
			if( course.getStatus( ).equals( Course.COMPLETED ) ) {
				totalCredits += course.getCredits( );
			}
		}
	}

	int getTotalCredits() {
		updateTotalCredits( );
		return totalCredits;
	}

	void setTotalCredits(int totalCredits) {
		this.totalCredits = totalCredits;
	}

	double getAverage() {
		updateAverage( );
		return average;
	}

	void setAverage(double average) {
		this.average = average;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	void addNewCourse(Course course) {
		courseArrayList.add( course );
	}

	void addNewTask(Task task) {
		tasksArrayList.add( task );
	}

	String getMajor() {
		return major;
	}

	void setMajor(String major) {
		this.major = major;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	String getPassword() {
		return password;
	}

	void setPassword(String password) {
		this.password = password;
	}

	ArrayList< Course > getCourseArrayList() {
		return courseArrayList;
	}

	ArrayList< Course > getFallCourses() {
		final String FALL = "a";
		ArrayList< Course > fallCoursesArrayList = new ArrayList<>( );
		String year = String.valueOf( Calendar.getInstance( ).get( Calendar.YEAR ) );

		for ( Course course : courseArrayList ) {
			if( course.getSemester( ).contains( year ) && course.getSemester( ).contains( FALL ) ) {
				fallCoursesArrayList.add( course );
			}
		}

		return fallCoursesArrayList;
	}

	ArrayList< Course > getWinterCourses() {
		final String WINTER = "b";
		ArrayList< Course > winterCoursesArrayList = new ArrayList<>( );
		String year = String.valueOf( Calendar.getInstance( ).get( Calendar.YEAR ) );

		for ( Course course : courseArrayList ) {
			if( course.getSemester( ).contains( year ) && course.getSemester( ).contains( WINTER ) ) {
				winterCoursesArrayList.add( course );
			}
		}

		return winterCoursesArrayList;
	}

	ArrayList< Course > getSummerCourses() {
		final String SUMMER = "c";
		ArrayList< Course > summerCoursesArrayList = new ArrayList<>( );
		String year = String.valueOf( Calendar.getInstance( ).get( Calendar.YEAR ) );

		for ( Course course : courseArrayList ) {
			if( course.getSemester( ).contains( year ) && course.getSemester( ).contains( SUMMER ) ) {
				summerCoursesArrayList.add( course );
			}
		}

		return summerCoursesArrayList;
	}

	ArrayList< Task > getTodayTasksArrayList() {
		ArrayList< Task > todayTasksArrayList = new ArrayList<>( );
		String todayDate = DateFormatHandler.getCurrentDate( );

		for ( Task task : tasksArrayList ) {
			if( todayDate.equals( task.getDate( ) ) )
				todayTasksArrayList.add( task );
		}
		return todayTasksArrayList;
	}

	ArrayList< Task > getPreviousTasksArrayList() {
		ArrayList< Task > previousTasksArrayList = new ArrayList<>( );
		String todayDate = DateFormatHandler.getCurrentDate( );

		for ( Task task : tasksArrayList ) {
			if( ! todayDate.equals( task.getDate( ) ) )
				previousTasksArrayList.add( task );
		}
		return previousTasksArrayList;
	}

	void deleteCourse(Integer id) {
		for ( int i = 0 ; i < courseArrayList.size( ) ; ++ i ) {
			if( courseArrayList.get( i ).getId( ).equals( id.toString( ) ) ) {
				courseArrayList.remove( i );
				return;
			}
		}
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder( );
		stringBuilder.append( name ).append( "\n" ).append( id ).append( "\n" ).
			append( major ).append( "\n" );
		return stringBuilder.toString( );
	}
}
