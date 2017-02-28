package org.lrf.HttpReader.dao;

import java.util.List;

import org.lrf.HttpReader.entity.DetailArticle;

public interface ArticlesDao {
	public List<DetailArticle> getList(int pageSize, int currentPageIndex);
	
	public int getCountOfList();
	
	public DetailArticle getDetail(int articleId);
}
