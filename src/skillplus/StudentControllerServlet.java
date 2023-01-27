package skillplus;

import java.io.IOException;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




 
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID =  102831973239L;;

	
	
private StudentDbUtil studentDbUtil;
	@Resource(name="jdbc/myproject")
	private DataSource dataSource;
	
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		try {
			studentDbUtil = new StudentDbUtil(dataSource);
		}
		catch(Exception exc) {
			throw new ServletException(exc);
		}
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String theCommand=request.getParameter("command");
			
			if(theCommand==null) {
				theCommand="LIST";
			}
			
			switch(theCommand) {
			case "LIST":
				listStudents(request, response);
				break;
			case "ADD":
				addStudent(request,response);
				break;
			case "LOAD":
			     loadStudent(request,response);
			     break;
			case "UPDATE":
			     updateStudent(request,response);
			     break;
			case "DELETE":
			     deleteStudent(request,response);
			     break;
			default:
				listStudents(request,response);
			
			}
			
		
		}
		catch(Exception exc) {
		throw new ServletException(exc);
		}
	}


	private void deleteStudent(HttpServletRequest request, HttpServletResponse response)throws Exception {
		// TODO Auto-generated method stub
		//read the student id from data
		String  theStudentId= request.getParameter("studentID");
		
		//delte the student data from DB
		
		studentDbUtil.deleteStudent(theStudentId);
		
		//send bak them to list-student page
		listStudents(request,response);
		
	}


	private void updateStudent(HttpServletRequest request, HttpServletResponse response)throws Exception {
		// TODO Auto-generated method stub
		//read the student id from data
		int id=Integer.parseInt(request.getParameter("studentId"));
		String firstName= request.getParameter("firstName");
		String  lastName= request.getParameter("lastName");
		String email= request.getParameter("email");
		
		//create the new student object
		Student theStudent = new Student(id, firstName, lastName,email);
		//perfrom update on Db
		studentDbUtil.updateStudent(theStudent);
		
		//send back to "list studen" page
		listStudents(request,response);
		
		
	}


	private void loadStudent(HttpServletRequest request, HttpServletResponse response)throws Exception {
		// TODO Auto-generated method stub
		
		//read the student id from data
		String  theStudentId= request.getParameter("studentId");
		
		
		//get student from db(dbUtil)
		 Student theStudent =studentDbUtil.get(theStudentId);
		
		
		
		
		//place student in the request attribute
		 
		 request.setAttribute("THE_STUDENT", theStudent);
		 
		
		//send to jsp page:update-student-form.jsp
		 
		 RequestDispatcher dispatcher= request.getRequestDispatcher("update-student-form.jsp");
		 
		 dispatcher.forward(request, response);
		
	}


	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		String firstName= request.getParameter("firstName");
		String lastName= request.getParameter("lastName");
		String email= request.getParameter("email");
		
		Student theStudent= new Student(firstName, lastName, email);
		studentDbUtil.addStudent(theStudent);
		listStudents(request,response);
	}


	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		List<Student>  students= studentDbUtil.getStudents();
		request.setAttribute("STUDENTS_LIST", students);
		RequestDispatcher dispatcher =request.getRequestDispatcher("/list-student.jsp");
		dispatcher.forward(request, response);
		
		
	}

}
