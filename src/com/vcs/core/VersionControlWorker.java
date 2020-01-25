
package com.vcs.core;

import java.io.File;

public class VersionControlWorker
{
   public VersionControlResponse run(File file, String mode) throws Exception
   {

      if (mode.equals(VersionControlConstants.MODE_WRITE))
      {
         int currentVersion = VersionControlUtil.getCurrentVersionNumber(file.getName());
         VersionControlUtil.writeFileToCVCS(file, currentVersion + 1);
         return null;

      }
      else if (mode.equals(VersionControlConstants.MODE_DELETE))
      {
         VersionControlUtil.deleteFileFromCVCS(file);
         return null;
      }
      else if (mode.equals(VersionControlConstants.MODE_GET_NUMBER_OF_VERSIONS))
      {
         int totalVersions = VersionControlUtil.getCurrentVersionNumber(file.getName());
         VersionControlResponse response = new VersionControlResponse();
         response.setTotalVersions(totalVersions);
         return response;
      }
      else if (mode.equals(VersionControlConstants.MODE_GET_ALL_VERSIONS))
      {
         VersionControlResponse response = new VersionControlResponse();
         response.setTotalVersions(VersionControlUtil.getCurrentVersionNumber(file.getName()));
         response.setVersions(VersionControlUtil.getAllVersions(file.getName()));
         return response;
      }
      return null;
   }
}
