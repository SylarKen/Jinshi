layui.use(['table', 'admin', 'ax', 'laydate'], function () {
    var $ = layui.$;
    var laydate = layui.laydate;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 日志管理
     */
    let dataLog = {
        tableId: 'dataLog'
    };
    dataLog.initColumn = function () {
        return [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'id', title: 'ID', sort: true},
            {field: 'title', title: '车牌号', sort: true},
            {field: 'type', title: '类型'},
            {field: 'operUser', title: '操作人'},
            {field: 'create_time', title: '时间'},
            {field: 'content', title: '操作记录'},

           // {align: 'center', toolbar: '#tableBar', width: 120}
        ]];
    };
    /**
     * 点击查询按钮
     */
    dataLog.search = function () {
        var queryData = {};
        queryData['beginTime'] = $("#beginTime").val();
        queryData['endTime'] = $("#endTime").val();
        table.reload(dataLog.tableId, {
            where: queryData, page: {curr: 1}
        });
    };
    /**
     * 弹出添加对话框
     */
    dataLog.openAddVehicle = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '车辆添加',
            content: Feng.ctxPath + '/dataLog/add',
            area: ['800px', '600px'],
            btn: ['取消新增车辆信息'],
            shade: [0.8, '#393D49'],
            btnAlign: 'c',
            anim: 5,
            end:function(){
                table.reload(dataLog.tableId);
            }
        });
    };
    /**
     * 导出excel按钮
     */
    dataLog.exportExcel = function () {
        var checkRows = table.checkStatus(dataLog.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };
    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    dataLog.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/dataLog/delete", function (data) {
                if (data.code == '200') {
                    Feng.success("删除成功!");
                    table.reload(dataLog.tableId);
                } else {
                    Feng.error(data.message);
                }

            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };
    //----点击更新-----
    dataLog.onEditItem = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '地磅修改',
            content: Feng.ctxPath + '/dataLog/edit?id='+ data.id,
            area: ['800px', '600px'],
            btn: ['暂不进行修改'],
            shade: [0.8, '#393D49'],
            btnAlign: 'c',
            anim: 5,
            end:function(){
                table.reload(dataLog.tableId);
            }
        });
    }
    // 渲染表格
    var tableResult = table.render({
        elem: '#' + dataLog.tableId,
        url: Feng.ctxPath + '/dataLog/list',
        height: "full-98",
        cellMinWidth: 100,
        text: {
            none: '未查找到数据,请联系管理员'
        },
        even: true,
        method: 'get',
        limit: 10,
        page: true,
        cols: dataLog.initColumn(),
        done: function (res, curr, count) {
            $("[data-field = 'type']").children().each(function () {
                if ($(this).text() == '2') {
                    $(this).text("更新");
                }else if ($(this).text() == '3') {
                    $(this).text("删除");
                }
            });
        }
    });
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        dataLog.search();
    });
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        dataLog.openAddVehicle();
    });
    // 导出excel
    $('#btnExp').click(function () {
        dataLog.exportExcel();
    });
    // 工具条点击事件
    table.on('tool(' + dataLog.tableId + ')', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            if (layEvent === 'delete') {
                dataLog.onDeleteItem(data);
            } else if (layEvent === 'edit') {
                dataLog.onEditItem(data);
            }
        }
    );
    var now = new Date();
    //渲染时间选择框
    let startTime = laydate.render({
        elem: '#beginTime',
        max: now.toLocaleDateString(),
        done: function (value, date) {
            endTime.config.min = {
                year:date.year,
                month:date.month-1,//注意是month-1，写在date上的话你后边的日期选择不了
                date: date.date,
                hours: 0,
                minutes: 0,
                seconds : 0

            }
        }
    });

    //渲染时间选择框
    let endTime = laydate.render({
        elem: '#endTime',
        max: now.toLocaleDateString(),
        done: function (value, date) {
            startTime.config.max = {
                year:date.year,
                month:date.month-1,//注意是month-1，写在date上的话你后边的日期选择不了
                date: date.date,
                hours: 0,
                minutes: 0,
                seconds : 0

            }

        }
    });
});