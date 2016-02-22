<%@page import="org.harouna.model.HashTags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="org.harouna.model.HashTags.*"%>
<%@page import="java.util.*"%>
<%@page import="twitter4j.*"%>

<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="Public/img/markerIcone.ico">

<title>GeoHashtag</title>

<!-- Bootstrap core CSS -->
<link href="Public/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="signin.css" rel="stylesheet">

<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="../Bootstrap/assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<script src="=/Public/assets/js/ie-emulation-modes-warning.js"></script>

<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="/Public/assets/js/ie10-viewport-bug-workaround.js"></script>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<script src="https://maps.googleapis.com/maps/api/js"></script>

<%
	HashTags hashTag = new HashTags();
	List<GeoLocation> listGeo = new ArrayList<GeoLocation>();
	listGeo = hashTag.getGeoLocation(request.getParameter("hashtag"));
%>

<script>
	var map;

	function initialize() {
		var mapOptions = {
			zoom : 3,
			center : new google.maps.LatLng(0, 0),
		};

		map = new google.maps.Map(document.getElementById('map-canvas'),
				mapOptions);
	<%for (int i = 0; i < listGeo.size(); i++) {%>
		setTimeout(function() {
			var marker = new google.maps.Marker({
				position : new google.maps.LatLng(<%=listGeo.get(i).getLatitude()%>,<%=listGeo.get(i).getLongitude()%>),

				title : "hey vous etes sur un  #",

				animation : google.maps.Animation.DROP,

			});
			marker.setMap(map);
		},<%=i%>* 1000);
	<%}%>
	}

	google.maps.event.addDomListener(window, 'load', initialize);
</script>

</head>

<%@ include file="Partials/_menu.jsp"%>

<body>
	<div class="container" style="margin-top: 50px">
		<form class="form-signin" role="form" action="RegistrationServlet"
			method="post">
			<h2 class="form-signin-heading">Mettez le HashtaG #</h2>
			<input name="hashtag" type="nom" class="form-control"
				placeholder="hashtag" required autofocus>

			<button class="btn btn-lg btn-primary btn-block" type="submit">Valider</button>
		</form>
	</div>

	<div>
		<div id="map-canvas"
			style="height: 900px; width: 2090px; margin-top: 100px; border-style: dotted; border-width: 1px; border-color: blue;"></div>

	</div>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->

	<%@ include file="Partials/_javascripts.jsp"%>

	<%@ include file="Partials/_footer.jsp"%>
</body>

</html>
