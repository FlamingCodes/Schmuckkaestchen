package de.flamingcode.facelets;

import java.util.Vector;

import de.flamingcode.facelets.exceptions.ContentColumnSizeException;

public class GuiListVO {

	private Vector<String> headers;
	private Vector<GuiListDataTableRow> tableContent;
	private int columnCnt;
	private boolean markable;
	private int rowCnt;
		
	public class GuiListDataTableRow {
		private  String styleClass;
		private int id;
		private boolean marked = true;
		private Vector<String> content;
		
		public GuiListDataTableRow(Vector<String> content, int id) {
			super();
			this.id = id;
			if(id % 2 == 0) {
				setStyleClass("guiList_Color_1");
			} else {
				setStyleClass("guiList_Color_2");
			}
			this.content = content;
		}
		
		public boolean isMarked() {
			return marked;
		}
		
		public void setMarked(boolean marked) {
			this.marked = marked;
		}
		
		public Vector<String> getContent() {
			return content;
		}
		
		public int getId() {
			return id;
		}

		public int size() {
			return this.content.size();
		}
		
		@Override
		public String toString() {
			return content.toString();
		}

		public String getStyleClass() {
			return styleClass;
		}

		public void setStyleClass(String styleClass) {
			this.styleClass = styleClass;
		}
	}

	public GuiListVO(Vector<String> content, int columnCnt,
			boolean markable) throws ContentColumnSizeException {
		super();
		if (content.size() % columnCnt != 0) {
			throw new ContentColumnSizeException();
		} else {
			this.columnCnt = columnCnt;
			this.headers = extractHeaders(content);
			this.tableContent = extractContent(content);
			this.markable = markable;
			this.rowCnt = this.tableContent.size();
		}
	}

	public GuiListVO(Vector<String> header, Vector<String> content,
			int columnCnt, boolean markable) throws ContentColumnSizeException {
		super();
		if (content.size() % columnCnt != 0) {
			throw new ContentColumnSizeException();
		} else {
			this.columnCnt = columnCnt;
			this.headers = header;
			this.tableContent = extractContent(content);
			this.markable = markable;
			this.rowCnt = this.tableContent.size() / columnCnt;
		}
	}

	public String get(int row, int coulumn) {
		GuiListDataTableRow r = this.tableContent.get(row - 1);
		return r.getContent().get(coulumn - 1);
	}
	
	private Vector<String> extractHeaders(Vector<String> content) {
		Vector<String> vec = new Vector<String>();
		for (int i = 0; i < this.columnCnt ;i++) {
			vec.add(content.get(i));
		}
		return vec;
	}

	private Vector<GuiListDataTableRow> extractContent(Vector<String> content) {
		Vector<String> vec;
		Vector<GuiListDataTableRow> rows = new Vector<GuiListDataTableRow>();
		for (int i = this.columnCnt; i < content.size() ;i+=this.columnCnt) {
			vec = new Vector<String>();
			for(int k = 0; k < this.columnCnt; k++) {
				vec.add(content.get(k+i));
			}
			rows.add(new GuiListDataTableRow(vec,i/columnCnt));
		}
		return rows;
	}
	
	public void printMarked() {
		for(GuiListDataTableRow row : this.tableContent) {
			if(row.marked) {
				System.out.println(row);
			}
		}
	}
	
	public Vector<String> getHeaders() {
		return headers;
	}

	public Vector<GuiListDataTableRow> getContent() {
		return tableContent;
	}

	public int getColumnCnt() {
		return columnCnt;
	}

	public int getRowCnt() {
		return rowCnt;
	}

	public boolean isMarkable() {
		return markable;
	}
}
