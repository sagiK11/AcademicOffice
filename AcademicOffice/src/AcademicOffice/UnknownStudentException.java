package academicoffice;


public class UnknownStudentException extends Exception {

	UnknownStudentException() {
	}

	public UnknownStudentException(String message) {
		super( message );
	}
}
