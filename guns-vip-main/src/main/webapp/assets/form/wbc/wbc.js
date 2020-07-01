layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 地磅管理
     */
    let wbc = {
        tableId: 'wbc'
    };
    wbc.initColumn = function () {
        return [[
            // id, weightbridge_code, name, type, toplimit, ip_meter, ip_camera, ip_led, available, is_online
            {type: 'checkbox', fixed: 'left'},
            {field: 'id', title: 'ID', sort: true},
            {field: 'weightbridge_code', title: '地磅编号'},
            {field: 'name', title: '地磅名称'},
            {field: 'type', title: '地磅类型'},
            {field: 'toplimit', title: '称重上限', sort: true},
            {field: 'ip_meter', title: '仪表IP地址'},
            {field: 'ip_camera', title: '车牌识别摄像机IP地址'},
            {field: 'ip_led', title: 'LED指示牌IP地址'},
            {field: 'ip_controlbox', title: '起落杆ip地址'},
            {field: 'available', title: '是否可用'},
            {field: 'is_online', title: '是否在线'},
            {align: 'center', toolbar: '#tableBar', width: 120}
        ]];
    };
    /**
     * 点击查询按钮
     */
    wbc.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(wbc.tableId, {
            where: queryData, page: {curr: 1}
        });
    };
    /**
     * 弹出添加对话框
     */
    wbc.openAddWbc = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '地磅添加',
            content: Feng.ctxPath + '/weighbridgeConfig/add',
            area: ['800px', '600px'],
            btn: ['取消新增地磅信息'],
            shade: [0.8, '#393D49'],
            btnAlign: 'c',
            anim: 5,
            end:function(){
                table.reload(wbc.tableId);
            }
        });
    };
    /**
     * 导出excel按钮
     */
    wbc.exportExcel = function () {
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
    wbc.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/weighbridgeConfig/delete", function (data) {
                if (data.code == '200') {
                    Feng.success("删除成功!");
                    table.reload(wbc.tableId);
                } else {
                    Feng.error(data.message);
                }

            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("wbcId", data.id);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };
    //----点击更新-----
    wbc.onEditItem = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '地磅修改',
            content: Feng.ctxPath + '/weighbridgeConfig/edit?id='+ data.id,
            area: ['800px', '600px'],
            btn: ['暂不进行修改'],
            shade: [0.8, '#393D49'],
            btnAlign: 'c',
            anim: 5,
            end:function(){
                table.reload(wbc.tableId);
            }
        });
    }
    // 渲染表格
    var tableResult = table.render({
        elem: '#' + wbc.tableId,
        url: Feng.ctxPath + '/weighbridgeConfig/list',
        height: "full-98",
        cellMinWidth: 100,
        text: {
            none: '未查找到数据,请联系管理员'
        },
        even: true,
        method: 'get',
        limit: 10,
        page: true,
        cols: wbc.initColumn(),
        done: function (res, curr, count) {
            $("[data-field = 'available']").children().each(function () {
                if ($(this).text() == '1') {
                    $(this).text("可用");
                } else if ($(this).text() == '0') {
                    $(this).text("不可用");
                }
            });
            $("[data-field = 'is_online']").children().each(function () {
                if ($(this).text() == '1') {
                    $(this).text("在线");
                } else if ($(this).text() == '0') {
                    $(this).text("不在线");
                }
            });
        }
    });
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        wbc.search();
    });
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        wbc.openAddWbc();
    });
    // 导出excel
    $('#btnExp').click(function () {
        wbc.exportExcel();
    });
    // 工具条点击事件
    table.on('tool(' + wbc.tableId + ')', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            if (layEvent === 'delete') {
                wbc.onDeleteItem(data);
            } else if (layEvent === 'edit') {
                wbc.onEditItem(data);
            }
        }
    );

});