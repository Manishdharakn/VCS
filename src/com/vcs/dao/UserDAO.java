package com.vcs.dao;

import java.sql.Timestamp;
import java.util.List;

import com.vcs.pojo.User;

public interface UserDAO
{

	public List<User> getAllUsers() throws Exception;
	
	public List<String> getAllEmails() throws Exception;

	public void register(User user) throws Exception;

	public User getUserDetails(String email, String password) throws Exception;

	public User getUserDetails(String email) throws Exception;

	public void updateProfile(User user) throws Exception;

	public void deleteProfile(String email) throws Exception;

	public boolean changePassword(String email, String oldpassword, String newpassword) throws Exception;

	public String forgotPassword(String email) throws Exception;
	
	public void updateLastLogin(String email, Timestamp date) throws Exception;


}
