$(function () {
    function  getUrlParam (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg); //匹配目标参数
        if (r != null) return unescape(r[2]); //返回参数值
        return null;
    }
    var id = getUrlParam ("id");

    $("#jqLowGrid").jqGrid({
        url: baseURL + 'sys/tongji/list',
        datatype: "json",
        colModel: [
            { label: '车牌号', name: 'tcar', index: 'tcar', width: 80 },
            { label: '排查开始时间', name: 'stime', index: 'stime', width: 80 },
            { label: '排查结束时间', name: 'etime', index: 'etime', width: 80 },
            { label: '评测级别', name: 'tlevel', index: 'tlevel', width: 80 },
            { label: '评测结果', name: 'tlast', index: 'tlast', width: 80 },
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

    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        // 获取已激活的标签页的名称
        var activeTab = $(e.target).text();
        //alert(activeTab);
        // if(activeTab=='导出低风险列表'){
        //     confirm('确定导出低风险列表记录？', function(){
        //         window.open(baseURL + 'sys/tongji/download');
        //     });
        // }
        // if(activeTab=='导出总排查列表'){
        //     confirm('确定导出总排查列表记录？', function(){
        //         window.open(baseURL + 'sys/tongji/download');
        //     });
        // }
        if(activeTab=='低风险列表'){
            $("#jqLowGrid").jqGrid({
                url: baseURL + 'sys/tongji/list',
                datatype: "json",
                colModel: [
                    { label: '车牌号', name: 'tcar', index: 'tcar', width: 80 },
                    { label: '排查开始时间', name: 'stime', index: 'stime', width: 80 },
                    { label: '排查结束时间', name: 'etime', index: 'etime', width: 80 },
                    { label: '评测级别', name: 'tlevel', index: 'tlevel', width: 80 },
                    { label: '评测结果', name: 'tlast', index: 'tlast', width: 80 },
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
        }else{
            $("#jqAllGrid").jqGrid({
                url: baseURL + 'sys/tongji/list',
                datatype: "json",
                colModel: [
                    { label: '车牌号', name: 'tcar', index: 'tcar', width: 80 },
                    { label: '排查开始时间', name: 'stime', index: 'stime', width: 80 },
                    { label: '排查结束时间', name: 'etime', index: 'etime', width: 80 },
                    { label: '评测级别', name: 'tlevel', index: 'tlevel', width: 80 },
                    { label: '评测结果', name: 'tlast', index: 'tlast', width: 80 },
                ],
                viewrecords: true,
                height: 385,
                rowNum: 10,
                rowList : [10,30,50],
                rownumbers: true,
                rownumWidth: 25,
                autowidth:true,
                multiselect: true,
                pager: "#jqAllGridPager",
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
                    $("#jqAllGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
                }
            });
        }
    });
    $.ajax({
        type: "POST",
        url: baseURL + 'sys/tongji/list?id='+id,
        contentType: "application/json",
        success: function(r){
            $("#z_count").append("5个");
        }
    });
    $.ajax({
        type: "POST",
        url: baseURL + 'sys/tongji/list?id='+id,
        contentType: "application/json",
        success: function(r){
            $("#d_count").append("5个");
        }
    });
});

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{

        },
        showList: true,
        showWMTable:false,
        title: null,
        tongji: {

        }
    },
    methods: {
        query: function () {
            //vm.reload();
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{"pname":vm.q.pname},
                page:page
            }).trigger("reloadGrid");
        }
    }
});