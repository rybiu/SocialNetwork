<%-- 
    Document   : detail
    Created on : Sep 17, 2020, 9:48:39 PM
    Author     : Ruby
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" 
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="style.css">
        <title>Detail Page</title>
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
              <article class="content-part shadow-sm">
              <c:set var="dto" value="${ requestScope.ARTICLE_DETAIL }" />
              <c:if test="${not empty dto}">
                <header class="entry-header">
                  <h1 class="entry-title">${ dto.title }</h1>
                  <p class="entry-description">published by <b>${ dto.authorName }</b> on ${ dto.date }</p>
                </header>
                <p>${ dto.description }</p>
                <c:if test="${ not empty dto.image }">
                    <div class="article-image">
                        <image src="uploads/${dto.image}" />
                    </div>
                </c:if>
                <footer class="entry-footer">
                  <span class="mr-4">
                    <c:if test="${ not dto.like }">
                        <c:url var="likeLink" value="DispatchServlet">
                            <c:param name="type" value="Like"></c:param>
                            <c:param name="articleId" value="${ param.articleId }"></c:param>
                            <c:param name="btAction" value="Change Emotion"></c:param>
                        </c:url>
                        <a href="${ likeLink }"><i class="fa fa-thumbs-o-up fa-lg mr-2" aria-hidden="true"></i></a>${ dto.numberLike }
                    </c:if>
            
                    <c:if test="${ dto.like }">
                        <c:url var="likeLink" value="DispatchServlet">
                            <c:param name="type" value="UnLike"></c:param>
                            <c:param name="articleId" value="${ param.articleId }"></c:param>
                            <c:param name="btAction" value="Change Emotion"></c:param>
                        </c:url>
                        <a href="${ likeLink }"><i class="fa fa-thumbs-up fa-lg mr-2" aria-hidden="true"></i></a>${ dto.numberLike }
                    </c:if>
                  </span> 
                  <span class="mr-4">
                    <c:if test="${ not dto.dislike }">
                        <c:url var="dislikeLink" value="DispatchServlet">
                            <c:param name="type" value="Dislike"></c:param>
                            <c:param name="articleId" value="${ param.articleId }"></c:param>
                            <c:param name="btAction" value="Change Emotion"></c:param>
                        </c:url>
                        <a href="${ dislikeLink }"><i class="fa fa-thumbs-o-down fa-lg mr-2" aria-hidden="true"></i></a>${ dto.numberDislike }
                    </c:if>

                    <c:if test="${ dto.dislike }">
                        <c:url var="dislikeLink" value="DispatchServlet">
                            <c:param name="type" value="UnDislike"></c:param>
                            <c:param name="articleId" value="${ param.articleId }"></c:param>
                            <c:param name="btAction" value="Change Emotion"></c:param>
                        </c:url>
                        <a href="${ dislikeLink }"><i class="fa fa-thumbs-down fa-lg mr-2" aria-hidden="true"></i></a>${ dto.numberDislike }
                    </c:if>
                  </span>
                  <c:if test="${ dto.authorId eq user.email }">
                    <c:url var="deleteArticleLink" value="DispatchServlet">
                        <c:param name="articleId" value="${ param.articleId }"></c:param>
                        <c:param name="btAction" value="Delete Article"></c:param>
                    </c:url>
                    <button type="button" class="btn btn-outline-dark" data-toggle="modal" 
                                  data-target="#articleModal">Delete Article</button>

                    <!-- Modal -->
                    <div class="modal fade" id="articleModal" tabindex="-1" role="dialog">
                      <div class="modal-dialog" role="document">
                        <div class="modal-content">
                          <div class="modal-header">
                            <h5 class="modal-title" id="commentModalLabel">Delete Article</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                              <span aria-hidden="true">&times;</span>
                            </button>
                          </div>
                          <div class="modal-body">
                              Are you sure to delete this article?
                          </div>
                          <div class="modal-footer">
                            <button type="button" class="btn btn-outline-darky" data-dismiss="modal">Close</button>
                            <a class="btn btn-outline-dark" href="${ deleteArticleLink }">Delete</a>
                          </div>
                        </div>
                      </div>
                    </div>
                  </c:if>
                </footer>
              </article>
               
              <div class="content-part shadow-sm">
                <h1 class="mb-5">Post Comment</h1>
                <form action="DispatchServlet" method="POST">
                    <div class="form-group">
                        <input type="hidden" name="txtArticleId" value="${ param.articleId }" />
                        <input type="text" class="form-control" name="txtComment" placeholder="Enter your comment" required>
                        <c:set var="error" value="${ requestScope.POST_COMMENT_ERROR }" />
                        <c:if test="${ not empty error }">
                            <small id="postCommentError" class="form-text text-danger">${ error }</small>
                        </c:if>
                    </div>
                    <input type="submit" name="btAction" value="Post Comment" class="btn btn-outline-dark" />
                </form>
              </div>
               
              <c:if test="${ not empty dto.comments }">
                <div class="content-part shadow-sm">
                  <c:forEach var="comment" items="${ dto.comments }" varStatus="counter">
                      <div class="comment-item">
                        <h5 class="d-inline mr-4">${ comment.username }</h5>
                        <c:if test="${ comment.userId eq user.email }">
                          <c:url var="deleteCommentLink" value="DispatchServlet">
                              <c:param name="commentId" value="${ comment.id }"></c:param>
                              <c:param name="articleId" value="${ param.articleId }"></c:param>
                              <c:param name="btAction" value="Delete Comment"></c:param>
                          </c:url>
                          <button type="button" class="btn btn-outline-dark" data-toggle="modal" 
                                  data-target="#commentModal${ comment.id }">Delete Comment</button>

                          <!-- Modal -->
                          <div class="modal fade" id="commentModal${ comment.id }" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                              <div class="modal-content">
                                <div class="modal-header">
                                  <h5 class="modal-title" id="commentModalLabel">Delete Comment</h5>
                                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                  </button>
                                </div>
                                <div class="modal-body">
                                    Are you sure to delete this comment?
                                </div>
                                <div class="modal-footer">
                                  <button type="button" class="btn btn-outline-darky" data-dismiss="modal">Close</button>
                                  <a class="btn btn-outline-dark" href="${ deleteCommentLink }">Delete</a>
                                </div>
                              </div>
                            </div>
                          </div>
                        </c:if>
                        <span><i class="fa fa-calendar mr-2" aria-hidden="true"></i>${ comment.date }</span>
                        <p class="comment-description">${ comment.description }</p>
                      </div>
                  </c:forEach>
                </div>
              </c:if>
            </c:if>
            <c:if test="${empty dto}">
                <h1>This article is not available!</h1>
            </c:if>
            </div>
          </div>
	</div>
	<script src="https://use.fontawesome.com/b35768308f.js"></script>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </body>
</html>