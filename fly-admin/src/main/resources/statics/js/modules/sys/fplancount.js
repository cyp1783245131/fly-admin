$(function () {

    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/fplancount/list',
        datatype: "json",
        colModel: [
            {label: '主键', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '计划名称', name: 'dname', index: 'dname', width: 60},
            {label: '开始时间', name: 'kaishi', index: 'kaishi', width: 60},
            {label: '结束时间', name: 'jieshu', index: 'jieshu', width: 60},
            {label: '部门编号', name: 'deptid', index: 'deptid', width: 60, hidden: true},
            {label: '范围id', name: 'fid', index: 'fid', width: 60, hidden: true},
            {label: '范围', name: 'fanwei', index: 'fanwei', width: 60 },
            {label: '引流位置', name: 'location', index: 'location', width: 60},
            {label: '计划流量(万)', name: 'flow', index: 'flow', width: 60},
            {
                label: '计划状态', name: 'state', index: 'state', width: 60, formatter: function (value, options, row) {
                    return value === 0 ?
                        '<span class="label label-danger">禁用</span>' :
                        '<span class="label label-success">启用</span>';
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
            dname: null,
            mobile: null
        },
        showList: true,
        title: null,
        // 引流数据
        fplancount: {
            fChannelDataList: []
        },
        planFlows: ['1', '5', '10', '50', '100', '500'],
        planFreeFlow: null,
        planChannels: [],
        planTime: null,
        selectedChannels: [],
        fchannelList: []
    },

    methods: {

        //点击引流位置改变样式
        selectFlow: function (e) {
            var el = e.target;
            var planFlow = $(el).html();
            vm.planFreeFlow = null;
            vm.fplancount.flow = planFlow;
            $(el).addClass("label label-success").siblings("span").removeClass("label label-success").addClass("label label-default");
            //$("#freeFlow").val('');
        },
        selectFreeFlow: function (e) {
            var el = e.target;
            var planFlow = $(el).val();
            if (!planFlow) {
                alert("计划流量不能为空！");
                return;
            }
            vm.planFreeFlow = planFlow;
            $(el).siblings("span").removeClass("label label-success").addClass("label label-default");

            //vm.fPlan.planFlow=planFlow;
        },
        selectChannel:function (e) {
            var el = e.target;
            var planChannel=$(el).html();
            if(vm.planChannels.indexOf(planChannel)!=-1){
                var index = vm.planChannels.indexOf(planChannel);
                vm.planChannels.splice(index,1);
                $(el).removeClass("label label-success").addClass("label label-default");
            }else{
                vm.planChannels.push(planChannel);
                $(el).removeClass("label label-default").addClass("label label-success");
            }
        },

        queryWmUsers: function () {
            //vm.showWMTable=true;
            var page = $("#jqWmGrid").jqGrid('getGridParam', 'page');
            $("#jqWmGrid").jqGrid('setGridParam', {
                postData: {"mobile": vm.q.mobile},
                page: page
            }).trigger("reloadGrid");

        },
        saveWmUsers: function (e) {
            var grid = $("#jqWmGrid");
            var rowKey = grid.getGridParam("selrow");
            vm.fplancount.planwms = grid.getGridParam("selarrrow").join(",");
            $('#planwms').val(vm.fplancount.planwms);
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
                //console.log(r);
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
                            //console.log("开启");
                        } else {
                            vm.fplancount.state = '0';
                            //console.log("关闭");
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
            //渠道数据
            var fChannelDataList = [];
            $.each(vm.selectedChannels, function (index, chan) {
                var cdata = {};
                //渠道编号
                cdata["id"] = chan.id;
                //渠道名称
                cdata["cname"] = chan.cname;
                //渠道图片
                if (chan.cimage == '1') {
                    var cimage = $("#imageurl" + chan.id).val();
                    cdata["cimage"] = cimage;
                }
                //渠道链接
                if (chan.curl == '1') {
                    var curl = $("#planurl" + chan.id).val();
                    cdata["curl"] = curl;
                }
                //同步微盟
                if (chan.cwm == '1') {
                    if ($("#planwm" + chan.id).get(0).checked) {
                        cdata["cwm"] = "1";
                    } else {
                        cdata["cwm"] = "0";
                    }
                }
                //文案
                if (chan.cwenan == '1') {
                    //var cWenan=layedit.getContent(index+1);
                    var cwenan = $("#wenan" + chan.id).text();
                    console.log(cwenan);
                    cdata["cwenan"] = cwenan;
                }

                fChannelDataList.push(cdata);

            });
            vm.fplancount.fChannelDataList = fChannelDataList;

            var url = vm.fplancount.id == null ? "sys/fplancount/save" : "sys/fplancount/update";
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
                    url: baseURL + "sys/fplancount/delete",
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
            $.get(baseURL + "sys/fplancount/info/" + id, function (r) {
                    vm.fplancount = r.fplancount;

                    $.get(baseURL + "sys/fchannel/findAll", function (r) {
                        //console.log(r);
                        vm.fchannelList = r.fchannelList;
                        //引流位置
                        var planChannel = vm.fplancount.location;
                        if (planChannel) {
                            vm.selectedChannels = [];
                            vm.planChannels = planChannel.split(",");
                            $.each(vm.planChannels, function (ind, item) {
                                $.each(vm.fchannelList, function (index, c) {
                                    if (c.cname == item) {
                                        vm.selectedChannels.push(c);
                                    }
                                });
                            });

                        }
                        vm.$nextTick(function () {
                            //console.log(vm.selectedChannels);
                            //var layedit;
                            layui.use(['upload', 'layedit'], function () {
                                var $ = layui.jquery, upload = layui.upload;
                                layedit = layui.layedit;
                                //上传文件
                                if (vm.selectedChannels) {
                                    $.each(vm.selectedChannels, function (index, channel) {
                                        // ld.build('wenan' + channel.id); //建立编辑器
                                        upload.render({
                                            elem: '#uploadFile' + channel.id //绑定元素
                                            , url: '/upload/' //上传接口
                                            , done: function (res) {
                                                //上传完毕回调
                                            }
                                            , error: function () {
                                                //请求异常回调
                                            }
                                        });
                                    });
                                }

                                if (vm.fplancount.fChannelDataList) {
                                    $.each(vm.fplancount.fChannelDataList, function (index, cData) {
                                        //console.log(cData);
                                        //渠道图片
                                        $("#imageurl" + cData.id).val(cData.cimage)
                                        //渠道链接
                                        $("#planurl" + cData.id).val(cData.curl);
                                        //同步微盟
                                        if (cData.cwm == '1') {
                                            $("#planwm" + cData.id).prop("checked", true);
                                        } else {
                                            $("#planwm" + cData.id).prop("checked", false);
                                        }
                                        console.log(cData.cwenan);
                                        //文案
                                        layedit.sync(index + 1);

                                    });
                                }
                            });
                        })
                    });

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
                        //console.log(vm.planFlows.indexOf(vm.fPlan.planFlow));
                        if (vm.planFlows.indexOf(vm.fplancount.flow) == -1) {
                            vm.planFreeFlow = vm.fplancount.flow;
                            //console.log(vm.planFreeFlow);
                            //$('#freeFlow').val(vm.planFreeFlow);
                            vm.fplancount.flow = null;
                        } else {
                            $("#freeFlow" + vm.fplancount.flow).addClass("label label-success").siblings("span").removeClass("label label-success").addClass("label label-default");
                            vm.planFreeFlow = null;
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
                                    //console.log("开启");
                                } else {
                                    vm.fplancount.state = '0';
                                    //console.log("关闭");
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
        saveLocation: function (e) {
            //console.log(vm.planTime);
            vm.fplancount.location = vm.planChannels.join(",");
            //console.log(vm.fPlan.planChannel);
            $("#planChannel").val(vm.fplancount.location);
            if (vm.planChannels) {
                vm.selectedChannels = [];
                //vm.planChannels=planChannel.split(",");
                $.each(vm.planChannels, function (ind, item) {
                    $.each(vm.fchannelList, function (index, c) {
                        if (c.cname == item) {
                            vm.selectedChannels.push(c);
                        }
                    });
                });
            }
            //console.log(vm.planTime);
            vm.$nextTick(function () {
                layui.use(['laydate', 'upload', 'layedit'], function () {
                    var $ = layui.jquery, upload = layui.upload;
                    if (!layedit) {
                        layedit = layui.layedit;
                    }
                    //上传文件
                    if (vm.selectedChannels) {
                        $.each(vm.selectedChannels, function (index, channel) {
                            layedit.build('wenan' + channel.Id); //建立编辑器
                            upload.render({
                                elem: '#uploadFile' + channel.Id //绑定元素
                                , url: '/upload/' //上传接口
                                , done: function (res) {
                                    //上传完毕回调
                                }
                                , error: function () {
                                    //请求异常回调
                                }
                            });

                            //渠道图片
                            $("#imageurl" + channel.Id).val('')
                            //渠道链接
                            $("#planurl" + channel.Id).val('');
                            //同步微盟
                            $("#planwm" + channel.Id).prop("checked", false);
                            //文案
                            layedit.sync(index + 1);

                        });
                    }
                });
            })

        },
        cancelSelectChanns: function (e) {
            if (vm.fplancount.location) {
                vm.planChannels = vm.fplancount.location.split(",");
            }

        },
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

    }
});