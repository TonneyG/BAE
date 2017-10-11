<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href=<%=basePath %>>
<title>步行导航</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=0;">
<style type="text/css">
	body,html,#allmap{
		width:100%;
		height:100%;
		overflow:hidden;
		margin:0;
	}
</style>
</head>
<script src="http://api.map.baidu.com/api?v=2.0&ak=OGAUPI7BdQR81V2cippv8ZnALhWccIzg" type="text/javascript"></script>
<%
	String p1 = request.getParameter("p1");
	String p2 = request.getParameter("p2");
%>
<body>
	<div id="allmap"></div>
</body>
<script>
	debugger
	//创建起点和终点的经纬度坐标点
	var p1 = new BMap.Point(<%=p1%>);
	var p2 = new BMap.Point(<%=p2%>);
	//创建地图，设置中心坐标和默认缩放级别
	var map = new BMap.Map("allmap");
	map.centerAndZoom(new BMap.Point((p1.lng+p2.lng)/2,(p1.lat+p2.lat)/2),17);
	//右下角添加缩放按钮
	map.addControl(new BMap.NavigationControl({
		anchor:BMAP_ANCHOR_BOTTOM_RIGHT,
		type:BMAP_NAVIGATION_CONTROL_ZOOM
	}));
	//开启鼠标滚轮缩放
	map.enableScrollWheelZoom(true);
	//步行导航检索
	var walking = new BMap.WalkingRoute(map,{renderOptions:{map:map,autoViewport:true}});
	walking.search(p1,p2);
</script>
</html>