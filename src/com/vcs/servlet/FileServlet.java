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
import com.vcs.pojo.Path;
import com.vcs.pojo.User;
import com.vcs.pojo.UserFile;
import com.vcs.service.ChecksumService;
import com.vcs.service.FileService;
import com.vcs.util.RunCommand;
import com.vcs.util.Util;

public class FileServlet extends HttpServlet
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
			String requestType = req.getParameter("requestType");
			User user = (User) req.getSession().getAttribute("user");
			PathDAO pDao = new PathDAOImpl();
			Path destination = pDao.getPathForUser(user.getEmail());
			FileService fileService = new FileService();
			ChecksumDAO checksumDao = new ChecksumDAOImpl();
			VersionControlWorker vcWorker = new VersionControlWorker();

			if (requestType == null)
			{
				resp.sendRedirect("error.jsp?msg=Bad Request");
			} else
			{
				if (requestType.equals("write"))
				{
					String name = req.getParameter("name");
					String contents = req.getParameter("contents");
					String fileId = "FILE-" + Util.generateID();

					UserFile uf = new UserFile();
					uf.setContents(contents);
					uf.setFileId(fileId);
					uf.setName(name);
					try
					{
						fileService.writeFile(uf, destination.getPath());
						vcWorker.run(
								new File("/root/temp" + File.separator + uf.getFileId() + "_" + uf.getName()),
								VersionControlConstants.MODE_WRITE);

						VersionControlResponse response = vcWorker.run(
								new File("/root/temp" + File.separator + uf.getFileId() + "_" + uf.getName()),
								VersionControlConstants.MODE_GET_ALL_VERSIONS);

						String checksum = ChecksumService
								.generateChecksum(response.getVersions().get(response.getTotalVersions()));
						checksumDao.addChecksumForFile(fileId, checksum);

						resp.sendRedirect("createfile.jsp?done=yes");
					} catch (Exception e)
					{
						e.printStackTrace();
						resp.sendRedirect("createfile.jsp?msg=Error while writing a File: " + e.getMessage());
					}
				} else if (requestType.equals("update"))
				{
					String name = req.getParameter("file");
					String contents = req.getParameter("contents");
					UserFile uf = new UserFile();
					uf.setContents(contents);
					uf.setFileId(name.substring(0, 16));
					uf.setName(name.substring(17));
					
					try
					{
						fileService.writeFile(uf, destination.getPath());

						vcWorker.run(
								new File("/root/temp" + File.separator + uf.getFileId() + "_" + uf.getName()),
								VersionControlConstants.MODE_WRITE);

						VersionControlResponse response = vcWorker.run(
								new File("/root/temp" + File.separator + uf.getFileId() + "_" + uf.getName()),
								VersionControlConstants.MODE_GET_ALL_VERSIONS);

						String checksum = ChecksumService
								.generateChecksum(response.getVersions().get(response.getTotalVersions()));

						checksumDao.addChecksumForFile(name.substring(0, 16), checksum);


						Map<String, String> files = fileService.getFileNamesAndContentsInDataPath(destination.getPath());
						req.removeAttribute("files");
						req.setAttribute("files", files);
						req.getSession().removeAttribute("files");
						req.getSession().setAttribute("files", files);

						resp.sendRedirect(
								"manage?requestType=details&file="+name+"&msg=Successfully Updated the File");
					} catch (Exception e)
					{
						e.printStackTrace();
						resp.sendRedirect("createfile.jsp?msg=Error while writing a File: " + e.getMessage());
					}

				} else if (requestType.equals("delete"))
				{
					String name = req.getParameter("file");
					String cmd = "hadoop fs -rm "+destination.getPath()+"/"+name;
					System.out.println("COMMAND .. "+cmd);
					RunCommand.run(cmd);
					fileService.deleteFile(name, destination.getPath());
					vcWorker.run(new File(destination.getPath() + File.separator + name),
							VersionControlConstants.MODE_DELETE);

					resp.sendRedirect("manage?requestType=get&msg=File Deleted Successfully");
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			resp.sendRedirect("error.jsp?msg=Something went wrong: " + e.getMessage());
		}
	}

}
