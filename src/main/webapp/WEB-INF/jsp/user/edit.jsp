<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<c:if test="${!isAjaxRequest}">
    <!DOCTYPE html>
    <html lang="zh-CN"/>
    <head>
        <%@ include file="/WEB-INF/jsp/inc/header.jsp" %>
        <title>用户编辑</title>
    </head>

    <body class="hold-transition skin-blue ">
        <%@ include file="/WEB-INF/jsp/inc/common.jsp" %>
</c:if>

        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <c:if test="${isAjaxRequest}">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span></button>
                    </c:if>
                    <h4 class="modal-title">用户信息</h4>
                </div>
                <div class="modal-body">
                    <form name="userEditForm" id="userEditForm" action="<c:url value="/user/save"/>">
                        <input type="hidden" name="userId" name="userId" value="<c:out value="${record.userId}"/>" />
                        <div class="row">
                            <div class="form-group col-sm-6">
                                <label for="username">用户名</label>
                                <input type="text" class="form-control"
                                       name="username" id="username"
                                       placeholder="用户名"
                                       value="<c:out value="${record.username}"/>" />
                            </div>
                            <div class="form-group col-sm-6">
                                <label for="fullname">姓名</label>
                                <input type="text" class="form-control"
                                       name="fullname" id="fullname"
                                       placeholder="姓名"
                                       value="<c:out value="${record.fullname}"/>" />
                            </div>
                        </div>

                        <div class="row">
                            <div class="form-group col-lg-6">
                                <label for="password">密码</label>
                                <input type="password" class="form-control" name="password" id="password" placeholder="密码">
                            </div>
                            <div class="form-group col-lg-6">
                                <label for="password1">验证密码</label>
                                <input type="password1" class="form-control" id="password1" placeholder="密码">
                            </div>
                        </div>

                    </form>
                </div>
                <div class="modal-footer">
                    <c:if test="${isAjaxRequest}">
                    <button type="button" class="btn btn-default pull-left" data-dismiss="modal">取消</button>
                    </c:if>
                    <c:if test="${isAdd}">
                        <button type="button" class="btn btn-primary" onclick="userEditPage.save();">保存</button>
                    </c:if>
                    <c:if test="${!isAdd}">
                        <button type="button" class="btn btn-primary" onclick="userEditPage.update();">保存</button>
                    </c:if>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->

<c:if test="${!isAjaxRequest}">
    <%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
</c:if>

<script>
    var userEditPage = {
        init: function () {

            $('#userEditForm').RapValidate({
                rules:{
                    username:{required:true},
                    fullname:{required:true}
                }
            });
        },
        save: function() {
            var editPage = this;
            if($('#userEditForm').valid()===false) return;
            $.ajax({
                cache: false,
                type: "POST",
                url: '<c:url value="/user/save"/>',
                data: $('#userEditForm').serialize(),
                async: false,
                success: function (data) {
                    //$.rap.dialog.message('提示','保存成功!');
                    $('#userEditPage').trigger('rap:user:saved',[data]);
                }
            });
        },
        update: function() {
            if($('#userEditForm').valid()===false) return;
            $.ajax({
                cache: false,
                type: "POST",
                url: '<c:url value="/user/update"/>',
                data: $('#userEditForm').serialize(),
                async: false,
                success: function (data) {
                    //$.rap.dialog.message('提示','保存成功!');
                    $('#userEditPage').trigger('rap:user:updated',[data]);
                }
            });
        },
        del: function(id) {
            $('#userEditPage').trigger('rap:user:delete',[id]);
            /*
            $.ajax({
                cache: false,
                type: "GET",
                url: '<c:url value="/user/delete/"/>'+id,
                async: false,
                success: function (data) {
                    $('#userEditPage').trigger('rap:user:deleted',[data]);
                }
            });
            */
        }
    };

    userEditPage.init();

</script>
<c:if test="${!isAjaxRequest}">
    </body>
    </html>
</c:if>