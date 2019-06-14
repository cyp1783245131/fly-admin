$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/payStatus/list',
        datatype: "json",
        colModel: [
            { label: '批次号', name: 'tid', index: 'tid', width: 80 },
            { label: '支付笔数', name: 'tnum', index: 'tnum', width: 80 },
            { label: '支付金额', name: 'tpay', index: 'tpay', width: 80 },
            { label: '申请时间', name: 'stime', index: 'stime', width: 80 },
            { label: '支付时间', name: 'ptime', index: 'ptime', width: 80 },
            { label: '手续费', name: 'pay', index: 'pay', width: 80 },
            { label: '支付状态', name: 'pstatus', index: 'pstatus', width: 80 },
            { label: '备注', name: 'remarks', index: 'remarks', width: 80 }
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
        payStatus: {}
    },
    methods: {
        query: function () {
            vm.reload();
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