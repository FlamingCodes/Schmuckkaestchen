package de.flamingcode;

import de.flamingcode.facelets.GuiListBean;

public interface ShopListBean extends GuiListBean {

	String editShopActionName();

	void editShopListAction();

	String elementActionName();

	String elementAction(int id);

}
