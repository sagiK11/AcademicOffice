package AcademicOffice;

import javafx.stage.Stage;

public abstract class PopupController {
	protected Stage stage;
	protected boolean editWindow, deleteWindow;

	abstract FieldsObject getFieldsObject();

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	protected void close() {
		if( stage != null ) {
			stage.close( );
		}
	}

	boolean getDeleteWindow() {
		return deleteWindow;
	}

	boolean getEditWindow() {
		return editWindow;
	}


}
