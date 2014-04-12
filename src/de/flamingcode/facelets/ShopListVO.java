package de.flamingcode.facelets;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Vector;

public class ShopListVO {

	private Vector<String> headers;
	private Vector<ShopListElement> content = new Vector<ShopListElement>();
	private int rowCnt;
		
	public static class ShopListElement {
		private int id;
		private String description;
		private String name;
		private String price;
		private String imgPath;
		
		
		
		public ShopListElement(int id, String description, String name,
				float price, String imgPath) {
			super();
			this.id = id;
			this.description = description;
			this.name = name;
			NumberFormat nf = new DecimalFormat("0.00");
			this.price = nf.format(price);
			this.setImgPath(imgPath);
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
//			System.out.println("setdes: " + description);
			this.description = description;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
//			System.out.println("setname: " + name);
			this.name = name;
		}

		public String getPrice() {
			return price.replace(".", ",");
		}

		public void setPrice(String price) {	
			price = price.replaceAll(",",".");
			float temp = Float.parseFloat(price);
			NumberFormat nf = new DecimalFormat("0.00");
			this.price = nf.format(temp);
		}

		public int getId() {
			return id;
		}

		public String getImgPath() {
			return imgPath;
		}

		public void setImgPath(String imgPath) {
			this.imgPath = imgPath;
		}
		
		@Override
		public String toString() {
			return id + ", " + this.name + ", " + this.description + ", " + this.price + ", " + this.imgPath;
		}
	}

	public Vector<String> getHeaders() {
		return headers;
	}

	public Vector<ShopListElement> getContent() {
		return content;
	}

	public void setContent(Vector<ShopListElement> content) {
		this.content = content;
	}

	public int getRowCnt() {
		return rowCnt;
	}
	
	public void addElement(ShopListElement element) {
		this.content.add(element);
		rowCnt++;
	}
}
