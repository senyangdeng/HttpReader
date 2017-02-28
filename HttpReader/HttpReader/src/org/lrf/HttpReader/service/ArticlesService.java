package org.lrf.HttpReader.service;

import java.util.Map;

import org.lrf.HttpReader.entity.DetailArticle;

public interface ArticlesService {
	public Map<String, Object> getList(int pageSize,int currentPage);
	public DetailArticle getDetail(int id);
}
