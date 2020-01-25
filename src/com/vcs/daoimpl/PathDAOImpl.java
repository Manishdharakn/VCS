package com.vcs.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.vcs.dao.PathDAO;
import com.vcs.pojo.Path;
import com.vcs.util.MySQLUtility;

public class PathDAOImpl implements PathDAO
{

   @Override
   public void write(Path p) throws Exception
   {
      Connection con = null;
      try
      {
         con = MySQLUtility.connect();
         con.createStatement().execute("delete from path where email= '" + p.getEmail() + "' ");
         PreparedStatement ps = con.prepareStatement("insert into path values (?,?)");
         ps.setString(1, p.getEmail());
         ps.setString(2, p.getPath());
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
   public void delete(String email) throws Exception
   {
      Connection con = null;
      try
      {
         con = MySQLUtility.connect();
         con.createStatement().execute("delete from path where email= '" + email + "'");
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
   public Path getPathForUser(String email) throws Exception
   {
      Connection con = null;
      Path result = null;
      try
      {
         con = MySQLUtility.connect();
         ResultSet rs = con.createStatement().executeQuery("select path from path where email='" + email + "' ");
         rs.next();
         result = new Path();
         result.setEmail(email);
         result.setPath(rs.getString(1));

      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         con.close();
      }
      return result;
   }

}
