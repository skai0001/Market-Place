package com.marketplace.filter;

import com.marketplace.web.UsersController;
import java.io.IOException;
import javax.faces.application.ResourceHandler;
import javax.inject.Inject;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebFilter(filterName = "usersAuthentication", urlPatterns = {"/users/*"}, dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
public class UsersAuthentication implements Filter {

	@Inject
	private UsersController usersController;
	
	public UsersAuthentication() {
	}	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		if (!httpRequest.getRequestURI().startsWith(httpRequest.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) { // skip JSF resources (CSS/JS/Images/etc)
            httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
            httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0
            httpResponse.setDateHeader("Expires", 0); // proxies
        }
		
		if (usersController.getUser() == null) {
			chain.doFilter(httpRequest, httpResponse);
		} else {			
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.xhtml");
		}
	}
	
	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void destroy() {		
	}
}
