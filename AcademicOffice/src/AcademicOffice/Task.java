package AcademicOffice;


public class Task {
	private String content;
	private String date;
	private String studentId;
	private boolean isTodayTask, isChecked;

	String getDate() {
		return date;
	}

	void setDate(String date) {
		this.date = date;
	}

	String getContent() {
		return content;
	}

	void setContent(String content) {
		this.content = content;
	}

	boolean isTodayTask() {
		return isTodayTask;
	}

	void setTodayTask(boolean todayTask) {
		isTodayTask = todayTask;
	}

	boolean isChecked() {
		return isChecked;
	}

	void setChecked(boolean checked) {
		isChecked = checked;
	}

	String getStudentId() {
		return studentId;
	}

	void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String toString() {
		return content;
	}
}
