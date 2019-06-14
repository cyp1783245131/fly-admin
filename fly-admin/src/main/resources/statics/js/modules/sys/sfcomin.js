$(function () {
    function getParamValue(id) {
        var reg = new RegExp("(^|&)" + id + "=([^&]*)(&|$)", "i"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg); //匹配目标参数
        if (r != null) return unescape(r[2]); //返回参数值
        return null;
    }

    var id = getParamValue('id');
    console.log(id)
    $("#jqGrid").jqGrid({
        url: baseURL + 's/fcomin/list',
        datatype: "json",
        postData: {"id": id},
        colModel: [
            {label: '主键', name: 'id', index: 'id', width: 240, key: true, hidden: true},
            {label: 'IP地址', name: 'fip', index: 'fip', width: 240, hidden: true},
            {label: '来源', name: 'fsource', index: 'fsource', width: 240, hidden: true},
            {label: '服务日期', name: 'fdate', index: 'fdate', width: 240},
            {label: '时间', name: 'ftime', index: 'ftime', width: 240},
            {label: '产品总体服务次数', name: 'fbrowses', index: 'fbroswses', width: 240},
            {label: '产品有效服务次数', name: 'fbrowse', index: 'fbrowse', width: 240},
            {label: '生成规则', name: 'rule', index: 'rule', width: 240},
            {label: '预览转化率', name: 'fconversion', index: 'fconversion', width: 240},
            {label: '访问地址', name: 'faddress', index: 'faddress', width: 240},
            {label: '引流区域', name: 'farea', index: 'farea', width: 240},
            {label: '是否区域内', name: 'fisarea', index: 'fisarea', width: 240},
            {label: '创建时间', name: 'cdate', index: 'cdate', width: 240, hidden: true},
            {label: '更新时间', name: 'udate', index: 'udate', width: 240, hidden: true}
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
            $("#jqgGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });


});


var vm = new Vue({
    el: '#rrapp',
    data: {

        showList: true,
        title: null,
        // 引流数据
        fcomin: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        }
    }
})