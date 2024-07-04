import com.i2i.app.controller.StudentController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.i2i.app")
@EntityScan(basePackages = "com.i2i.app.model")
@EnableJpaRepositories(basePackages = "com.i2i.app.repositories")
public class CmsApplication implements CommandLineRunner {
	@Autowired
	private StudentController studentController;

	public static void main(String[] args) {
		SpringApplication.run(CmsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
