<%-- 
    Document   : postArticle
    Created on : Sep 22, 2020, 1:43:15 PM
    Author     : Ruby
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="style.css">
        <title>Post Article Page</title>
    </head>
    <body>
        <c:set var="user" value="${ sessionScope.USER }" />
        <c:if test="${ empty user }">
            <c:redirect url="404.html"></c:redirect>
        </c:if>
        <div id="page" class="page">
          <div clas="row">
            <div id="sidebar" class="sidebar col col-3 shadow-sm row">
              <header id="masthead" class="col col-12 site-header" role="banner">
                <div class="site-branding">
                  <p class="site-title">
                    <a href="" rel="home">Social Network</a>
                  </p>
                  <p class="site-description">The mini social network releases in 2020</p>
		</div><!-- .site-branding -->
                </header>
              <div id="secondary" class="col col-12">
                <ul id="menu-demo-menu" class="nav-menu">
                  <li><a href="DispatchServlet?btAction=Search">Search Article</a></li>
                  <li><a href="DispatchServlet?btAction=Post Article">Post Article</a></li>
                  <li><a href="DispatchServlet?btAction=Show Notification">Notification
                        <c:set var="countNotification" value="${ requestScope.NEW_NOTIFICATION }" />
                        <c:if test="${ not empty countNotification and countNotification > 0 }">
                            (${ countNotification })
                        </c:if>
                      </a></li>
                  <li><a href="DispatchServlet?btAction=Logout">Logout</a></li>
                </ul>
              </div>
            </div>
            <div id="content" class="site-content col col-9">
              <div class="content-header">
		<h4>Welcome, ${ user.name }!</h4>
              </div>
              
              <div class="content-part shadow-sm">
                <h1 class="mb-5">Post Article</h1>
                <c:set var="error" value="${ requestScope.POST_ARTICLE_ERROR }" />
                <form action="DispatchServlet" method="POST" enctype="multipart/form-data">
                  <div class="form-group">
                    <label for="articleTitle">Article Title (1 - 100 chars)</label>
                    <input type="text" class="form-control" id="articleTitle" 
                           name="txtTitle" required value="${ param.txtTitle }"/>
                    <c:if test="${ not empty error.titleLengthErr }">
                        <small id="postArticleError" class="form-text text-danger">${ error.titleLengthErr }</small>
                    </c:if>
                  </div>

                  <div class="form-group">
                    <label for="articleDescription">Article Description (1 - 500 chars)</label>
                    <textarea class="form-control" id="articleDescription" rows="3" name="txtDescription" 
                        required>${ param.txtDescription }</textarea>
                     <c:if test="${ not empty error.descriptionLengthErr }">
                        <small id="postArticleError" class="form-text text-danger">${ error.descriptionLengthErr }</small>
                    </c:if>
                  </div>

                  <div class="form-group">
                      <label for="customFile" class="mr-3">Choose Article Image</label>
                      <input type="file" id="customFile" name="imgFile" accept="image/*">
                  </div>

                  <input type="submit" class="btn btn-outline-dark" value="Post Article" name="btAction">
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

