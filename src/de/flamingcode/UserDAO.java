package de.flamingcode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends AbstractDAO {
	
	@SuppressWarnings("unused")
	private boolean createUser(String name, String password) {
		Connection cn = null;
		try {
			cn = getConnectionInstance();
			PreparedStatement pstm = cn
					.prepareStatement("insert into USER (NAME, PASSWORD) VALUES ('"
							+ name + "', '" + encrypt(password) + "');");
			return pstm.execute();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (cn != null) {
					cn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	private String encrypt(String password) {
		StringBuilder result = new StringBuilder();
		try {
			byte[] passByte = password.getBytes();
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] newPass = md.digest(passByte);
			for (byte b : newPass) {
				result.append(Integer.toHexString(0xff & b));
			}

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.toString();
	}

	public boolean auth(String username, String password) {
		Connection cn = null;
		try {
			cn = getConnectionInstance();
			PreparedStatement pstm = cn.prepareStatement("SELECT PASSWORD FROM USER WHERE NAME='" + username + "'");
			ResultSet rs = pstm.executeQuery();
			rs.next();
			String temp = rs.getString("PASSWORD");
			if( encrypt(password).equals(temp)) {
				return true;
			} else {
				return false;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(cn != null) {
				try {
					cn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
}
