$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 's/region/list',
        datatype: "json",
        colModel: [
            {label: '主键', name: 'id', index: 'id', width: 50, hidden: true},
            {label: '规则名称', name: 'rname', index: 'rname', width: 240},
            {label: '符合区域比例', name: 'rsure', index: 'rsure', width: 240},
            {label: '不符合区域比例', name: 'rnsure', index: 'rnsure', width: 240},
            {
                label: '操作',
                name: 'id',
                index: "id",
                width: 220,
                formatter: function (value, options, rowObject) {
                    return "<span class='btn btn-primary btn-xs' onclick='updateByid(\" " + rowObject.id + " \")'><i class='fa fa-trash-o'></i>&nbsp;修改</span>&nbsp;&nbsp;&nbsp;" +
                        "<span class='btn btn-primary btn-xs' onclick=' deleteByid(\" " + rowObject.id + " \")'><i class='fa fa-pencil-square-o'></i>&nbsp;删除</span>";
                }
            }
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 30,
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
// var layedit;
var vm = new Vue({
    el: '#rrapp',
    data: {
        // 查询参数
        q: {
            rname: null
        },
        showList: true,
        title: null,
        region: {
            rname: "",
            rsure: "",
            rnsure: ""
        },
        rsure: "",
        regionList: [],


    },

    methods: {
        query: function () {
            vm.reload();
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'rname': vm.q.rname},
                page: page
            }).trigger("reloadGrid");
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增计划";
            vm.region = {};
        },
        saveOrUpdate: function (event) {
            var url = vm.region.id == null ? "s/region/save" : "s/region/update";
            var rname = vm.region.rname;
            var rsure = vm.rsure;
            var rnsure = vm.region.rnsure
            if (!rname) {
                alert("规则名称不能为空！");
                return;
            }

            if (!rsure) {
                alert("符合区域比例不能为空！");
                return;
            }
            if (!rnsure) {
                alert("不符合区域比例不能为空！");
                return;
            }
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.region),
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
            //名称
            var rname = vm.region.rname;
            if (rname) {
                vm.rname = rname;
                $("#rname").val(vm.rname);
            }
        },
        getInfo: function (id) {
            $.get(baseURL + "s/region/info/" + id, function (r) {
                    console.log(r.region.rnsure)
                    vm.region = r.region;
                    vm.rsure = r.region.rsure;
                vm.region.rnsure = r.region.rnsure;
                    // vm.region.rnsure = r.region.rnsure;
                    $.get(baseURL + "s/region/findAll", function (r) {
                        //console.log(r);
                        vm.regionList = r.regionList;

                    });
                }
            );

        }

    },
    created: function () {
        $.ajax({
            type: "get",
            url: baseURL + "s/region/findAll",
            contentType: "application/json",
            data: "",
            success: function (r) {
                vm.regionList = r.regionList
            }
        });
    },
    computed: {
        rnsureMessage: function () {
            var rnsure = 100 - this.rsure;
            var num = parseFloat(rnsure).toFixed(1);
            this.region.rnsure = num;
            return num;
        }
    }
});

function updateByid(a) {
    var id = 0;
    var rowData = vm.regionList;
    for (var i = 0; i < rowData.length; i++) {
        if (a == rowData[i].id) {
            id = rowData[i].id
        }
    }
    vm.showList = false;
    vm.title = "修改";
    vm.getInfo(id);
}

function deleteByid(a) {
    var id = "";
    var rowData = vm.regionList;
    for (var i = 0; i < rowData.length; i++) {
        if (a == rowData[i].id) {
            id = rowData[i].id
        }
    }
    confirm('确定要删除选中的记录？', function () {
        $.ajax({
            type: "POST",
            url: baseURL + "s/region/deleteByid/" + id,
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