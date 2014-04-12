package de.flamingcode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Part;


import de.flamingcode.facelets.ShopListVO;
import de.flamingcode.facelets.ShopListVO.ShopListElement;

public class AngeboteDAO extends AbstractDAO {
	

	public ShopListVO update() {
		ShopListVO vo = new ShopListVO();
		Connection cn = null;
		try {
			cn = getConnectionInstance();
			PreparedStatement pstm = cn
					.prepareStatement("select * from ANGEBOTE");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				String id = rs.getString("ID");
				String name = rs.getString("NAME");
				String description = rs.getString("DESCRIPTION");
				String price = rs.getString("PREIS");
				String imgPath = rs.getString("IMG_PATH");
				float temp_price = Float.parseFloat(price);
				int temp_id = Integer.parseInt(id);
				ShopListElement temp = new ShopListElement(temp_id,
						description, name, temp_price, imgPath);
				vo.addElement(temp);

			}
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
		return vo;
	}


	public boolean update(int id, String name, String description, float price,
			String imgPath) {
		Connection con = null;
		try {
			con = getConnectionInstance();
			String query = "update ANGEBOTE set NAME='" + name
					+ "',DESCRIPTION='" + description + "', PREIS=" + price
					+ ", IMG_PATH='" + imgPath + "' where ID=" + id;
			PreparedStatement pstm = con.prepareStatement(query);
			boolean result = pstm.execute();
			con.commit();
			System.out.println("Execute Query: \"" + query + "\"");
			System.out.println("Execute with result: " + result);
			return result;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;

	}

	public void add(String name, String description, String price, Part img) {
	    InputStream inputStream;
	    String fileName = null;
		try {
			inputStream = img.getInputStream();
			for(String s : img.getHeaderNames()) {
				System.out.print(s + ": ");
				System.out.println(img.getHeader(s));
			}
			//TODO FILE CHECK
			fileName = createUniqueFileName(img);
			String appHome = System.getenv("CATALINA_BASE") + "/webapps/";
	        FileOutputStream outputStream = new FileOutputStream(appHome + fileName);  
	          
	        byte[] buffer = new byte[4096];          
	        int bytesRead = 0;  
	        while(true) {                          
	            bytesRead = inputStream.read(buffer);  
	            if(bytesRead > 0) {  
	                outputStream.write(buffer, 0, bytesRead);  
	            }else {  
	                break;  
	            }                         
	        }  
	        outputStream.close();  
	        inputStream.close();  
		} catch (IOException e1) {
			e1.printStackTrace();
		}          

		Connection con = null;
		try {
			price.replaceAll(",", ".");
			con = getConnectionInstance();
			String webAdress = prop.getProperty("web_adress");
			String query = "insert into ANGEBOTE (NAME, DESCRIPTION, PREIS, IMG_PATH) VALUES ('"
					+ name
					+ "', '"
					+ description
					+ "',"
					+ price
					+ ", '" + webAdress + fileName + "')";
			System.out.println("Execute Query: " + query);
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.execute();
			con.commit();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private String createUniqueFileName(Part img) {
		String filePath = "/images/";
		String[] temp = img.getHeader("content-type").split("/");
		String fileEnding = temp[1];
		SimpleDateFormat formatter = new SimpleDateFormat("DDMMyyHHmmssSSS");
		String fileName = formatter.format(new Date()) + "." + fileEnding;
		return filePath + fileName;
	}


	public void delete(int id) {
		Connection con = null;
		try {
			con = getConnectionInstance();
			String query = "delete from ANGEBOTE where id=" + id;
			System.out.println("Execute Query: " + query);
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.execute();
			con.commit();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
	}
}
