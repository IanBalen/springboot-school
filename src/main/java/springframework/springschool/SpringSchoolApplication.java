package springframework.springschool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class SpringSchoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSchoolApplication.class, args);
	}

}
