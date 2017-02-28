package org.lrf.HttpReader.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.lrf.HttpReader.dao.ReaderDao;
import org.lrf.HttpReader.dao.impl.ReaderDaoImpl;
import org.lrf.HttpReader.entity.DetailArticle;
import org.lrf.HttpReader.service.ReaderService;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

public class ReaderServiceImpl implements ReaderService{
	private static Map<String, String> articles = new HashMap<String, String>();

	@Override
	public void saveTodayHtml(String listUrl,String listWrapperId,String detailWrapperId,String listWrapperClass,String detailWrapperClass,String charset){
		if(charset == null)
			charset = "gb2312";
		setArticles(listUrl,listWrapperId,listWrapperClass, charset);
		readAndSaveTodayOriginalElementOfPage(listUrl,detailWrapperId,null,charset);
	}
	
	/**
	 * 获取每一个新闻页面类容的详情
	 * */
	private void readAndSaveTodayOriginalElementOfPage(String host,String wrapperId,String wrapperClass,String charset){
		ReaderDao rd = new ReaderDaoImpl();
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		for(String title : articles.keySet()){
			String url = articles.get(title);
			if(articles.get(title).indexOf(host) < 0)
				url = host + articles.get(title);
			
			HttpGet httpGet = new HttpGet(url);
			httpGet.addHeader("Content-Type", "text/html; charset="+charset);
			try {
				response = client.execute(httpGet);
				response.addHeader("Content-Type", "text/html; charset="+charset);
				HttpEntity entity = response.getEntity();
				String responseContent = EntityUtils.toString(entity, charset);
				
				//jsoup analysis html to DOM
				//and save article title and article url to articles(map)
				Document document = Jsoup.parse(responseContent);
				Element element = null; 
				if(wrapperId != null){
					element = document.getElementById(wrapperId);
				}else if(wrapperClass != null){
					Elements elements = document.getElementsByClass(wrapperClass);
					if(elements != null && elements.size() > 0){
						element = elements.get(0);
					}
				}else{
					throw new Exception("there is no correct specific wrapper element");
				}
				
				//delete advertising and img
				if(element == null){
					return ;
				}
				if(element.select(".gg200x300") == null)
				{
					return;
				}
				element.select(".gg200x300").remove();	
				element.select("img").remove();
				//get date
				Elements datetimeElements = document.getElementsByClass("post_time_source");
				String dateTimeString = null;
				if(datetimeElements !=null && datetimeElements.size() > 0 && datetimeElements.text().length() >= 19){
					dateTimeString = datetimeElements.text().substring(0, 19);
				}
				
				if(element!= null && element.toString() != null){
					//jdbc :save article instance to database
					rd.saveArticle(title, element.toString(),dateTimeString);
				}
				
				
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
					
			}
			
		}
		
		try {
			if(response !=null)
				response.close();
			if(client != null)
				client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 1.获取list html页面
	 * 2.根据html页面指定id或者class的div
	 * 3.向下递归div，获取到文本信息
	 * 4.向上递归文本信息，获取到A标签和href
	 * 5.将文本信息和href存储到article这个map中
	 * */
	private String setArticles(String url,String wrapperId,String wrapperClass, String charset){
		/*
		 * 使用 GetMethod 来访问一个 URL 对应的网页
		 * 实现步骤: 
		 * 1:生成一个 CloseableHttpClient 对象并设置相应的参数。
		 * 2:生成一个 HttpGet 对象并设置响应的参数。 
		 * 3:用 CloseableHttpClient对象执行execute获取CloseableHttpResponse
		 * 4:HttpEntity是返回响应的封装
		 * 5:释放连接。
		 */
		
		CloseableHttpClient client = HttpClients.createDefault();		
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Content-Type", "text/html; charset="+charset);
		CloseableHttpResponse response = null;
		try {
			response = client.execute(httpGet);
			response.addHeader("Content-Type", "text/html; charset="+charset);
			
			//print http status code   200 or 400 
			System.out.println(response.getStatusLine());
			
			HttpEntity entity = response.getEntity();
			String responseContent = EntityUtils.toString(entity, charset);
			//print entire http code
			//System.out.println(responseContent);
			
			//jsoup analysis html to DOM
			//and save article title and article url to articles(map)
			Document document = Jsoup.parse(responseContent);
			
			Element element = null;
			if(wrapperId != null){
				element = document.getElementById(wrapperId);
			}else if(wrapperClass != null){
				List<Element> elementsByClass = document.getElementsByClass(wrapperClass);
				element = elementsByClass.get(0);
			}else{
				throw new Exception("there is no correct specific wrapper element");
			}
			this.getTextElementInCurrentNodeSaveAsArticles(element);

			//print article title and article url
			System.out.println(articles);
		} catch (IOException e) {
			System.out.println(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
				try {
					if(response !=null)
						response.close();
					if(client != null)
						client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
			
		return "";
	}
	
	/**
	 * 向下递归寻找textNode
	 * 
	 * 1.循环Element下所有childnodes
	 * 
	 * Node的子类分为（Ctrl+t）：
	 * comment
	 * dataNode
	 * documentType
	 * element
	 * textNode
	 * 
	 * 2.判断子类类型：如果是element表示这个对象是一个标签
	 * 3.如果是标签，再次调用这个方法，进入该标签的子标签
	 * 4.如果node类型为textNode，则将该值存入articles的value，把包裹它的父亲标签存的href值（也就是链接）存为articles的key
	 * */
	private Element getTextElementInCurrentNodeSaveAsArticles(Node node){
		if(Element.class.isInstance(node)){
			Element element = (Element)node;
			for(Node currentNode : element.childNodes()){
				if(Element.class.isInstance(currentNode)){
					this.getTextElementInCurrentNodeSaveAsArticles(currentNode);
				}
				if(TextNode.class.isInstance(currentNode)){
					TextNode textNode = (TextNode)currentNode;
					if(textNode != null && textNode.text() != null  && !textNode.text().trim().equals("")){
						Element parentElement = this.getAElement((Element)textNode.parentNode());
						if(parentElement == null){
							continue;
						}
						if(parentElement.tagName().equals("a")){
							String href = parentElement.attr("href");
							articles.put(textNode.text(),href );
						}
					}
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 向上递归寻找a标签
	 * 找到a标签存在result中，在层层返回result
	 * */
	private Element getAElement(Element childElement){
		Element result = null;
		if(!childElement.tagName().equals("a")){
			Node parentNode = childElement.parentNode();
			if(Element.class.isInstance(parentNode)){
				result = this.getAElement((Element)parentNode);
			}
		}else{
			result = childElement;
		}
		return result;
	}
	
	public static void main(String[] args) {
		ReaderService rs = new ReaderServiceImpl();
		rs.saveTodayHtml("http://news.163.com/", "js_top_news", "endText",  null, null,null);
	}
}
