$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/user/list',
        datatype: "json",
        colModel: [
			{ label: '用户ID', name: 'userId', index: "user_id", width: 45, hidden: true },
			{ label: '用户名', name: 'username',index: "username", width: 75 },
            { label: '所属部门', name: 'deptName',index: "dept_name", sortable: false, width: 75 },
			{ label: '邮箱', name: 'email',index: "email", width: 90 },
			{ label: '手机号', name: 'mobile',index: "mobile", width: 100 },
            { label: '创建时间', name: 'createTime', index: "create_time", width: 85},
            { label: '状态', name: 'status',index: "status", width: 60,
                formatter: function(value, options, row){
                    return value === 0 ?
                        "<input type='button' value='禁用' class='btn btn-danger btn-xs' onclick='subType(\" " + options.rowId + " \")' />" :
                        "<input type='button' value='正常' class='btn btn-success btn-xs'onclick='subType(\" " + options.rowId + " \")' />" ;
                }
			},
            {
                label: '操作',
                name: 'userId',
                index: "user_id",
                width: 70,
                formatter: function (value, options, rowObject) {
                    return "<a class='btn btn-primary btn-xs' onclick='deleteByid(\" " + value + " \")'><i class='fa fa-trash-o'></i>&nbsp;删除</a>" +
                        "<a class='btn btn-primary btn-xs' onclick='updateByid(\" " + value + " \")'><i class='fa fa-pencil-square-o'></i>&nbsp;修改</a>" ;
                }
            }
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
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};
var ztree;

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            username: null
        },
        showList: true,
        title:null,
        roleList:{},
        user:{
            status:1,
            deptId:null,
            deptName:null,
            roleIdList:[]
        },
        repassword : '',
        userList : []
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.roleList = {};
            vm.user = {deptName:null, deptId:null, status:1, roleIdList:[]};

            //获取角色信息
            this.getRoleList();

            vm.getDept();
        },
        getDept: function(){
            //加载部门树
            $.get(baseURL + "sys/dept/list", function(r){
                ztree = $.fn.zTree.init($("#deptTree"), setting, r);
                var node = ztree.getNodeByParam("deptId", vm.user.deptId);
                // console.info(node)
                if(node != null){
                    ztree.selectNode(node);
                    // console.info(node)
                    vm.user.deptName = node.name;
                }
            })
        },
        update: function () {



            var userId = getSelectedRow();
            if(userId == null){
                return ;
            }

            vm.showList = false;
            vm.title = "修改";

            vm.getUser(userId);
            //获取角色信息
            this.getRoleList();
        },
        permissions: function () {
            var userId = getSelectedRow();
            if(userId == null){
                return ;
            }

            window.location.href=baseURL+"sys/permissions/index/"+userId;
        },
        del: function () {

            var userIds = [];
            var ids = getSelectedRows();
            for (var i = 0; i < ids.length; i++) {
                var rowData = jQuery("#jqGrid").jqGrid("getRowData", ids[i]);
                // console.info(rowData)
                userIds.push(rowData.userId)
            }

            if(userIds == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/user/delete",
                    contentType: "application/json",
                    data: JSON.stringify(userIds),
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        saveOrUpdate: function () {
            if(vm.user.password != vm.repassword){
                alert("两次输入密码不一致")
                return;
            }

            var url = vm.user.userId == null ? "sys/user/save" : "sys/user/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.user),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        getUser: function(userId){
            $.get(baseURL + "sys/user/info/"+userId, function(r){
                vm.user = r.user;
                vm.user.password = null;
                // console.info(vm.user)
                vm.getDept();
            });
        },
        getRoleList: function(){
            $.get(baseURL + "sys/role/select", function(r){
                vm.roleList = r.list;
            });
        },
        deptTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择部门",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#deptLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    // console.info(node[0])
                    //选择上级部门
                    vm.user.deptId = node[0].deptId;
                    vm.user.deptName = node[0].name;
                    // console.info(node[0])
                    layer.close(index);
                }
            });
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'username': vm.q.username},
                page:page
            }).trigger("reloadGrid");
        },
        download : function () {
            var url = "sys/user/download";
            $.ajax({
                type: "get",
                url: baseURL + url,
                contentType: "application/json",
                data: "",
                success: function (r) {
                    alert("导出成功")
                }
            });
        },
        daoru : function () {
            $("#upfile").click();
        },
        moban : function () {
            window.location.href = (baseURL+'statics/template/用户导入模板.xlsx');
        }
    },
    created : function () {
        var url = "sys/user/findAll";
        $.ajax({
            type: "get",
            url: baseURL + url,
            contentType: "application/json",
            data: "",
            success: function (r) {
                vm.userList = r.userList;
            }
        });
    }
});

function subType(a) {
    var uid = '';
    var rowData = vm.userList;
    for(var i = 0 ; i < rowData.length ; i ++){
        if(a == i+1){
            // console.info(rowData[i])
            uid = rowData[i].userId
        }
    }
    confirm('确定要修改计划状态吗？', function () {
        $.ajax({
            type: "get",
            url: baseURL + "sys/user/subType/"+uid,
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
function deleteByid(a) {
    var uid = '';
    var rowData = vm.userList;
    for(var i = 0 ; i < rowData.length ; i ++){
        if(a == rowData[i].userId){
            // console.info(rowData[i])
            uid = rowData[i].userId
        }
    }
    confirm('确定要删除选中的记录？', function () {
        $.ajax({
            type: "POST",
            url: baseURL + "sys/user/deleteByid/"+uid,
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
    var uid = '';
    var rowData = vm.userList;
    for(var i = 0 ; i < rowData.length ; i ++){
        if(a == rowData[i].userId){
            // console.info(rowData[i])
            uid = rowData[i].userId
        }
    }
    vm.showList = false;
    vm.title = "修改";

    vm.getUser(uid);
}

//导入文件
function importExp(type) {
    var index = layer.load(1, {time: 20*1000});  // 调用加载层
    var formData = new FormData();
    var name = $("#upfile").val();
    formData.append("name",name);
    formData.append("file",$("#upfile")[0].files[0]);
    // console.info(formData)
    $.ajax({
        url : baseURL + "sys/user/uploadExcel/"+type,
        type : 'POST',
        data : formData,
        //ajax2.0可以不用设置请求头，但是jq帮我们自动设置了，这样的话需要我们自己取消掉
        contentType:false,
        //取消帮我们格式化数据，是什么就是什么
        processData:false,
        success : function(data) {
            //关闭 加载层
            layer.close(index);
            if(data.state=="00"){
                layer.msg("导入成功");
                vm.reload();9
            }else{
                layer.msg("导入失败");
            }
        }
    });

    $("#upfile").val("");
}


$("#jqGrid").jqGrid("setGridParam", {
    postData: {}
}).trigger("reloadGrid");












