package academicoffice;

import java.sql.*;
import java.util.ArrayList;


class DataBaseHandler {

	private Connection conn;
	private Student student;
	private final int COLUMN_INDEX_ONE = 1;
	private final int COLUMN_INDEX_TWO = 2;
	private final int COLUMN_INDEX_THREE = 3;
	private final int COLUMN_INDEX_FOUR = 4;
	private final int COLUMN_INDEX_FIVE = 5;
	private final int COLUMN_INDEX_SIX = 6;
	private final int COLUMN_INDEX_SEVEN = 7;
	private final int COLUMN_INDEX_EIGHT = 8;

	Connection connect() {
		final String url = "jdbc:postgresql://localhost:5432/AcademicOffice";
		final String user = "postgres";
		final String password = "classified my friend";


		try {
			conn = DriverManager.getConnection( url , user , password );
		} catch ( SQLException e ) {
			System.out.println( e.getMessage( ) );
		}


		return conn;
	}

	Student getStudent(String username , String id , String pass) throws UnknownStudentException {
		String sqlQuery = "SELECT * FROM STUDENT WHERE STUDENT.NAME = " + "\'" + username + "\'"
			+ " AND STUDENT.STUDENTID = " + "\'" + id + "\'" + " AND STUDENT.PASSWORD = "
			+ "\'" + pass + "\'";

		try ( Connection connection = connect( ) ;
		      Statement stmt = connection.createStatement( ) ;
		      ResultSet resultSet = stmt.executeQuery( sqlQuery ) ) {
			{
				resultSet.next( );
				student = composeNewStudent( resultSet );
				addCoursesListToStudent( student );
				addTasksListToStudent( student );
			}
		} catch ( SQLException e ) {
			System.out.println( e.getMessage( ) );
			throw new UnknownStudentException( );
		}
		return student;
	}

	private void addTasksListToStudent(Student student) {
		ArrayList< Task > tasksArrayList = getAllTasks( student );
		for ( Task task : tasksArrayList )
			student.addNewTask( task );
	}

	void addStudentToDataBase(String name , String id , String major , String pass) {
		String sqlQuery = "INSERT INTO STUDENT VALUES(" + 0 + "," + "\'" + major +
			"\'," + "\'" + name + "\'," + "\'" + pass + "\',"
			+ "\'" + id + "\'," + 0 + ")";

		try ( Connection connection = connect( ) ;
		      Statement stmt = connection.createStatement( ) ;
		      ResultSet resultSet = stmt.executeQuery( sqlQuery ) ) {
			{
				resultSet.next( );
			}
		} catch ( SQLException e ) {
			System.out.println( e.getMessage( ) );
		}
	}

	private void addCoursesListToStudent(Student student) {
		ArrayList< Course > coursesArrayList = getAllCourses( student );

		for ( Course course : coursesArrayList ) {
			addExercisesListToCourse( course );
			addExamListToCourse( course );
			student.addNewCourse( course );
		}
	}

	private void addExamListToCourse(Course course) {
		String sqlQuery = "SELECT * FROM EXAM " +
			"WHERE EXAM.COURSEID = " + "\'" + course.getId( ) + "\'" +
			" AND EXAM.STUDENTID = " + "\'" + student.getId( ) + "\'";
		ArrayList< Exam > exercisesArrayList = course.getExamsArrayList( );

		try ( Connection connection = connect( ) ;
		      Statement stmt = connection.createStatement( ) ;
		      ResultSet resultSet = stmt.executeQuery( sqlQuery ) ) {
			{
				for ( int i = 0 ; resultSet.next( ) ; i++ ) {
					Exam newExam = composeNewExam( resultSet );
					exercisesArrayList.add( newExam );
				}
			}
		} catch ( SQLException e ) {
			System.out.println( e.getMessage( ) );
		}
	}

	private void addExercisesListToCourse(Course course) {
		String sqlQuery = "SELECT * FROM EXERCISE " +
			"WHERE EXERCISE.COURSEID = " + "\'" + course.getId( ) + "\'";
		ArrayList< Exercise > exercisesArrayList = course.getExerciseArrayList( );

		try ( Connection connection = connect( ) ;
		      Statement stmt = connection.createStatement( ) ;
		      ResultSet resultSet = stmt.executeQuery( sqlQuery ) ) {
			{
				for ( int i = 0 ; resultSet.next( ) ; i++ ) {
					Exercise newExercise = composeNewExercise( resultSet );
					exercisesArrayList.add( newExercise );
				}
			}
		} catch ( SQLException e ) {
			System.out.println( e.getMessage( ) );
		}
	}

