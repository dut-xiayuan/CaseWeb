package com.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Service.NewsService;
import com.alibaba.fastjson.JSONArray;
import com.pojo.News;

@Controller
@RequestMapping("")
public class NewsPageController {
	@Autowired
	@Qualifier("NewsPageServiceImpl")
	NewsService newsService;
	
	@RequestMapping("newsPageServlet")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		String newscountry=request.getParameter("newscountry");
		String currentpage = request.getParameter("pageNumber");
		String pageSize = request.getParameter("pageSize");
		int currentPage = Integer.valueOf(currentpage);
		int count = Integer.valueOf(pageSize);
		String country=newscountry.substring(4);
		
		List<News> listNews = newsService.showNews(country,currentPage,count);
		JSONObject data = new JSONObject();
		JSONArray dataList = new JSONArray();
		for(Iterator<News> i = listNews.iterator();i.hasNext();)
			{
			    News nn = i.next();   
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("id",nn.getNews_id());
			jsonobj.put("title",nn.getNews_title());
			dataList.add(jsonobj);
			}
		
		data.put("pageNo", String.valueOf(currentPage));
		data.put("pages", pageSize);
		data.put("dataList", dataList);
		
		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		out.write(data.toString());
		}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception{
		doGet(request,response);
	}
	}