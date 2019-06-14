$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/pmoney/list',
        datatype: "json",
        colModel: [
            { label: '批次号', name: 'monid', index: 'monid', width: 80 },
            { label: '支付笔数', name: 'pcount', index: 'pcount', width: 80 },
            { label: '支付金额', name: 'pmoney', index: 'pmoney', width: 80 },
            { label: '申请时间', name: 'ptime', index: 'ptime', width: 80 },
            { label: '手续费', name: 'pay', index: 'pay', width: 80 },
            { label: '组员', name: 'pname', index: 'pname', width: 80 },
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
            starttime:null,
            endtime:null,
            monid:''
        },
        showList: true,
        title: null,
        pmoney: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
            vm.showList = false;
            vm.title = "添加信息";
            vm.pmoney = {};
        },
        daoru : function () {
            $("#upfile").click();
        },
        saveOrUpdate: function (event) {
            var url = "sys/pmoney/save";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.pmoney),
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
                    url: baseURL + "sys/pmoney/delete",
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
        reload: function (event) {
            vm.showList = true;
            vm.q.starttime=$('#starttime').val();
            vm.q.endtime=$('#endtime').val();
            vm.q.monid=$('#monid').val();
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{"starttime":vm.q.starttime,"endtime":vm.q.endtime,"monid":vm.q.monid},
                page:page
            }).trigger("reloadGrid");
        }
    }
});

//导入文件
function importExp(type) {
    var index = layer.load(1, {time: 20*1000});  // 调用加载层
    var formData = new FormData();
    var name = $("#upfile").val();
    formData.append("name",name);
    formData.append("file",$("#upfile")[0].files[0]);
    // console.info(formData)
    $.ajax({
        url : baseURL + "sys/pmoney/uploadExcel/"+type,
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