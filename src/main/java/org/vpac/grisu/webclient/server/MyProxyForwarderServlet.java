package org.vpac.grisu.webclient.server;

import java.io.IOException;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import sun.security.action.GetBooleanAction;

import com.sun.corba.se.spi.activation.Server;

/**
 * Servlet implementation class test
 */

public class MyProxyForwarderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static final Logger myLogger = Logger
			.getLogger(MyProxyForwarderServlet.class.getName());
	private ServerConfiguration serverConfiguration = ServerConfiguration
			.getInstance();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * 
	 * 	Redirect's the user to Lanyard to get the MYproxy Credentials 
	 * 
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	
		
		myLogger.debug("About to Redirect Client To Lanyard");
		myLogger.debug(serverConfiguration
				.getConfiguration(ServerConfiguration.LANYARD_URL)
						+ "?"+serverConfiguration.getConfiguration(ServerConfiguration.URL_PARAMETER_NAME) + "="
						+ getURLFromRequest(request));
		response.sendRedirect(serverConfiguration
				.getConfiguration(ServerConfiguration.LANYARD_URL)
						+ "?"+serverConfiguration.getConfiguration(ServerConfiguration.URL_PARAMETER_NAME) + "="
						+getURLFromRequest(request));

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * 
	 *      Forwards the My Proxy User Name and Password to the Application by
	 *      storing the User Name and password within the session and then
	 *      redirects the client to the Application
	 * 
	 *      This post is made via a Redirect From Lanyard
	 * 
	 *      The User name is stored using the relative MyProxyAttribute Key from
	 *      the server configuration xml
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Retrive's User name and pass from
		String userName = request
				.getParameter(serverConfiguration
						.getConfiguration(ServerConfiguration.MY_PROXY_USER_NAME_ATTRIBUTE_KEY));
		String userPass = request
				.getParameter(serverConfiguration
						.getConfiguration(ServerConfiguration.MY_PROXY_PASSPHRASE_ATTRIBUTE_KEY));

		// Stores User Name and password in the session
		myLogger.debug("Storing User Name and password in Session");
		HttpSession session = request.getSession();

		session
				.setAttribute(
						serverConfiguration
								.getConfiguration(ServerConfiguration.MY_PROXY_USER_NAME_ATTRIBUTE_KEY),
						userName);
		session
				.setAttribute(
						serverConfiguration
								.getConfiguration(ServerConfiguration.MY_PROXY_PASSPHRASE_ATTRIBUTE_KEY),
						userPass);

		// Redirect client to application
		myLogger.debug("About to Redirect Client To the Grisu Web application");
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context
				.getRequestDispatcher(serverConfiguration
						.getConfiguration(ServerConfiguration.APPLICATION_URL));
		if (dispatcher != null) {
			dispatcher.forward(request, response);
			myLogger.debug("Redirect Successful");
		} else {
			myLogger.error("Dispatcher is null");
			throw new ServletException();
		}
	}

	/**
	 * Method that gets the Base URL of request
	 * 
	 * @param request
	 * @return requestURL for the Servlet
	 */
	public String getURLFromRequest(HttpServletRequest request) {
		String requestURL = request.getRequestURL().toString();

		return requestURL;
	}

}