package org.lrf.HttpReader.controller;
import java.util.Timer;
import java.util.TimerTask;

import javax.jws.soap.InitParam;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebListener;

import org.lrf.HttpReader.service.ReaderService;
import org.lrf.HttpReader.service.impl.ReaderServiceImpl;

@WebListener
public class ReaderListener implements ServletContextListener {

	public class ReadTask extends TimerTask{
		
		@Override
		public void run() {			
			ReaderService rs = new ReaderServiceImpl();
			rs.saveTodayHtml("http://news.163.com/", "js_top_news", "endText",  null, null,null);
			System.out.println("_________reader completed_______");
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		readAndSaveArticle();
	}

	
	private void readAndSaveArticle(){
		Timer timer = new Timer();
		//设置为启动2秒后执行读取网页信息，每4个小时执行一次
		timer.schedule(new ReadTask(), 1000*2, 1000*60*4);
	}
}
