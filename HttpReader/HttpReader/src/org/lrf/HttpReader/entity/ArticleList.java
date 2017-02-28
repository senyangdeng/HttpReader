package org.lrf.HttpReader.entity;

import java.util.ArrayList;
import java.util.Date;

public class ArticleList extends ArrayList<DetailArticle>{
	
	private static final long serialVersionUID = 1L;
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
