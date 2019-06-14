$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/fail/list',
        datatype: "json",
        colModel: [
            { label: '保单号', name: 'pid', index: 'pid', width: 80 },
            { label: '车牌号', name: 'pcat', index: 'pcat', width: 80 },
            { label: '支付金额', name: 'pay', index: 'pay', width: 80 },
            { label: '净保费', name: 'pmoney', index: 'pmoney', width: 80 },
            { label: '计算规则', name: 'rule', index: 'rule', width: 80 },
            { label: '户名', name: 'pname', index: 'pname', width: 80 },
            { label: '银行', name: 'pbank', index: 'pbank', width: 80 },
            { label: '开户行', name: 'pobank', index: 'pobank', width: 80 },
            { label: '银行卡号', name: 'pbanknum', index: 'pbanknum', width: 80 },
            { label: '失败原因', name: 'pbeacause', index: 'pbeacause', width: 80 },
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
        },
        showList: true,
        title: null,
        fail: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        update: function (event) {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            var url = vm.fail.id == null ? "sys/fail/save" : "sys/fail/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.fail),
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
        getInfo: function(id){
            $.get(baseURL + "sys/fail/info/"+id, function(r){
                vm.fail = r.fail;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                page:page
            }).trigger("reloadGrid");
        }
    }
});
