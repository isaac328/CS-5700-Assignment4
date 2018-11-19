package main.exceptions;

public class InvalidSymbolException extends Exception {
	String originalPuzzle;
	public InvalidSymbolException(String message, String originalPuzzle){
		super(message);
		this.originalPuzzle = originalPuzzle;
	}

	public String getOriginalPuzzle() { return originalPuzzle; }
}
