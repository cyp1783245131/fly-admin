$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/paySearch/list',
        datatype: "json",
        colModel: [
            { label: '日期', name: 'stime', index: 'stime', width: 80 },
            { label: '批次号', name: 'sid', index: 'sid', width: 80 },
            { label: '交易号', name: 'spid', index: 'spid', width: 80 },
            { label: '交易类型', name: 'stype', index: 'stype', width: 80 },
            { label: '收入', name: 'smoney', index: 'smoney', width: 80 },
            { label: '支出', name: 'spay', index: 'spay', width: 80 },
            { label: '手续费', name: 'smy', index: 'smy', width: 80 },
            { label: '余额', name: 'save', index: 'save', width: 80 }
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
            starttime:null,
            endtime:null,
        },
        showList: true,
        title: null,
        paySearch: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        viewRecord:function(){
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            location.href='det.html?id='+id;
        },
        reload: function (event) {
            vm.showList = true;
            vm.q.starttime=$('#starttime').val();
            vm.q.endtime=$('#endtime').val();
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{"starttime":vm.q.starttime,"endtime":vm.q.endtime},
                page:page
            }).trigger("reloadGrid");
        }
    }
});