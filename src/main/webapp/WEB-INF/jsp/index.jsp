<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN"/>
<head>
    <%@ include file="/WEB-INF/jsp/inc/header.jsp" %>
    <title>Gradle + Spring MVC</title>
    <style>
        .ui-layout-pane {
            /*background: #FFF;*/
            padding-top: 0px;
            padding-right: 0px;
            padding-bottom: 0px;
            padding-left: 0px;
            overflow: auto;
            border:0px;
        }

    </style>
</head>

<body>
<%@ include file="/WEB-INF/jsp/inc/common.jsp" %>
<div class="container-fluid">
    <div class="navbar navbar-fixed-top" >
        <div class="navbar-inner">
            <div class="container">

                <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="brand" href="#">Project name</a>
                <div class="nav-collapse collapse">
                    <ul class="nav">
                        <li class="active"><a href="#">Home</a></li>
                        <li><a href="#about">About</a></li>
                        <li><a href="#contact">Contact</a></li>
                    </ul>
                </div>

            </div>
        </div>
    </div>


</div>


<div class="ui-layout-west">West</div>
<div class="ui-layout-center" style="padding:0px">
    <div id="tabs">
        <ul>
            <li><a href="#tabs-a">First</a></li>
            <li><a href="#tabs-b">Second</a></li>
            <li><a href="#tabs-c">Third</a></li>
        </ul>
        <div id="tabs-a">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</div>
        <div id="tabs-b">Phasellus mattis tincidunt nibh. Cras orci urna, blandit id, pretium vel, aliquet ornare, felis. Maecenas scelerisque sem non nisl. Fusce sed lorem in enim dictum bibendum.</div>
        <div id="tabs-c">Nam dui erat, auctor a, dignissim quis, sollicitudin eu, felis. Pellentesque nisi urna, interdum eget, sagittis et, consequat vestibulum, lacus. Mauris porttitor ullamcorper augue.</div>
    </div>
</div>
<div class="ui-layout-north">

</div>
<div class="ui-layout-south" style="padding-left: 0px;padding-right: 0px;">
    <div class="container-fluid" style="background-color: #f5f5f5;">
        <p class="muted credit" style="margin-left: auto;margin-right:auto">Example courtesy <a href="http://martinbean.co.uk">Martin Bean</a> and <a href="http://ryanfait.com/sticky-footer/">Ryan Fait</a>.</p>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
<script type="text/javascript">
    $(function () {
        $('#tabs').tabs();

        var layout = $('body').layout({
            applyDemoStyles: false,
            //applyDefaultStyles: false,

            south:{
                resizable:false,
                closable:false,
                spacing_open:0,
                size:31
            },
            north:{
                resizable:false,
                closable:false,
                spacing_open:0,
                size:41
            }
        });


    });
</script>
</body>
</html>