<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${article.title}</title>
	<script type="text/javascript" src=".\js\jquery-3.1.0.js"></script>
	<script type="text/javascript" src=".\js\jquery-ui\jquery-ui.js"></script>
	<link href=".\js\jquery-ui\jquery-ui.css" rel="stylesheet">
	<script type="text/javascript">
	
	$(document).ready(function(){
		var font= $("#myWrapper").css("font-size");
		var fontSize = parseInt(font.substring(0,font.length-2));
		var maxSize = 30;
		var minSize = 9;
		$("#increaseFontSize").click(function(){
			if(fontSize < maxSize){
				fontSize+=1;
				$("#myWrapper").css("font-size",fontSize);
			}
		});
		$("#reduceFontSize").click(function(){
			if(fontSize > minSize){
				fontSize-=1;
				$("#myWrapper").css("font-size",fontSize);
			}
		});	
			
		});
	</script>
	<style type="text/css">
		#myWrapper{
			margin:0px 30px;
			font-size:18px;
		}
		
		#changeFontSizeDiv{
			float:right;
			margin-right:30px;
		}
	</style>
</head>
<body>
<div id="changeFontSizeDiv">
	<span style="font-size:20px;">A<span style="font-size:10px;">A</span></span>
	<button id="increaseFontSize" class="ui-button ui-corner-all ui-widget">+</button>
	<button id="reduceFontSize" class="ui-button ui-corner-all ui-widget">-</button>
</div>
	<h3>${article.title }</h3>
	<h6>${article.date }</h6>
	<div id="myWrapper">${article.content }</div>
</body>
</html>