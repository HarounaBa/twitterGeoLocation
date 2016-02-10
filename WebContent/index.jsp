<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="org.harouna.model.HashTags"%>
<%@page import="java.util.*"%>
<%@page import="twitter4j.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="https://maps.googleapis.com/maps/api/js"></script>

<%
	HashTags hashTag = new HashTags();
	List<GeoLocation> listGeo = new ArrayList<GeoLocation>();
	listGeo = hashTag.getGeoLocation("#SuperBowl");
	//List<Double> listLat = hashTag.getLat(listGeo);
	//List<Double> listLong = hashTag.getLong(listGeo);
	%>
<script>
var map;



function initialize() {
  var mapOptions = {
    zoom: 2,
    center: new google.maps.LatLng(0, 0),
  };
  
  map = new google.maps.Map(document.getElementById('map-canvas'),
      mapOptions);
  
  <%for(int i = 0; i <listGeo.size(); i ++ ){%>
	  var marker = new google.maps.Marker({
		    position: new google.maps.LatLng(<%=listGeo.get(i).getLatitude()%>,<%=listGeo.get(i).getLongitude()%>),
		    title:"BONJOUR!"
		});
	  marker.setMap(map);
 <% }%>

	
}

google.maps.event.addDomListener(window, 'load', initialize);

</script>

<!-- 
<script >
function initMap() {
	  var myLatLng = {lat: -25.363, lng: 131.044};

	  // Create a map object and specify the DOM element for display.
	  var map = new google.maps.Map(document.getElementById('map-canvas'), {
	    center: myLatLng,
	    scrollwheel: false,
	    zoom: 4
	  });

	  // Create a marker and set its position.
	  var marker = new google.maps.Marker({
	    map: map,
	    position: myLatLng,
	    title: 'Hello World!'
	  });
	}
</script> -->
</head>
<body>
	<div id="map-canvas" style="height: 300px; width: 500px"></div>

</body>
</html>