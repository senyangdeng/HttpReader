package org.lrf.HttpReader.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.lrf.HttpReader.dao.ArticlesDao;
import org.lrf.HttpReader.entity.DetailArticle;

public class ArticlesDaoImpl implements ArticlesDao{
	
	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	
	public static void main(String[] args) {
		ArticlesDao ad = new ArticlesDaoImpl();
		ad.getList(4,3);
	}
	
	@Override
	public List<DetailArticle> getList(int pageSize, int currentPageIndex){
		List<DetailArticle> result= new ArrayList<DetailArticle>();
		try { 
			Connection connection = DBConnection.getInstance().getConnection();
			String sql = "SELECT * FROM detail_article ORDER BY create_date DESC LIMIT ?,?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, currentPageIndex*pageSize);
			ps.setInt(2, pageSize);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()){
				DetailArticle da = new DetailArticle();
				da.setContent(resultSet.getString("content").toString());
				da.setDate(resultSet.getTimestamp("create_date"));
				da.setTitle(resultSet.getString("title").toString());
				da.setId(resultSet.getInt("id"));
				
				result.add(da);
			}
			DBConnection.closeResultSet(resultSet);
			DBConnection.closePreparedStatement(ps);
			DBConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public DetailArticle getDetail(int articleId){
		Connection connection = null;
		DetailArticle result = new DetailArticle();
		try {
			connection = DBConnection.getInstance().getConnection();
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM detail_article WHERE id = ?");
			ps.setInt(1, articleId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				result.setId(rs.getInt("id"));
				result.setContent(rs.getString("content"));
				result.setTitle(rs.getString("title"));
				result.setDate(rs.getTimestamp("create_date"));
			}
			
			DBConnection.closeResultSet(rs);
			DBConnection.closePreparedStatement(ps);
			DBConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int getCountOfList() {
		int result = 0;
		try { 
			Connection connection = DBConnection.getInstance().getConnection();
			String sql = "SELECT COUNT(*) FROM detail_article";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()){
				result = resultSet.getInt(1);
			}
			DBConnection.closeResultSet(resultSet);
			DBConnection.closePreparedStatement(ps);
			DBConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
