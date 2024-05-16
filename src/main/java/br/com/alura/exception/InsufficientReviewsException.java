package br.com.alura.exception;

public class InsufficientReviewsException extends RuntimeException {
    public InsufficientReviewsException(String message) { super(message); }
}
