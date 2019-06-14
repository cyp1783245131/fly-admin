$(function () {
    function  getParamValue (id) {
        var reg = new RegExp("(^|&)" + id + "=([^&]*)(&|$)", "i"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg); //匹配目标参数
        if (r != null) return unescape(r[2]); //返回参数值
        return null;
    }
    var id = getParamValue('id');

    $("#jqAreaGrid").jqGrid({
        url: baseURL + 'sys/fcomin/list',
        datatype: "json",
        postData: {"id": id, "type": "area"},
        colModel: [
            {label: '主键', name: 'id', index: 'id', width: 240, key: true, hidden: true},
            {label: 'IP地址', name: 'fip', index: 'fip', width: 240},
            {label: '来源', name: 'fsource', index: 'fsource', width: 240},
            {label: '日期', name: 'fdate', index: 'fdate', width: 240},
            {label: '时间', name: 'ftime', index: 'ftime', width: 240},
            {label: '浏览次数', name: 'fbrowse', index: 'fbrowse', width: 240},
            {label: '预览转化率', name: 'fconversion', index: 'fconversion', width: 240},
            {label: '访问地址', name: 'faddress', index: 'faddress', width: 240, hidden: true},
            {label: '引流区域', name: 'farea', index: 'farea', width: 240, hidden: true},
            {label: '是否区域内', name: 'fisarea', index: 'fisarea', width: 240, hidden: true},
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
        pager: "#jqAreaGridPager",
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

    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        // 获取已激活的标签页的名称
        var activeTab = $(e.target).text();
        //alert(activeTab);
        if(activeTab=='导出区域引流'){
            confirm('确定导出区域引流记录？', function(){
                window.open(baseURL + 'sys/fcomin/export?type=area&id='+id);
            });
        }
        if(activeTab=='导出总量引流'){
            confirm('确定导出总量引流记录？', function(){
                window.open(baseURL + 'sys/fcomin/export?type=all&id='+id);
            });
        }
        if(activeTab=='符合区域引流列表') {

            $("#jqAreaGrid").jqGrid({
                url: baseURL + 'sys/fcomin/list',
                datatype: "json",
                postData: {"id": id, "type": "area"},
                colModel: [
                    {label: '主键', name: 'id', index: 'id', width: 240, key: true, hidden: true},
                    {label: 'IP地址', name: 'fip', index: 'fip', width: 240},
                    {label: '来源', name: 'fsource', index: 'fsource', width: 240},
                    {label: '日期', name: 'fdate', index: 'fdate', width: 240},
                    {label: '时间', name: 'ftime', index: 'ftime', width: 240},
                    {label: '浏览次数', name: 'fbrowse', index: 'fbrowse', width: 240},
                    {label: '预览转化率', name: 'fconversion', index: 'fconversion', width: 240},
                    {label: '访问地址', name: 'faddress', index: 'faddress', width: 240, hidden: true},
                    {label: '引流区域', name: 'farea', index: 'farea', width: 240, hidden: true},
                    {label: '是否区域内', name: 'fisarea', index: 'fisarea', width: 240, hidden: true},
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
                pager: "#jqAreaGridPager",
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
                    $("#jqAreaGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                }
            });
        }else {
            $("#jqAllGrid").jqGrid({
                url: baseURL + 'sys/fcomin/list',
                datatype: "json",
                postData: {"id": id, "type": "all"},
                colModel: [
                    {label: '主键', name: 'id', index: 'id', width: 240, key: true, hidden: true},
                    {label: 'IP地址', name: 'fip', index: 'fip', width: 240},
                    {label: '来源', name: 'fsource', index: 'fsource', width: 240},
                    {label: '日期', name: 'fdate', index: 'fdate', width: 240},
                    {label: '时间', name: 'ftime', index: 'ftime', width: 240},
                    {label: '浏览次数', name: 'fbrowse', index: 'fbrowse', width: 240},
                    {label: '预览转化率', name: 'fconversion', index: 'fconversion', width: 240},
                    {label: '访问地址', name: 'faddress', index: 'faddress', width: 240, hidden: true},
                    {label: '引流区域', name: 'farea', index: 'farea', width: 240, hidden: true},
                    {label: '是否区域内', name: 'fisarea', index: 'fisarea', width: 240, hidden: true},
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
                pager: "#jqAllGridPager",
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
                    $("#jqAllGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                }
            });
        }
    });
    $.ajax({
        type: "POST",
        url: baseURL + 'sys/fcomin/count?id='+id,
        contentType: "application/json",
        success: function(r){
            $("#z_count").append(r.count+"个")
            $("#f_count").append(r.area_count+"个")
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
                var page = $("#jqGrid").jqGrid('getGridParam','page');
                $("#jqGrid").jqGrid('setGridParam',{
                    page:page
                }).trigger("reloadGrid");
            }
        }
    })