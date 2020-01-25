package com.vcs.core;import java.util.HashMap;
import java.util.Map;

public class VersionControlResponse
{

	int totalVersions;
	Map<Integer, String> versions = new HashMap<>();

	public int getTotalVersions()
	{
		return totalVersions;
	}

	public void setTotalVersions(int totalVersions)
	{
		this.totalVersions = totalVersions;
	}

	public Map<Integer, String> getVersions()
	{
		return versions;
	}

	public void setVersions(Map<Integer, String> versions)
	{
		this.versions = versions;
	}

}
