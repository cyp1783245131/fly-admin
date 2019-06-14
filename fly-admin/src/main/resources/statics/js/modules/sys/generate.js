$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 's/generate/list',
        datatype: "json",
        colModel: [
            // {label: '主键', name: 'id', index: 'id', width: 50, hidden: true},
            {label: '规则名称', name: 'rname', index: 'rname', width: 240},
            {label: '符合区域比例', name: 'rsure', index: 'rsure', width: 240},
            {label: '天数', name: 'gdate', index: 'gdate', width: 240},
            {
                label: '操作',
                name: 'id',
                index: 'id',
                width: 220,
                formatter: function (value, options, rowObject) {
                    return "<span class='btn btn-primary btn-xs' onclick='updateByid(\" " + value + " \")'><i class='fa fa-trash-o'></i>&nbsp;修改</span>" +
                        "&nbsp;&nbsp;&nbsp;" +
                        "<span class='btn btn-primary btn-xs' onclick=' deleteByid(\" " + value + " \")'><i class='fa fa-pencil-square-o'></i>&nbsp;删除</span>";
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
        // 单个规则对象
        generate: {
            rname: "",
            rsure: "",
            gdate: ""
        },
        // 所有规则集合
        generateList: [],
        // 符合区域比例集合
        rsureList: [],
        rsure: ""

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
            vm.generate = {};
        },
        saveOrUpdate: function (event) {
            //符合区域比例
            vm.generate.rsure = $("#rsure").val();
            var url = vm.generate.id == null ? "s/generate/save" : "s/generate/update"
            var rname = vm.generate.rname;
            var rsure = vm.rsure;
            var gdate = vm.generate.gdate;
            if (!rname) {
                alert("规则名称不能为空！");
                return;
            }
            if (!gdate) {
                alert("天数不能为空！");
                return;
            }
            if (!rsure) {
                alert("符合区域比例不能为空！");
                return;
            }
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.generate),
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
            var rname = vm.generate.rname;
            if (rname) {
                vm.rname = rname;
                $("#rname").val(vm.rname);
            }
        },
        getInfo: function (id) {
            $.get(baseURL + "s/generate/info/" + id, function (r) {
                    vm.generate = r.generate;
                    vm.rsure = r.generate.rsure;
                    // vm.gdate = r.generate.gdate;
                    $.get(baseURL + "s/generate/findAll", function (r) {
                        vm.generateList = r.generateList;
                    });
                }
            );

        }

    },
    created: function () {
        $.ajax({
            type: "get",
            url: baseURL + "s/generate/findAll",
            contentType: "application/json",
            data: "",
            success: function (r) {
                vm.generateList = r.generateList
            }
        });
        // this.lessANumber();

    },
    computed: {
        rsureMessage: function () {
            var arr = [];
            var num = 0;
            var rsures = 100;
            var str = this.rsure;
            console.log(str)
            if (str) {
                arr = str.split(",");
                for (var i = 0; i < arr.length; i++) {
                    num += arr[i] * 1;
                    rsures = 100 - (num * 1);
                    if(rsures<0){
                        // history.go(0);
                        alert("不可低于0%",function(){
                            location.reload();
                        });
                    }
                }
            }
            return rsures + "%";
        },
        gdateMessage: function () {
            var le = 0;
            var arr = [];
            var str = this.rsure;
            if (str) {
                arr = str.split(",");
            }
            le = arr.length;
            this.generate.gdate = le;
            return le;
        }
    }
});


function updateByid(a) {
    var id = 0;
    var rowData = vm.generateList;

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
    var rowData = vm.generateList;
    for (var i = 0; i < rowData.length; i++) {
        if (a == rowData[i].id) {
            id = rowData[i].id
        }
    }
    confirm('确定要删除选中的记录？', function () {
        $.ajax({
            type: "POST",
            url: baseURL + "s/generate/deleteByid/" + id,
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