package academicoffice;


public class Task {
	private String content;
	private String date;
	private String studentId;
	private boolean isTodayTask;
	private boolean isChecked;

	public static class Builder {
		private String content;
		private String date;
		private String studentId;
		private boolean isTodayTask;
		private boolean isChecked;

		Builder(String content , String studentId) {
			this.content = content;
			this.studentId = studentId;
		}

		Builder date(String date) {
			this.date = date;
			return this;
		}

		Builder isTodayTask(Boolean isTodayTask) {
			this.isTodayTask = isTodayTask;
			return this;
		}

		Builder isChecked(Boolean isChecked) {
			this.isChecked = isChecked;
			return this;
		}

		Task build() {
			Task task = new Task( );
			task.setContent( this.content );
			task.setStudentId( this.studentId );
			task.setChecked( this.isChecked );
			task.setTodayTask( this.isTodayTask );
			task.setDate( this.date );
			return task;
		}
	}

	private Task() {
	}

	String getDate() {
		return date;
	}

	void setDate(String date) {
		this.date = date;
	}

	String getContent() {
		return content;
	}

	private void setContent(String content) {
		this.content = content;
	}

	boolean isTodayTask() {
		return isTodayTask;
	}

	private void setTodayTask(boolean todayTask) {
		isTodayTask = todayTask;
	}

	boolean isChecked() {
		return isChecked;
	}

	private void setChecked(boolean checked) {
		isChecked = checked;
	}

	String getStudentId() {
		return studentId;
	}

	private void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String toString() {
		return content;
	}
}
