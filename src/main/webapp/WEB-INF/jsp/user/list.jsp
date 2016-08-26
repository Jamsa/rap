<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<c:if test="${!isAjaxRequest}">
<!DOCTYPE html>
<html lang="zh-CN"/>
<head>
    <%@ include file="/WEB-INF/jsp/inc/header.jsp" %>
    <title>用户管理</title>
</head>

<body class="hold-transition skin-blue ">
<%@ include file="/WEB-INF/jsp/inc/common.jsp" %>
</c:if>
<!-- Content Header (Page header) -->
<section class="content-header">
    <h1>
        用户
        <small>用户管理</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i>首页</a></li>
        <li><a href="#"><i class="fa fa-dashboard"></i>系统设置</a></li>
        <li class="active">用户管理</li>
    </ol>
</section>

<!-- Main content -->
<section class="content">

    <div class="row">
        <div class="col-xs-12">
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">Hover Data Table</h3>
                </div>
                <!-- /.box-header -->
                <div class="box-body">
                    <table id="userTable" class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>用户名</th>
                            <th>姓名</th>
                            <th>注册日期</th>
                            <th>备注</th>
                            <th>操作</th>
                        </tr>
                        </thead>

                    </table>
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->

</section>
<!-- /.content -->
<c:if test="${!isAjaxRequest}">
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
</c:if>

<script>
    var userListPage = {
        init: function () {
            var userTable = $('#userTable').RapGrid({
                ajax: function (data, callback, settings) {
                    //封装请求参数
                    var param = $.fn.RapGrid.getGridParam(data);
                    $.ajax({
                        type: "GET",
                        url: "<c:url value="/user/list"/>",
                        cache: false,  //禁用缓存
                        data: param,  //传入组装的参数
                        dataType: "json",
                        success: function (result) {
                            var cbData = $.fn.RapGrid.getGridData(data, result);
                            callback(cbData);
                        }
                    });
                },
                //列表表头字段
                columns: [
                    {"data": "username"},
                    {"data": "fullname"},
                    {"data": "birthday"},
                    {"data": "memo"},
                    {"data": null}
                ],
                columnDefs: [{
                    targets: 4,
                    render: function (data, type, row, meta) {
                        return '<div class="text-center">' +
                                '<a class="btn fa fa-edit"></a>' +
                                '<a class="btn fa fa-trash"></a>' +
                                '</div>';
                    }
                }]
            });
        }

    };
    userListPage.init();

</script>
<c:if test="${!isAjaxRequest}">
</body>
</html>
</c:if>