package org.lrf.HttpReader.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.lrf.HttpReader.dao.ReaderDao;
import org.lrf.HttpReader.entity.DetailArticle;

import com.mysql.jdbc.Statement;

public class ReaderDaoImpl implements ReaderDao{
	@Override
	public void saveArticle(String title,String content,String date){
		DetailArticle da = new DetailArticle();
		da.setContent(content);
		if(date!=null){
			try {
				da.setDate(dateFormat.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			da.setDate(new Date());
		}
		da.setTitle(title);
		try {
			this.insertDetailArticle(da);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/http_reader?useUnicode=true&characterEncoding=utf-8&useSSL=false";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "33css";
	private static final DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
		
	private void insertDetailArticle(DetailArticle detailArticle) throws SQLException {

		Connection dbConnection = DBConnection.getInstance().getConnection();
		PreparedStatement statement = null;

		String insertTableSQL = "INSERT INTO detail_article"
				+ "(title, content, create_date) " + "VALUES"
				+ "('"+detailArticle.getTitle()+"','"+detailArticle.getContent()+"','"+ getCurrentTimeStamp(detailArticle.getDate()) + "')";
		try {
			statement = dbConnection.prepareStatement(insertTableSQL);
			int result = statement.executeUpdate();
			if(result > 0){
				System.out.println("excute insert "+detailArticle.getTitle());
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			DBConnection.closePreparedStatement(statement);
			DBConnection.close();
		}

	}
	
	private static String getCurrentTimeStamp(Date date) {
		return dateFormat.format(date.getTime());
	}

	
	private static Connection getDBConnection() {

		Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}

		try {

			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;

	}

}
