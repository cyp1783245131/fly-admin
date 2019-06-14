/**
 * Created by Administrator on 2019/5/13.
 */
$(function () {

    $("#jqGrid").jqGrid({
        url: baseURL + '/sys/merchant/list',
        datatype: "json",
        colModel: [
            {label: 'mid', name: 'mid', index: "mid", width: 230, hidden: true},
            {label: '租户名称', name: 'merchantName', index: "merchant_name", width: 230},
            {label: '租户简称', name: 'merchantJiancheng', index: "merchant_jiancheng", width: 230},
            {label: '开始时间', name: 'openTime', index: "open_time", width: 230},
            {label: '结束时间', name: 'endTime', index: "end_time", width: 230},
            {
                label: '开通状态',
                name: 'merchantType',
                index: "merchant_type",
                width: 220,
                formatter: function (value, options, rowObject) {
                    return value == 1 ?
                        "<input type='button' value='禁用' class='btn btn-danger btn-xs' onclick='subType(\" " + options.rowId + " \")' />" :
                        "<input type='button' value='正常' class='btn btn-success btn-xs'onclick='subType(\" " + options.rowId + " \")' />" ;
                }
            },
            {
                label: '操作',
                name: 'mid',
                index: "mid",
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
            merchantName: null
        },
        showList: true,
        title: null,
        roleList: {},
        merchant : {
            merchantName: null,
            merchantJiancheng: null,
            contractCreateTime: null,
            contractEndTime: null,
            openTime: null,
            endTime: null,
            contraceNumber: null,
            merchantType: 1
        },
        merchantList : []
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.roleList = {};
            vm.merchant = {
                merchantName: null,
                merchantJiancheng: null,
                contractCreateTime: null,
                contractEndTime: null,
                openTime: null,
                endTime: null,
                contraceNumber: null,
                merchantType: 0
            };
        },
        //修改按钮
        update: function () {
            var id = getSelectedRow();
            var rowData = jQuery("#jqGrid").jqGrid("getRowData", id);



            var merchantId = rowData.mid;
            if (merchantId == null) {
                return;
            }

            vm.showList = false;
            vm.title = "修改";

            vm.getMerchant(merchantId);

        },
        //删除引流数据，可以批量删除
        del: function () {
            var merchantIds = [];
            var ids = getSelectedRows();
            for (var i = 0; i < ids.length; i++) {
                var rowData = jQuery("#jqGrid").jqGrid("getRowData", ids[i]);
                merchantIds.push(rowData.mid)
            }

            if (merchantIds == null) {
                return;
            }

            //
            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/merchant/delete",
                    contentType: "application/json",
                    data: JSON.stringify(merchantIds),
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
        },
        //保存和修复
        saveOrUpdate: function () {

            //非空校验
            var merchantName=$("#merchantName").val();
            if (merchantName == '' || merchantName == undefined || merchantName == null){
                $("#merchantName").css('borderColor','red'); //添加css样式
                alert("租户名称不能为空")
                return;
            }else {
                $("#merchantName").css('borderColor',''); //取消css样式
            }

            var merchantJiancheng=$("#merchantJiancheng").val();
            if (merchantJiancheng == '' || merchantJiancheng == undefined || merchantJiancheng == null){
                $("#merchantJiancheng").css('borderColor','red'); //添加css样式
                alert("租户简称不能为空")
                return;
            }else {
                $("#merchantJiancheng").css('borderColor',''); //取消css样式
            }

            var contractCreateTime=$("#contractCreateTime").val();
            if (contractCreateTime == '' || contractCreateTime == undefined || contractCreateTime == null){
                $("#contractCreateTime").css('borderColor','red'); //添加css样式
                alert("合同开始时间不能为空")
                return;
            }else {
                $("#contractCreateTime").css('borderColor',''); //取消css样式
            }

            var contractEndTime=$("#contractEndTime").val();
            if (contractEndTime == '' || contractEndTime == undefined || contractEndTime == null){
                $("#contractEndTime").css('borderColor','red'); //添加css样式
                alert("合同结束时间不能为空")
                return;
            }else {
                $("#contractEndTime").css('borderColor',''); //取消css样式
            }

            var openTime=$("#openTime").val();
            if (openTime == '' || openTime == undefined || openTime == null){
                $("#openTime").css('borderColor','red'); //添加css样式
                alert("开通时间不能为空")
                return;
            }else {
                $("#openTime").css('borderColor',''); //取消css样式
            }

            var endTime=$("#endTime").val();
            if (endTime == '' || endTime == undefined || endTime == null){
                $("#endTime").css('borderColor','red'); //添加css样式
                alert("结束时间不能为空")
                return;
            }else {
                $("#endTime").css('borderColor',''); //取消css样式
            }

            var contraceNumber=$("#contraceNumber").val();
            if (contraceNumber == '' || contraceNumber == undefined || contraceNumber == null){
                $("#endTime").css('borderColor','red'); //添加css样式
                alert("合同编号不能为空")
                return;
            }else {
                $("#contraceNumber").css('borderColor',''); //取消css样式
            }





            //提交数据
            var url = "sys/merchant/saveOrUpdate";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.merchant),
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
        //根据id获得对应引流对象
        getMerchant: function (merchantId) {
            var url = "sys/merchant/getMerchantById/" + merchantId;
            $.ajax({
                type: "get",
                url: baseURL + url,
                contentType: "application/json",
                data: "",
                success: function (r) {
                    vm.merchant = r.merchant;
                    console.info(vm.merchant)
                }
            });
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'merchantName': vm.q.merchantName},
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
    }

});

$(function () {
    $('#mySwitch').on('switch-change', function (e, data) {
        var $el = $(data.el)
            , value = data.value;
//            console.log(e);
//            console.log( $el);
//            console.log(value);
        if (value) {
            vm.merchant.merchantType = 0
        } else {
            vm.merchant.merchantType = 1
        }
    });
})

function subType(a) {

    var mid = '';
    var rowData = vm.merchantList;
    for(var i = 0 ; i < rowData.length ; i ++){
        if(a == i+1){
            console.info(rowData[i])
            mid = rowData[i].mid
        }
    }
    confirm('确定要修改计划状态吗？', function () {
        $.ajax({
            type: "get",
            url: baseURL + "sys/merchant/subType/"+mid,
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
function deleteByid(a) {
    var mid = '';
    var rowData = vm.merchantList;
    for(var i = 0 ; i < rowData.length ; i ++){
        if(a == rowData[i].mid){
            console.info(rowData[i])
            mid = rowData[i].mid
        }
    }
    confirm('确定要删除选中的记录？', function () {
        $.ajax({
            type: "POST",
            url: baseURL + "sys/merchant/deleteByid/"+mid,
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
    var mid = '';
    var rowData = vm.merchantList;
    for(var i = 0 ; i < rowData.length ; i ++){
        if(a == rowData[i].mid){
            console.info(rowData[i])
            mid = rowData[i].mid
        }
    }
    vm.showList = false;
    vm.title = "修改";

    vm.getMerchant(mid);
}
//执行一个laydate实例
layui.use(['laydate','upload'], function() {
    var $ = layui.jquery,upload = layui.upload;
    var laydate = layui.laydate;

    //合同开始时间
    laydate.render({
        elem: '#contractCreateTime',
        type: 'datetime',
        done: function (value, date, endDate) {
            vm.merchant.contractCreateTime = value;
        }
    });

    //合同结束时间
    laydate.render({
        elem: '#contractEndTime'
        ,type: 'datetime',
        done: function (value, date, endDate) {
            vm.merchant.contractEndTime = value;
        }
    });

    //开通时间
    laydate.render({
        elem: '#openTime'
        ,type: 'datetime',
        done: function (value, date, endDate) {
            vm.merchant.openTime = value;
        }
    });

    //关闭时间
    laydate.render({
        elem: '#endTime'
        ,type: 'datetime',
        done: function (value, date, endDate) {
            vm.merchant.endTime = value;
        }
    });

});

$(function () {
    Date.prototype.Format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "H+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
})

