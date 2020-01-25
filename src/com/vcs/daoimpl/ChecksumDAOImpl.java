package com.vcs.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.vcs.dao.ChecksumDAO;
import com.vcs.util.MySQLUtility;

public class ChecksumDAOImpl implements ChecksumDAO
{

   @Override
   public void addChecksumForFile(String filename, String checksum) throws Exception
   {
      Connection con = null;
      try
      {
         con = MySQLUtility.connect();
         con.createStatement().execute("delete from checksum where filename='"+filename+"' ");
         PreparedStatement ps = con.prepareStatement("insert into checksum values (?,?) ");
         ps.setString(1, filename);
         ps.setString(2, checksum);
         ps.execute();
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw e;
      }
      finally
      {
         con.close();
      }

   }

   @Override
   public String getChecksumForFile(String filename) throws Exception
   {
      Connection con = null;
      String result = null;
      try
      {
         con = MySQLUtility.connect();
         ResultSet rs = con.createStatement().executeQuery("select checksum from checksum where filename='" + filename + "' ");
         rs.next();
         result = rs.getString(1);
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw e;
      }
      finally
      {
         con.close();
      }
      return result;
   }

   @Override
   public void removeChecksumForFile(String filename) throws Exception
   {
      Connection con = null;

      try
      {
         con = MySQLUtility.connect();
         con.createStatement().execute("delete from checksum where filename='" + filename + "' ");

      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw e;
      }
      finally
      {
         con.close();
      }
   }

}
