package org.lrf.HttpReader.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lrf.HttpReader.dao.ArticlesDao;
import org.lrf.HttpReader.dao.impl.ArticlesDaoImpl;
import org.lrf.HttpReader.entity.DetailArticle;
import org.lrf.HttpReader.service.ArticlesService;

public class ArticlesServiceImpl implements ArticlesService{

	private static ArticlesDao ad = new ArticlesDaoImpl();
	
	@Override
	public Map<String, Object> getList(int pageSize, int currentPage) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("articles", ad.getList(pageSize, currentPage-1));
		result.put("articleCount", ad.getCountOfList());
		return result;
	}

	@Override
	public DetailArticle getDetail(int id) {
		return ad.getDetail(id);
	}
}
