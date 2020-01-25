package com.vcs.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.vcs.dao.SharingDAO;
import com.vcs.pojo.Sharing;
import com.vcs.util.MySQLUtility;

public class SharingDAOImpl implements SharingDAO {

	@Override
	public void write(Sharing s) throws Exception {
		Connection con = null;
		try {
			con = MySQLUtility.connect();
			PreparedStatement ps = con.prepareStatement("insert into sharing values(?,?,?,?,?)");
			ps.setString(1, s.getPath());
			ps.setString(2, s.getFilename());
			ps.setString(3, s.getAccesslevel());
			ps.setString(4, s.getEmail());
			ps.setString(5, s.getOwner());
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}

	}

	@Override
	public void remove(String email, String filename) throws Exception {
		Connection con = null;
		try {
			con = MySQLUtility.connect();
			con.createStatement()
					.execute("delete from sharing where email='" + email + "' and filename='" + filename + "' ");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}

	}
	
	@Override
	public void removeAllUsers(String filename) throws Exception {
		Connection con = null;
		try {
			con = MySQLUtility.connect();
			con.createStatement()
					.execute("delete from sharing where filename='" + filename + "' ");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}

	}
	
	

	@Override
	public void changeAccessLevel(String filename, String email, String accesslevel) throws Exception {
		Connection con = null;
		try {
			con = MySQLUtility.connect();
			con.createStatement().execute("update sharing set accesslevel='" + accesslevel + "' where email='" + email
					+ "' and filename='" + filename + "' ");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}

	}

	@Override
	public List<Sharing> getSharedFiles(String email) throws Exception {
		Connection con = null;
		List<Sharing> result = new ArrayList<>();
		try {
			con = MySQLUtility.connect();
			ResultSet rs = con.createStatement().executeQuery("select * from sharing where email='" + email + "' ");
			while (rs.next()) {
				Sharing s = new Sharing();
				s.setAccesslevel(rs.getString("accesslevel"));
				s.setEmail(email);
				s.setFilename(rs.getString("filename"));
				s.setOwner(rs.getString("owner"));
				s.setPath(rs.getString("path"));
				result.add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return result;
	}

	@Override
	public List<Sharing> getCollaborators(String filename) throws Exception {
		Connection con = null;
		List<Sharing> result = new ArrayList<>();
		try {
			con = MySQLUtility.connect();
			ResultSet rs = con.createStatement()
					.executeQuery("select * from sharing where filename='" + filename + "' ");
			while (rs.next()) {
				Sharing s = new Sharing();
				s.setAccesslevel(rs.getString("accesslevel"));
				s.setEmail(rs.getString("email"));
				s.setFilename(rs.getString("filename"));
				s.setOwner(rs.getString("owner"));
				s.setPath(rs.getString("path"));
				result.add(s);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return result;
	}

	@Override
	public List<String> getCollaboratorsEmail(String filename) throws Exception {
		Connection con = null;
		List<String> result = new ArrayList<>();
		try {
			con = MySQLUtility.connect();
			ResultSet rs = con.createStatement()
					.executeQuery("select email from sharing where filename='" + filename + "' ");
			while (rs.next()) {
				result.add(rs.getString("email"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return result;
	}

	@Override
	public Sharing getSharingDetails(String filename, String email) throws Exception {
		Connection con = null;
		Sharing result = new Sharing();
		try {
			con = MySQLUtility.connect();
			ResultSet rs = con.createStatement()
					.executeQuery("select * from sharing where filename='" + filename + "' and email= '" + email + "'");
			rs.next();
			result.setAccesslevel(rs.getString("accesslevel"));
			result.setEmail(email);
			result.setFilename(filename);
			result.setOwner(rs.getString("owner"));
			result.setPath(rs.getString("path"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return result;
	}

}
