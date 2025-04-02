package edu.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ServletComponentScan
@EnableAspectJAutoProxy
@SpringBootApplication
public class Backend2Application {

    public static void main(final String[] args) {
        SpringApplication.run(Backend2Application.class, args);
    }

}
