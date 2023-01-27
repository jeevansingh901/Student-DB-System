package skillplus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {

	private DataSource dataSource;

	public StudentDbUtil(DataSource theDataSource) {
		super();
		dataSource = theDataSource;
		
	}
	public List<Student> getStudents() throws Exception{
		List<Student>  students= new ArrayList<>();
		
		Connection myConn=null;
		Statement mySmt=null;
		ResultSet myRs=null;
		
		try {
			myConn =dataSource.getConnection();
			
			String sql="select * from students order by lastName";
			mySmt =myConn.createStatement();
			myRs=mySmt.executeQuery(sql);
			
			while(myRs.next()){
				int id=myRs.getInt("students.id");
				String firstName=myRs.getString("students.firstName");
				String lastName=myRs.getString("students.lastName");
				String email=myRs.getString("students.email");
				
				Student tempStudent =new Student(id, firstName, lastName,email);
				students.add(tempStudent);
				
			}
			
			return students;
		}
		
		finally{
			close(myConn, mySmt, myRs);
		}
		 
		
		
	}
	private void close(Connection myConn, Statement mySmt, ResultSet myRs) {
		// TODO Auto-generated method stub
		try {
			if(myRs!=null) {
				myRs.close();
			}
			if(mySmt!=null) {
				mySmt.close();
			}
			if(myConn!=null) {
				myConn.close();
			}
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}
		
	}
	public void addStudent(Student theStudent)throws Exception {
		// TODO Auto-generated method stub
		
		Connection myConn=null;
		PreparedStatement mySmt=null;
		
		try {
			myConn= dataSource.getConnection();
			String sql="insert into students " + "(firstName,lastName,email) "
					+ "values(?,?,?)";
			mySmt =myConn.prepareStatement(sql);
			mySmt.setString(1, theStudent.getFirstName());
			mySmt.setString(2, theStudent.getLastName());
			mySmt.setString(3, theStudent.getEmail());
			mySmt.execute();
		}
		finally {
			close(myConn, mySmt, null);
		}
		
	}
	public Student get(String theStudentId)throws Exception {
		// TODO Auto-generated method stub
		
		Student theStudent=null;
		Connection myConn=null;
		PreparedStatement mySmt=null;
		ResultSet myRs=null;
		int studentId;
		try {
			//convert studentId into int
			
			studentId= Integer.parseInt(theStudentId);
			
			//get the connection from DB
			myConn= dataSource.getConnection();
			
		//get id from DB
			String sql= "select * from students where id=?";
			
			// create a prepared statement
			mySmt=myConn.prepareStatement(sql);
			
			//set param
			mySmt.setInt(1, studentId);
			
			//execute statment
			myRs=mySmt.executeQuery();
			
			//retreive daata from result set roe
			
			if(myRs.next()) {
				String firstName=myRs.getString("firstName");
				String lastName=myRs.getString("lastName");
				String email=myRs.getString("email");
				//Use the studentId during construction
				theStudent =new Student(studentId,firstName,lastName,email);
			}
			else {
				throw new Exception("could not find student id:" + studentId);
			}
			
			return theStudent;
		}
		
		finally {
			//clean jdbc object
			close(myConn, mySmt, myRs);
		}
		
	}
	public void updateStudent(Student theStudent)throws Exception {
		// TODO Auto-generated method stub
		//get the DB connection
		Connection myConn=null;
		PreparedStatement mySmt=null;
		try {
			//get the DB connection
		myConn= dataSource.getConnection();
		//create a Sql Statement 
		String sql= "update students " +"set firstName=?, lastName=?, email=? " + "where id=?";
		
		// create a prepared statement
					mySmt=myConn.prepareStatement(sql);
					
		
		// set param
					
					mySmt.setString(1, theStudent.getFirstName());
					mySmt.setString(2, theStudent.getLastName());
					mySmt.setString(3, theStudent.getEmail());
					mySmt.setInt(4,theStudent.getId());
					
					
					mySmt.execute();
		}
		finally {
			close(myConn,mySmt,null);
		}
			
			
		
		
	}
	public void deleteStudent(String theStudentId) throws Exception{
		// TODO Auto-generated method stub
		Connection myConn=null;
		PreparedStatement mySmt=null;
		
		try {
			// convert student id in to INT
			
			int studentId= Integer.parseInt(theStudentId);
			
			//get the connection from DB
			myConn =dataSource.getConnection();
			
			//create a sql statement to Delete student
			
			String sql ="delete from students where id=?";
			
			//prapare statement
			
			mySmt= myConn.prepareStatement(sql);
			
			//set param
			
			mySmt.setInt(1,studentId);
			
			//execute sql statement
			mySmt.execute();
			
			
		}
		finally {
			//  clean up jdbc code
			close(myConn,mySmt, null);
		}
	}
}
