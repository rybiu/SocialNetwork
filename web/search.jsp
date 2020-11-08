<%-- 
    Document   : search
    Created on : Jun 10, 2020, 1:22:23 PM
    Author     : Ruby
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--@page session="false" --%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="style.css">
        <title>Search Page</title>
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
                    <a href="#" rel="home">Social Network</a>
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
                <h1 class="mb-5">Search Article</h1>
                <form action="DispatchServlet">
                  <div class="form-group">
                    <label for="txtSearch">Search Value</label>
                    <input type="text" class="form-control" id="txtSearch" name="txtSearchValue" value="${ param.txtSearchValue }" />
                    <input type="hidden" class="form-control" id="txtPageIndex" name="txtPageIndex" value="1" />
                    <small id="searchHelp" class="form-text text-muted">We'll show up articles that contain your search string in their title.</small>
                  </div>
                  <input type="submit" class="btn btn-outline-dark" value="Search" name="btAction">
                </form>
              </div>
            
              
                <c:set var="searchValue" value="${param.txtSearchValue}" />
                <c:set var="pageSize" value="${ requestScope.SEARCH_RESULT_PAGE_SIZE }" />
                <c:set var="pageIndex" value="${ requestScope.SEARCH_RESULT_PAGE_INDEX }" />
                <c:if test="${not empty searchValue}">
                    <c:set var="result" value="${requestScope.SEARCH_RESULT}" />
                    <c:if test="${not empty result}">
                      <div class="content-part-table">
                        <table class="table border bottom mb-5">
                          <thead>
                            <tr>
                              <th scope="col">#</th>
                              <th scope="col">Title</th>
                              <th scope="col">Description</th>
                              <th scope="col">Image</th>
                              <th scope="col">Date</th>
                              <th scope="col">Action</th>
                            </tr>
                          </thead>
                          <tbody>
                            <c:forEach var="dto" items="${result}" varStatus="counter">
                                <tr>
                                    <th>${counter.count + (pageIndex - 1) * pageSize }</th>
                                    <td>${dto.title}</td>
                                    <td>${dto.description}</td>
                                    <td>
                                        <c:if test="${ not empty dto.image }">
                                            <image src="uploads/${dto.image}" />
                                        </c:if>
                                    </td>
                                    <td>${dto.date}</td>
                                    <td>
                                        <c:url var="viewDetailLink" value="DispatchServlet">
                                            <c:param name="articleId" value="${dto.id}"></c:param>
                                            <c:param name="btAction" value="View Detail"></c:param>
                                        </c:url>
                                        <a href="${ viewDetailLink }">View Detail</a>
                                    </td>
                                </tr>               
                            </c:forEach>
                          </tbody>
                        </table>
                        <c:set var="maxIndex" value="${ requestScope.SEARCH_RESULT_MAX_INDEX }" />
                        <c:url var="pageUrl" value="DispatchServlet">
                            <c:param name="txtSearchValue" value="${ param.txtSearchValue }"></c:param>
                            <c:param name="btAction" value="Search"></c:param>
                            <c:param name="txtPageIndex" value=""></c:param>
                        </c:url>
                        <nav aria-label="Page navigation" class="pb-5">
                            <ul class="pagination justify-content-center m-0">
                              <li class="page-item 
                                <c:if test="${ pageIndex eq 1 }">disabled</c:if>">
                                <a class="page-link" href="${ pageUrl }${ pageIndex - 1 }">Previous</a>
                              </li>
                              <li class="page-item active">
                                  <a class="page-link" href="${ pageUrl }${ pageIndex }">${ pageIndex }</a>
                              </li>
                              <li class="page-item 
                                <c:if test="${ pageIndex eq maxIndex }">disabled</c:if>">
                                <a class="page-link" href="${ pageUrl }${ pageIndex + 1 }">Next</a>
                              </li>
                            </ul>
                        </nav>
                      </div>
                    </c:if>
                    <c:if test="${empty result}">
                      <div class="content-part">
                        <h1>No article is matched!</h1>
                      </div>
                    </c:if>
                </c:if>
              </div>
            </div>
          </div>
        </div>

	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </body>
</html>
