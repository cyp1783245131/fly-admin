/**
 * Created by Administrator on 2019/5/20.
 */
/**
 * Created by Administrator on 2019/5/13.
 */
$(function () {

    $("#jqGrid").jqGrid({
        url: baseURL + '/sys/cooperation/list',
        datatype: "json",
        colModel: [
            {label: 'cooperation_id', name: 'cooperationId', index: "cooperation_id", width: 230, hidden: true},
            {label: '商户名称', name: 'merchantEntity.merchantName', index: "merchantEntity.mid", width: 230},
            {label: '使用功能', name: 'cooperationType', index: "cooperation_type", width: 230},
            {
                label: '操作',
                index: "cooperation_id",
                name: "cooperationId",
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
            merchantId: null
        },
        showList: true,
        title: null,
        roleList: {},
        cooperation : {
            cooperation_id : null,
            merchant_id : 0,
            cooperation_type : null
        },
        merchantList : [],
        cooperationList : []
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.roleList = {};
            vm.cooperation = {
                merchantId : null,
                cooperationType : null
            };
        },
        //修改按钮
        update: function () {
            var id = getSelectedRow();
            var rowData = jQuery("#jqGrid").jqGrid("getRowData", id);



            var cooperationId = rowData.mid;
            if (cooperationId == null) {
                return;
            }

            vm.showList = false;
            vm.title = "修改";

            vm.getCooperation(cooperationId);

        },
        //批量删除
        del: function () {
            var cooperationIds = [];
            var ids = getSelectedRows();
            for (var i = 0; i < ids.length; i++) {
                var rowData = jQuery("#jqGrid").jqGrid("getRowData", ids[i]);
                cooperationIds.push(rowData.cooperationId)
                console.info(rowData)
            }

            if (cooperationIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/cooperation/delete",
                    contentType: "application/json",
                    data: JSON.stringify(cooperationIds),
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
            var merchantId=$("#merchantId").val();
            if (merchantId == '' || merchantId == undefined || merchantId == null || merchantId == 0){
                $("#merchantId").css('borderColor','red'); //添加css样式
                alert("商户名称不能为空")
                return;
            }else {
                $("#merchantId").css('borderColor',''); //取消css样式
            }

            var cooperationType=$("#cooperationType").val();
            if (cooperationType == '' || cooperationType == undefined || cooperationType == null){
                $("#cooperationType").css('borderColor','red'); //添加css样式
                alert("使用功能不能为空")
                return;
            }else {
                $("#cooperationType").css('borderColor',''); //取消css样式
            }


            //提交数据
            var url = "sys/cooperation/saveOrUpdate";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.cooperation),
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
        getCooperation: function (cooperationId) {
            var url = "sys/cooperation/getCooperationById/" + cooperationId;
            $.ajax({
                type: "get",
                url: baseURL + url,
                contentType: "application/json",
                data: "",
                success: function (r) {
                    vm.cooperation = r.cooperation;
                }
            });
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'merchantId': vm.q.merchantId},
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

        url = "sys/cooperation/findAll";
        $.ajax({
            type: "get",
            url: baseURL + url,
            contentType: "application/json",
            data: "",
            success: function (r) {
                vm.cooperationList = r.cooperationList;
            }
        });

    }

});

function deleteByid(a) {
    var cooperationId = '';
    var rowData = vm.cooperationList;
    for(var i = 0 ; i < rowData.length ; i ++){
        if(a == rowData[i].cooperationId){
            console.info(rowData[i])
            cooperationId = rowData[i].cooperationId
        }
    }
    confirm('确定要删除选中的记录？', function () {
        $.ajax({
            type: "POST",
            url: baseURL + "sys/cooperation/deleteByid/"+cooperationId,
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
    var cooperationId = '';
    var rowData = vm.cooperationList;
    for(var i = 0 ; i < rowData.length ; i ++){
        if(a == rowData[i].cooperationId){
            console.info(rowData[i])
            cooperationId = rowData[i].cooperationId
        }
    }
    vm.showList = false;
    vm.title = "修改";

    vm.getCooperation(cooperationId);
}

$("#jqGrid").jqGrid("setGridParam", {
    postData: {}
}).trigger("reloadGrid");