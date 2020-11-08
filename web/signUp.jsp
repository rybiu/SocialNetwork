<%-- 
    Document   : createNewAcccount
    Created on : Jun 26, 2020, 1:16:53 PM
    Author     : Ruby
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
   <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="style.css">
        <title>Sign Up Page</title>
    </head>
    <body>
        <div id="page" class="page">
          <div class="row">
            <div id="sidebar" class="sidebar col col-3 shadow-sm row">
              <header id="masthead" class="col col-12 site-header" role="banner">
                <div class="site-branding">
                  <p class="site-title">
                    <a href="#" rel="home">Social Network</a>
                  </p>
                  <p class="site-description">The mini social network releases in 2020</p>
		</div><!-- .site-branding -->
              </header>
              <div id="secondary" class="col col-12">
                <ul id="menu-demo-menu" class="nav-menu">
                  <li><a href="DispatchServlet?btAction=Login">Log In</a></li>
                  <li><a href="DispatchServlet?btAction=Sign Up">Sign Up</a></li>
                </ul>
              </div>
            </div>
            <div id="content" class="site-content col col-9">
              <div class="content-header">
		<h4>Welcome to the Social Network!</h4>
              </div>
              
              <div class="content-part shadow-sm">
                <h1 class="mb-5">Sign Up</h1>
                
                <form action="DispatchServlet" method="POST">
                    <c:set var="errors" value="${requestScope.CREATE_ERROR}" />
                    <div class="form-group">
                      <label for="txtEmail">Email Address</label>
                      <input type="email" class="form-control" id="txtEmail" name="txtEmail" value="${param.txtEmail}" required>
                      <c:if test="${not empty errors.emailFormatErr}">
                        <small id="nameError" class="form-text text-danger">${errors.emailFormatErr}</small>
                      </c:if>
                      <c:if test="${not empty errors.emailIsExisted}">
                        <small id="nameError" class="form-text text-danger">${errors.emailIsExisted}</small>
                      </c:if>
                    </div>
                    <div class="form-group">
                      <label for="txtPassword">Password (6 - 30 chars)</label>
                      <input type="password" class="form-control" id="txtPassword" name="txtPassword" value="" required>
                      <c:if test="${not empty errors.passwordLengthErr}">
                        <small id="passwordError" class="form-text text-danger">${ errors.passwordLengthErr }</small>
                      </c:if>
                    </div>
                    <div class="form-group">
                      <label for="txtConfirm">Confirm</label>
                      <input type="password" class="form-control" id="txtConfirm" name="txtConfirm" value="" required>
                      <c:if test="${not empty errors.confirmFormatErr}">
                        <small id="confirmError" class="form-text text-danger">${ errors.confirmFormatErr }</small>
                      </c:if>
                    </div>
                    <div class="form-group">
                      <label for="txtName">Full Name (2 - 50 chars)</label>
                      <input type="text" class="form-control" id="txtName" name="txtName" value="${param.txtName}" required>
                      <c:if test="${not empty errors.nameLengthErr}">
                        <small id="nameError" class="form-text text-danger">${errors.nameLengthErr}</small>
                      </c:if>
                    </div>
                    <input type="submit" class="btn btn-outline-dark" name="btAction" value="Sign Up">
                    <input type="reset" class="btn btn-outline-dark" value="Reset">
                </form>
              </div>
            </div>
          </div>
        </div>

	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </body>
</html>
