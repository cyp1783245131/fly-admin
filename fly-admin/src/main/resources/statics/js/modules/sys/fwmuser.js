$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/fwmuser/list',
        datatype: "json",
        colModel: [
            {label: '主键', name: 'id', index: 'id', width: 60, hidden: true},
            {label: '微盟用户手机号', name: 'mobile', index: 'mobile', width: 60},
            {label: '用户名称', name: 'name', index: 'name', width: 60},
            {label: '身份证号码', name: 'certifynum', index: 'certifynum', width: 60},
            {label: '城市名称', name: 'cityname', index: 'cityname', width: 60}

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
            mobile: null
        },
        showList: true,
        title: null,
        // 引流数据
        fwmuser: {}
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
            vm.title = "新增微盟用户";
            $('#cityname').data("value","");
            $('#cityid').val("");
            $('#cityname').val("");
            vm.fwmuser = {};

        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改微盟用户";
            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            vm.fwmuser.cityid = $('#cityname').data("value");
            vm.fwmuser.cityname = $('#cityname').val();
            var url = vm.fwmuser.id == null ? "sys/fwmuser/save" : "sys/fwmuser/update";

            var password = vm.fwmuser.password;
            var cpassword = vm.fwmuser.cpassword;
            var mobile = vm.fwmuser.mobile;
            var name = vm.fwmuser.name;
            var certifynum = vm.fwmuser.certifynum;
            var cityname = vm.fwmuser.cityname;
            var banknum = vm.fwmuser.banknum;
            if (!mobile) {
                alert("手机号不能为空！");
                return;
            }

            if (!name) {
                alert("姓名不能为空！");
                return;
            }
            if (!certifynum) {
                alert("身份证号不能为空！");
                return;
            }


            if (!cityname) {
                alert("城市不能为空！");
                return;
            }
            if (!password || !cpassword) {
                alert("密码不能为空！");
                return;
            }
            if (password != cpassword) {
                alert("输入密码不一致！");
                return;
            }
            if (!banknum) {
                // if (!checkBankNo(banknum)) {
                    alert("银行卡号输入错误！");
                    return;
                // }

            }

            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.fwmuser),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
            //引流名称
            var mobile = vm.fwmuser.mobile;
            if (mobile) {
                vm.mobile = mobile;
                $("#mobile").val(vm.mobile);
            }

            var mobile = $("#mobile").val();
            if (mobile == '' || mobile == undefined || mobile == null) {
                $("#mobile").css('borderColor', 'red'); //添加css样式
                alert("用户手机号不能为空");
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
                    url: baseURL + "sys/fwmuser/delete",
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
            $.get(baseURL + "sys/fwmuser/info/" + id, function (r) {
                    vm.fwmuser = r.fwmuser;
                    vm.fwmuser.cpassword = vm.fwmuser.password;
                    $('#cityname').data("value", vm.fwmuser.cityid);
                    $('#cityid').val(vm.fwmuser.cityid);
                    $('#cityname').val(vm.fwmuser.cityname);

                }
            );

        }

    },
    checked: {

    }
});