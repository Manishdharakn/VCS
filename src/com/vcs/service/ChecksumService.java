package com.vcs.service;

import java.security.MessageDigest;

public class ChecksumService
{

   public static String generateChecksum(String contents) throws Exception
   {

      byte[] byteArray = contents.getBytes();

      MessageDigest digest = MessageDigest.getInstance("MD5");
      digest.update(byteArray, 0, byteArray.length);

      byte[] bytes = digest.digest();
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < bytes.length; i++)
      {
         sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
      }

      return sb.toString();
   }

   public static boolean verifyChecksum(String contents, String checksum) throws Exception
   {

      String generatedChecksum = generateChecksum(contents);
      System.out.println("Contents");
      System.out.println(contents);
      System.out.println("Checking .. "+generatedChecksum+" and "+checksum);
      if (generatedChecksum.equals(checksum))
         return true;
      else
         return false;
   }
   
   public static void main (String arg[]) throws Exception
   {
      String content = "Hello 1";
      String checksum = generateChecksum(content);
      System.out.println(checksum);
   }

}
