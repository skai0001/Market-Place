package com.marketplace.filter;

import com.marketplace.web.UsersController;
import java.io.IOException;
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


@WebFilter(filterName = "ItemsAuthentication", urlPatterns = {"/items/*"}, dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
public class ItemsAuthentication implements Filter {

	@Inject
	private UsersController usersController;
	
	public ItemsAuthentication() {
	}	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		if (usersController.getUser() != null) {
			chain.doFilter(httpRequest, httpResponse);
		} else {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/users/Login.xhtml");
		}
	}
	
	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void destroy() {		
	}
}