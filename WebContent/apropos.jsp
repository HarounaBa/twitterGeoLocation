<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="Public/img/markerIcone.ico">

<title>GeolocationTwitter</title>
<%@ include file="Partials/_brother.jsp"%>
<link href="carousel.css" rel="stylesheet">
</head>


<!-- NAVBAR
================================================== -->
<%@ include file="Partials/_menu.jsp"%>



    <h1> A propos de nous ...</h1>
    <hr></hr>

<!-- Marketing messaging and featurettes
    ================================================== -->
<!-- Wrap the rest of the page in another container to center all the content. -->
<div class="container marketing">

<p class="lead" style="font-family: Times; font-size: 30px; margin-top :15%;" >
		Bonjour à toi Tweetos ;) 
		<img
			src="Public/img/tweetos.PNG"
			style="position: relative; top: 15px; right: 400px; width: 80px;"> </br> </br> Nous avons le plaisir de t'accueillir sur Geohashtag. </br>

    Nous sommes deux étudiants actuellement en troisieme année d'école d'ingénieur en formation 
    Télecoms & Réseaux à Sup Galilee.

   Dans le cadre de notre formation nous avons la chance de réaliser un projet qui 
   dans notre cas est orienté vers les services REST pour analyser  la propagation géographique des # Tweeter. 
   </br> </br>
    <a href="GeoHashtag.jsp">
			<button class="btn btn-lg btn-primary btn-block" type="submit">
				Bonne visite #</button>
		</a>

	</p>

<!-- Bootstrap core JavaScript
    ================================================== -->
<%@ include file="Partials/_javascripts.jsp"%>

</body>

	<!-- FOOTER -->
	<%@ include file="Partials/_footer.jsp"%>
</div>
<!-- /.container -->


</html>
