package main.exceptions;

public class BadPuzzleException extends Exception {
	String originalPuzzle;
	public BadPuzzleException(String message, String originalPuzzle){
		super(message);
		this.originalPuzzle = originalPuzzle;
	}

	public String getOriginalPuzzle() { return originalPuzzle; }
}
