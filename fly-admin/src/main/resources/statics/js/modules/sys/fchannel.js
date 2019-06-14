$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/fchannel/list',
        datatype: "json",
        colModel: [
            {label: '主键', name: 'id', index: 'id', width: 60, hidden: true},
            {label: '渠道名称', name: 'cname', index: 'cname', width: 60},
            {
                label: '渠道图片', name: 'cimage', index: 'cimage', width: 60, formatter: function (value, options, row) {
                    return value === "0" ? '<span class="label label-danger">禁用</span>' :
                        '<span class="label label-success">启用</span>';
                }
            },
            {
                label: '渠道url', name: 'curl', index: 'curl', width: 60, formatter: function (value, options, row) {
                    return value === "0" ? '<span class="label label-danger">禁用</span>' :
                        '<span class="label label-success">启用</span>';
                }
            },
            {
                label: '微盟用户', name: 'cwm', index: 'cwm', width: 60, formatter: function (value, options, row) {
                    return value === "0" ? '<span class="label label-danger">禁用</span>' :
                        '<span class="label label-success">启用</span>';
                }
            },
            {
                label: '渠道文案', name: 'cwenan', index: 'cwenan', width: 60, formatter: function (value, options, row) {
                    return value === "0" ? '<span class="label label-danger">禁用</span>' :
                        '<span class="label label-success">启用</span>';
                }
            },
            {
                label: '渠道状态', name: 'cstatus', index: 'cstatus', width: 60, formatter: function (value, options, row) {
                    return value === "0" ? '<span class="label label-danger">禁用</span>' :
                        '<span class="label label-success">启用</span>';
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


var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        // 引流数据
        fchannel: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {},
                page: page
            }).trigger("reloadGrid");
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增渠道";
            vm.fchannel = {};
            var arr = ['cimage', 'curl', 'cwm', 'cwenan', 'cstatus'];

            $.each(arr, function (index, item) {
                vm.fchannel[item] = "1";
                $('#' + item).bootstrapSwitch('destroy');
                $('#' + item).bootstrapSwitch({
                    onText: "开启",
                    offText: "禁用",
                    onColor: "success",
                    offColor: "danger",
                    size: "mini",
                    state: true,
                    onSwitchChange: function (event, state) {
                        if (state == true) {
                            vm.fchannel[item] = "1";
                        } else {
                            vm.fchannel[item] = "0";
                        }
                    }
                });
            })


        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改计划";
            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            var url = vm.fchannel.id == null ? "sys/fchannel/save" : "sys/fchannel/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.fchannel),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        //alert("信息未填完整");
                    }
                }
            });
            //引流名称
            var cname = vm.fchannel.cname;
            if (cname) {
                vm.cname = cname;
                $("#cname").val(vm.cname);
            }

            var name = $("#cname").val();
            if (name == '' || name == undefined || name == null) {
                $("#cname").css('borderColor', 'red'); //添加css样式
                alert("渠道名称不能为空");
                return;
            }

        },
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/fchannel/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function (id) {
            $.get(baseURL + "sys/fchannel/info/" + id, function (r) {
                    vm.fchannel = r.fchannel;
                    //渠道名称
                    var cname = vm.fchannel.cname;
                    if (cname) {
                        vm.cname = cname;
                        $("#cname").val(vm.cname);
                    }

                    var name = $("#cname").val();
                    if (name == '' || name == undefined || name == null) {
                        $("#cname").css('borderColor', 'red'); //添加css样式
                        alert("渠道名称不能为空");
                        return;
                    } else {
                        $("#cname").css('borderColor', ''); //取消css样式
                    }

                    var arr = ['cimage', 'curl', 'cwm', 'cwenan', 'cstatus'];

                    $.each(arr, function (index, item) {
                        vm.fchannel[item] = "1";
                        $('#' + item).bootstrapSwitch('destroy');
                        $('#' + item).bootstrapSwitch({
                            onText: "开启",
                            offText: "禁用",
                            onColor: "success",
                            offColor: "danger",
                            size: "mini",
                            state: true,
                            onSwitchChange: function (event, state) {
                                if (state == true) {
                                    vm.fchannel[item] = "1";
                                } else {
                                    vm.fchannel[item] = "0";
                                }
                            }
                        });
                    })

                }
            );

        }
    }
});