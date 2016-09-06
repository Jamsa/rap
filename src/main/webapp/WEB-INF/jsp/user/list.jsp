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
    var userListPage = {
        grid: null,
        init: function () {
            var listPage = this;
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
                                '<a class="btn fa fa-edit" onclick="userListPage.edit('+row.userId+');"></a>' +
                                '<a class="btn fa fa-trash" onclick="userListPage.del('+row.userId+');"></a>' +
                                '</div>';
                    }
                }]
            });
            this.grid = userTable;

            $('#userEditPage').on('rap:user:saved',function(evt,data){
                if(data.userId){
                    listPage.edit(data.userId);
                }
                $("#userEditPage").modal('hide');
                listPage.grid.ajax.reload(null,false);
            });
            $('#userEditPage').on('rap:user:updated',function(evt,data){
                $("#userEditPage").modal('hide');
                listPage.grid.ajax.reload(null,false);
            });
            $('#userEditPage').on('rap:user:delete',function(evt,data){
                listPage.del(data);
            });
        },
        query:function(){
            this.grid.ajax.reload();
        },
        add:function(){
            $("#userEditPage").html('');
            $("#userEditPage").load('<c:url value="/user/add" />');
            $("#userEditPage").modal('show');
        },
        edit:function(id){
            $("#userEditPage").html('');
            $("#userEditPage").load('<c:url value="/user/edit/" />'+id);
            $("#userEditPage").modal('show');
        },
        del: function(id) {
            var listPage = this;
            $.rap.dialog.confirm('确认','确定要删除记录吗?',function(){
                $.ajax({
                    cache: false,
                    type: "POST",
                    url: '<c:url value="/user/delete/"/>'+id,
                    async: false,
                    success: function (data) {
                        listPage.grid.ajax.reload(null,false);
                    }
                });
            });
        }
    };
    userListPage.init();

</script>
<c:if test="${!isAjaxRequest}">
</body>
</html>
</c:if>