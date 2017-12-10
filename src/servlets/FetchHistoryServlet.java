package servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.json.JSONArray;
import org.json.JSONObject;
import dto.Data;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

@WebServlet("/FetchHistoryServlet")
public class FetchHistoryServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public FetchHistoryServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{	
			PrintWriter p = response.getWriter();
			String id = request.getParameter("id");
			System.out.println(id);
			if(id.equals("null")){
				System.out.println("no id");
				p.write("No Id");
			}else{
				Calendar from = Calendar.getInstance();
				Calendar to = Calendar.getInstance();
				from.add(Calendar.YEAR, -1);

				List<HistoricalQuote> history = (YahooFinance.get(id)).getHistory(from,to,Interval.DAILY);
				HashMap<String, Data> data = new HashMap<>();

				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				for(HistoricalQuote temp : history){
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
					public int compare(Map.Entry<String, Data> one,Map.Entry<String, Data> two){
						int i = 0;
						try{
							i = f.parse(one.getValue().getDate()).compareTo(f.parse(two.getValue().getDate()));
						} 
						catch (ParseException e) {
							e.printStackTrace();
						}
						return i;
					}
				}
						);

				LinkedHashMap<String, Data> data1 = new LinkedHashMap<>();
				for(Entry<String, Data> dd : entryList){
					data1.put(dd.getValue().getDate(), dd.getValue());
				}
				String str = FetchHistoryServlet.createJSON(data1);
				p.write(str);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String createJSON(HashMap<String, Data> data){
		JSONObject main = new JSONObject();
		JSONArray arr = new JSONArray();
		for(String key : data.keySet()){
			Data d = data.get(key);
			//System.out.println(d);
			JSONObject j = new JSONObject();
			j.put("symbol", d.getSymbol());
			j.put("low", d.getLow());
			j.put("high", d.getHigh());
			j.put("open", d.getOpen());
			j.put("close", d.getClose());
			j.put("volume", d.getvolume());
			j.put("date", d.getDate());
			j.put("amt_change", d.getAmt_change());
			j.put("per_change", d.getPercent_change());
			arr.put(j);

		}
		main.put("d",arr);
		return main.toString();
	}

}