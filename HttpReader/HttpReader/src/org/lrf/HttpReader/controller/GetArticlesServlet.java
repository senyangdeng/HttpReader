package org.lrf.HttpReader.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lrf.HttpReader.entity.DetailArticle;
import org.lrf.HttpReader.service.ArticlesService;
import org.lrf.HttpReader.service.impl.ArticlesServiceImpl;

@WebServlet(
	urlPatterns="/get_articles",
	initParams={
		@WebInitParam(name="pageSize",value="3")
	}
)
public class GetArticlesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static ArticlesService as = new  ArticlesServiceImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetArticlesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pageSize = new Integer(getServletConfig().getInitParameter("pageSize"));
		String currentPageString = request.getParameter("currentPage");
		int currentPage = 1;
		if(currentPageString != null && !currentPageString.equals("")){
			currentPage = new Integer(currentPageString);
		}
		Map<String, Object> result = as.getList(pageSize, currentPage);
		List<DetailArticle> das = (List<DetailArticle>)result.get("articles");
		int articleCount = (int)result.get("articleCount");
		request.setAttribute("articles", das);
		request.setAttribute("articleCount", articleCount);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageSize", pageSize);
		request.getRequestDispatcher("list.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
