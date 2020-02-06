<%@page import="com.vcs.pojo.Sharing"%>
<%@page import="java.util.List"%>
<%@page import="com.vcs.pojo.User"%>
<%
	User u1 = (User) session.getAttribute("user");
	if (u1 == null) {
		response.sendRedirect("login.jsp?msg=Session expired. Login again");
	} else {
%>

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
					<li  ><a href="welcome.jsp" > <i
							class="now-ui-icons business_bank"></i>
							<p>Welcome</p>
					</a></li>

					<li ><a href="path.jsp" > <i  
							class="fa fa-folder-open"></i>
							<p>Data Path</p>
					</a></li>

					<li ><a href="createfile.jsp" > <i
							class="fa fa-plus-square"></i>
							<p>Create File</p>
					</a></li>

					<li id='manageMenu' ><a onclick='hideMenu();' href="manage?requestType=get" > <i
							class="fa fa-cog"></i>
							<p>Manage Files</p>
					</a></li>

					<li style='display: none;' id='loadingMenu' ><a > <i
							class="fa fa-cog"></i>
							<p><img src='loading.gif' width=40/></p>
					</a></li>

					<li class="active"><a href="shared?requestType=get" > <i
							class="fa fa-share-square"></i>
							<p>Shared Files</p>
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


							<li class="nav-item dropdown"><a
								class="nav-link dropdown-toggle" href="http://example.com"
								id="navbarDropdownMenuLink" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false"> <i
									class="now-ui-icons users_single-02"></i>
									<p>
										<span class="d-md-block"><%=u1.getFname() %> <%=u1.getLname() %></span>
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
								<h5 class="title">Shared Files</h5>
								<p class="category">Here, you can collaborate on the files other users have shared with you</p>
							</div>
							<div class="card-body">
							
								<div class='col-md-12'>
									<%
										String msg = request.getParameter("msg");
									%>
									<%
										if (msg != null)
										{
									%>
									<div class="alert alert-success alert-dismissable">
										<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
										<strong>Message!</strong>
										<%=msg%>.
									</div>
									<%
										}
									%>
									
									<br/>
									<%
										List<Sharing> shared_files= (List<Sharing>) request.getAttribute("files");
										if (shared_files == null || shared_files.size() == 0)
										{
											%>
												<h6>You don't have rights to collaborate on any files at this moment</h6>
											<%
										}
										else
										{
											%>
										   			<div class='row'>
										   			<%
										   				int i=0;
										   				for (Sharing s: shared_files)
										   				{
										   					i++;
										   					%>
 											   				   		<div class='col-md-2' style='text-align: center; border:solid lightgray 1px; margin:10px; padding:20px;'>
											   				   			<a id='openshared<%=i%>' onClick='hideLoading(<%=i %>);' href='shared?requestType=details&path=<%=s.getPath() %>&file=<%=s.getFilename()%>'><i style='font-size: 78px;' class='fa fa-file'></i></a> 
											   				   			<img src='loading.gif' width=50 style='display: none;' id='loadingA<%=i %>' />
											   				   			
											   				   			<hr/>
											   				   			<%=s.getFilename().substring(17) %>
											   				   			<hr/>
											   				   			Access Level: <br/><b><%=s.getAccesslevel() %></b>
											   				   			<hr/>
																		Data Owner: <br/><b><%=s.getOwner() %></b>
											   				   		</div>
 										   					
										   					<%
										   				}
										   			%>
											<%
										}
									%>
									
								</div>
							</div>
							
							
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
	function hideMenu()
	{
		$('#manageMenu').hide();
		$('#loadingMenu').show();
	}
	
	function hideLoading(i)
	{
		$('#openshared'+i).hide();
		$('#loadingA'+i).show();

	}
	
	
		$(document).ready(function() {
			// Javascript method's body can be found in assets/js/demos.js
			demo.initDashboardPageCharts();

		});
	</script>
</body>

</html>

<% } %>
