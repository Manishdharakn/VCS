package com.vcs.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import com.vcs.pojo.UserFile;
import com.vcs.util.RunCommand;

public class FileService {

	public String getFileContents(String path, String filename) throws Exception {
		String contents = "";
		String cmd = "hadoop fs -get " + path + "/" + filename + " .";
		RunCommand.run(cmd);
		File file = new File(filename);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		int i = 0;
		String line = "";
		while ((line = br.readLine()) != null) {
			if (i != 0)
				contents += "\n";
			i++;
			contents += line;
		}
		br.close();

		file.delete();
		return contents;
	}
	
	

	public Map<String, String> getFileNamesAndContentsInDataPath(String path) throws Exception {
		String cmd1 = "mkdir -p /root/temp/tt";
		String cmd2 = "hadoop fs -get " + path + "/* /root/temp/tt";
		RunCommand.run(cmd1);
		RunCommand.run(cmd2);

		Map<String, String> result = new LinkedHashMap<>();
		File folder = new File("/root/temp/tt");
		for (String fname : folder.list()) {
			try {
				String contents = "";
				String line = "";
				BufferedReader br = new BufferedReader(
						new InputStreamReader(new FileInputStream(new File("/root/temp/tt/" + fname))));
				int i = 0;
				while ((line = br.readLine()) != null) {
					if (i != 0)
						contents += "\n";
					i++;
					contents += line;
				}
				br.close();
				result.put(fname, contents);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String cmd3 = "rm -rf /root/temp/tt";
		RunCommand.run(cmd3);
		return result;
	}

	public void deleteFile(String file, String destination) {
		String command = "hadoop fs - rm " + destination + "/" + file;
		RunCommand.run(command);
	}

	public void writeFile(UserFile file, String destination) throws Exception {
		try {
			File tempFile = new File("/root/temp/" + file.getFileId() + "_" + file.getName());
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile)));
			bw.write(file.getContents());
			bw.close();

			String command = "hadoop fs -put -f /root/temp/" + file.getFileId() + "_" + file.getName() + " "
					+ destination;
			RunCommand.run(command);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
