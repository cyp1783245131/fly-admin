$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/yinliu/list',
        datatype: "json",
        colModel: [
            { label: '计划名称', name: 'name', index: 'name', width: 80 },
            { label: '开始时间', name: 'stime', index: 'stime', width: 80 },
            { label: '结束时间', name: 'etime', index: 'etime', width: 80 },
            { label: '范围', name: 'ranges', index: 'ranges', width: 80 },
            { label: '引流位置', name: 'adds', index: 'adds', width: 80 },
            { label: '计划流量(万)', name: 'meth', index: 'meth', width: 80 },
            { label: '计划状态', name: 'status', index:'status',width: 80, formatter: function(value, options, row){
                return value === 0 ?
                    '<span class="label label-danger">禁用</span>' :
                    '<span class="label label-success">启用</span>';
            }}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page",
            rows:"limit",
            order: "order"
        },
        gridComplete:function(){
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
});

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            name: null
        },
        showList: true,
        title: null,
        yinliu: {},
        selectedChannels:[],
        planChannels:[]
    },
    methods: {
        query: function () {
            vm.reload();
        },
        //计划流量（万）
        subFlow : function (number) {
            vm.yinliu.meth = number;
        },
        //引流位置图标选择
        subLocation: function (a, e) {
            var el = e.target;
            var planChannel = $(el).html();
            if (vm.planChannels.indexOf(planChannel) != -1) {
                var index = vm.planChannels.indexOf(planChannel);
                vm.planChannels.splice(index, 1);
                var location = document.getElementById("location" + a);
                location.style.backgroundColor = 'gray'
            } else {
                vm.planChannels.push(planChannel);
                var location = document.getElementById("location" + a);
                location.style.backgroundColor = 'Cyan'
            }
        },
        //引流位置图标保存
        saveLocation: function (e) {
            vm.yinliu.adds = vm.planChannels.join(",");
            $("#planChannel").val(vm.yinliu.adds);
            if (vm.planChannels) {
                vm.selectedChannels = [];
                //vm.planChannels=planChannel.split(",");
                $.each(vm.planChannels, function (ind, item) {
                    $.each(vm.planChannels, function (index, c) {
                        if (c == item) {
                            vm.selectedChannels.push(c);
                        }
                    });
                });
            }

            vm.$nextTick(function () {

            })
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增计划";
            vm.yinliu = {status:1};

            //引流时间
            vm.time=null;
            $("#time").val('');

            //引流范围
            vm.range=null;
            $("#range").val('');

            //引流位置
            vm.planChannel=null;
            $("#planChannel").val('');

        },
        update: function (event) {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改计划";
            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            //计划时间
            vm.time=$("#time").val();

            vm.range = $("#range").val();

            vm.planChannel = $("#planChannel").val();

            if(vm.time){
                var times= vm.time.split("~");
                vm.yinliu.stime=times[0];
                vm.yinliu.etime=times[1];
            }

            if (vm.range){
                var rr = vm.range;
                vm.yinliu.ranges=rr;
            }

            if (vm.planChannel){
                var adds = vm.planChannel;
                vm.yinliu.adds=adds;
            }

            //文本框没有值则文本框变红，提醒用户输入信息
            var name=$("#name").val();
            if (name == '' || name == undefined || name == null){
                $("#name").css('borderColor','red'); //添加css样式
            }else {
                $("#name").css('borderColor',''); //取消css样式
            }

            var times=$("#time").val();
            if (times == '' || times == undefined || times == null){
                $("#time").css('borderColor','red'); //添加css样式
            }else {
                $("#time").css('borderColor',''); //取消css样式
            }

            var ranges=$("#range").val();
            if (ranges == '' || ranges == undefined || ranges == null){
                $("#range").css('borderColor','red'); //添加css样式
            }else {
                $("#range").css('borderColor',''); //取消css样式
            }

            var adds=$("#planChannel").val();
            if (adds == '' || adds == undefined || adds == null){
                $("#planChannel").css('borderColor','red'); //添加css样式
            }else {
                $("#planChannel").css('borderColor',''); //取消css样式
            }

            var meths=$("#meths").val();
            if (meths == '' || meths == undefined || meths == null){
                $("#meths").css('borderColor','red'); //添加css样式
            }else {
                $("#meths").css('borderColor',''); //取消css样式
            }

            var url = vm.yinliu.id == null ? "sys/yinliu/save" : "sys/yinliu/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.yinliu),
                                  success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(index){
                            vm.reload();
                        });
                    }
                    // else{
                    //     alert(r.msg);
                    // }
                }
            });
        },
        del: function (event) {
            var ids = getSelectedRows();
            if(ids == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/yinliu/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(index){
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function(id){
            $.get(baseURL + "sys/yinliu/info/"+id, function(r){
                vm.yinliu = r.yinliu;

                //引流名称
                var names=vm.yinliu.name;
                if(names){
                    vm.name=names;
                    $("#name").val(vm.name);
                }

                //引流时间
                var stime=vm.yinliu.stime;
                var etime=vm.yinliu.etime;
                if(stime){
                    vm.time=stime+' ~ '+etime;
                    $("#time").val(vm.time);
                }

                //引流范围
                var ranges=vm.yinliu.ranges;
                if(ranges){
                    vm.range=ranges;
                    $("#range").val(vm.range);
                }

                //引流位置
                var adds=vm.yinliu.adds;
                if(adds){
                    vm.planChannel=adds;
                    $("#planChannel").val(vm.planChannel);
                }

                //计划数量（万）
                var meths=vm.yinliu.meth;
                if(meths){
                    vm.meths=meths;
                    $("#meths").val(vm.meths);
                }

                //文本框没有值则文本框变红，提醒用户输入信息
                var name=$("#name").val();
                if (name == '' || name == undefined || name == null){
                    $("#name").css('borderColor','red'); //添加css样式
                }else {
                    $("#name").css('borderColor',''); //取消css样式
                }

                var times=$("#time").val();
                if (times == '' || times == undefined || times == null){
                    $("#time").css('borderColor','red'); //添加css样式
                }else {
                    $("#time").css('borderColor',''); //取消css样式
                }

                var ranges=$("#range").val();
                if (ranges == '' || ranges == undefined || ranges == null){
                    $("#range").css('borderColor','red'); //添加css样式
                }else {
                    $("#range").css('borderColor',''); //取消css样式
                }

                var adds=$("#planChannel").val();
                if (adds == '' || adds == undefined || adds == null){
                    $("#planChannel").css('borderColor','red'); //添加css样式
                }else {
                    $("#planChannel").css('borderColor',''); //取消css样式
                }

                var meths=$("#meths").val();
                if (meths == '' || meths == undefined || meths == null){
                    $("#meths").css('borderColor','red'); //添加css样式
                }else {
                    $("#meths").css('borderColor',''); //取消css样式
                }
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'name': vm.q.name},
                page:page
            }).trigger("reloadGrid");
        }
    }
});