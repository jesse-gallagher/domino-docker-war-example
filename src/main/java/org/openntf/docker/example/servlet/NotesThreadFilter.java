package org.openntf.docker.example.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.darwino.domino.napi.DominoAPI;
import com.darwino.domino.napi.DominoException;

public class NotesThreadFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			DominoAPI.get().NotesInitThread();
		} catch (DominoException e) {
			throw new ServletException("Exception initializing Notes thread", e);
		}
		try {
			chain.doFilter(request, response);
		} finally {
			DominoAPI.get().NotesTermThread();
		}
	}
}
