package AcademicOffice;

public class ValidInputTester {

	static boolean validStringInput(String string) {
		return string != null && string.length( ) != 0;
	}

	static boolean validStringArrayInput(String[] stringsArray) {
		for ( String string : stringsArray ) {
			if( ! validStringInput( string ) )
				return false;
		}
		return true;
	}

	static boolean numberFormatError(String string) {
		try {
			Integer.parseInt( string );
		} catch ( NumberFormatException e ) {
			return true;
		}
		return false;
	}

	static boolean courseNameError(String courseName) {
		return ! courseName.matches( "[A-Za-z _]*[A-Za-z][A-Za-z0-9 _]*$" );
	}

}
