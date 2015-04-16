package control;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import control.actions.EventAdder;
import control.actions.EventMover;

/**
 * Servlet implementation class TimelineController
 */
@WebServlet("/TimelineController")
public class TimelineController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private HashMap<String, Action> actions = new HashMap<String, Action>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TimelineController() {
		super();
		this.initializeMap();
	}

	private void initializeMap() {
		this.actions.put("addition", new EventAdder());
		this.actions.put("changeDate", new EventMover());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.actions.get(request.getParameter("action")).perform(request, response);
	}

}
