/**
 * Created by Administrator on 2019/5/20.
 */
/**
 * Created by Administrator on 2019/5/13.
 */
$(function () {

    $("#jqGrid").jqGrid({
        url: baseURL + '/sys/calculation/list',
        datatype: "json",
        colModel: [
            {label: 'calculation_id', name: 'calculationId', index: "calculation_id", width: 230, hidden: true},
            {label: '计算规则', name: 'rule', index: "rule", width: 230},
            {label: '开始时间', name: 'createTime', index: "create_time", width: 230},
            {label: '结束时间', name: 'endTime', index: "end_time", width: 230},
            {label: '可使用额度', name: 'usableQuota', index: "usable_quota", width: 230},
            {label: '新锐营', name: 'company1', index: "company1", width: 230},
            {label: '益斗', name: 'company2', index: "company2", width: 230},
            {label: '第三方', name: 'thirdparty', index: "thirdparty", width: 230},
            {
                label: '操作',
                name: 'calculationId',
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
            rule: null
        },
        showList: true,
        title: null,
        roleList: {},
        calculation : {
            rule: null,
            createTime: null,
            endTime: null,
            usableQuota: null,
            company1: null,
            company2: null,
            thirdparty: null
        },
        calculationList : []
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.roleList = {};
            vm.calculation = {
                rule: null,
                createTime: null,
                endTime: null,
                usableQuota: null,
                company1: null,
                company2: null,
                thirdparty: null
            };
        },
        //修改按钮
        update: function () {
            var id = getSelectedRow();
            var rowData = jQuery("#jqGrid").jqGrid("getRowData", id);

            var calculationId = rowData.calculationId;
            if (calculationId == null) {
                return;
            }
            alert(calculationId)
            vm.showList = false;
            vm.title = "修改";

            vm.getCalculation(calculationId);
        },
        //批量删除
        del: function () {
            var calculationIds = [];
            var ids = getSelectedRows();
            for (var i = 0; i < ids.length; i++) {
                var rowData = jQuery("#jqGrid").jqGrid("getRowData", ids[i]);
                calculationIds.push(rowData.calculationId)
                console.info(rowData)
            }

            if (calculationIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/calculation/delete",
                    contentType: "application/json",
                    data: JSON.stringify(calculationIds),
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
            var rule=$("#rule").val();
            if (rule == '' || rule == undefined || rule == null){
                $("#rule").css('borderColor','red'); //添加css样式
                alert("计算规则不能为空")
                return;
            }else {
                $("#rule").css('borderColor',''); //取消css样式
            }

            var createTime=$("#createTime").val();
            if (createTime == '' || createTime == undefined || createTime == null){
                $("#createTime").css('borderColor','red'); //添加css样式
                alert("开始时间不能为空")
                return;
            }else {
                $("#createTime").css('borderColor',''); //取消css样式
            }

            var endTime=$("#endTime").val();
            if (endTime == '' || endTime == undefined || endTime == null){
                $("#endTime").css('borderColor','red'); //添加css样式
                alert("结束时间不能为空")
                return;
            }else {
                $("#endTime").css('borderColor',''); //取消css样式
            }

            var usableQuota=$("#usableQuota").val();
            if (usableQuota == '' || usableQuota == undefined || usableQuota == null){
                $("#usableQuota").css('borderColor','red'); //添加css样式
                alert("可使用额度不能为空")
                return;
            }else {
                $("#usableQuota").css('borderColor',''); //取消css样式
            }

            var company1=$("#company1").val();
            if (company1 == '' || company1 == undefined || company1 == null){
                $("#company1").css('borderColor','red'); //添加css样式
                alert("新锐营不能为空")
                return;
            }else {
                $("#company1").css('borderColor',''); //取消css样式
            }

            var company2=$("#company2").val();
            if (company2 == '' || company2 == undefined || company2 == null){
                $("#company2").css('borderColor','red'); //添加css样式
                alert("益斗不能为空")
                return;
            }else {
                $("#company2").css('borderColor',''); //取消css样式
            }

            var thirdparty=$("#thirdparty").val();
            if (thirdparty == '' || thirdparty == undefined || thirdparty == null){
                $("#thirdparty").css('borderColor','red'); //添加css样式
                alert("第三方不能为空")
                return;
            }else {
                $("#thirdparty").css('borderColor',''); //取消css样式
            }






            //提交数据
            var url = "sys/calculation/saveOrUpdate";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.calculation),
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
        getCalculation: function (calculationId) {
            var url = "sys/calculation/getCalculationById/" + calculationId;
            $.ajax({
                type: "get",
                url: baseURL + url,
                contentType: "application/json",
                data: "",
                success: function (r) {
                    vm.calculation = r.calculation;
                }
            });
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'rule': vm.q.rule},
                page: page
            }).trigger("reloadGrid");
        }
    },
    created : function () {
        var url = "sys/calculation/findAll";
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

function deleteByid(a) {
    var calculationId = '';
    var rowData = vm.calculationList;
    for(var i = 0 ; i < rowData.length ; i ++){
        if(a == rowData[i].calculationId){
            console.info(rowData[i])
            calculationId = rowData[i].calculationId
        }
    }
    confirm('确定要删除选中的记录？', function () {
        $.ajax({
            type: "POST",
            url: baseURL + "sys/calculation/deleteByid/"+calculationId,
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
    var calculationId = '';
    var rowData = vm.calculationList;
    for(var i = 0 ; i < rowData.length ; i ++){
        if(a == rowData[i].calculationId){
            console.info(rowData[i])
            calculationId = rowData[i].calculationId
        }
    }
    vm.showList = false;
    vm.title = "修改";

    console.info(a)
    vm.getCalculation(calculationId);
}
//执行一个laydate实例
layui.use(['laydate','upload'], function() {
    var $ = layui.jquery,upload = layui.upload;
    var laydate = layui.laydate;

    //开始时间
    laydate.render({
        elem: '#createTime',
        type: 'datetime',
        done: function (value, date, endDate) {
            vm.calculation.createTime = value;
        }
    });



    //结束时间
    laydate.render({
        elem: '#endTime'
        ,type: 'datetime',
        done: function (value, date, endDate) {
            vm.calculation.endTime = value;
        }
    });

});
$("#jqGrid").jqGrid("setGridParam", {
    postData: {}
}).trigger("reloadGrid");