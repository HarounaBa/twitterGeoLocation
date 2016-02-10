<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script
	src="https://maps.googleapis.com/maps/api/js"></script>
<script>
var map;
var myLatlng =  new google.maps.LatLng(48.8534100,2.3488000);


function initialize() {
  var mapOptions = {
    zoom: 5,
    center: new google.maps.LatLng(0, 0),
  	
  };
  map = new google.maps.Map(document.getElementById('map-canvas'),
      mapOptions);
  
  var marker = new google.maps.Marker({
	    position: myLatlng,
	    title:"BONJOUR!"
	});
	marker.setMap(map);
}

google.maps.event.addDomListener(window, 'load', initialize);

</script>
</head>
<body>
	<p>
		Recuperation hashtag : 
		<%= request.getParameter("hashtag")%>
		
	</p>
	<div id="map-canvas" style="height: 300px; width: 500px"></div>
</body>
</html>