	void addCourseToDateBase(Course course , Student student) {
		String courseName = "\'" + course.getName( ) + "\'";
		String courseId = "\'" + course.getId( ) + "\',";
		int courseCredit = course.getCredits( );
		int courseGrade = course.getFinalGrade( );
		String courseStatus = "\'" + course.getStatus( ) + "\'";
		String courseSemester = "\'" + course.getSemester( ) + "\'";

		String sqlQuery = "INSERT INTO COURSE VALUES(" +
			courseId + courseCredit + "," + courseGrade + "," + courseStatus
			+ ",'" + student.getName( ) + "','" + student.getId( ) + "'," + courseName + "," +
			courseSemester + ")";
		System.out.println( sqlQuery );


		try ( Connection conn = connect( ) ;
		      Statement stmt = conn.createStatement( ) ;
		      ResultSet resultSet = stmt.executeQuery( sqlQuery ) ) {
			resultSet.next( );
		} catch ( SQLException ex ) {
			System.out.println( ex.getMessage( ) );
		}
		student.addNewCourse( course );

	}

	void addTaskToDateBase(Task task , Student student) {
		String taskContent = "\'" + task.getContent( ) + "\',";
		String studentId = "\'" + student.getId( ) + "\',";
		Boolean isChecked = task.isChecked( );
		String taskDate = ",\'" + task.getDate( ) + "\'";

		String sqlQuery = "INSERT INTO TASK VALUES(" +
			taskContent + studentId + isChecked + taskDate + ")";
		System.out.println( sqlQuery );


		try ( Connection conn = connect( ) ;
		      Statement stmt = conn.createStatement( ) ;
		      ResultSet resultSet = stmt.executeQuery( sqlQuery ) ) {
			resultSet.next( );
		} catch ( SQLException ex ) {
			System.out.println( ex.getMessage( ) );
		}
		student.addNewTask( task );

	}

	void updateCourse(Course course) {
		String sqlQuery =
			"UPDATE COURSE SET STATUS =" + "\'" + course.getStatus( ) + "\'" +
				", GRADE = " + course.getFinalGrade( ) +
				"  WHERE ID = " + "\'" + course.getId( ) + "\'" +
				" AND STUDENTID = " + "\'" + Main.student.getId( ) + "\'";
		System.out.println( sqlQuery );
		executePreparedStatement( sqlQuery );
	}

	void updateTask(Task task , boolean newSelect) {
		String sqlQuery =
			"UPDATE TASK SET ISCHECKED = " + newSelect +
				"  WHERE STUDENTID = " + "\'" + task.getStudentId( ) + "\'"
				+ " AND TASK.CONTENT = " + "\'" + task.getContent( ) + "\'";

		executePreparedStatement( sqlQuery );
	}

	void addExerciseToDataBase(Exercise exercise , Course course) {
		String exerciseName = "\'" + exercise.getName( ) + "\',";
		int exerciseWeight = exercise.getWeight( );
		Boolean exerciseIsSent = exercise.getSent( );
		int exerciseGrade = exercise.getGrade( );
		String courseId = "\'" + course.getId( ) + "\'";
		String studentId = "\'" + Main.student.getId( ) + "\'";
		String exerciseDue = "\'" + exercise.getDue( ) + "\'";

		String sqlQuery = "INSERT INTO EXERCISE VALUES(" +
			exerciseName + exerciseWeight + "," + exerciseIsSent + "," +
			exerciseGrade + "," + courseId + "," + exerciseDue + "," + studentId + ")";
		executePreparedStatement( sqlQuery );
		course.addNewExercise( exercise );
	}

	void addExamToDataBase(Exam exam , Course course) {
		String examAttempt = "\'" + exam.getAttempt( ) + "\',";
		Boolean examIsPassed = exam.isPassed( );
		int examGrade = exam.getGrade( );
		String courseId = "\'" + course.getId( ) + "\'";
		String examDate = "\'" + exam.geExamDate( ) + "\'";
		String studentId = "\'" + Main.student.getId( ) + "\'";


		String sqlQuery = "INSERT INTO EXAM VALUES(" +
			examAttempt + examDate + "," + examGrade + "," +
			examIsPassed + "," + courseId + "," + studentId + ")";

		try ( Connection conn = connect( ) ;
		      Statement stmt = conn.createStatement( ) ;
		      ResultSet resultSet = stmt.executeQuery( sqlQuery ) ) {
			resultSet.next( );

		} catch ( SQLException ex ) {
			System.out.println( ex.getMessage( ) );
		}
		course.addNewExam( exam );
	}

