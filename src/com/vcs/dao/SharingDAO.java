package com.vcs.dao;

import java.util.List;

import com.vcs.pojo.Sharing;

public interface SharingDAO {

	public void write(Sharing s) throws Exception;

	public void remove(String email, String filename) throws Exception;

	public void changeAccessLevel(String filename, String email, String accesslevel) throws Exception;

	public List<Sharing> getSharedFiles(String email) throws Exception;
	
	public List<Sharing> getCollaborators(String filename) throws Exception;
	
	public List<String> getCollaboratorsEmail(String filename) throws Exception;
	
	public Sharing getSharingDetails(String filename, String email) throws Exception;
	
	public void removeAllUsers(String filename) throws Exception;
	
}
