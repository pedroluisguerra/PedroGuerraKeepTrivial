package io.keepcoding.pgkeeptrivial.model;

public class Questions {
	private String statement;
	private String[] options;
	private int correctAnswer;
	private String category;
	
	public Questions(String statement, String[] options, int correctAnswer, String category) {
		this.statement = statement;
		this.options = options;
		this.correctAnswer = correctAnswer;	
		this.category = category;
	}
	
	public String getStatement() {
		return statement;
	}
	
	public String[] getOptions() {
		return options;
	}
	
	public int getCorrectAnswer() {
		return correctAnswer;
	}
	
	public String getCategory() {
        return category;
    }
	
	public boolean isCorrect(int indexAnswer) {
		return correctAnswer == indexAnswer;
	}
	
}
