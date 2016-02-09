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
var myLatlng =  new google.maps.LatLng(0,0);


function initialize() {
  var mapOptions = {
    zoom: 8,
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