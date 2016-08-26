/**
 * Created by zhujie on 16/8/26.
 */

/* 公用函数 $.rap.* */
(function($){
    $.extend({
        rap:{

        }
    });
})(jQuery);

/* RapGrid组件 */
(function($){

    $.fn.RapGrid = function(options){
        var opts = $.extend({
            language: {
                "sProcessing": "处理中...",
                "sLengthMenu": "每页 _MENU_ 项",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
                "sInfoEmpty": "当前显示第 0 至 0 项，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "搜索:",
                "sUrl": "",
                "sEmptyTable": "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands": ",",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页",
                    "sJump": "跳转"
                },
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            },
            pageLength:8,
            lengthChange:false,//禁止选择每页显示
            autoWidth: false,  //禁用自动调整列宽
            stripeClasses: ["odd", "even"],  //为奇偶行加上样式，兼容不支持CSS伪类的场合
            processing: true,  //隐藏加载提示,自行处理
            serverSide: true,  //启用服务器端分页
            searching: false,  //禁用原生搜索
            orderMulti: false,  //启用多列排序
            order: [],  //取消默认排序查询,否则复选框一列会出现小箭头
            renderer: "bootstrap",  //渲染样式：Bootstrap和jquery-ui
            pagingType: "simple_numbers",  //分页样式：simple,simple_numbers,full,full_numbers
            columnDefs: [{
                "targets": 'nosort',  //列的样式名
                "orderable": false    //包含上样式名‘nosort’的禁止排序
            }],
            ajax: function (data, callback, settings) {
                //封装请求参数
                var param = $.fn.RapGrid.getGridParam(data);
                $.ajax({
                    type: "GET",
                    url: "/hello/list",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
                    success: function (result) {
                        var cbData = $.fn.RapGrid.getGridData(data,result);
                        callback(cbData);
                    }
                });
            },
            //列表表头字段
            columns: [
                {"data": "Id"},
                {"data": "col1"},
                {"data": "col2"}
            ]
        },options?options:{});

        return $(this).dataTable(opts);
    };

    $.fn.RapGrid.getGridData=function(data,result){
        var returnData = {};
        returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
        returnData.recordsTotal = result.total;//返回数据全部记录
        returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
        returnData.data = result.list;//返回的数据列表
        return returnData;
    };

    $.fn.RapGrid.getGridParam=function(data){
        var param = data?data:{};
        param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
        param.startRow = data.start;//开始的记录序号
        param.pageNum = (data.start / data.length) + 1;//当前页码
        return param;
    };

})(jQuery);

/** pjax **/
(function($){
    $.fn.RapPjax = function(options){
        return $(this).pjax(options);
    }
})(jQuery);

/** ajax 加载,类pjax模式 **/
(function($){
    $.fn.RapAjaxLoad=function(options){

        var opts = $.extend({
            href : '/'
        },options?options:{});
        $(opts.id).html('');

        $(this).click(function(){
            $(opts.id).load(opts.href);
        });

    }
})(jQuery);