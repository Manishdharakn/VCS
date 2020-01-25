package com.vcs.dao;

import com.vcs.pojo.Path;

public interface PathDAO
{

   public void write(Path p) throws Exception;

   public void delete(String email) throws Exception;

   public Path getPathForUser(String email) throws Exception;

}
