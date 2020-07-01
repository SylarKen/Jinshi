layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 数据管理
     */
    let weighRecord = {
        tableId: 'weighRecord'
    };
    weighRecord.initColumn = function () {
        return [[
            //
            {type: 'checkbox', fixed: 'left'},
            {field: 'id', title: 'ID', sort: true},
            {field: 'plate_number', title: '车牌号', sort: true},
            {field: 'weighbridge_code', title: '地磅编号', sort: true},
            {field: 'tare', title: '皮重', sort: true},
            {field: 'gross', title: '毛重', sort: true},
            {field: 'time_weigh', title: '称重时间'},
            {align: 'center', toolbar: '#tableBar', width: 120}
        ]];
    };
    /**
     * 点击查询按钮
     */
    weighRecord.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(weighRecord.tableId, {
            where: queryData, page: {curr: 1}
        });
    };
    /**
     * 弹出添加对话框
     */
    weighRecord.openAddVehicle = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '车辆添加',
            content: Feng.ctxPath + '/weighRecord/add',
            area: ['800px', '600px'],
            btn: ['取消新增车辆信息'],
            shade: [0.8, '#393D49'],
            btnAlign: 'c',
            anim: 5,
            end:function(){
                table.reload(weighRecord.tableId);
            }
        });
    };
    /**
     * 导出excel按钮
     */
    weighRecord.exportExcel = function () {
        //window.location.href ="/weighRecord/getXlsx";
        /*admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '车辆添加',
            content: Feng.ctxPath + '/weighRecord/excelByTime',
            area: ['800px', '600px'],
            btn: ['取消新增车辆信息'],
            shade: [0.8, '#393D49'],
            btnAlign: 'c',
            anim: 5,
            end:function(){
                table.reload(weighRecord.tableId);
            }
        });*/
        var checkRows = table.checkStatus(wbc.tableId);
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
    weighRecord.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/weighRecord/delete", function (data) {
                if (data.code == '200') {
                    Feng.success("删除成功!");
                    table.reload(weighRecord.tableId);
                } else {
                    Feng.error(data.message);
                }

            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", data.id);
            ajax.set("plateNumber", data.plate_number)
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };
    //----点击更新-----
    weighRecord.onEditItem = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '地磅修改',
            content: Feng.ctxPath + '/weighRecord/edit?id='+ data.id,
            area: ['800px', '600px'],
            btn: ['暂不进行修改'],
            shade: [0.8, '#393D49'],
            btnAlign: 'c',
            anim: 5,
            end:function(){
                table.reload(weighRecord.tableId);
            }
        });
    }
    // 渲染表格
    var tableResult = table.render({
        elem: '#' + weighRecord.tableId,
        url: Feng.ctxPath + '/weighRecord/list',
        height: "full-98",
        cellMinWidth: 100,
        text: {
            none: '未查找到数据,请联系管理员'
        },
        even: true,
        method: 'get',
        limit: 10,
        page: true,
        cols: weighRecord.initColumn(),
        done: function (res, curr, count) {
            $("[data-field = 'vehicle_type']").children().each(function () {
                if ($(this).text() == '1') {
                    $(this).text("卡车");
                } else if ($(this).text() == '0') {
                    $(this).text("其他车");
                }
            });
        }
    });
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        weighRecord.search();
    });
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        weighRecord.openAddVehicle();
    });
    // 导出excel
    $('#btnExp').click(function () {
        weighRecord.exportExcel();
    });
    // 工具条点击事件
    table.on('tool(' + weighRecord.tableId + ')', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            if (layEvent === 'delete') {
                weighRecord.onDeleteItem(data);
            } else if (layEvent === 'edit') {
                weighRecord.onEditItem(data);
            }
        }
    );

});