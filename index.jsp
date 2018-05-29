<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<title>Web Crawler</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />
<meta name="author" content="http://bootstraptaste.com" />
<!-- css -->
<link href="css/bootstrap.min.css" rel="stylesheet" />
<link href="css/fancybox/jquery.fancybox.css" rel="stylesheet">
<link href="css/jcarousel.css" rel="stylesheet" />
<link href="css/flexslider.css" rel="stylesheet" />
<link href="css/style.css" rel="stylesheet" />


<!-- Theme skin -->
<link href="skins/default.css" rel="stylesheet" />

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

</head>
<body>
<div id="wrapper">
	<!-- start header -->
	<header>
        <div class="navbar navbar-default navbar-static-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#"><span>Web</span> Crawler</a>
                </div>
                <div class="navbar-collapse collapse ">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="#">Home</a></li>
                        <!-- <li class="dropdown">
                            <a href="#" class="dropdown-toggle " data-toggle="dropdown" data-hover="dropdown" data-delay="0" data-close-others="false">Features <b class=" icon-angle-down"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="typography.html">Typography</a></li>
                                <li><a href="components.html">Components</a></li>
								<li><a href="pricingbox.html">Pricing box</a></li>
                            </ul>
                        </li>
                        <li><a href="portfolio.html">Portfolio</a></li>
                        <li><a href="blog.html">Blog</a></li>
                        <li><a href="contact.html">Contact</a></li> -->
                    </ul>
                </div>
            </div>
        </div>
	</header>
	<!-- end header -->
	<section id="featured">
	<!-- start slider -->
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
	<!-- Slider -->
        <div id="main-slider" class="flexslider">
            <ul class="slides">
              <li>
                <img src="img/slides/1.jpg" alt="" />
               
              </li>
              <li>
                <img src="img/slides/2.jpg" alt="" />
                
              </li>
              <li>
                <img src="img/slides/3.jpg" alt="" />
                
              </li>
            </ul>
        </div>
	<!-- end slider -->
			</div>
		</div>
	</div>	
	
	

	</section>
	<section class="callaction">
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<div class="big-cta">
					<div class="cta-text">
						<h2><span>Web</span> Content Extraction</h2>
					</div>
				</div>
			</div>
		</div>
	</div>
	</section>
	<section id="content">
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<h4>Enter Website URL Here</strong></h4>
				<!-- <form id="contactform" action="StartCrawler" method="post" class="validateform" name="send-contact"> -->
					<div id="contactform" class="validateform">
					<div class="row">
						<div class="col-lg-4 field">
							<input type="text" name="url" placeholder="Enter URL" id="url" data-msg="Enter URL" required/>
							<div class="validation">
							</div>
						</div>
						
						<div class="col-lg-12 margintop10 field">
							
							<p>
								<button class="btn btn-theme margintop10 pull-left" id="crawl" type="button" onclick="return callCrawler();">Start Crawling</button>
								<button class="btn btn-theme margintop10" style="display:none;" id="displayData" type="button" onclick="return checkRelated();">Show Data</button> 
								
							</p>
						</div>
					</div>
					</div>
				<!-- </form> -->
				<div id="nextdiv">
				
				</div>
			</div>
		</div>
	</div>
	</section>
	<footer>
	<div class="container">
		<div class="row">
			<div class="col-lg-3">
				
			</div>
			<div class="col-lg-3">
				
			</div>
			<div class="col-lg-3">
				
			</div>
			<div class="col-lg-3">
				
			</div>
		</div>
	</div>
	<div id="sub-footer">
		<div class="container">
			<div class="row">
				<div class="col-lg-6">
					<div class="copyright">
						<p>
							<span>&copy; Web Crawler 2017 All right reserved.</span>
						</p>
					</div>
				</div>
				
			</div>
		</div>
	</div>
	</footer>
</div>
<a href="#" class="scrollup"><i class="fa fa-angle-up active"></i></a>
<!-- javascript
    ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="js/jquery.js"></script>
<script src="js/jquery.easing.1.3.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.fancybox.pack.js"></script>
<script src="js/jquery.fancybox-media.js"></script>
<script src="js/google-code-prettify/prettify.js"></script>
<script src="js/portfolio/jquery.quicksand.js"></script>
<script src="js/portfolio/setting.js"></script>
<script src="js/jquery.flexslider.js"></script>
<script src="js/animate.js"></script>
<script src="js/custom.js"></script>
<script type="text/javascript">
function callCrawler()
	{
	var u = document.getElementById("url").value;
	
		$.ajax({
		  type: 'post',
		  url: 'StartCrawler',
		  data: {
		  	url : u
		  },
		  success: function (response) {
			  var res = response.substring(22, response.length);
			  console.log(response)
			  $('#nextdiv').html(res);
			  $('#crawl').hide();
			  $("#displayData").show();
		  }
		 });		
	}
	
	function checkRelated(){
		$.ajax({
			  type: 'post',
			  url: 'CheckRelatedContent',
			  data: {
			  	
			  },
			  success: function (response) {
				 window.location.href = "showdata.jsp";
			  }
			 });	
	}
</script>


</body>
</html>