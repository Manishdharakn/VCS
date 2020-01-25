package com.vcs.core;
import java.io.File;

public class Test
{

	public static void main(String[] args) throws Exception
	{

		File f = new File("D:\\Projects2019\\CVCS\\WorkingCopy\\me.txt");

		VersionControlWorker vcw = new VersionControlWorker();
		
		System.out.println("Total Versiosns : "+vcw.run(f, VersionControlConstants.MODE_DELETE));
		

	}

}
