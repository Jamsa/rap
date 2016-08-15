<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN"/>
<head>
    <%@ include file="/WEB-INF/jsp/inc/header.jsp" %>
    <title>Gradle + Spring MVC</title>
</head>

<body>
<%@ include file="/WEB-INF/jsp/inc/common.jsp" %>
<!-- Navbar -->
<div class="navbar navbar-inverse ">
    <div class="navbar-inner">
        <div class="container">
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="brand" href="/p/jquery-ui-bootstrap/">jQuery UI Bootstrap</a>
            <div class="nav-collapse collapse">
                <ul class="nav">
                    <li class="active">
                        <a href="./index.html">手册</a>
                    </li>
                    <li>
                        <a href="./extra.html">扩展</a>
                    </li>

                </ul>
                <div id="twitter-share" class="pull-right">

                </div>
            </div>
        </div>
    </div>
</div>


<div class="container-fluid">
    <h1>${title}</h1>

    <p>
        <c:if test="${not empty msg}">
            Hello ${msg}
        </c:if>

        <c:if test="${empty msg}">
            Welcome Welcome!
        </c:if>
    </p>

    <p>
        <a class="btn btn-primary btn-lg"
           href="#" role="button">Learn more</a>
    </p>
</div>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>

</body>
</div>