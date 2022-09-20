package org.gl.exceptionhandler;


import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.gl.exception.StudentNotFoundException;
import org.gl.exception.StudentUpdateDelete;
//Exception handler class to set the response
 
@Provider
public class ExceptionHandler implements ExceptionMapper<Exception>{
    //set response if status not found
    @Override
    public Response toResponse(Exception exception) {
        if(exception instanceof StudentNotFoundException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponseBody(exception.getMessage()))
                    .build();
        }
        if(exception instanceof StudentUpdateDelete){
            return Response.status(Response.Status.NO_CONTENT)
            .build();
        }
        //set response if Internal server Error happens
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity(new ErrorResponseBody("Something unexpected happened. Try again"))
        .build();
    }
    

//inner class to set the response generated to the response body
public static final class ErrorResponseBody {

    private final String message;

    public ErrorResponseBody(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
}
