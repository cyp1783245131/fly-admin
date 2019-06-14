$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/code/list',
        datatype: "json",
        colModel: [
            { label: '节点名称', name: 'cname', index: 'cname', width: 80 },
            { label: '节点状态', name: 'status', index:'status',width: 80, formatter: function(value, options, row){
                return value === 0 ?
                    '<span class="label label-danger">禁用</span>' :
                    '<span class="label label-success">启用</span>';
            }}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page",
            rows:"limit",
            order: "order"
        },
        gridComplete:function(){
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
});

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            cname: null
        },
        showList: true,
        title: null,
        code: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },

        add: function(){
            vm.showList = false;
            vm.title = "新增计划";
            vm.code = {status:1};
        },
        update: function (event) {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改计划";
            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            //文本框没有值则文本框变红，提醒用户输入信息
            // var name=$("#name").val();
            // if (name == '' || name == undefined || name == null){
            //     $("#name").css('borderColor','red'); //添加css样式
            // }else {
            //     $("#name").css('borderColor',''); //取消css样式
            // }

            var url = vm.code.id == null ? "sys/code/save" : "sys/code/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.code),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(index){
                            vm.reload();
                        });
                    } else{
                        alert(r.msg);
                    }
                }
            });
        },
        del: function (event) {
            var ids = getSelectedRows();
            if(ids == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/code/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(index){
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function(id){
            $.get(baseURL + "sys/code/info/"+id, function(r){
                vm.code = r.code;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'cname': vm.q.cname},
                page:page
            }).trigger("reloadGrid");
        }
    }
});