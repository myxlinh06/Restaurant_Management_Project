package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.LogFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
@WebFilter("/*")
public class AuthenticationFilter extends HttpFilter implements Filter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> excludeRequests;

	/**
	 * @see HttpFilter#HttpFilter()
	 */
	public AuthenticationFilter() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		excludeRequests = new ArrayList<>();
		excludeRequests.add(new String("/login"));
		excludeRequests.add(new String("/register"));
		excludeRequests.add(new String("/.js"));
		excludeRequests.add(new String("/.css"));
	}

	private boolean isValidRequest(String request) {
		for (String excludeRequest : excludeRequests) {
			if (request.contains(excludeRequest)) {
				return true;
			}
		}
		return false;
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession();

		boolean loggedIn = session != null && session.getAttribute("userLogin") != null;
		String userRequest = request.getRequestURI();

		LogFactory.getLogger().info("Request URI: " + userRequest);
		LogFactory.getLogger().info("User Logged In: " + loggedIn);

		if (loggedIn || isValidRequest(userRequest)) {
			LogFactory.getLogger().info("Request allowed: " + userRequest);
			chain.doFilter(request, response);
		} else {
			LogFactory.getLogger().info("Invalid Request: " + userRequest);
			response.sendRedirect(request.getContextPath() + "/login");
		}
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */

}