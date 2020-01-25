package com.vcs.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vcs.core.VersionControlConstants;
import com.vcs.core.VersionControlResponse;
import com.vcs.core.VersionControlWorker;
import com.vcs.dao.ChecksumDAO;
import com.vcs.dao.PathDAO;
import com.vcs.daoimpl.ChecksumDAOImpl;
import com.vcs.daoimpl.PathDAOImpl;
import com.vcs.pojo.User;
import com.vcs.pojo.UserFile;
import com.vcs.service.ChecksumService;
import com.vcs.service.FileService;

public class ManageServlet extends HttpServlet {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String requestType = req.getParameter("requestType");
			User user = (User) req.getSession().getAttribute("user");
			PathDAO pDao = new PathDAOImpl();
			FileService fileService = new FileService();
			String path = pDao.getPathForUser(user.getEmail()).getPath();
			VersionControlWorker worker = new VersionControlWorker();
			ChecksumDAO checksumDao = new ChecksumDAOImpl();
			if (requestType == null) {
				resp.sendRedirect("error.jsp?msg=Bad Request");
			}
			if (requestType.equals("get")) {
				Map<String, String> files = fileService.getFileNamesAndContentsInDataPath(path);
				req.removeAttribute("files");
				req.setAttribute("files", files);
				req.getSession().removeAttribute("files");
				req.getSession().setAttribute("files", files);
				req.getRequestDispatcher("manage_read.jsp").forward(req, resp);
			} else if (requestType.equals("details")) {
				req.setAttribute("file", req.getParameter("file"));
				req.getRequestDispatcher("manage_file.jsp").forward(req, resp);
			} else if (requestType.equals("fix")) {
				String file = req.getParameter("file");
				VersionControlResponse versions = worker.run(new File(file),
						VersionControlConstants.MODE_GET_ALL_VERSIONS);
				String contents = versions.getVersions().get(versions.getTotalVersions());

				((Map<String, String>) req.getSession().getAttribute("files")).put(file, contents);
				UserFile uf = new UserFile();
				uf.setContents(contents);
				uf.setFileId(file.substring(0, 16));
				uf.setName(file.substring(17));
				fileService.writeFile(uf, path);

				String checksum = ChecksumService.generateChecksum(contents);

				checksumDao.addChecksumForFile(file.substring(0, 16), checksum);

				resp.sendRedirect("manage?requestType=details&file=" + file
						+ "&msg=The File has been successfully recovered from corruption");

			}

		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect("error.jsp?msg=Something went wrong: " + e.getMessage());
		}
	}

}
