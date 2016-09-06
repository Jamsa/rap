/**
 * Created by zhujie on 16/8/26.
 */

/* 公用函数 $.rap.* */
(function($){
    $.extend({
        rap:{
            global:{
                contextPath:"/rap"
            },
            randomId:function(){
                return Math.round(new Date().getTime() + (Math.random() * 100));
            },
            dialog:{
                dialog:function(options){
                    var eleId = '_rap_dlg_'+$.rap.randomId();
                    var opts = $.extend({
                        onclose:function(){},
                        title:'Dialog title',
                        styleClass:'',
                        message:'Message.',
                        buttons:[{
                            label:'Button',
                            type: 'button',
                            styleClass:'btn-default',
                            attr:{
                                'data-dismiss':'modal'
                            },
                            click:function(modal){
                                /*setTimeout(function(){
                                    $('#'+eleId).remove();
                                });*/
                            }
                        }]
                    },options?options:{});

                    var template = '<div class="modal ' + opts.styleClass+ '" tabindex="-1" role="dialog" id="' + eleId +'">'+
                        '    <div class="modal-dialog" role="document">'+
                        '      <div class="modal-content">'+
                        '        <div class="modal-header">'+
                        '          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'+
                        '          <h4 class="modal-title">Dialog Title</h4>'+
                        '        </div>'+
                        '        <div class="modal-body">'+
                        '          <p>One fine body&hellip;</p>'+
                        '        </div>'+
                        '        <div class="modal-footer">'+
                        //'          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'+
                        //'          <button type="button" class="btn btn-primary">确定</button>'+
                        '        </div>'+
                        '      </div>'+
                        '    </div>'+
                        '  </div>';

                    $('body').append(template);
                    var ele = $('#'+eleId);

                        //隐藏时调用onclose并自动销毁
                    $('#'+eleId).on('hidden.bs.modal', function () {
                        var result = opts.onclose($('#'+eleId));
                        if(result===false){

                        }else{
                            setTimeout(function(){
                                $('#'+eleId).remove();
                                //console.log('modal element removed');
                            });
                        }
                    });

                    var footer = ele.find('.modal-footer');
                    $.each(opts.buttons,function(idx,btnOpts){
                        var button = $('<button/>').addClass('btn');
                        if(btnOpts.label) button.html(btnOpts.label);
                        if(btnOpts.styleClass) button.addClass(btnOpts.styleClass);
                        if(btnOpts.attr) button.attr(btnOpts.attr);
                        if(btnOpts.click){
                            button.click(function(){
                                btnOpts.click($('#'+eleId));
                            });
                        }
                        footer.append(button);
                    });

                    var title = ele.find('.modal-title');
                    title.html(opts.title);

                    var msg = ele.find('.modal-body');
                    msg.html(opts.message);

                    $('#'+eleId).modal('show');
                },
                message:function(title,msg,onok,ponclose){
                    $.rap.dialog.dialog({title:title,
                        message:msg,
                        onclose:ponclose?ponclose:function(modal){},
                        buttons:[{label:'确定',
                            styleClass:'btn-primary btn-default',
                            attr:{
                                'data-dismiss':'modal'
                            },
                            click:function(modal){
                                if(onok){
                                    var result = onok(modal);
                                    if(result===false){

                                    }else{
                                        modal.modal('hide');
                                    }
                                }
                            }}]
                    });
                },
                confirm:function(title,msg,onok,oncancel,ponclose){
                    $.rap.dialog.dialog({title:title,
                        message:msg,
                        onclose:ponclose?ponclose:function(modal){},
                        buttons:[{
                            label:'确定',
                            styleClass:'btn-primary',
                            click:function(modal){
                                if(onok){
                                    var result = onok(modal);
                                    if(result===false){

                                    }else{
                                        modal.modal('hide');
                                    }
                                }
                            }
                        },{
                            label:'取消',
                            styleClass:'btn-default',
                            click:function(modal){
                                if(oncancel){
                                    oncancel();
                                }
                            },
                            attr:{
                                'data-dismiss':'modal'
                            }}]
                    });
                },
                input:function(title,msg,onok,oncancel,ponclose){
                    var inputId = '_rap_dlg_input_'+$.rap.randomId();
                    var content = '<div class="form-group"><label for="'+inputId+'">'+msg+'</label>'+
                        '<input type="text" class="form-control" id="'+inputId+'"></div>';
                    $.rap.dialog.dialog({title:title,
                        message:content,
                        onclose:ponclose?function(modal){ponclose(modal,$('#'+inputId).val());}:function(modal){},
                        buttons:[{
                            label:'确定',
                            styleClass:'btn-primary',
                            click:function(modal){
                                if(onok){
                                    var inputVal = $('#'+inputId).val();
                                    var result = onok(modal,inputVal);
                                    if(result===false){
                                    }else{
                                        modal.modal('hide');
                                    }
                                }
                            }
                        },{
                            label:'取消',
                            styleClass:'btn-default',
                            click:function(modal){
                                if(oncancel){
                                    var inputVal = $('#'+inputId).val();
                                    oncancel(modal,inputVal);
                                }
                            },
                            attr:{
                                'data-dismiss':'modal'
                            }}]
                    });
                },
                error:function(title,msg,onok,ponclose){
                    $.rap.dialog.dialog({title:title,
                        message:msg,
                        styleClass:'modal-warning',
                        onclose:ponclose?ponclose:function(modal){},
                        buttons:[{label:'确定',
                            styleClass:'btn-outline',
                            attr:{
                                'data-dismiss':'modal'
                            },
                            click:function(modal){
                                if(onok){
                                    var result = onok();
                                    if(result===false){

                                    }else{
                                        modal.modal('hide');
                                    }
                                }
                            }}]
                    });
                }
            }
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
            processing: false,  //隐藏加载提示,自行处理
            serverSide: true,  //启用服务器端分页
            searching: false,  //禁用原生搜索
            orderMulti: false,  //启用多列排序
            order: [],  //取消默认排序查询,否则复选框一列会出现小箭头
            renderer: "bootstrap",  //渲染样式：Bootstrap和jquery-ui
            pagingType: "simple_numbers",  //分页样式：simple,simple_numbers,full,full_numbers
            info:true,
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
        var result = this.DataTable(opts);
        return result;
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

/** 校验类 **/
(function($){
    $.fn.RapValidate=function(options){
        var opts = $.extend({
            errorElement:'span',
            errorClass:'help-block',
            highlight: function(element, errorClass, validClass) {
                var ele = $(element).parent('div[class*="form-group"]');
                ele.addClass('has-error').removeClass('');

            },
            unhighlight: function(element, errorClass, validClass) {
                var ele = $(element).parent('div[class*="form-group"]');
                ele.removeClass('has-error').addClass('');
            }
        },options?options:{});
        $(opts.id).html('');

        return $(this).validate(opts);
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
            return false;
        });
    }
})(jQuery);

/** ajax 初始设置 **/
(function($){
    $.ajaxSetup({
        success:function(resp,status,xhr){
            //console.log('##'+status);
            if(status==='500'){
                $.rap.dialog.error("出错",resp);
            }
        },
        error:function(xhr,status,errorThrown){
            //console.log('##'+status);
            $.rap.dialog.error("出错",errorThrown);
        }
    });
})(jQuery);


/** 列表页 **/
(function($){
    $.fn.RapListPage=function(options){
        var listPage = this;
        var opts = $.extend({
            modelName:'modelName',
            keyAttrName:'id',//主键属性名
            editElementId:undefined,
            listOptions:{
                url:undefined,
                queryFormId:undefined,
                gridOptions:{},
                onList:function(data){},
                onListSuccess:function(data){}
            },
            addOptions:{
                url:undefined,
                onAdd:function(data){},
                onAddSuccess:function(data){}
            },
            editOptions:{
                url:undefined,
                onEdit:function(id,data){},
                onEditSuccess:function(data){}
            },
            delOptions:{
                url:undefined,
                onDel:function(id,data){},
                onDelSuccess:function(data){}
            },
            messages:{//编辑页传递来的消息
                onRecordSaved:function(data){},
                onRecordUpdated:function(data){}
            }
        },options?options:{});
        var modulePath = $.rap.global.contextPath+'/'+opts.modelName;
        if(!opts.listOptions.url) opts.listOptions.url=modulePath+'/list';
        if(!opts.addOptions.url) opts.addOptions.url=modulePath+'/add';
        if(!opts.editOptions.url) opts.editOptions.url=modulePath+'/edit';
        if(!opts.delOptions.url) opts.delOptions.url=modulePath+'/del';

        if(!opts.listOptions.queryFormId) opts.listOptions.queryFormId=opts.modelName+'QueryForm';
        if(!opts.editElementId) opts.editElementId=opts.modelName+'EditPage';

        listPage.options = opts;
        //属性处理结束

        //表格的处理
        var gridOptions = $.extend({
            ajax: function (data, callback, settings) {
                //封装请求参数
                var param = $.fn.RapGrid.getGridParam(data);
                var cbResult = listPage.options.listOptions.onList(data);
                if(cbResult===false) {
                    console.log('Rap listpage.grid.ajax onList回调中止了查询操作。');
                    return;
                }

                $.ajax({
                    type: "GET",
                    url: listPage.options.listOptions.url,
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
                    success: function (result) {
                        var cbData = $.fn.RapGrid.getGridData(data, result);
                        var cbResult = listPage.options.listOptions.onListSuccess(cbData);
                        if(cbResult===false) return;

                        callback(cbData);
                    }
                });
            },
            //列表表头字段
            columns: [
                {"data": "col1"},
                {"data": "col1"},
                {"data": "col3"},
                {"data": "col4"},
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
        },listPage.options.listOptions.gridOptions?listPage.options.listOptions.gridOptions:{});
        listPage.grid = $(this).RapGrid(gridOptions);
        //表格处理结束

        var editElementSelector = '#'+listPage.options.editElementId;
        //事件绑定处理
        $(editElementSelector).on('rap:'+listPage.options.modelName+':saved',function(evt,data){
            var cbResult = listPage.options.messages.onRecordSaved(data);
            if(cbResult===false) return;

            if(data[listPage.options.keyAttrName]){
                listPage.edit(data[listPage.options.keyAttrName]);
            }
            $(editElementSelector).modal('hide');
            listPage.grid.ajax.reload(null,false);
        });
        $(editElementSelector).on('rap:'+listPage.options.modelName+':updated',function(evt,data){
            var cbResult = listPage.options.messages.onRecordUpdated(data);
            if(cbResult===false) return;

            $(editElementSelector).modal('hide');
            listPage.grid.ajax.reload(null,false);
        });
        $(editElementSelector).on('rap:'+listPage.options.modelName+':delete',function(evt,data){
            listPage.del(data);
        });
        //事件处理结束

        listPage.query = function(listOpts){
            listPage.grid.ajax.reload();
        };

        listPage.add = function(addOpts){
            var opts = $(listPage.options.addOptions,addOpts?addOpts:{});
            var cbResult = opts.onAdd();
            if(cbResult===false) return;
            var editElementSelector = '#'+listPage.options.editElementId;

            $(editElementSelector).html('');
            $(editElementSelector).load(opts.url);
            $(editElementSelector).modal('show');

            cbResult = opts.onAddSuccess();
            if(cbResult===false) return;
        };

        listPage.edit = function(id,editOpts){
            var opts = $(listPage.options.editOptions,editOpts?editOpts:{});
            var cbResult = opts.onEdit();
            if(cbResult===false) return;
            var editElementSelector = '#'+listPage.options.editElementId;

            $(editElementSelector).html('');
            $(editElementSelector).load(opts.url+'/'+id);
            $(editElementSelector).modal('show');

            cbResult = opts.onEditSuccess();
            if(cbResult===false) return;
        };

        listPage.del = function(id,opts){

        };

        return listPage;
    }
})(jQuery);

/** 编辑页 **/
(function($){
    $.fn.RapEditPage=function(options){
        var editPage = this;

        var opts = $.extend({
            modelName:'modelName',
            keyAttrName:'id',//主键属性名
            editFormId:undefined,
            editElementId:undefined,//编辑元素id,在列表页中
            isAdd:false,
            saveOptions:{
                url:undefined,
                onSave:function(data){},
                onSaveSuccess:function(data){}
            },
            validateOptions:{}
        },options?options:{});

        var modulePath = $.rap.global.contextPath+'/'+opts.modelName;
        if(!opts.saveOptions.url){
            opts.saveOptions.url=modulePath+ (opts.isAdd===true?'/save':'/update');
        }

        if(!opts.editFormId) opts.editFormId=opts.modelName+'EditForm';
        if(!opts.editElementId) opts.editElementId=opts.modelName+'EditPage';

        editPage.options = opts;

        editPage.save=function(saveOpts){
            var opts = $.extend(editPage.options.saveOptions,saveOpts?saveOpts:{});
            var formSelector = '#'+editPage.options.editFormId;

            if($(formSelector).valid()===false) return;

            var formData = $(formSelector).serialize();

            var cbResult = opts.saveOptions.onSave(formData);
            if(cbResult===false) return;

            $.ajax({
                cache: false,
                type: "POST",
                url: opts.url,
                data: formData,
                async: false,
                success: function (data) {
                    //$.rap.dialog.message('提示','保存成功!');
                    var cbResult = opts.saveOptions.onSaveSuccess(data);
                    if(cbResult===false) return;

                    var editElementSelector = '#'+editPage.options.editElementId;
                    var messageKey = 'rap:'+editPage.options.modelName+':'+editPage.options.isAdd?'saved':'updated';
                    $(editElementSelector).trigger(messageKey,[data]);
                }
            });
        };



        return this;
    }
})(jQuery);

