$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/paicha/list',
        datatype: "json",
        colModel: [
            { label: '项目名称', name: 'pname', index: 'pname', width: 80 },
            { label: '排查节点', name: 'cname', index: 'cname', width: 80 },
            { label: '导入数据', name: 'number', index: 'number', width: 80 },
            { label: '排查进度', name: 'speed', index: 'speed', width: 80 }
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
            pname: null
        },
        showList: true,
        title: null,
        paicha: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        look: function(){
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            location.href='tongji2.html?id='+id;
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'pname': vm.q.pname},
                page:page
            }).trigger("reloadGrid");
        }
    }
});