	void updateExam(Exam exam , Course course) {
		String sqlQuery =
			"UPDATE EXAM SET DATE =" + "\'" + exam.geExamDate( ) + "\'" +
				", GRADE = " + exam.getGrade( ) + ", PASSED = " + exam.getExamPassed( ) +
				"  WHERE COURSEID = " + "\'" + course.getId( ) + "\'" +
				" AND STUDENTID = " + "\'" + Main.student.getId( ) + "\'" +
				" AND ATTEMPT = " + "\'" + exam.getAttempt( ) + "\'";

		executePreparedStatement( sqlQuery );
		course.updateExam( exam );
	}

	void updateExercise(Exercise exercise , Course course) {
		String sqlQuery =
			"UPDATE EXERCISE SET DUE =" + "\'" + exercise.getDue( ) + "\'" +
				", GRADE = " + exercise.getGrade( ) + ", SENT = " + exercise.getSent( ) +
				"  WHERE COURSEID = " + "\'" + course.getId( ) + "\'" +
				" AND STUDENTID = " + "\'" + Main.student.getId( ) + "\'" +
				" AND NAME = " + "\'" + exercise.getName( ) + "\'";

		executePreparedStatement( sqlQuery );
		course.updateExercise( exercise );
	}

	void deleteCourse(Integer id) {
		String sqlQuery = "DELETE FROM COURSE WHERE COURSE.COURSEID = " + "\'" + id + "\'";
		executePreparedStatement( sqlQuery );
		student.deleteCourse( id );
	}

	void deleteExercise(String exerciseName , Course course) {
		String sqlQuery = "DELETE FROM EXERCISE " +
			"WHERE EXERCISE.NAME = " + "\'" + exerciseName + "\'"
			+ " AND EXERCISE.COURSEID =  " + "\'" + course.getId( ) + "\'";

		executePreparedStatement( sqlQuery );
	}

	void deleteExam(String examAttempt , Course course) {
		String sqlQuery = "DELETE FROM EXAM " +
			"WHERE EXAM.ATTEMPT = " + "\'" + examAttempt + "\'"
			+ " AND EXAM.COURSEID =  " + "\'" + course.getId( ) + "\'";

		executePreparedStatement( sqlQuery );
		course.deleteExam( examAttempt );
	}

	private void executePreparedStatement(String sqlQuery) {
		PreparedStatement preparedStatement = null;

		try {
			conn = connect( );
			preparedStatement = conn.prepareStatement( sqlQuery );
			preparedStatement.executeUpdate( );
		} catch ( SQLException e ) {
			e.printStackTrace( );
		} finally {
			closeConnection( );
		}
	}

	void closeConnection() {
		try {
			conn.close( );
		} catch ( SQLException e ) {
			e.printStackTrace( );
		}
	}

	void updateStudentInfo(String name , String major) {
		String sqlQuery =
			"UPDATE STUDENT SET NAME = " + "\'" + name + "\'" +
				", MAJOR = " + "\'" + major + "\'" +
				"  WHERE STUDENTID = " + "\'" + student.getId( ) + "\'";

		System.out.println( sqlQuery );
		executePreparedStatement( sqlQuery );
	}

	void updateStudentPassword(String newPassword) {
		String sqlQuery =
			"UPDATE STUDENT SET PASSWORD = " + "\'" + newPassword + "\'" +
				"  WHERE STUDENTID = " + "\'" + student.getId( ) + "\'";

		executePreparedStatement( sqlQuery );

	}

	private Student composeNewStudent(ResultSet resultSet) throws SQLException {
		Integer studentAverage = ( Integer ) resultSet.getObject( COLUMN_INDEX_ONE );
		String studentMajor = ( String ) resultSet.getObject( COLUMN_INDEX_TWO );
		String studentName = ( String ) resultSet.getObject( COLUMN_INDEX_THREE );
		String studentPassword = ( String ) resultSet.getObject( COLUMN_INDEX_FOUR );
		String studentId = ( String ) resultSet.getObject( COLUMN_INDEX_FIVE );
		Integer studentTotalCredits = ( Integer ) resultSet.getObject( COLUMN_INDEX_SIX );

		return new Student.Builder( studentName , studentId )
			.major( studentMajor )
			.password( studentPassword )
			.totalCredits( studentTotalCredits )
			.average( studentAverage )
			.build( );
	}

