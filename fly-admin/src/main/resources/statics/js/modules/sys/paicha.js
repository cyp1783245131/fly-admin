$(function () {

    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/paicha/list',
        datatype: "json",
        colModel: [
            { label: '项目名称', name: 'pname', index: 'pname', width: 80 },
            { label: '排查开始时间', name: 'stime', index: 'stime', width: 80 },
            { label: '排查结束时间', name: 'etime', index: 'etime', width: 80 },
            { label: '排查节点', name: 'cname', index: 'cname', width: 80 },
            { label: '导入数据', name: 'number', index: 'number', width: 80 },
            { label: '排查进度', name: 'speed', index: 'speed', width: 80 },
            { label: '项目状态', name: 'status', index:'status',width: 80, formatter: function(value, options, row){
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
            pname: null
        },
        showList: true,
        title: null,
        paicha: {

        },
        codeList:[],
        planChannels: [],
        selectedChannels: [],
        code:[]
    },
    methods: {
        query: function () {
            vm.reload();
        },

        //点击引流位置改变样式
        subPosition: function (a, e) {
            var el = e.target;
            var planChannel = $(el).html();

            console.info(planChannel)

            if (vm.planChannels.indexOf(planChannel) != -1) {
                var index = vm.planChannels.indexOf(planChannel);
                vm.planChannels.splice(index, 1);
                var id = document.getElementById("code" + a);
                $(el).removeClass("label label-success").addClass("label label-default");
            } else {
                vm.planChannels.push(planChannel);
                var id = document.getElementById("code" + a);
                $(el).removeClass("label label-default").addClass("label label-success");
            }
        },
        //选完引流位置，点击确定打开上传等功能框
        savePosition: function (e) {
            vm.paicha.cname = vm.planChannels.join(",");
            $("#cid").val(vm.paicha.cname);
            if (vm.planChannels) {
                vm.selectedChannels = [];
                $.each(vm.planChannels, function (ind, item) {
                    $.each(vm.planChannels, function (index, c) {
                        if (c == item) {
                            vm.selectedChannels.push(c);
                        }
                    });
                });
            }
            console.info(vm.planChannels)
        },

        add: function(){
            vm.showList = false;
            vm.title = "新增计划";
            vm.paicha = {status:1};

            //引流时间
            vm.time=null;
            $("#time").val('');

            vm.cid=null;
            $("#cid").val('');

            vm.path=null;
            $("#path").val('');
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
            vm.paicha.number = Math.round(Math.random()*200);
            vm.paicha.speed = "100%";

            //计划时间
            vm.time=$("#time").val();

            vm.cid = $("#cid").val();

            vm.path = $("#path").val();

            if(vm.time){
                var times= vm.time.split("~");
                vm.paicha.stime=times[0];
                vm.paicha.etime=times[1];
            }

            if (vm.cid){
                var cids = vm.cid;
                vm.paicha.cname=cids;
            }

            if (vm.path){
                var paths = vm.path;
                vm.paicha.paths=paths;
            }

            var url = vm.paicha.id == null ? "sys/paicha/save" : "sys/paicha/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.paicha),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(index){
                            vm.reload();
                        });
                    } else{
                        alert(r.msg);
                    }
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
                    url: baseURL + "sys/paicha/delete",
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
            $.get(baseURL + "sys/paicha/info/"+id, function(r){
                vm.paicha = r.paicha;

                var cids=vm.paicha.cname;

                var paths=vm.paicha.paths;

                //引流时间
                var stime=vm.paicha.stime;
                var etime=vm.paicha.etime;
                if(stime){
                    vm.time=stime+' ~ '+etime;
                    $("#time").val(vm.time);
                }

                if(cids){
                    vm.cid=cids;
                    $("#cid").val(vm.cid);
                }

                if(paths){
                    vm.path=paths;
                    $("#path").val(vm.path);
                }
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'pname': vm.q.pname},
                page:page
            }).trigger("reloadGrid");
        }
    },
    created : function () {
        $.ajax({
            type: "get",
            url: baseURL + "sys/code/codeList",
            contentType: "application/json",
            data: "",
            success: function (r) {
                vm.codeList = r.codeList
            }
        });
    }
});