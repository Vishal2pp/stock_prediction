package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.GetConnection;
import dao.SaveData;
import dto.Data;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

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

			Calendar from = Calendar.getInstance();
			Calendar to = Calendar.getInstance();
			from.add(Calendar.YEAR, -1);

			List<HistoricalQuote> history = (YahooFinance.get(id)).getHistory(from, to, Interval.DAILY);
			HashMap<String, Data> data = new HashMap<>();

			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			for(HistoricalQuote temp : history)
			{
				if(temp.getLow() != null){
					Data d = new Data();
					d.setSymbol(temp.getSymbol());
					d.setLow(temp.getLow());
					d.setHigh(temp.getHigh());
					d.setOpen(temp.getOpen());
					d.setClose(temp.getClose());
					d.setVolume(temp.getVolume());
					d.setDate(format1.format(temp.getDate().getTime()));
					d.setAmt_change(d.getClose().floatValue() - d.getOpen().floatValue());
					d.setPercent_change((d.getAmt_change()/d.getOpen().floatValue())*100);
					data.put(d.getDate(), d);
				}
			}

			List<Map.Entry<String, Data>> entryList = new ArrayList<Map.Entry<String, Data>>(data.entrySet());

			Collections.sort(entryList, new Comparator<Map.Entry<String, Data>>() {
				SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
				@Override
				public int compare(Map.Entry<String, Data> one,Map.Entry<String, Data> two) 
				{
					int i = 0;
					try 
					{
						i = f.parse(one.getValue().getDate()).compareTo(f.parse(two.getValue().getDate()));
					} 
					catch (ParseException e) 
					{
						e.printStackTrace();
					}
					return i;
				}
			}
					);

			LinkedHashMap<String, Data> data1 = new LinkedHashMap<>();
			for(Entry<String, Data> dd : entryList)
			{
				data1.put(dd.getValue().getDate(), dd.getValue());
			}

			new SaveData().saveData(data1);

			float noOfNextDayIncrease = 0;
			float noOfNextDayDecrease = 0;
			float noOfNextDayNoChange = 0;
			int totalDays = 0;

			float sumOfNextDayIncrease = 0;
			float sumOfNextDayDecrease = 0;

			Statement st = con.createStatement();
			String sql = "SELECT date,percent_change FROM `"+id.trim()+"` WHERE percent_change < '0' ORDER BY date ASC";
			ResultSet result = st.executeQuery(sql);
			if (!result.isBeforeFirst() ) {    
				System.out.println("No data"); 
			}
			else{
				result.beforeFirst();
				while(result.next()){
					String date = result.getString(1);
					Statement st2 = con.createStatement();
					String sql2 = "SELECT date,percent_change FROM `"+id.trim()+"` WHERE date > '"+date+"' ORDER BY date ASC LIMIT 1";
					ResultSet r2 = st2.executeQuery(sql2);
					r2.last();
					int noOfRows = r2.getRow();
					r2.beforeFirst();
					if(noOfRows == 1){
						r2.first();
						float tomPercent_change = r2.getFloat(2);
						if(tomPercent_change > 0){
							noOfNextDayIncrease++;
							sumOfNextDayIncrease += tomPercent_change;
							totalDays++;
						}else if(tomPercent_change < 0){
							noOfNextDayDecrease++;
							sumOfNextDayDecrease += tomPercent_change;
							totalDays++;
						}else{
							noOfNextDayNoChange++;
							totalDays++;
						}

					}else if(noOfRows == 0){
						System.out.println("No next day data data");
					}else{
						System.out.println("Something is wrong Vishal");
					}
				}
				result.close();
			}

			System.out.println("No of next day increase="+noOfNextDayIncrease);
			System.out.println("No of next day decrease="+noOfNextDayDecrease);
			System.out.println("Sum of next day increase="+sumOfNextDayIncrease);
			System.out.println("Sum of next day decrease="+sumOfNextDayDecrease);
			System.out.println("Total days="+totalDays);

			float nextDayIncreasePercent = (noOfNextDayIncrease/totalDays) * 100;
			float nextDayDecreasePercent = (noOfNextDayDecrease/totalDays) * 100;
			float avgIncreasePercent = (sumOfNextDayIncrease/noOfNextDayIncrease);
			float avgDecreasePercent = (sumOfNextDayDecrease/noOfNextDayDecrease);

			System.out.println("I="+(noOfNextDayIncrease/totalDays) * 100);
			System.out.println("D="+(noOfNextDayDecrease/totalDays) * 100);

			PrintWriter p = response.getWriter();
			p.write("<b>Analysis of possibilites of next day increase or decrease in share price for "+id+" based on past 1 year duration.</b></br></br>");
			p.write("1) There is posibility of "+nextDayIncreasePercent+"% increase in price tommorrow.</br></br>");
			p.write("2) There is posibility of "+nextDayDecreasePercent+"% decrease in price tommorrow.</br></br>");
			p.write("3) Average increase in price for last 1 year is "+avgIncreasePercent+"%.</br></br>");
			p.write("4) Average decrease in price for last 1 year is "+avgDecreasePercent+"%.</br></br>");
			p.write("5) There were "+noOfNextDayNoChange+" days out of "+totalDays+" when there was no change in price.</br></br>");
			con.close();
			st.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