	private Course composeNewCourse(ResultSet resultSet) throws SQLException {
		String courseId = ( String ) resultSet.getObject( COLUMN_INDEX_ONE );
		Integer courseCredit = ( Integer ) resultSet.getObject( COLUMN_INDEX_TWO );
		Integer courseGrade = ( Integer ) resultSet.getObject( COLUMN_INDEX_THREE );
		String courseStatus = ( String ) resultSet.getObject( COLUMN_INDEX_FOUR );
		String studentId = ( String ) resultSet.getObject( COLUMN_INDEX_SIX );
		String courseName = ( String ) resultSet.getObject( COLUMN_INDEX_SEVEN );
		String courseSemester = ( String ) resultSet.getObject( COLUMN_INDEX_EIGHT );

		Course newCourse = new Course.Builder( courseName , courseId )
			.semester( courseSemester )
			.status( courseStatus )
			.studentId( studentId )
			.credits( courseCredit )
			.finalGrade( courseGrade )
			.build( );
		newCourse.initialize( );
		return newCourse;
	}

	private Exercise composeNewExercise(ResultSet resultSet) throws SQLException {
		String exerciseName = ( String ) resultSet.getObject( COLUMN_INDEX_ONE );
		Integer exerciseWeight = ( Integer ) resultSet.getObject( COLUMN_INDEX_TWO );
		Boolean exerciseSent = ( Boolean ) resultSet.getObject( COLUMN_INDEX_THREE );
		Integer exerciseGrade = ( Integer ) resultSet.getObject( COLUMN_INDEX_FOUR );
		String exerciseId = ( String ) resultSet.getObject( COLUMN_INDEX_FIVE );
		String exerciseDue = ( String ) resultSet.getObject( COLUMN_INDEX_SIX );

		Exercise newExercise = new Exercise.Builder( exerciseName )
			.id( exerciseId )
			.weight( exerciseWeight )
			.due( exerciseDue )
			.grade( exerciseGrade )
			.sent( exerciseSent )
			.build( );
		newExercise.initialize( );
		return newExercise;
	}

	private Exam composeNewExam(ResultSet resultSet) throws SQLException {
		String examAttempt = ( String ) resultSet.getObject( COLUMN_INDEX_ONE );
		String examDate = ( String ) resultSet.getObject( COLUMN_INDEX_TWO );
		Integer examGrade = ( Integer ) resultSet.getObject( COLUMN_INDEX_THREE );
		Boolean examPassed = ( Boolean ) resultSet.getObject( COLUMN_INDEX_FOUR );
		String courseId = ( String ) resultSet.getObject( COLUMN_INDEX_FIVE );

		Exam newExam = new Exam.Builder( examAttempt , courseId )
			.date( examDate )
			.grade( examGrade )
			.isPassed( examPassed )
			.build( );
		newExam.initialize( );
		return newExam;
	}

	private Task composeNewTask(ResultSet resultSet) throws SQLException {
		String taskContent = ( String ) resultSet.getObject( COLUMN_INDEX_ONE );
		String studentId = ( String ) resultSet.getObject( COLUMN_INDEX_TWO );
		Boolean isChecked = ( Boolean ) resultSet.getObject( COLUMN_INDEX_THREE );
		String dateCreated = ( String ) resultSet.getObject( COLUMN_INDEX_FOUR );

		return new Task.Builder( taskContent , studentId )
			.isChecked( isChecked )
			.date( dateCreated )
			.build( );
	}

	private ArrayList< Course > getAllCourses(Student student) {
		final int COURSES_NUM = 25;
		ArrayList< Course > coursesArrayList = new ArrayList<>( COURSES_NUM );
		String sqlQuery = "SELECT * FROM COURSE " +
			" WHERE COURSE.STUDENTID = " + "\'" + student.getId( ) + "\'";

		try ( Connection conn = connect( ) ;
		      Statement stmt = conn.createStatement( ) ;
		      ResultSet resultSet = stmt.executeQuery( sqlQuery ) ) {
			{
				for ( int i = 0 ; resultSet.next( ) ; i++ ) {
					Course newCourse = composeNewCourse( resultSet );
					coursesArrayList.add( newCourse );
				}
			}
		} catch ( SQLException ex ) {
			System.out.println( ex.getMessage( ) );
		}
		return coursesArrayList;
	}

	private ArrayList< Task > getAllTasks(Student student) {
		ArrayList< Task > tasksArrayList = new ArrayList<>( );
		String sqlQuery = "SELECT * FROM TASK " +
			" WHERE TASK.STUDENTID = " + "\'" + student.getId( ) + "\'";

		try ( Connection conn = connect( ) ;
		      Statement stmt = conn.createStatement( ) ;
		      ResultSet resultSet = stmt.executeQuery( sqlQuery ) ) {
			{
				for ( int i = 0 ; resultSet.next( ) ; i++ ) {
					Task newTask = composeNewTask( resultSet );
					tasksArrayList.add( newTask );
				}
			}
		} catch ( SQLException ex ) {
			System.out.println( ex.getMessage( ) );
		}
		return tasksArrayList;
	}


}
