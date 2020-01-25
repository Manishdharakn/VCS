package com.vcs.dao;

public interface ChecksumDAO
{

   public void addChecksumForFile(String filename, String checksum) throws Exception;

   public String getChecksumForFile(String filename) throws Exception;

   public void removeChecksumForFile(String filename) throws Exception;

}
