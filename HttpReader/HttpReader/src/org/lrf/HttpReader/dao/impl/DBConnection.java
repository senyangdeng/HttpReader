package org.lrf.HttpReader.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Statement;

public class DBConnection {

	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/http_reader?useUnicode=true&characterEncoding=utf-8&useSSL=false";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "33css";
	private static Connection dbConnection = null;

	// private static DBConnection instance = new DBConnection();  
	private static DBConnection instance = null;  

	private DBConnection(){}
	
    public static DBConnection getInstance() {  
        if (instance == null) {  
            synchronized (DBConnection.class) {  
                if (instance == null) {  
                   instance = new DBConnection();  
                }  
            }  
        }  
        return instance;  
    }  

    static {  
        try {  
            Class.forName(DB_DRIVER);  
        } catch (ClassNotFoundException e) {  
            throw new ExceptionInInitializerError(e);  
        }  
    }  

    public static Connection getConnection() throws SQLException {  
    	return DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);  
    } 

    public static void closeResultSet(ResultSet rs){
    	if(rs != null){
    		try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    public static void closeStatement(Statement statement){
    	if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    
    public static void closePreparedStatement(PreparedStatement statement){
    	if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    
    public static void close(){
    	if (dbConnection != null) {
			try {
				dbConnection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
}
