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

    <div class="ui-layout-center">Center
        <p><a href="http://layout.jquery-dev.com/demos.html">Go to the Demos page</a></p>
        <p>* Pane-resizing is disabled because ui.draggable.js is not linked</p>
        <p>* Pane-animation is disabled because ui.effects.js is not linked</p>
    </div>
    <div class="ui-layout-north">
        north
    </div>
    <div class="ui-layout-south">South</div>
    <div class="ui-layout-west">West</div>

<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
<script type="text/javascript">
    $(function () {
        var layout = $('body').layout({
            applyDemoStyles: false,
            applyDefaultStyles: false,
            south:{
                resizable:false,
                closable:false,
                togglerLength_open: 0
            },
            north:{
                resizable:false,
                closable:false
            }
        });
    });
</script>
</body>
</html>