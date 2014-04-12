package de.flamingcode.sk_facelets;

import java.util.Vector;

import de.flamingcode.facelets.GuiListVO;
import de.flamingcode.facelets.exceptions.ContentColumnSizeException;

public class DetailsVO extends GuiListVO {
	
	private String description;

	public DetailsVO(Vector<String> content, int columnCnt, boolean markable)
			throws ContentColumnSizeException {
		super(content, columnCnt, markable);
		
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
