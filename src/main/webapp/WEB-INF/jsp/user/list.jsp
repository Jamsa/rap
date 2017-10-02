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

            <div class="box">
                <div class="box-header">
                    <div class="form-group row">
                        <div class="col-md-2"></div>
                        <form class="form-horizontal" id="userQueryForm">
                        <label for="queryUsername" class="control-label col-md-1">用户名</label>
                        <div class="col-md-2">
                            <input type="text" class="form-control" id="queryUsername" name="username">
                        </div>
                        <label for="queryFullname" class="control-label col-md-1">姓名</label>
                        <div class="col-md-2">
                            <input type="text" class="form-control" id="queryFullname" name="fullname">
                        </div>
                        </form>
                        <div class="col-md-2">
                            <button class="btn btn-primary" onclick="userListPage.query();">查询</button>
                            <button class="btn btn-default" onclick="userListPage.resetCondition();">重置</button>
                        </div>
                        <div class="col-md-2"></div>
                    </div>

                    <div class="row">
                        <div class="col-md-11"/>
                        <div class="col-md-1">
                            <button class="btn btn-primary" onclick="userListPage.add();">新建</button>
                        </div>
                    </div>
                </div>
                <!-- /.box-header -->
                <div class="box-body">

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