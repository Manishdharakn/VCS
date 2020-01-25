package com.vcs.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class VersionControlUtil
{

	public static boolean renameFile(File file, String newName)
	{
		File newFile = new File(file.getParent() + File.separator + newName);
		if (file.renameTo(newFile))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public static int getCurrentVersionNumber(String name)
	{
		File cvcsFolder = new File(VersionControlConstants.VCS_SERVER_PATH);
		int count = 0;
		for (String filenames : cvcsFolder.list())
		{
			if (filenames.contains(name + ".version."))
			{
				count++;
			}
		}
		return count;
	}

	public static void writeFileToCVCS(File file, int version) throws IOException
	{
		File cvcsFolder = new File(VersionControlConstants.VCS_SERVER_PATH);
		FileUtils.copyFileToDirectory(file, cvcsFolder);
		renameFile(new File(VersionControlConstants.VCS_SERVER_PATH + File.separator + file.getName()),
				file.getName() + ".version." + String.valueOf(version));
	}

	public static void deleteFileFromCVCS(File file)
	{
		File cvcsFolder = new File(VersionControlConstants.VCS_SERVER_PATH);
		for (String filenames : cvcsFolder.list())
		{
			if (filenames.contains(file.getName() + ".version."))
			{
				File cvcsFile = new File(VersionControlConstants.VCS_SERVER_PATH + File.separator + filenames);
				cvcsFile.delete();
			}
		}
	}

	public static String getContents(String name, int versionNumber) throws Exception
	{
		String fileContents = "";
		File file = new File(
				VersionControlConstants.VCS_SERVER_PATH + File.separator + name + ".version." + versionNumber);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = "";
		int i = 0;
		while ((line = br.readLine()) != null)
		{
			if (i != 0)
				fileContents += "\n";
			i++;
			fileContents += line;

		}
		br.close();
		return fileContents;

	}

	public static Map<Integer, String> getAllVersions(String name) throws Exception
	{
		int totalVersions = getCurrentVersionNumber(name);
		Map<Integer, String> versions = new HashMap<>();
		for (int i = 1; i <= totalVersions; i++)
		{
			versions.put(i, getContents(name, i));
		}
		return versions;
	}
}
