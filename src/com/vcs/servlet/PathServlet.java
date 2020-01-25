package com.vcs.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vcs.dao.PathDAO;
import com.vcs.daoimpl.PathDAOImpl;
import com.vcs.pojo.Path;
import com.vcs.pojo.User;
import com.vcs.util.RunCommand;

public class PathServlet extends HttpServlet
{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		try
		{
			User user = (User) req.getSession().getAttribute("user");
			PathDAO pDao = new PathDAOImpl();
			String requestType = req.getParameter("requestType");
			if (requestType == null)
			{
				resp.sendRedirect("error.jsp?msg=Bad Request");
			} else
			{
				if (requestType.equals("add"))
				{
					Path p = new Path();
					p.setEmail(user.getEmail());
					String path = req.getParameter("path");

					String command = "hadoop fs -mkdir -p " + path;
					RunCommand.run(command);

					p.setPath(req.getParameter("path"));
					pDao.write(p);
					resp.sendRedirect("path.jsp?msg=Data Path Added to your account '" + p.getPath() + "' ");
				} else if (requestType.equals("delete"))
				{
					String path = pDao.getPathForUser(user.getEmail()).getPath();
					String command = "hadoop fs -rmr " + path.substring(0, path.indexOf("/"));
					RunCommand.run(command);
					pDao.delete(user.getEmail());
					resp.sendRedirect("path.jsp?msg=Deleted the Data path from your account. ");
				}

			}
		} catch (Exception e)
		{
			e.printStackTrace();
			resp.sendRedirect("error.jsp?msg=Error: " + e.getMessage());

		}
	}

}
