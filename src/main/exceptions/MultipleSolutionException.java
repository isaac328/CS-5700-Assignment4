package main.exceptions;

public class MultipleSolutionException extends Exception {
	String originalPuzzle;
	public MultipleSolutionException(String message, String originalPuzzle){
		super(message);
		this.originalPuzzle = originalPuzzle;
	}

	public String getOriginalPuzzle() { return originalPuzzle; }
}
