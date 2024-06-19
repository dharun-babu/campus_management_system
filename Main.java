import java.util.Scanner;

import com.i2i.app.controller.GradeController;
import com.i2i.app.controller.StudentController;
import com.i2i.app.controller.TeacherController;
import com.i2i.app.customexception.StudentException;
class Main {
    private Scanner scanner = new Scanner(System.in);

    public static void main (String [] args) {
	     try {
			 Main main = new Main();
			 main.runApplication();

	     } catch (StudentException e) {
			 System.out.println("Student Exception : "+e.getMessage());
			 e.printStackTrace();
	     }
	}

	private void runApplication () throws StudentException {
		 System.out.println("\n\t\t\tWELCOME TO CAMPUS MANAGEMENT SYSTEM\n\tENETR THE OPTION...\n1.STUDENT\n2.GRADE\n3.TEACHER");
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
	     default :
			  System.out.println("\n\t\t\tINVALID OPTION");
		 }
    }
}