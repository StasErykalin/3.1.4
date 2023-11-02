package app;

import app.configs.WebConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import java.util.List;

public class RestApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
        Communication communicator = context.getBean("communication", Communication.class);
        System.out.println(communicator.getAnswer());
    }

}
