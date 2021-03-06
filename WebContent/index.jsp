<!DOCTYPE html>
<%@page import="com.vcs.util.Constants"%>
<html lang="en">

<head>
<meta charset="utf-8" />
<link rel="apple-touch-icon" sizes="76x76"
	href="assets/img/apple-icon.png">
<link rel="icon" type="image/png" href="assets/img/favicon.png">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title><%=Constants.PROJECT_NAME %> by <%=Constants.COMPANY_SHORT_NAME %></title>
<meta
	content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no'
	name='viewport' />
<!--     Fonts and icons     -->
<link
	href="https://fonts.googleapis.com/css?family=Montserrat:400,700,200"
	rel="stylesheet" />
<link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css"
	rel="stylesheet">
<!-- CSS Files -->
<link href="assets/css/bootstrap.min.css" rel="stylesheet" />
<link href="assets/css/now-ui-dashboard.css?v=1.2.0" rel="stylesheet" />
<!-- CSS Just for demo purpose, don't include it in your project -->
<link href="assets/demo/demo.css" rel="stylesheet" />
</head>

<body class="">
	<div class="wrapper ">
		<div class="sidebar" data-color="blue">
			<!--
        Tip 1: You can change the color of the sidebar using: data-color="blue | green | orange | red | yellow"
    -->
			<div class="logo">
				<a href="#" class="simple-text logo-normal"> <%=Constants.PROJECT_SHORT_NAME %> </a>
			</div>
			<div class="sidebar-wrapper">
				<ul class="nav">
					<li class="active" ><a href="index.jsp" > <i
							class="now-ui-icons business_bank"></i>
							<p>Home</p>
					</a></li>
					<li><a href="register.jsp"> <i
							class="now-ui-icons users_single-02"></i>
							<p>Register</p>
					</a></li>
					<li><a href="login.jsp"> <i
							class="now-ui-icons media-1_button-play"></i>
							<p>Login</p>
					</a></li>


					<li class="active-pro"><a href="#"> <i
							class="now-ui-icons arrows-1_cloud-download-93"></i>
							<p>Developed by <%=Constants.COMPANY_SHORT_NAME %></p>
					</a></li>
				</ul>
			</div>
		</div>
		<div class="main-panel">
			<!-- Navbar -->
			<nav
				class="navbar navbar-expand-lg fixed-top navbar-transparent  bg-primary  navbar-absolute">
				<div class="container-fluid">
					<div class="navbar-wrapper">
						<div class="navbar-toggle">
							<button type="button" class="navbar-toggler">
								<span class="navbar-toggler-bar bar1"></span> <span
									class="navbar-toggler-bar bar2"></span> <span
									class="navbar-toggler-bar bar3"></span>
							</button>
						</div>
						<a class="navbar-brand" href="#"><%=Constants.PROJECT_NAME %></a>
					</div>
					<button class="navbar-toggler" type="button" data-toggle="collapse"
						data-target="#navigation" aria-controls="navigation-index"
						aria-expanded="false" aria-label="Toggle navigation">
						<span class="navbar-toggler-bar navbar-kebab"></span> <span
							class="navbar-toggler-bar navbar-kebab"></span> <span
							class="navbar-toggler-bar navbar-kebab"></span>
					</button>
					<div class="collapse navbar-collapse justify-content-end"
						id="navigation">
						<ul class="navbar-nav">

<!-- 
							<li class="nav-item dropdown"><a
								class="nav-link dropdown-toggle" href="http://example.com"
								id="navbarDropdownMenuLink" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false"> <i
									class="now-ui-icons users_single-02"></i>
									<p>
										<span class="d-md-block">Ashok Kumar</span>
									</p>
							</a>
								<div class="dropdown-menu dropdown-menu-right"
									aria-labelledby="navbarDropdownMenuLink">
									<a class="dropdown-item" href="updateprofile.jsp">Edit
										Profile</a> <a class="dropdown-item" href="changepassword.jsp">Change
										Password</a> <a class="dropdown-item"
										href="account?request_type=deleteprofile">Delete Profile</a> <a
										class="dropdown-item" href="account?request_type=logout">Logout</a>
								</div></li>
 -->
						</ul>
					</div>
				</div>
			</nav>
			<!-- End Navbar -->
			<div class="panel-header panel-header-sm"></div>
			<div class="content">
				<div class="row" style='min-height: 600px;'>

					<div class="col-md-12">
						<div class="card">
							<div class="card-header">
								<h5 class="title">Home Page</h5>
								<p class="category">Welcome to the <%=Constants.PROJECT_NAME %> (<%=Constants.PROJECT_SHORT_NAME %>)</p>
							</div>
							<div class="card-body">Welcome</div>
							
							
						</div>
					</div>
				</div>

				<footer class="footer">
					<div class="container">

						<div class="copyright" id="copyright">
							Version Control System &copy;
							<script>
								document
										.getElementById('copyright')
										.appendChild(
												document
														.createTextNode(new Date()
																.getFullYear()))
							</script>
							, Deeloped by <%=Constants.COMPANY_NAME %> (<%=Constants.COMPANY_SHORT_NAME %>).
						</div>
					</div>
				</footer>
			</div>
		</div>
	</div>
	<!--   Core JS Files   -->
	<script src="assets/js/core/jquery.min.js"></script>
	<script src="assets/js/core/popper.min.js"></script>
	<script src="assets/js/core/bootstrap.min.js"></script>
	<script src="assets/js/plugins/perfect-scrollbar.jquery.min.js"></script>
	<!--  Google Maps Plugin    -->
	<script src="https://maps.googleapis.com/maps/api/js?key=YOUR_KEY_HERE"></script>
	<!-- Chart JS -->
	<script src="assets/js/plugins/chartjs.min.js"></script>
	<!--  Notifications Plugin    -->
	<script src="assets/js/plugins/bootstrap-notify.js"></script>
	<!-- Control Center for Now Ui Dashboard: parallax effects, scripts for the example pages etc -->
	<script src="assets/js/now-ui-dashboard.min.js?v=1.2.0"
		type="text/javascript"></script>
	<!-- Now Ui Dashboard DEMO methods, don't include it in your project! -->
	<script src="assets/demo/demo.js"></script>
	<script>
		$(document).ready(function() {
			// Javascript method's body can be found in assets/js/demos.js
			demo.initDashboardPageCharts();

		});
	</script>
</body>

</html>
