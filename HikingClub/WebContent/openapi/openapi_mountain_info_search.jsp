<%@page import="hikingclub.model1.mybatis.model.Mountain"%>
<%@page
	import="hikingclub.model1.mybatis.service.impl.MountainServiceImpl"%>
<%@page import="hikingclub.model1.mybatis.service.MountainService"%>
<%@page import="retrofit2.Call"%>
<%@page
	import="hikingclub.model1.retrofit.service.MountainInfoSearchService"%>
<%@page import="hikingclub.model1.retrofit.model.MountainInfo"%>
<%@page import="hikingclub.model1.mybatis.MyBatisConnectionFactory"%>
<%@page import="hikingclub.model1.helper.WebHelper"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.ibatis.session.SqlSession"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%
	// 파라미터를 수신하고 페이지 이동을 수행하기 위한
	WebHelper webHelper = WebHelper.getInstance(request, response);

	SqlSession sqlSession = MyBatisConnectionFactory.getSqlSession();

	// SQL 처리를 요청하기 위한 Service
	MountainService mountainService = new MountainServiceImpl(sqlSession);

	String searchWrd = "북한산";

	// 저장할 Beans 객체 선언
	MountainInfo mountainInfo = null;

	// Data.go.kr을 통해서 결과를 수신
	MountainInfoSearchService mountainInfoSearchService = MountainInfoSearchService.retrofit
			.create(MountainInfoSearchService.class);
	Call<MountainInfo> call = mountainInfoSearchService.getMountainInfo(searchWrd);
	mountainInfo = call.execute().body();

	List<MountainInfo.Response.Body.Items.Item> list = mountainInfo.response.body.items.item;

	if (list != null && list.size() > 0) {

		for (int i = 0, len = list.size(); i < len; i++) {

			MountainInfo.Response.Body.Items.Item item = list.get(i);

			Mountain input = new Mountain();

			input.setMntiAdd(item.mntiAdd);
			input.setMntiAdmin(item.mntiAdmin);
			input.setMntiAdminNum(item.mntiAdminNum);
			input.setMntiDetails(item.mntiDetails);
			input.setMntiListNo(item.mntiListNo);
			input.setMntiName(item.mntiName);

			mountainService.addMountain(input);
		}
	}
	
	sqlSession.close();
%>