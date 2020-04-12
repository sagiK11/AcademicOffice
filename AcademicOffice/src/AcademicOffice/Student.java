package academicoffice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Student {
	private String name, id, major, password;
	private int totalCredits;
	private double average;
	private final int COURSE_NUMBER = 25;
	private List< Course > courseArrayList = new ArrayList<>( COURSE_NUMBER );
	private List< Task > tasksArrayList = new ArrayList<>( COURSE_NUMBER );


	public static class Builder {
		private String name, id, major, password;
		private int totalCredits;
		private double average;

		Builder(String name , String id) {
			this.name = name;
			this.id = id;
		}

		Builder major(String major) {
			this.major = major;
			return this;
		}

		Builder password(String password) {
			this.password = password;
			return this;
		}

		Builder totalCredits(int totalCredits) {
			this.totalCredits = totalCredits;
			return this;
		}

		Builder average(double average) {
			this.average = average;
			return this;
		}

		Student build() {
			Student student = new Student( );
			student.setName( this.name );
			student.setId( this.id );
			student.setMajor( this.major );
			student.setPassword( this.password );
			student.setTotalCredits( this.totalCredits );
			student.setAverage( this.average );
			return student;
		}


	}

	private Student() {

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

	List< Course > getCourseArrayList() {
		return courseArrayList;
	}

	List< Course > getFallCourses() {
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
