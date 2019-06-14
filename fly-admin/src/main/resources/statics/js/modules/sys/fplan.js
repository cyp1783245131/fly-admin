$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/fplancount/list',
        datatype: "json",
        colModel: [
            {label: '主键', name: 'id', index: 'id', width: 60, hidden: true},
            {label: '计划名称', name: 'dname', index: 'dname', width: 60},
            {label: '开始时间', name: 'kaishi', index: 'kaishi', width: 60},
            {label: '结束时间', name: 'jieshu', index: 'jieshu', width: 60},
            {label: '范围', name: 'fanwei', index: 'fanwei', width: 60},
            {label: '引流位置', name: 'location', index: 'location', width: 60},
            {label: '计划流量(万)', name: 'flow', index: 'flow', width: 60},
            {label: '实际流量(万)', name: 'actual', index: 'actual', width: 60},
            {
                label: '计划状态', name: 'state', index: 'state', width: 60, formatter: function (value, options, row) {
                    return value === 0 ?
                        '<span class="label label-danger">禁用</span>' :
                        '<span class="label label-success">启用</span>';
                }
            },
            {label: '微盟用户', name: 'planwms', index: 'planwms', width: 80, hidden: true},
            { label: '创建用户', name: 'cuser', index: 'cuser', width: 80 ,hidden:true},
            { label: '创建时间', name: 'ctime', index: 'ctime', width: 80,hidden:true },
            { label: '修改时间', name: 'utime', index: 'utime', width: 80 ,hidden:true},

        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        // 查询参数
        q: {
            dname: ''
        },
        showList: true,
        title: null,
        // 引流数据
        fplancount: {},
        planFlows:['1','5','10','50','100','500'],
        planFreeFlow:null,
        planTime:null,
        planAllChannels:[],
        channel:{
            selectedChannels:[]
        },
        planChannels:[]
    },
    methods: {
        query: function () {
            vm.reload();
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'dname': vm.q.dname},
                page: page
            }).trigger("reloadGrid");
        },
        viewRecord: function () {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            location.href='/modules/sys/fcomin.html?id='+id;
        }

    }
});