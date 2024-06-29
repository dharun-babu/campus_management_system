import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import com.i2i.app.controller.GradeController;
import com.i2i.app.controller.StudentController;
import com.i2i.app.controller.TeacherController;

@SpringBootApplication
@ComponentScan(basePackages = "com.i2i.app")
public class Main {
    @Autowired
    private StudentController studentController;
    @Autowired
    private TeacherController teacherController;
    @Autowired
    private GradeController gradeController;

    private Scanner scanner = new Scanner(System.in);

    public static void main (String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        Main main = context.getBean(Main.class);
        main.runApplication();
    }

    private void runApplication (){
        boolean repetition = true;
        System.out.println("\n\t\t\tWELCOME TO CAMPUS MANAGEMENT SYSTEM\n\tENTER THE OPTION...\n1.STUDENT\n2.GRADE\n3.TEACHER\n4.EXISTs");
        int MainMenuOption = scanner.nextInt();
        do {
            switch (MainMenuOption) {
                case 1: {
                    studentController.startApplication();
                    break;
                }
                case 2: {
                    gradeController.startApplication();
                    break;
                }
                case 3: {
                    teacherController.startApplication();
                    break;
                }
                case 4: {
                    repetition = false;
                    break;
                }
                default :
                    System.out.println("\n\t\t\tINVALID OPTION");
                    break;
            }
        } while (repetition);
        System.out.println("THANK YOU");
    }
}