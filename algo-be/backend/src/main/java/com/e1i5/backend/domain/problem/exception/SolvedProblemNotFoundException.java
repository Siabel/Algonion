package com.e1i5.backend.domain.problem.exception;

public class SolvedProblemNotFoundException extends RuntimeException{
    public SolvedProblemNotFoundException(String msg) {
        super(msg);
    }
}
