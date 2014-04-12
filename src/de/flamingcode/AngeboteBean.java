package de.flamingcode;

import java.util.Vector;

import javax.faces.bean.*;
import javax.servlet.http.Part;

import de.flamingcode.facelets.ShopListVO;
import de.flamingcode.facelets.ShopListVO.ShopListElement;

@ManagedBean
public class AngeboteBean implements ShopListBean {
	Vector<String> messages = new Vector<String>();

	// hinzufuegen
	private String name;
	private String description;
	private String price;
	private Part imagePath;

	private boolean login = false;
	private String username = "";
	private String password = "";
	private ShopListVO vo = getAngebote();

	public ShopListVO getAngebote() {
		AngeboteDAO dao = new AngeboteDAO();
		return dao.update();
	}

	@Override
	public String elementActionName() {
		return "Löschen";
	}

	@Override
	public String elementAction(int id) {
		messages.clear();
		AngeboteDAO dao = new AngeboteDAO();
		dao.delete(id);
		vo = dao.update();
		return "angeboteBearbeiten";
	}

	@Override
	public void editShopListAction() {
		messages.clear();
		AngeboteDAO dao = new AngeboteDAO();
		for(ShopListElement ele : this.vo.getContent()) {
			String price = ele.getPrice().replaceAll(",", ".");
			try {
				Float.parseFloat(price);
			} catch (NumberFormatException e) {
				messages.add("Fehler - Preis Format nicht korrekt.");
			}
		}
		for (ShopListElement ele : this.vo.getContent()) {
			String price = ele.getPrice().replaceAll(",", ".");
			float temp = Float.parseFloat(price);
			dao.update(ele.getId(), ele.getName(), ele.getDescription(), temp,
					ele.getImgPath());
		}
		vo = dao.update();
	}

	@Override
	public String editShopActionName() {
		return "Speichern";
	}

	@Override
	public String guiListAction() {
		return "angeboteBearbeiten";
	}

	@Override
	public String actionName() {
		return "Angebote bearbeiten";
	}

	public boolean isLogin() {
		return login;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void logMeIn() {
		messages.clear();
		UserDAO dao = new UserDAO();
		if (dao.auth(username, password)) {
			login = true;
			password = "";
		} else {
			login = false;
			password = "";
		}
	}

	public String add() {
		messages.clear();
		AngeboteDAO dao = new AngeboteDAO();
		if (this.name != null && !this.name.equals("")
				&& this.description != null && !this.description.equals("")
				&& this.price != null && this.imagePath != null) {
			try {
				price.replaceAll(",", ".");
				Float.parseFloat(this.price); //check the format
				dao.add(this.name, this.description, this.price, this.imagePath);
				this.vo = dao.update();
				return "angeboteBearbeiten";
			} catch (NumberFormatException e) {
				messages.add("Fehler - Formatings Fehler beim Preis.");
				return "angebotHinzufuegen";
			}
		} else {
			messages.add("Fehler - Nicht alle Felder ausgefüllt.");
			return "angebotHinzufuegen";
		}

	}

	public ShopListVO getVo() {
		return vo;
	}

	public void setVo(ShopListVO vo) {
		System.out.println("khjoiaqfpi");
		this.vo = vo;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Part getImagePath() {
		return imagePath;
	}

	public void setImagePath(Part imagePath) {
		this.imagePath = imagePath;
	}
}
