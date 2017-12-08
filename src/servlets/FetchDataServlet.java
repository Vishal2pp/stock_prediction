package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import dto.LiveData;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

@WebServlet("/FetchDataServlet")
public class FetchDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FetchDataServlet() {
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{	
			String id = request.getParameter("sid");
			Stock stock = YahooFinance.get(id, true);
			LiveData d1 = null;
			System.out.println("==============LIVE DATA=============");
			System.err.println("Symbol-"+stock.getSymbol());
			System.out.println("Price-"+stock.getQuote().getPrice());
			System.out.println("Opening price-"+stock.getQuote().getOpen());
			System.out.println("Ask-"+stock.getQuote().getAsk());
			System.out.println("Bid-"+stock.getQuote().getBid());

			d1 = new LiveData();
			d1.setSymbol(stock.getSymbol());
			d1.setPrice(stock.getQuote().getPrice());
			d1.setAsk(stock.getQuote().getAsk());
			d1.setBid(stock.getQuote().getBid());
			d1.setOpeningPrice(stock.getQuote().getOpen());
			d1.setPreviousClose(stock.getQuote().getPreviousClose());
			String str = FetchDataServlet.toJSON(d1);
			PrintWriter p = response.getWriter();
			p.write(str);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	public static String toJSON(LiveData d)
	{
		JSONObject main = new JSONObject();
		JSONObject data = new JSONObject();
		data.put("symbol", d.getSymbol());
		data.put("price", d.getPrice());
		data.put("openingPrice", d.getOpeningPrice());
		data.put("ask", d.getAsk());
		data.put("bid", d.getBid());
		data.put("preClose", d.getPreviousClose());
		main.put("liveData", data);
		return main.toString();
	}
}