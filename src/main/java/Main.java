import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.i2i.app.controller.GradeController;
import com.i2i.app.controller.StudentController;
import com.i2i.app.controller.TeacherController;
import com.i2i.app.customexception.StudentException;
import com.i2i.app.helper.SessionFactoryProvider;

public class Main {
	private static final Logger logger = LogManager.getLogger(Main.class);
	private Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		try {
			Main main = new Main();
			main.runApplication();
		} catch (StudentException e) {
			logger.error(e.getMessage());
		}
	}

	private void runApplication () throws StudentException {
		 boolean repetition = true;

		 do {
			 System.out.println("\n\t\t\tWELCOME TO CAMPUS MANAGEMENT SYSTEM\n\tENTER THE OPTION...\n1.STUDENT\n2.GRADE\n3.TEACHER\n4.EXIST");
			 int MainMenuOption = scanner.nextInt();
			 switch (MainMenuOption) {
	         case 1: {
			     StudentController studentController = new StudentController();
				 studentController.startApplication();
			     break;
		     } 
	         case 2: {
			     GradeController gradeController = new GradeController();
	             gradeController.startApplication();
			     break;
		     }
	         case 3: {
			     TeacherController teacherController = new TeacherController();
	             teacherController.startApplication();
			     break;
		     }
			 case 4: {
			     SessionFactoryProvider.shutDown();
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