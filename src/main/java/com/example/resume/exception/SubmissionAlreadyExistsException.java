package com.example.resume.exception;

public class SubmissionAlreadyExistsException extends RuntimeException {
    public SubmissionAlreadyExistsException(String message) {
        super(message);
    }
}
