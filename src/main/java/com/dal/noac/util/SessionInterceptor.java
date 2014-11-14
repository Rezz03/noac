package com.dal.noac.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;


import javax.servlet.annotation.WebFilter;
import javax.servlet.DispatcherType;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;

import com.vaadin.data.hbnutil.ApplicationLogger;



@WebFilter(urlPatterns = { "/*" }, dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ERROR })
public class SessionInterceptor implements Filter{
private static final ApplicationLogger logger = new ApplicationLogger(SessionInterceptor.class);

@Override
public void init(FilterConfig filterConfig) throws ServletException
{
    logger.executionTrace();
}

@Override
public void destroy()
{
    logger.executionTrace();
    final Session session = DatabaseUtil.getSessionFactory().getCurrentSession();

    if (session.getTransaction().isActive())
        session.getTransaction().commit();

    if (session.isOpen())
        session.close();
}

@Override
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
{
    final Session session = DatabaseUtil.getSessionFactory().getCurrentSession();

    try
    {
        if (!session.getTransaction().isActive())
            session.beginTransaction();

        chain.doFilter(request, response);

        if (session.getTransaction().isActive())
            session.getTransaction().commit();
    }
    catch (StaleObjectStateException e)
    {
        logger.error(e);

        if (session.getTransaction().isActive())
            session.getTransaction().rollback();

        throw e;
    }
    catch (Throwable e)
    {
        logger.error(e);

        if (session.getTransaction().isActive())
            session.getTransaction().rollback();

        throw new ServletException(e);
    }
}
}
