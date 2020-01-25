package com.vcs.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vcs.dao.PathDAO;
import com.vcs.dao.SharingDAO;
import com.vcs.daoimpl.PathDAOImpl;
import com.vcs.daoimpl.SharingDAOImpl;
import com.vcs.pojo.Sharing;
import com.vcs.pojo.User;

public class SharingServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String requestType = req.getParameter("requestType");
			SharingDAO sDao = new SharingDAOImpl();
			PathDAO pDao = new PathDAOImpl();
			User user = (User) req.getSession().getAttribute("user");
			if (requestType == null) {
				resp.sendRedirect("error.jsp?msg=Bad Request");
			} else {
				if (requestType.equals("share")) {
					String email = req.getParameter("email");
					String path = pDao.getPathForUser(user.getEmail()).getPath();
					String filename = req.getParameter("file");
					String accesslevel = req.getParameter("access");
					Sharing s = new Sharing();
					s.setAccesslevel(accesslevel);
					s.setEmail(email);
					s.setFilename(filename);
					s.setOwner(user.getEmail());
					s.setPath(path);
					sDao.write(s);
					resp.sendRedirect(
							"manage?requestType=details&file=" + filename + "&msg=Shared this file with " + email);
				} else if (requestType.equals("remove")) {
					String email = req.getParameter("email");
					String filename = req.getParameter("filename");
					sDao.remove(email, filename);
					resp.sendRedirect("manage?requestType=details&file=" + filename
							+ "&msg=Removed Collaboration access to " + email);

				} else if (requestType.equals("changeaccess")) {
					String email = req.getParameter("email");
					String filename = req.getParameter("filename");
					String accesslevel = req.getParameter("access");
					sDao.changeAccessLevel(filename, email, accesslevel);
					resp.sendRedirect("manage?requestType=details&file=" + filename + "&msg=Modified the access level");

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect("error.jsp?msg=Something went wrong: " + e.getMessage());
		}
	}

}
