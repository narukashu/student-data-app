package org.gl.exception;
//class for exception handling if student not found
public class StudentNotFoundException extends Exception {
    
    public StudentNotFoundException(String message){
        super(message);
    }
}
