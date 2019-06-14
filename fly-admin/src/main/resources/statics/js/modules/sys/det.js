$(function () {
    function  getUrlParam (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg); //匹配目标参数
        if (r != null) return unescape(r[2]); //返回参数值
        return null;
    }
    var id = getUrlParam ("id");

    $("#jqLowGrid").jqGrid({
        url: baseURL + 'sys/paySearch/detList?id='+id,
        datatype: "json",
        colModel: [
            { label: '批次号', name: 'dsid', index: 'dsid', width: 80 },
            { label: '交易号', name: 'dspid', index: 'dspid', width: 80 },
            { label: '支付金额', name: 'dpay', index: 'dpay', width: 80 },
            { label: '申请时间', name: 'dtime', index: 'dtime', width: 80 },
            { label: '户名', name: 'dusername', index: 'dusername', width: 80 },
            { label: '银行', name: 'dbank', index: 'dbank', width: 80 },
            { label: '开户行', name: 'dbankname', index: 'dbankname', width: 80 },
            { label: '银行卡号', name: 'dbanknum', index: 'dbanknum', width: 80 },
            { label: '手续费', name: 'dmoney', index: 'dmoney', width: 80 }
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        multiselect: true,
        pager: "#jqLowGridPager",
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
            $("#jqLowGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
});

var vm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        title: null,
        det: {}
    },
    methods: {
        query: function () {
            vm.reload();
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