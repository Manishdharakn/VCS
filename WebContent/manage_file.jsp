<%@page import="com.vcs.daoimpl.UserDAOImpl"%>
<%@page import="com.vcs.dao.UserDAO"%>
<%@page import="com.vcs.pojo.Sharing"%>
<%@page import="java.util.List"%>
<%@page import="com.vcs.daoimpl.SharingDAOImpl"%>
<%@page import="com.vcs.dao.SharingDAO"%>
<%@page import="com.vcs.service.ChecksumService"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Map"%>
<%@page import="com.vcs.core.VersionControlConstants"%>
<%@page import="com.vcs.daoimpl.ChecksumDAOImpl"%>
<%@page import="com.vcs.core.VersionControlResponse"%>
<%@page import="com.vcs.dao.ChecksumDAO"%>
<%@page import="com.vcs.core.VersionControlWorker"%>
<%@page import="com.vcs.pojo.Path"%>
<%@page import="com.vcs.daoimpl.PathDAOImpl"%>
<%@page import="com.vcs.dao.PathDAO"%>
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
		<div class="sidebar" data-color="orange">
			<!--
        Tip 1: You can change the color of the sidebar using: data-color="blue | green | orange | red | yellow"
    -->
			<div class="logo">
				<a href="#" class="simple-text logo-normal"> <%=Constants.PROJECT_SHORT_NAME %> </a>
			</div>
			<div class="sidebar-wrapper">
				<ul class="nav">
					<li ><a href="welcome.jsp" > <i
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

					<li class='active' id='manageMenu' ><a onclick='hideMenu();' href="manage?requestType=get" > <i
							class="fa fa-cog"></i>
							<p>Manage Files</p>
					</a></li>

					<li style='display: none;' id='loadingMenu' ><a > <i
							class="fa fa-cog"></i>
							<p><img src='loading.gif' width=40/></p>
					</a></li>

					<li ><a href="shared?requestType=get" > <i
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

<%
String file = (String) request.getAttribute("file");
Map<String, String> files = (Map<String, String>) session.getAttribute("files");
String currentFileContents = files.get(file);
%>
					<div class="col-md-12">
						<div class="card">
							<div class="card-header">
								<h5 class="title">Manage <%=file.substring(17) %></h5>
								<p class="category">Here, you can manage your file <%=file.substring(17) %></p>
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
									<br/>
									
									<%
										}
									%>
									
									<a onclick='goback();' id='goback' href='manage?requestType=get'><i class='fa fa-chevron-left'></i> Go Back to Manage Files</a>
									<img style='display: none;' id='loading1' src='loading.gif' width=100 />														

										   			<%
											            VersionControlWorker vcWorker = new VersionControlWorker();
														ChecksumDAO cDao = new ChecksumDAOImpl();
														VersionControlResponse respo = vcWorker.run(new File(file), VersionControlConstants.MODE_GET_ALL_VERSIONS);
														Map<Integer, String> version = respo.getVersions();
										   			%>

									<hr/>
									<br/>
									<%
									 	boolean checksumValid = ChecksumService.verifyChecksum(currentFileContents, cDao.getChecksumForFile(file.substring(0,16)));
										if (!checksumValid)
										{
											%>
												<div class="alert alert-danger" role="alert">
												This File has been corrupted. The Checksum validation have failed. <a id='fixthis' href='manage?requestType=fix&file=<%=file%>' class='btn btn-default' >Fix This</a>
												<img src='loading.gif' width=50 id='loading3' style='display: none;'/>
												</div>

												<br/>
											<%
										}
									%>
									
									Current Version: <b><%=respo.getTotalVersions() %></b>
									<hr/>
									<br/>
									<form action='file' method=post>
										<input type=hidden name='requestType' value='update' />
										<input type=hidden name='file' value='<%=file%>' />
										<textarea style='width:100%; height:300px;' name='contents'><%=currentFileContents%></textarea>
										<br/>
										<input type=submit value='Update' class='btn btn-primary' id='updatebtn' />
										<img src='loading.gif' width=100 id='loading2' style='display: none;' />
									</form>

