package AcademicOffice;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class Exercise {
	final static String SENT = "Sent", UNSENT = "Unsent";
	private String name, id;
	private Integer weight, grade;
	private Boolean sent;
	private String due;
	private SimpleStringProperty exerciseName;
	private SimpleStringProperty exerciseId;
	private SimpleIntegerProperty exerciseWeight;
	private SimpleIntegerProperty exerciseGrade;
	private SimpleStringProperty exerciseDue;
	private SimpleStringProperty exerciseSent;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Boolean getSent() {
		return sent;
	}

	public void setSent(Boolean sent) {
		this.sent = sent;
	}

	public String getDue() {
		return due;
	}

	public void setDue(String due) {
		this.due = due;
	}

	public String toString() {
		return name;
	}

	public void initialize() {
		this.exerciseName = new SimpleStringProperty( name );
		this.exerciseWeight = new SimpleIntegerProperty( weight );
		this.exerciseDue = new SimpleStringProperty( due );
		this.exerciseGrade = new SimpleIntegerProperty( grade );
		this.exerciseSent = new SimpleStringProperty( sent.toString( ) );
		this.exerciseId = new SimpleStringProperty( id );
	}

	public String getExerciseName() {
		return exerciseName.get( );
	}

	public Integer getExerciseWeight() {
		return exerciseWeight.get( );
	}

	public String getExerciseDue() {
		return exerciseDue.get( );
	}

	public String getExerciseSent() {
		return exerciseSent.get( );
	}

	public String getExerciseId() {
		return exerciseId.get( );
	}

	public Integer getExerciseGrade() {
		return exerciseGrade.get( );
	}

}
