import java.util.Scanner;

import com.i2i.app.controller.GradeController;
import com.i2i.app.controller.StudentController;
import com.i2i.app.controller.TeacherController;
import com.i2i.app.helper.SessionFactoryProvider;

public class Main {

	private Scanner scanner = new Scanner(System.in);

    public static void main (String[] args) {
			 Main main = new Main();
			 main.runApplication();
	}

	private void runApplication (){
		 boolean repetition = true;
		 System.out.println("\n\t\t\tWELCOME TO CAMPUS MANAGEMENT SYSTEM\n\tENETR THE OPTION...\n1.STUDENT\n2.GRADE\n3.TEACHER\n4.EXISTs");
		 int MainMenuOption = scanner.nextInt();
		 do {
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
			     SessionFactoryProvider.shutDown();kk
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