package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import models.Data;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;

@WebServlet("/FetchHistoryServlet")
public class FetchHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public FetchHistoryServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{	
			String id = request.getParameter("id");
			Calendar from = Calendar.getInstance();
			Calendar to = Calendar.getInstance();
			from.add(Calendar.YEAR, -1);
			
			List<HistoricalQuote> history = (YahooFinance.get(id)).getHistory();
			HashMap<String, Data> data = new HashMap<>();
			
			SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
			int i = 0;
			for(HistoricalQuote temp : history)
			{
				Data d = new Data();
				d.setSymbol(temp.getSymbol());
				d.setLow(temp.getLow());
				d.setHigh(temp.getHigh());
				d.setOpen(temp.getOpen());
				d.setClose(temp.getClose());
				d.setAdjustedClose(temp.getAdjClose());
				d.setDate(format1.format(temp.getDate().getTime()));
				data.put(d.getDate(), d);
			}
			String str = FetchHistoryServlet.createJSON(data);
			PrintWriter p = response.getWriter();
			p.write(str);
			
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	public static String createJSON(HashMap<String, Data> data)
	{
		JSONObject main = new JSONObject();
		JSONObject temp = new JSONObject();
		
		for(String key : data.keySet())
		{
			Data d = data.get(key);
			JSONObject j = new JSONObject();
			j.put("symbol", d.getSymbol());
			j.put("low", d.getLow());
			j.put("high", d.getHigh());
			j.put("open", d.getOpen());
			j.put("close", d.getClose());
			j.put("adjClose", d.getAdjustedClose());
			j.put("date", d.getDate());
			
			main.put(d.getDate(), j);
		}
		return main.toString();
	}

}
