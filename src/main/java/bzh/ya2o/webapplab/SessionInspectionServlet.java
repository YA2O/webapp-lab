package bzh.ya2o.webapplab;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

public class SessionInspectionServlet extends HttpServlet {


	public static final String ATTR_IS_NEW_TEST = "isNewTest";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.isNew()) {
			request.setAttribute(ATTR_IS_NEW_TEST, "Session is created first time.");
		} else {
			request.setAttribute(ATTR_IS_NEW_TEST, "Session already created");
			session.setMaxInactiveInterval(120);
		}

		if (request.getParameter("action") != null) {
			if (request.getParameter("action").equals("Set Attribute") &&
			    request.getParameter("key") != null && request.getParameter("value") != "null") {
				session.setAttribute(request.getParameter("key"), request.getParameter("value"));
			}

			if (request.getParameter("action").equals("Get Attribute") &&
			    request.getParameter("key") != null) {
				request.setAttribute("getKey", session.getAttribute(request.getParameter("key")));
			}

			if (request.getParameter("action").equals("Delete Attribute") &&
			    request.getParameter("key") != null) {
				session.removeAttribute(request.getParameter("key"));
			}
		}

		Enumeration names = session.getAttributeNames();
		StringBuilder sb = new StringBuilder();
		sb.append("<table border=\"1\"><th>Key</th><th>Value</th>");
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			sb.append("<tr><td>" + name + "</td><td>" + session.getAttribute(name) + "</td></tr>");
		}
		sb.append("</table>");
		String res = sb.toString();

		request.setAttribute("res", res);
		request.getRequestDispatcher("/show-session.jsp").forward(request, response);

	}
}
