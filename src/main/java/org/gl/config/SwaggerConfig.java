package org.gl.config;

import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
        tags = {
                @Tag(name = "student", description = "student operations."),
        },
        info = @Info(
                title = "Student API with Quarkus",
                version = "0.0.1",
                contact = @Contact(
                        name = "Shubham Naruka",
                        url = "",
                        email = "shubham@abc.com"),
                license = @License(
                        name = "MIT",
                        url = "https://opensource.org/licenses/MIT"))
)

public class SwaggerConfig extends Application{


    
}
