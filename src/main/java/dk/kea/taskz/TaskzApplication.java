package dk.kea.taskz;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SpringBootApplication
public class TaskzApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskzApplication.class, args);
    }

}
