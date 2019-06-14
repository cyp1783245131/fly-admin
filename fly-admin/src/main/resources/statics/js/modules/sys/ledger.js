/**
 * Created by Administrator on 2019/5/20.
 */
/**
 * Created by Administrator on 2019/5/13.
 */
$(function () {

    $("#jqGrid").jqGrid({
        url: baseURL + '/sys/ledger/list',
        datatype: "json",
        colModel: [
            {label: 'ledger_id', name: 'ledgerId', index: "ledger_id", width: 230, hidden: true},
            {label: '台账名称', name: 'ledgerName', index: "ledger_name", width: 230},
            {label: '商户类型', name: 'merchantType', index: "merchant_type", width: 230},
            {label: '金额', name: 'ledgerMoney', index: "ledger_money", width: 230},
            {label: '商户名称', name: 'merchant.merchantName', index: "merchant.merchant_name", width: 230},
            {label: '可使用资金', name: 'ksyzj', index: "ksyzj", width: 230},
            {label: '新锐营', name: 'xry', index: "xry", width: 230},
            {label: '益斗', name: 'yd', index: "yd", width: 230},
            {label: '第三方', name: 'dsf', index: "dsf", width: 230},
            {label: '规则名称', name: 'calculation.rule', index: "calculation.rule", width: 230},
            {label: '台账状态', name: 'ledgerType', index: "ledger_type", width: 230,
                formatter: function(value, options, row){
                    return value == 1 ?
                        "<input type='button' value='已完成' class='btn btn-success btn-xs' />" :
                        "<input type='button' value='未完成' class='btn btn-danger btn-xs'/>" ;
                }},

            {label: '时间', name: 'ledgerTime', index: "ledger_time", width: 230},
            {
                label: '操作',
                index: "ledger_id",
                name: "ledgerId",
                width: 220,
                formatter: function (value, options, rowObject) {
                    return "<a class='btn btn-primary btn-xs' onclick='deleteByid(\" " + value + " \")'><i class='fa fa-trash-o'></i>&nbsp;删除</a>" +
                        "<a class='btn btn-primary btn-xs' onclick='updateByid(\" " + value + " \")'><i class='fa fa-pencil-square-o'></i>&nbsp;修改</a>" ;
                }
            }
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
var ztree;
var layedit;
var vm = new Vue({
    el: '#app',
    data: {
        q: {
            ledgerName: null
        },
        showList: true,
        title: null,
        roleList: {},
        ledger : {
            ledgerName : null,
            ledgerMoney : null,
            ledgerType : null,
            ledgerTime : null,
            ledgerRemarks : null,
            calculationId : 0,
            merchantId : 0,
            merchantType : 0
        },
        merchantList : [],
        calculationList : [],
        ledgerList : []
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.roleList = {};
            vm.ledger = {
                ledgerName : null,
                ledgerMoney : null,
                ledgerType : 0,
                ledgerTime : null,
                ledgerRemarks : null,
                calculationId : 0,
                merchantId : 0,
                merchantType : 0
            };
        },
        //保存和修复
        saveOrUpdate: function () {
            //非空校验
            var ledgerName=$("#ledgerName").val();
            if (ledgerName == '' || ledgerName == undefined || ledgerName == null){
                $("#ledgerName").css('borderColor','red'); //添加css样式
                alert("台账名称不能为空")
                return;
            }else {
                $("#ledgerName").css('borderColor',''); //取消css样式
            }

            var ledgerMoney=$("#ledgerMoney").val();
            if (ledgerMoney == '' || ledgerMoney == undefined || ledgerMoney == null){
                $("#ledgerMoney").css('borderColor','red'); //添加css样式
                alert("金额不能为空")
                return;
            }else {
                $("#ledgerMoney").css('borderColor',''); //取消css样式
            }

            var calculationId=$("#calculationId").val();
            if (calculationId == '' || calculationId == undefined || calculationId == null || calculationId == 0){
                $("#calculationId").css('borderColor','red'); //添加css样式
                alert("规则名称不能为空")
                return;
            }else {
                $("#calculationId").css('borderColor',''); //取消css样式
            }

            var merchantType=$("#merchantType").val();
            if (merchantType == '' || merchantType == undefined || merchantType == null || merchantType == 0){
                $("#merchantType").css('borderColor','red'); //添加css样式
                alert("商户类型不能为空")
                return;
            }else {
                $("#merchantType").css('borderColor',''); //取消css样式
            }

            var merchantId=$("#merchantId").val();
            if (merchantId == '' || merchantId == undefined || merchantId == null ||merchantId == 0){
                $("#merchantId").css('borderColor','red'); //添加css样式
                alert("商户名称不能为空")
                return;
            }else {
                $("#merchantId").css('borderColor',''); //取消css样式
            }

            var ledgerType=$("#ledgerType").val();
            if (ledgerType == '' || ledgerType == undefined || ledgerType == null || ledgerType == 0){
                $("#ledgerType").css('borderColor','red'); //添加css样式
                alert("状态不能为空")
                return;
            }else {
                $("#ledgerType").css('borderColor',''); //取消css样式
            }

            var ledgerTime=$("#ledgerTime").val();
            if (ledgerTime == '' || ledgerTime == undefined || ledgerTime == null){
                $("#ledgerTime").css('borderColor','red'); //添加css样式
                alert("时间不能为空")
                return;
            }else {
                $("#ledgerTime").css('borderColor',''); //取消css样式
            }

            var ledgerRemarks=$("#ledgerRemarks").val();
            if (ledgerRemarks == '' || ledgerRemarks == undefined || ledgerRemarks == null){
                $("#ledgerRemarks").css('borderColor','red'); //添加css样式
                alert("备注信息不能为空")
                return;
            }else {
                $("#ledgerRemarks").css('borderColor',''); //取消css样式
            }



            //提交数据
            var url = "sys/ledger/saveOrUpdate";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.ledger),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function () {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        //根据id获得对应对象
        getLedger: function (ledgerId) {
            var url = "sys/ledger/getLedgerById/" + ledgerId;
            $.ajax({
                type: "get",
                url: baseURL + url,
                contentType: "application/json",
                data: "",
                success: function (r) {
                    vm.ledger = r.ledger;
                }
            });
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'ledgerName': vm.q.ledgerName},
                page: page
            }).trigger("reloadGrid");
        }
    },
    created : function () {
        var url = "sys/merchant/findAll";
        $.ajax({
            type: "get",
            url: baseURL + url,
            contentType: "application/json",
            data: "",
            success: function (r) {
                vm.merchantList = r.merchantList;
            }
        });

        url = "sys/ledger/findAll";
        $.ajax({
            type: "get",
            url: baseURL + url,
            contentType: "application/json",
            data: "",
            success: function (r) {
                vm.ledgerList = r.ledgerList;
            }
        });

        url = "sys/calculation/findAll";
        $.ajax({
            type: "get",
            url: baseURL + url,
            contentType: "application/json",
            data: "",
            success: function (r) {
                vm.calculationList = r.calculationList;
            }
        });

    }

});

function deleteByid(a) {
    var ledgerId = '';
    var rowData = vm.ledgerList;
    for(var i = 0 ; i < rowData.length ; i ++){
        if(a == rowData[i].ledgerId){
            console.info(rowData[i])
            ledgerId = rowData[i].ledgerId
        }
    }
    confirm('确定要删除选中的记录？', function () {
        $.ajax({
            type: "POST",
            url: baseURL + "sys/ledger/deleteByid/"+ledgerId,
            contentType: "application/json",
            data: "",
            success: function (r) {
                if (r.code == 0) {
                    alert('操作成功', function () {
                        vm.reload();
                    });
                } else {
                    alert(r.msg);
                }
            }
        });
    });

}
function updateByid(a) {
    var ledgerId = '';
    var rowData = vm.ledgerList;
    for(var i = 0 ; i < rowData.length ; i ++){
        if(a == rowData[i].ledgerId){
            console.info(rowData[i])
            ledgerId = rowData[i].ledgerId
        }
    }
    vm.showList = false;
    vm.title = "修改";

    vm.getLedger(ledgerId);
}

//执行一个laydate实例
layui.use(['laydate','upload'], function() {
    var $ = layui.jquery,upload = layui.upload;
    var laydate = layui.laydate;

    //合同开始时间
    laydate.render({
        elem: '#ledgerTime',
        type: 'datetime',
        format: 'yyyy-MM-dd HH:mm:ss', //可任意组合
        done: function (value, date, endDate) {
            vm.ledger.ledgerTime = value;
        }
    });
});

$("#jqGrid").jqGrid("setGridParam", {
    postData: {}
}).trigger("reloadGrid");