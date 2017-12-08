package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.GetConnection;

@WebServlet("/AnalyseServlet")
public class AnalyseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AnalyseServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
		String id = request.getParameter("sid");
		Connection con = new GetConnection().getConnection();
		
		int noOfNextDayIncrease = 0;
		int noOfNextDayDecrease = 0;
		int noOfNextDayNoChange = 0;
		int totalDays = 0;
		
		int sumOfNextDayIncrease = 0;
		int sumOfNextDayDecrease = 0;
		
		Statement st = con.createStatement();
		String sql = "SELECT date,percent_change FROM `"+id.trim()+"` WHERE percent_change < '0' ORDER BY date ASC";
		ResultSet result = st.executeQuery(sql);
		if (!result.isBeforeFirst() ) {    
		    System.out.println("No data"); 
		}
		else{
			while(result.next())
			{
				
				String date = result.getString(1);
				float percent_change = result.getFloat(2);
				System.out.println("Date-"+date);
				String sql2 = "SELECT date,percent_change FROM `"+id.trim()+"` WHERE date > '"+date+"' ORDER BY date ASC LIMIT 1";
				ResultSet r2 = st.executeQuery(sql2);
				r2.last();
				int noOfRows = r2.getRow();
				r2.beforeFirst();
				if(noOfRows == 1)
				{
					r2.first();
				}
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
