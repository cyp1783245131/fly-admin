$(function () {

    $("#jqGrid").jqGrid({
        url: baseURL + 's/fplancount/list',
        datatype: "json",
        colModel: [
            {label: '主键', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '计划名称', name: 'dname', index: 'dname', width: 80},
            {label: '开始时间', name: 'kaishi', index: 'kaishi', width: 100},
            {label: '结束时间', name: 'jieshu', index: 'jieshu', width: 100},
            {label: '部门编号', name: 'deptid', index: 'deptid', width: 60, hidden: true},
            {label: '范围id', name: 'fid', index: 'fid', width: 60, hidden: true},
            {label: '范围', name: 'fanwei', index: 'fanwei', width: 60},
            {label: '计划流量', name: 'flow', index: 'flow', width: 60},
            {
                label: '操作',
                name: "id",
                index: "id",
                width: 220,
                formatter: function (value, options, rowObject) {
                    return "<span class='btn btn-primary btn-xs' onclick='updateByid(\" " + rowObject.id + " \")'><i class='fa fa-trash-o'></i>&nbsp;修改</span>&nbsp;&nbsp;&nbsp;" +
                        "<span class='btn btn-primary btn-xs' onclick=' deleteByid(\" " + rowObject.id + " \")'><i class='fa fa-pencil-square-o'></i>&nbsp;删除</span>";
                }
            },
            {label: '微盟用户', name: 'planwms', index: 'planwms', width: 80, hidden: true},
            {label: '创建用户', name: 'cuser', index: 'cuser', width: 80, hidden: true},
            {label: '创建时间', name: 'ctime', index: 'ctime', width: 80, hidden: true},
            {label: '修改时间', name: 'utime', index: 'utime', width: 80, hidden: true}

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
    $("#jqWmGrid").jqGrid({
        url: baseURL + 'sys/fwmuser/list',
        datatype: "json",
        colModel: [
            {label: '主键', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '店铺手机号ID', name: 'mobile', index: 'mobile', width: 240},
            {label: '店主姓名', name: 'name', index: 'name', width: 240}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqWmGridPager",
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
            $("#jqWmGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

});
// var layedit;
var vm = new Vue({
    el: '#rrapp',
    data: {
        // 查询参数
        q: {
            dname: null,
            mobile: null
        },
        showList: true,
        title: null,
        // 引流数据
        fplancount: {},
        fplancountList: [],
        planFlows: ['1', '5', '10', '50', '100', '500'],
        planFreeFlow: null,
        planChannels: [],
        planTime: null,
        selectedChannels: [],
        fchannelList: [],
        fcominList: [],
        rsureList: []
    },

    methods: {
        findRsure: function () {
            var id = getSelectedRow();
            var kaishi = $("#kaishi").val().split(" ");
            var jieshu = $("#jieshu").val().split(" ");
            var start_time = kaishi[0];
            var end_time = jieshu[1];

            var a1 = Date.parse(new Date(start_time));
            var a2 = Date.parse(new Date(end_time));
            var length = parseInt((a2-a1)/ (1000 * 60 * 60 * 24));//核心：时间戳相减，然后除以天数\
            var days = (length+1)+"";
            console.log(days)
            $.ajax({
                type: "get",
                url: baseURL + "s/fplancount/findRsure/" + id+'/'+days,
                dataType: 'json',
                success: function (data) {
                    if(data.region!=null){
                        $("#tabletest").html("");
                        //方法中传入的参数data为后台获取的数据
                        // for(i in data.entity){} //data.data指的是数组，数组里是8个对象，i为数组的索引
                        var rsures = data.generate.rsure;
                        var rs = rsures.split(",");
                        var tr = '<td>' + '服务日期' + '</td>' + '<td>' + '产品总体服务次数' + '</td>' + '<td>' + '产品有效服务次数' + '</td>' + '<td>' + '生成规则' + '</td>'
                        $("#tabletest").append('<tr >' + tr + '</tr>')
                        var tr1;
                        var tr2;
                        //总计产品总体服务次数
                        var rnsureNum = 0;

                        var kaishi = $("#kaishi").val().split(" ");
                        var jieshu = $("#jieshu").val().split(" ");
                        var start_time = kaishi[0];
                        var end_time = jieshu[1];
                        console.log(end_time)
                        var a1 = Date.parse(new Date(start_time));
                        var a2 = Date.parse(new Date(end_time));
                        var length = parseInt((a2-a1)/ (1000 * 60 * 60 * 24));//核心：时间戳相减，然后除以天数\

                        for (i = 0; i <= length; i++) {
                            //总计产品有效服务次数
                            var rsureNum = (vm.planFreeFlow * 10000)
                            //产品有效服务次数
                            var rsure = Math.ceil(rsureNum * rs[i] / 100)
                            //产品总体服务次数
                            var rnsure = Math.ceil(rsure /data.region.rnsure*100)
                            // rnsureNum= rnsureNum+rnsure
                            //服务日期
                            var kaishi = $("#kaishi").val().split(" ");
                            var str = kaishi[0];
                            // str = str.replace(/-/g, '/'); // "2010/08/01";
                            var datetime = new Date(str);
                            datetime = datetime.setDate(datetime.getDate() + i);
                            datetime = new Date(datetime);
                            var date = datetime.toLocaleDateString()
                            tr1 = '<td>' + date + '</td>' + '<td>' + rnsure + '</td>' + '<td>' + rsure + '</td>' + '<td>' + rs[i]+'%'+ '</td>'
                            $("#tabletest").append('<tr >' + tr1 + '</tr>')
                            rnsureNum+=rnsure*1
                        }
                        tr2 = '<td>' + '总计' + '</td>' + '<td>' + rnsureNum + '</td>' + '<td>' + rsureNum + '</td>' + '<td>' + '100%' + '</td>'
                        tr3 = '<td colspan="2">'+'<button type="button" class="btn btn-default" onclick="quxiao()">取消</button>'+'</td>' + '<td  colspan="4">' + '<button type="button" onclick="viewRecord()" class="btn btn-primary">提交</button>' + '</td>'
                        $("#tabletest").append('<tr>' + tr2 + '</tr>')
                        $("#tabletest").append('<tr>' + tr3 + '</tr>')
                    }else if(data.region==null){
                        // var ele='modules/sys/generate.html';
                        // alert(ele);
                        // alert(ele.outerHTML)
                        alert("没有找到对应规则天数，请自行前往添加",function(){
                            location.href='/modules/sys/generate.html'
                        });
                    }

                }
            });
        },

        //点击引流位置改变样式
        selectFlow: function (e) {
            var el = e.target;
            var planFlow = $(el).html();
            vm.planFreeFlow = planFlow;
            vm.fplancount.flow = planFlow;
            $(el).addClass("label label-success").siblings("span").removeClass("label label-success").addClass("label label-default");
            //$("#freeFlow").val('');
        },
        //自定义引流计划
        selectFreeFlow: function (e) {
            var el = e.target;
            var planFlow = $(el).val();
            if (!planFlow) {
                alert("计划流量不能为空！");
                return;
            }
            vm.planFreeFlow = planFlow;
            vm.fplancount.flow = planFlow;
            $(el).siblings("span").removeClass("label label-success").addClass("label label-default");

            //vm.fPlan.planFlow=planFlow;
        },

        query: function () {
            vm.reload();
        },

        add: function () {
            vm.showList = false;
            vm.title = "新增计划";
            vm.fplancount = {state: 1};
            //引流时间
            vm.time = null;
            $("#time").val('');
            //引流流量
            vm.planFreeFlow = null;
            vm.fplancount.flow = null;
            $(".divSpan").removeClass("label label-success").addClass("label label-default");
            //引流位置
            vm.selectedChannels = [];
            vm.planChannels = [];
            vm.fplancount.location = null;
            $(".divChannelSpan").removeClass("label label-success").addClass("label label-default");
            //加载系统引流位置
            $.get(baseURL + "sys/fchannel/findAll", function (r) {
                vm.fchannelList = r.fchannelList;
            });
            //引流范围
            $("#planCity").data("value", "");
            $("#planCity").val("");

            //计划状态
            $(function () {
                /* 初始化控件 */
                $('#state').bootstrapSwitch('destroy');
                $("#state").bootstrapSwitch({
                    onText: "开启",      // 设置ON文本  
                    offText: "禁用",    // 设置OFF文本  
                    onColor: "success",// 设置ON文本颜色     (info/success/warning/danger/primary)  
                    offColor: "danger",  // 设置OFF文本颜色        (info/success/warning/danger/primary)  
                    size: "mini",    // 设置控件大小,从小到大  (mini/small/normal/large)  
                    handleWidth: "35",//设置控件宽度
                    onSwitchChange: function (event, state) {
                        if (state == true) {
                            vm.fplancount.state = '1';
                        } else {
                            vm.fplancount.state = '0';
                        }
                    }
                });
            });
            $(function () {
                // var flag = vm.fplancount.generate == '1' ? true : false;
                /* 初始化控件 */
                $('#generate').bootstrapSwitch('destroy');
                $("#generate").bootstrapSwitch({
                    onText: "可生成",      // 设置ON文本  
                    offText: "不可生成",    // 设置OFF文本  
                    onColor: "success",// 设置ON文本颜色     (info/success/warning/danger/primary)  
                    offColor: "danger",  // 设置OFF文本颜色        (info/success/warning/danger/primary)  
                    size: "mini",    // 设置控件大小,从小到大  (mini/small/normal/large)  
                    handleWidth: "50",//设置控件宽度
                    // state: flag,
                    onSwitchChange: function (event, generate) {
                        if (generate == true) {
                            vm.fplancount.generate = '1';
                        } else {
                            vm.fplancount.generate = '0';
                        }
                    }
                });
            });
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
            //计划时间
            vm.time = $("#time").val();
            if (vm.time) {
                var times = vm.time.split("~");
                vm.fplancount.kaishi = times[0];
                vm.fplancount.jieshu = times[1];
            }

            //计划范围
            var cityIds = $("#planCity").data("value");
            var cityNames = $("#planCity").val();
            if (cityIds) {
                vm.fplancount.fid = cityIds;
                vm.fplancount.fanwei = cityNames;
            }
            if (vm.planFreeFlow) {
                vm.fplancount.flow = vm.planFreeFlow;
            }

            var url = vm.fplancount.id == null ? "s/fplancount/save" : "s/fplancount/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.fplancount),
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
        },
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }
            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "s/fplancount/delete",
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
            $.get(baseURL + "s/fplancount/info/" + id, function (r) {
                    vm.fplancount = r.fplancount;
                    //引流时间
                    var stime = vm.fplancount.kaishi;
                    var etime = vm.fplancount.jieshu;
                    if (stime) {
                        vm.time = stime + ' ~ ' + etime;
                        $("#time").val(vm.time);
                    }
                    // vm.selectedChannels = vm.fplancount.location.split(",");

                    //引流流量
                    if (vm.fplancount.flow) {
                        if (vm.planFlows.indexOf(vm.fplancount.flow) == -1) {
                            vm.planFreeFlow = vm.fplancount.flow;
                            vm.fplancount.flow = null;
                        } else {
                            $("#freeFlow" + vm.fplancount.flow).addClass("label label-success").siblings("span").removeClass("label label-success").addClass("label label-default");
                            vm.planFreeFlow = vm.fplancount.flow;
                        }
                    }

                    //引流范围
                    if (vm.fplancount.fid) {
                        $("#planCity").data("value", vm.fplancount.fid);
                        $("#planCity").val(vm.fplancount.fanwei);
                    }

                    //引流名称
                    var dname = vm.fplancount.dname;
                    if (dname) {
                        vm.dname = dname;
                        $("#dname").val(vm.dname);
                    }

                    //计划状态
                    $(function () {
                        var flag = vm.fplancount.state == '1' ? true : false;
                        /* 初始化控件 */
                        $('#state').bootstrapSwitch('destroy');
                        $("#state").bootstrapSwitch({
                            onText: "开启",      // 设置ON文本  
                            offText: "禁用",    // 设置OFF文本  
                            onColor: "success",// 设置ON文本颜色     (info/success/warning/danger/primary)  
                            offColor: "danger",  // 设置OFF文本颜色        (info/success/warning/danger/primary)  
                            size: "mini",    // 设置控件大小,从小到大  (mini/small/normal/large)  
                            handleWidth: "35",//设置控件宽度
                            state: flag,
                            onSwitchChange: function (event, state) {
                                if (state == true) {
                                    vm.fplancount.state = '1';
                                } else {
                                    vm.fplancount.state = '0';
                                }
                            }
                        });
                    });
                    //生成情况
                    $(function () {
                        var flag = vm.fplancount.generate == '1' ? true : false;
                        /* 初始化控件 */
                        $('#generate').bootstrapSwitch('destroy');
                        $("#generate").bootstrapSwitch({
                            onText: "可生成",      // 设置ON文本  
                            offText: "不可生成",    // 设置OFF文本  
                            onColor: "success",// 设置ON文本颜色     (info/success/warning/danger/primary)  
                            offColor: "danger",  // 设置OFF文本颜色        (info/success/warning/danger/primary)  
                            size: "mini",    // 设置控件大小,从小到大  (mini/small/normal/large)  
                            handleWidth: "50",//设置控件宽度
                            state: flag,
                            onSwitchChange: function (event, generate) {
                                if (generate == true) {
                                    vm.fplancount.generate = '1';
                                } else {
                                    vm.fplancount.generate = '0';
                                }
                            }
                        });
                    });
                }
            );

        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'dname': vm.q.dname},
                page: page
            }).trigger("reloadGrid");
        },
        cancelSelectChanns: function (e) {
            if (vm.fplancount.location) {
                vm.planChannels = vm.fplancount.location.split(",");
            }

        },
        hideTab: function () {
            $.ajax({
                type: "get",
                url: baseURL + "sys/fchannel/findAll",
                contentType: "application/json",
                data: "",
                success: function (r) {
                    vm.fchannelList = r.fchannelList
                }
            });
        }
    },
    created: function () {
        $.ajax({
            type: "get",
            url: baseURL + "sys/fchannel/findAll",
            contentType: "application/json",
            data: "",
            success: function (r) {
                vm.fchannelList = r.fchannelList
            }
        });
        $.ajax({
            type: "get",
            url: baseURL + "s/fplancount/findAll",
            contentType: "application/json",
            data: "",
            success: function (r) {
                vm.fplancountList = r.fplancountList
            }
        });
        $.ajax({
            type: "get",
            url: baseURL + "s/fcomin/findAll",
            contentType: "application/json",
            data: "",
            success: function (r) {
                vm.fcominList = r.fcominList
            }
        });

    }

});


function updateByid(a) {
    var id = 0;
    var rowData = vm.fplancountList;
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
    var rowData = vm.fplancountList;
    for (var i = 0; i < rowData.length; i++) {
        if (a == rowData[i].id) {
            id = rowData[i].id
        }
    }
    confirm('确定要删除选中的记录？', function () {
        $.ajax({
            type: "POST",
            url: baseURL + "s/fplancount/deleteByid",
            contentType: "application/json",
            data: JSON.stringify(id),
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
