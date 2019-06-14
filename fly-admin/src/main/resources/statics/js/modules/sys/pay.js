$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/pay/list',
        datatype: "json",
        colModel: [
            { label: '操作员', name: 'pid', index: 'pid', width: 80 },
            { label: '充值时间', name: 'ptime', index: 'ptime', width: 80 },
            { label: '充值账户', name: 'pname', index: 'pname', width: 80 },
            { label: '充值金额', name: 'pmoney', index: 'pmoney', width: 80 },
            { label: '账户余额', name: 'psave', index: 'psave', width: 80 },
            { label: '状态', name: 'pstatus', index:'pstatus',width: 80, formatter: function(value, options, row){
                return value === 0 ?
                    '<span class="label label-danger">失败</span>' :
                    '<span class="label label-success">成功</span>';
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
            starttime:null,
            endtime:null,
        },
        showList: true,
        title: null,
        pay: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
            //获取当前时间
            var date = new Date();
            var seperator1 = "-";
            var seperator2 = ":";
            var month = date.getMonth() + 1<10? "0"+(date.getMonth() + 1):date.getMonth() + 1;
            var strDate = date.getDate()<10? "0" + date.getDate():date.getDate();
            var currentdate = date.getFullYear() + seperator1  + month  + seperator1  + strDate
                + " "  + date.getHours()  + seperator2  + date.getMinutes()
                + seperator2 + date.getSeconds();

            vm.showList = false;
            vm.title = "充值服务";
            vm.pay = {
                pid:"13200138000",
                ptime:currentdate,
                pname:"手动虚拟充值",
                psave:"2000",
                pstatus:1
            };
        },
        saveOrUpdate: function (event) {
            $.ajax({
                type: "POST",
                url: baseURL + "sys/pay/save",
                contentType: "application/json",
                data: JSON.stringify(vm.pay),
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