package skillplus;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;


/**
 * Servlet implementation class ServletJNDIDemo
 */
@WebServlet("/ServletJNDIDemo")
public class ServletJNDIDemo extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 
	 * 
	 */
	
	@Resource(name="jdbc/myproject") //load resource file- context.xml
    private DataSource datasource;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 response.setContentType("text/html");
        PrintWriter pw=response.getWriter();
        //initializing connections
        Connection con=null;
        Statement stmt=null;
        ResultSet rs=null;
        try {
            con=datasource.getConnection();
            stmt = con.createStatement();   
            rs = stmt.executeQuery("select * from students");
            while(rs.next()) {
                pw.print("<br>"+rs.getString("students.FirstName")+", "+rs.getString("students.Email"));
            }
        }// End of try block
        catch(Exception e) {e.printStackTrace();}       
    }
		
	}


