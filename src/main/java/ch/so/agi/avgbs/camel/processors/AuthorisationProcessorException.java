package ch.so.agi.avgbs.camel.processors;

public class AuthorisationProcessorException extends Exception { 
    public AuthorisationProcessorException(String errorMessage) {
        super(errorMessage);
    }
} 