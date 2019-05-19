package ch.so.agi.avgbs.camel.processors.authorisation;

public class AuthorisationProcessorException extends Exception { 
    public AuthorisationProcessorException(String errorMessage) {
        super(errorMessage);
    }
} 