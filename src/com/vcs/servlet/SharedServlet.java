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
import com.vcs.dao.SharingDAO;
import com.vcs.daoimpl.ChecksumDAOImpl;
import com.vcs.daoimpl.PathDAOImpl;
import com.vcs.daoimpl.SharingDAOImpl;
import com.vcs.pojo.User;
import com.vcs.pojo.UserFile;
import com.vcs.service.ChecksumService;
import com.vcs.service.FileService;
import com.vcs.util.RunCommand;

public class SharedServlet extends HttpServlet {

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
			VersionControlWorker worker = new VersionControlWorker();
			ChecksumDAO checksumDao = new ChecksumDAOImpl();
			SharingDAO sDao = new SharingDAOImpl();

			if (requestType == null) {
				resp.sendRedirect("error.jsp?msg=Bad Request");
			}
			if (requestType.equals("get")) {
				req.setAttribute("files", sDao.getSharedFiles(user.getEmail()));
				req.getRequestDispatcher("shared_files.jsp").forward(req, resp);
			} else if (requestType.equals("details")) {
				req.setAttribute("file", req.getParameter("file"));
				req.setAttribute("path", req.getParameter("path"));

				req.getRequestDispatcher("shared_manage_file.jsp").forward(req, resp);

			} else if (requestType.equals("update") || requestType.equals("restore")) {
				String file = req.getParameter("file");
				String path = req.getParameter("path");
				String contents = req.getParameter("contents");
				UserFile uf = new UserFile();
				uf.setContents(contents);
				uf.setFileId(file.substring(0, 16));
				uf.setName(file.substring(17));

				try {
					fileService.writeFile(uf, path);

					worker.run(new File("/root/temp" + File.separator + uf.getFileId() + "_" + uf.getName()),
							VersionControlConstants.MODE_WRITE);

					VersionControlResponse response = worker.run(
							new File("/root/temp" + File.separator + uf.getFileId() + "_" + uf.getName()),
							VersionControlConstants.MODE_GET_ALL_VERSIONS);

					String checksum = ChecksumService
							.generateChecksum(response.getVersions().get(response.getTotalVersions()));

					checksumDao.addChecksumForFile(file.substring(0, 16), checksum);

					Map<String, String> files = fileService.getFileNamesAndContentsInDataPath(path);
					req.removeAttribute("files");
					req.setAttribute("files", files);
					req.getSession().removeAttribute("files");
					req.getSession().setAttribute("files", files);

					if (requestType.equals("update"))
						resp.sendRedirect(
								"shared?requestType=details&path=" + path + "&file=" + file + "&msg=Update Successful");
					else
						resp.sendRedirect("shared?requestType=details&path=" + path + "&file=" + file
								+ "&msg=File Restore Successful");
				} catch (Exception e) {
					e.printStackTrace();
					resp.sendRedirect("error.jsp.jsp?msg=Something went wrong: " + e.getMessage());
				}

			} else if (requestType.equals("delete")) {
				String name = req.getParameter("file");
				String path = req.getParameter("path");
				String cmd = "hadoop fs -rm " + path + "/" + name;
				RunCommand.run(cmd);
				fileService.deleteFile(name, path);
				worker.run(new File(path + File.separator + name), VersionControlConstants.MODE_DELETE);
				sDao.removeAllUsers(name);
				resp.sendRedirect("shared?requestType=get" + "&msg=File Deleted Successfully");

			} else if (requestType.equals("fix"))
			{
				String file = req.getParameter("file");
				String path = req.getParameter("path");
				VersionControlResponse versions = worker.run(new File(file),
						VersionControlConstants.MODE_GET_ALL_VERSIONS);
				String contents = versions.getVersions().get(versions.getTotalVersions());

				UserFile uf = new UserFile();
				uf.setContents(contents);
				uf.setFileId(file.substring(0, 16));
				uf.setName(file.substring(17));
				fileService.writeFile(uf, path);

				String checksum = ChecksumService.generateChecksum(contents);

				checksumDao.addChecksumForFile(file.substring(0, 16), checksum);

				resp.sendRedirect("shared?requestType=details&path=" + path + "&file=" + file
						+ "&msg=The file has been recovered from corruption");

				
			}

		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect("error.jsp?msg=Something went wrong: " + e.getMessage());
		}
	}

}
