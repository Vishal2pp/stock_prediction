package servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.GetConnection;
import dto.SMA;
@WebServlet("/SMAServlet")
public class SMAServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public SMAServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String id = request.getParameter("sid");
			LinkedHashMap<String, SMA> smaData = new LinkedHashMap<>();
			Connection con = new GetConnection().getConnection();
			Statement st = con.createStatement();
			String sql = "SELECT date,close from `"+id+"` WHERE 1 ORDER BY date";
			ResultSet result = st.executeQuery(sql);
			while(result.next())
			{
				SMA s = new SMA();
				s.setClose(result.getFloat(2));
				s.setDate(result.getString(1));
				s.setSma(0.0f);
				smaData.put(result.getString(1), s);
			}
			con.close();
			st.close();
			String[] arr = smaData.keySet().toArray(new String[0]);
			
			float sum = 0f;
			int j;
			for(int i = 0; i < arr.length - 4; i++){
				for(j = i; j < (i+5); j++){
					sum = sum + smaData.get(arr[j]).getClose();
				}
				try{
				smaData.get(arr[j]).setSma(sum/5);
				sum = 0f;
				}catch(ArrayIndexOutOfBoundsException e){
					System.out.println("Udya="+sum/5);
				}
			}
			
			for(String key : smaData.keySet()){
				//System.out.println(smaData.get(key).getDate()+"   "+smaData.get(key).getClose()+"   "+smaData.get(key).getSma());
			}
			String path = request.getServletContext().getRealPath("/analysis");
			System.out.println(path+File.separatorChar+id+".csv");
			PrintWriter pw = new PrintWriter(new File(path+File.separatorChar+id+".csv"));
	        StringBuilder sb = new StringBuilder();
	        sb.append("date");
	        sb.append(',');
	        sb.append("close");
	        sb.append(',');
	        sb.append("SMA");
	        sb.append("\r\n");
	        		
	        for(String key : smaData.keySet()){
				//System.out.println(smaData.get(key).getDate()+"   "+smaData.get(key).getClose()+"   "+smaData.get(key).getSma());
	        	sb.append(smaData.get(key).getDate());
	        	sb.append(',');
	        	sb.append(smaData.get(key).getClose());
	        	sb.append(',');
	        	sb.append(smaData.get(key).getSma());
	        	sb.append("\r\n");
			}
	    

	        pw.write(sb.toString());
	        pw.close();
	        System.out.println("done!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