<br/><br/>
										<hr/>				
									<a href='#' data-toggle="modal" data-target="#deletefile"><i class='fa fa-trash'></i> Delete File</a>														
											<!-- Modal -->
											<div class="modal fade bs-example-modal-lg" id="deletefile" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
											  <div class="modal-dialog modal-lg" role="document">
											    <div class="modal-content">
											      <div class="modal-header">
											        <h4 class="modal-title" id="myModalLabel">Confirm Delete</h4>
											      </div>
											      <div class="modal-body">
											        Are you sure to delete this file. All the version information of this file will be lost. This operation cannot be undone.
											      </div>
											      <form action='file'>
											      		<input type=hidden name='requestType' value='delete' />
											      		<input type=hidden name='file' value='<%=file %>' />
												      <div class="modal-footer">
												        <button type="button" class="btn btn-default" id='cancelThis' data-dismiss="modal">Cancel</button>
												        <button type="submit" class="btn btn-primary" id='deleteThis' >Delete</button>
												        <img src='loading.gif' width=100 style='display: none;' id='loading4' />
												      </div>
											      </form>
											    </div>
											  </div>
											</div>
											
											
											<br/>
											<hr/>
									<%
										if (respo.getTotalVersions()>1)
										{
									%>
											Previous Versions:
											<br/><br/>
											
											<%
												for (int i=1;i<=respo.getTotalVersions();i++)
												{
											%>
												<a href='#' data-toggle="modal" data-target="#version<%=i%>" style='background-color: orange; color: white; padding:18px; font-size:20px;'><%=i %></a>
												
													<!-- Modal -->
													<div class="modal fade bs-example-modal-lg" id="version<%=i %>" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
													  <div class="modal-dialog modal-lg" role="document">
													    <div class="modal-content">
													      <div class="modal-header">
													        <h4 class="modal-title" id="myModalLabel"><%=file.substring(17) %> - Version <%=i %></h4>
													      </div>
													      <div class="modal-body">
													        <textarea style='width:100%; height:500px;' readonly="readonly"><%=respo.getVersions().get(i) %></textarea>
													      </div>
													      <form action='file'>
													      		<input type=hidden name='requestType' value='update' />
													      		<input type=hidden name='contents' value='<%=respo.getVersions().get(i) %>' />
													      		<input type=hidden name='file' value='<%=file %>' />
														      <div class="modal-footer">
														        <button type="submit" onClick='restoreHide(<%=i %>);' class="btn btn-primary" id='restore<%=i %>' >Restore this Version</button>
														        <img src='loading.gif' width=100 style='display:none;' id='loading5<%=i %>' />
														      </div>
													      </form>
													    </div>
													  </div>
													</div>
												
												
																							
											<%												   
												}
											%>
											
									
									<% } %> 
											<br/><br/>
											<hr/>
											
											<h5>File Collaborators</h5>
											<%
												UserDAO uDao = new UserDAOImpl();
												SharingDAO sDao = new SharingDAOImpl();
												List<String> collabs = sDao.getCollaboratorsEmail(file);
												List<String> users = uDao.getAllEmails();
												users.remove(u1.getEmail());
												users.removeAll(collabs);
											%>
											<br/>
											<a href='#' data-toggle="modal" data-target="#addC" ><i class='fa fa-plus'></i> Add Collaborators</a>
											<br/><br/>
													<!-- Modal -->
													<div class="modal fade bs-example-modal-lg" id="addC" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
													  <div class="modal-dialog modal-lg" role="document">
													    <div class="modal-content">
													      <div class="modal-header">
													        <h4 class="modal-title" id="myModalLabel">Add Collaborators for <%=file.substring(17) %></h4>
													      </div>
													      <form action='sharing'>
													      		<input type=hidden name='requestType' value='share' />
													      		<input type=hidden name='file' value='<%=file %>' />

													      <div class="modal-body">
																	<label>Email</label>
																	<select required="required" name='email' class='form-control'>
																		<%
																			for (String e: users)
																			{
																				%>
																					<option value='<%=e %>'><%=e %></option>
																				<%
																			}
																		%>
																	</select>
																	
																	<br/>
																	<label>Access Type</label>
																	<select name='access' class='form-control'>
																		<option value='READ ONLY'>READ ONLY ACCESS</option>
																		<option value='READ-WRITE'>READ-WRITE Access</option>
																	</select>
													      </div>
														      <div class="modal-footer">
												        		<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>														      
														        <button type="submit" class="btn btn-primary">Add Collaborator</button>
														      </div>
													      </form>
													    </div>
													  </div>
													</div>
											
											<%
												List<Sharing> collaborators = sDao.getCollaborators(file);
												if (collaborators != null && collaborators.size() > 0)
												{
													%>
														
														<table class='table'>
															<tr>
																<th> Collaborator Email ID</th>
																<th> Access Level</th>
																<th> Change Access Level </th>
																<th> Remove</th>
															</tr>			
															<%
																for (Sharing s: collaborators)
																{
																	%>
																		<tr>
																			<td> <i class='fa fa-user'></i> <%=s.getEmail() %></td>
																			<td> <%=s.getAccesslevel() %> Access</td>
																			<td> 
																				<form action='sharing'>
																					<input type=hidden name='requestType' value='changeaccess' />
																					<input type=hidden name='email' value='<%=s.getEmail() %>' />
																					<input type=hidden name='filename' value='<%=s.getFilename() %>' />
																					<%
																						if (s.getAccesslevel().equals("READ ONLY"))
																						{
																							%>
																								<select name='access'>
																									<option value='READ ONLY' >READ ONLY</option>
																									<option value='READ-WRITE' >READ-WRITE</option>
																								</select>	
																							<%
																						}
																						else if (s.getAccesslevel().equals("READ-WRITE"))
																						{
																							%>
																								<select name='access'>
																									<option value='READ-WRITE' >READ-WRITE</option>
																									<option value='READ ONLY' >READ ONLY</option>
																								</select>	
																							
																							<%
																						}
																					%>
																					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																					<input type=submit value='Change Access' class='btn btn-primary btn-xs' />
																				</form>																			
																			</td>
																			
																			<td>
																				<a href='sharing?requestType=remove&filename=<%=s.getFilename() %>&email=<%=s.getEmail()%>'><i class='fa fa-trash'></i> Delete Access</a>
																			</td>
																		</tr>
																	<%
																}
															%>											
														</table>
														
													<%													
												}
												else
												{
													%>
														No Collaborators found for this file.
													<%
												}
											%>
											<br/><br/><br/><br/><br/>
									
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
	
	$('#updatebtn').click(function(){
		$('#updatebtn').hide();
		$('#loading2').show();
	});

	$('#fixthis').click(function(){
		$('#fixthis').hide();
		$('#loading3').show();
	});

	$('#deleteThis').click(function(){
		$('#deleteThis').hide();
		$('#cancelThis').hide();
		$('#loading4').show();
	});

	function restoreHide(i)
	{
		$('#restore'+i).hide();		
		$('#loading5'+i).show();
	}
	
	function goback()
	{
		$('#goback').hide();
		$('#loading1').show();

	}

	function hideMenu()
	{
		$('#manageMenu').hide();
		$('#loadingMenu').show();
	}

		$(document).ready(function() {
			// Javascript method's body can be found in assets/js/demos.js
			demo.initDashboardPageCharts();
			
		});
	</script>
</body>

</html>

<% } %>