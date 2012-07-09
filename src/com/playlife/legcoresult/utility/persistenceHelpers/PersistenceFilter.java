package com.playlife.legcoresult.utility.persistenceHelpers;

import java.io.IOException;

import javax.jdo.JDOHelper;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class PersistenceFilter implements Filter {

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			if (PMF.get() == null || PMF.get().isClosed())
				PMF.set(JDOHelper.getPersistenceManagerFactory("transactions-optional"));
			
			PMF.pm.set(PMF.get().getPersistenceManager());
			
			chain.doFilter(request, response);
			if (!PMF.pm.get().isClosed())
				PMF.pm.get().close();
		} catch (Exception ex){
		}
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}
}
