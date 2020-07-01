layui.use(['table', 'admin', 'ax', 'rowSpans'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var rowSpans = layui.rowSpans;

    /**
     * 车辆管理
     */
    let vehicle = {
        tableId: 'vehicle'
    };
    vehicle.initColumn = function () {
        return [[
            //        id, plate_number, tare, loads, vehicle_type, b1, b2, b3
            {type: 'checkbox', fixed: 'left'},
            {field: 'id', title: 'ID', sort: true},
            {field: 'plate_number', title: '车牌号', sort: true},
            {field: 'tare', title: '皮重', sort: true},
            {field: 'loads', title: '载重', sort: true},
            {field: 'vehicle_type', title: '车型'},
            {field: 'work_area', title: '工区'},
            {field: 'status', title: '白名单'},
            {align: 'center', toolbar: '#tableBar', width: 120}
        ]];
    };
    /**
     * 点击查询按钮
     */
    vehicle.search = function () {
        var queryData = {};
        queryData['plateNumber'] = $("#plateNumber").val();
        queryData['workArea'] = $("#areaCode").val();
        queryData['vehicleType'] = $("#vehicleType").val();
        table.reload(vehicle.tableId, {
            where: queryData, page: {curr: 1}
        });
    };
    /**
     * 弹出添加对话框
     */
    vehicle.openAddVehicle = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '车辆添加',
            content: Feng.ctxPath + '/vehicle/add',
            area: ['800px', '80%'],
            btn: ['取消新增车辆信息'],
            shade: [0.8, '#393D49'],
            btnAlign: 'c',
            anim: 5,
            end: function () {
                table.reload(vehicle.tableId);
            }
        });
    };
    /**
     * 导出excel按钮
     */
    vehicle.exportExcel = function () {
       /* var checkRows = table.checkStatus(vehicle.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }*/
        layer.confirm('将会下载全部车辆信息', {icon: 6, title:'提示'}, function(index){
            location.href = Feng.ctxPath + "/reports/allCarsExcel";
            layer.close(index); //关闭confirm框
        });
    };
    /**
     *导出筛选后的excel
     */
    vehicle.exportExcel1 = function () {
        location.href = Feng.ctxPath + "/reports/getCarsExcel?plateNumber="+ $("#plateNumber").val() +
                                                            "&workArea=" + $("#areaCode").val() +
                                                            "&vehicleType=" + $("#vehicleType").val();
    }
    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    vehicle.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/vehicle/delete", function (data) {
                if (data.code == '200') {
                    Feng.success("删除成功!");
                    table.reload(vehicle.tableId);
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
    vehicle.onEditItem = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '地磅修改',
            content: Feng.ctxPath + '/vehicle/edit?id=' + data.id,
            area: ['800px', '80%'],
            btn: ['暂不进行修改'],
            shade: [0.8, '#393D49'],
            btnAlign: 'c',
            anim: 5,
            end: function () {
                table.reload(vehicle.tableId);
            }
        });
    }
    // 渲染表格
    var tableResult = table.render({
        elem: '#' + vehicle.tableId,
        url: Feng.ctxPath + '/vehicle/list',
        height: "full-98",
        cellMinWidth: 100,
        text: {
            none: '未查找到数据,请联系管理员'
        },
        even: false,
        method: 'get',
        limit: 10,
        page: true,
        cols: vehicle.initColumn(),
        done: function (res, curr, count) {
            $("[data-field = 'vehicle_type']").children().each(function () {
                if ($(this).text() == '1') {
                    $(this).text("运输车辆");
                } else if ($(this).text() == '0') {
                    $(this).text("其他车");
                } else if ($(this).text() == '2') {
                    $(this).text("叉装车辆");
                } else if ($(this).text() == '3') {
                    $(this).text("挖掘机");
                } else if ($(this).text() == '4') {
                    $(this).text("洒水车");
                } else if ($(this).text() == '5') {
                    $(this).text("维修车辆");
                } else if ($(this).text() == '6') {
                    $(this).text("管理车辆");
                }
            });
            $("[data-field = 'status']").children().each(function () {
                if ($(this).text() == 'ENABLE') {
                    $(this).text("是");
                } else if ($(this).text() == 'DISENABLE') {
                    $(this).text("否");
                }
            });
            rowSpans.layuiRowspan(['work_area'], 1);//格式化表格
        }
    });
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        vehicle.search();
    });
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        vehicle.openAddVehicle();
    });
    // 导出excel
    $('#btnExp').click(function () {
        vehicle.exportExcel();
    });
    // 导出excel
    $('#btnExp1').click(function () {
        vehicle.exportExcel1();
    });
    // 工具条点击事件
    table.on('tool(' + vehicle.tableId + ')', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            if (layEvent === 'delete') {
                vehicle.onDeleteItem(data);
            } else if (layEvent === 'edit') {
                vehicle.onEditItem(data);
            }
        }
    );
    $.ajax({
        url: Feng.ctxPath + "/workArea/queryAllWorkArea",
        async: false,
        dataType: "json",
        type: "get",
        success: function (data) {
            if (data.code == 200) {
                let html = "";
                $.each(data.data, function (i, workArea) {
                    html += "<option class='layui-input' value='" + workArea.workArea + "'>" + workArea.workArea + "</option>";
                });
                $("#areaCode").html(html);
                layui.form.render("select");
            }
        }
    });


});