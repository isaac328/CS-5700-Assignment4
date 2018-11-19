package main.exceptions;

public class InvalidSizeException extends Exception {
	String originalPuzzle;
	public InvalidSizeException(String message, String originalPuzzle){
		super(message);
		this.originalPuzzle = originalPuzzle;
	}

	public String getOriginalPuzzle() { return originalPuzzle; }
}
