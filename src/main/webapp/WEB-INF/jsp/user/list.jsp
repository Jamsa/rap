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
        <div class="col-sm-12">
            <div class="box">
                <div class="box-header">

                </div>
                <!-- /.box-header -->
                <div class="box-body">
                    <div class="row">
                        <div class="col-sm-offset-11">
                            <button class="btn btn-primary" onclick="userListPage.add();">新建</button>
                        </div>
                    </div>
                    <table id="userTable" width="100%" class="table table-bordered table-hover">
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

<div id="userEditPage" class="modal">

</div>
<c:if test="${!isAjaxRequest}">
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
</c:if>

<script>
    var userListPage = $('#userTable').RapListPage({
        modelName:'user',
        keyAttrName:'userId',
        listOptions:{
            gridOptions:{
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
                                '<a class="btn fa fa-edit" onclick="userListPage.edit('+row.userId+');"></a>' +
                                '<a class="btn fa fa-trash" onclick="userListPage.del('+row.userId+');"></a>' +
                                '</div>';
                    }
                }]
            }
        }
    })

</script>
<c:if test="${!isAjaxRequest}">
</body>
</html>
</c:if>