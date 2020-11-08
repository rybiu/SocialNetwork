<%-- 
    Document   : notification
    Created on : Sep 18, 2020, 11:23:36 PM
    Author     : Ruby
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="style.css">
        <title>Notification Page</title>
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
                <h1 class="mb-5">Your Notification</h1>
                
                <c:set var="notificationDetails" value="${ requestScope.NOTIFICATION }" />
                <c:if test="${ not empty notificationDetails }">
                    <c:forEach var="dto" items="${ notificationDetails }" varStatus="counter">
                        <c:url var="viewDetailLink" value="DispatchServlet">
                            <c:param name="articleId" value="${ dto.articleId }"></c:param>
                            <c:param name="btAction" value="View Detail"></c:param>
                        </c:url>
                        
                        <a href="${ viewDetailLink }" class="notification-items notification-items-new shadow-sm row">
                            <div class="col col-9">
                                <p>
                                    <c:if test="${ dto.status }">[NEW] </c:if>
                                    <b>${ dto.username }</b>
                                    <c:if test="${ dto.type eq 0 }"> has commented on your article </c:if>
                                    <c:if test="${ dto.type eq 1 }"> has liked your article </c:if>
                                    <b>${ dto.articleTitle }</b>
                                </p>
                            </div>
                            <div class="col col-auto">
                                <span><i class="fa fa-calendar mr-2" aria-hidden="true"></i>${ dto.date }</span>
                            </div>
                        </a>
                    </c:forEach>
                </c:if>
                <c:if test="${ empty notificationDetails }">
                    <h5>(^-^) Nothing new !!!</h5>
                </c:if>
              </div>
            </div>
          </div>
        </div>
                
        <script src="https://use.fontawesome.com/b35768308f.js"></script>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </body>
</html>
