<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>第${currentPage}页</title>
	<script type="text/javascript" src=".\js\jquery-3.1.0.js"></script>
	<script type="text/javascript" src=".\js\jquery-ui\jquery-ui.js"></script>
	<link href=".\js\jquery-ui\jquery-ui.css" rel="stylesheet">
	<link href=".\css\bootstrap.css" rel="stylesheet">
	<style type="text/css">
		#contentDiv{
			max-width:900px;
			margin: 0px 20px;
		}
		
		#contentUl li{
		  list-style-type:none;
		}
		
		#contentUl li a{
			color:#444;
		}
		
		#contentUl li a:hover{
			color:#111;
			text-decoration: none;
		}
		
		#pageTag{
		text-align:center;
			margin: 0px 20px;
		}
		
		#logoDiv{
			position:absolute;
			right:10px;
			bottom:10px;
		}
		
		#logoDiv img{
			width:300px;
			height:80px;
		}
	</style>
	
	<script type="text/javascript">
	
		var currentPage = ${currentPage};
		var count = ${articleCount};
		var pageSize = ${pageSize};
		var maxPage,minPage = 1;
		$(document).ready(function(){
			//向上取整,有小数就整数部分加1
			maxPage = Math.ceil(count/pageSize);
			
			setPagination("pageTag");
		});
		
		
		
		function setPagination(wrapperDivId){
			var pageWrapperDiv = document.createElement("div");
			pageWrapperDiv.setAttribute("class","pagination");
			var ul =  document.createElement("ul");
			
			//add previous button
			if(currentPage != minPage){
				var li = document.createElement("li");
				var a = document.createElement("a");
				a.setAttribute("href","get_articles?currentPage="+(currentPage-1));
				a.appendChild(document.createTextNode("<<"));
				li.appendChild(a);
				ul.appendChild(li);
			}
			
			//add number button
			for(var i=0;i < maxPage;i++){
				var li = document.createElement("li");
				var a = document.createElement("a");
				a.setAttribute("href","get_articles?currentPage="+(i+1));
				a.appendChild(document.createTextNode(i+1));
				li.appendChild(a);
				if(${currentPage} == (i+1)){
					li.setAttribute("class","active");
				}
				ul.appendChild(li);
			}
			
			//add next button
			if(currentPage != maxPage){
				var li = document.createElement("li");
				var a = document.createElement("a");
				a.setAttribute("href","get_articles?currentPage="+(currentPage+1));
				a.appendChild(document.createTextNode(">>"));
				li.appendChild(a);
				ul.appendChild(li);
			}
			
			
			pageWrapperDiv.appendChild(ul);
			$("#"+wrapperDivId).html(pageWrapperDiv);
		}
		
	</script>
</head>
<body>
	<div id="contentDiv">
		<ul id="contentUl">
			<c:forEach items="${articles}" var="article">
				<li><h4><a href="article?id=${article.id}">${article.title}</a></h4></li>
			</c:forEach>
		</ul>
	</div>
	<div id="pageTag"></div>
	<div id="logoDiv"><img src="./image/text_title.png"/></div>
</body>
</html>