layui.use(['jquery', 'layer'], function () {
    let $ = layui.jquery;
    let admin = layui.admin;
    let weighRecord = {};
    let layer = layui.layer;
    // 导出流水日计excel
    $('#btnExp').click(function () {
        weighRecord.exportExcel();
    });
    weighRecord.exportExcel = function () {
        top.layui.admin.open({
            type: 2,
            title: '时间选择',
            content: Feng.ctxPath + '/reports/excelByTime',
            area: ['800px', '600px'],
            btn: ['取消下载'],
            shade: [0.8, '#393D49'],
            btnAlign: 'c',
            anim: 5,
            end: function () {
                //location=window.location;//
            }
        });
    };
    // 导出各工区日计excel
    $('#btnExp1').click(function () {
        weighRecord.exportExcelByTimeAndAreaCode();
    });
    weighRecord.exportExcelByTimeAndAreaCode = function () {
        // admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '时间选择',
            content: Feng.ctxPath + '/reports/excelByTimeAndAreaCode',
            area: ['800px', '600px'],
            btn: ['取消下载'],
            shade: [0.8, '#393D49'],
            btnAlign: 'c',
            anim: 5,
            end: function () {
            }
        });
    };
    // 导出各工区yue计excel
    $('#btnExp2').click(function () {
        top.layui.admin.open({
            type: 2,
            title: '时间选择',
            content: Feng.ctxPath + '/reports/monthExcelByTimeAndAreaCode',
            area: ['800px', '600px'],
            btn: ['取消下载'],
            shade: [0.8, '#393D49'],
            btnAlign: 'c',
            anim: 5,
            end: function () {
                //location=window.location;//
            }
        });
    });
    // 导出各工区季计excel
    $('#btnExp3').click(function () {
        top.layui.admin.open({
            type: 2,
            title: '时间选择',
            content: Feng.ctxPath + '/reports/seasonExcelByTimeAndAreaCode',
            area: ['800px', '600px'],
            btn: ['取消下载'],
            shade: [0.8, '#393D49'],
            btnAlign: 'c',
            anim: 5,
            end: function () {
                //location=window.location;//
            }
        });
    });
    // 导出各工区半年计excel
    $('#btnExp4').click(function () {
        top.layui.admin.open({
            type: 2,
            title: '时间选择',
            content: Feng.ctxPath + '/reports/halfExcelByTimeAndAreaCode',
            area: ['800px', '600px'],
            btn: ['取消下载'],
            shade: [0.8, '#393D49'],
            btnAlign: 'c',
            anim: 5,
            end: function () {
                //location=window.location;//
            }
        });
    });
    // 导出各工区年计excel
    $('#btnExp5').click(function () {
        top.layui.admin.open({
            type: 2,
            title: '时间选择',
            content: Feng.ctxPath + '/reports/yearExcelByTimeAndAreaCode',
            area: ['800px', '600px'],
            btn: ['取消下载'],
            shade: [0.8, '#393D49'],
            btnAlign: 'c',
            anim: 5,
            end: function () {
                //location=window.location;//
            }
        });
    });
    // 导出六工区日计excel
    $('#btnExp6').click(function () {
        top.layui.admin.open({
            type: 2,
            title: '时间选择',
            content: Feng.ctxPath + '/reports/daysExcelBySix',
            area: ['800px', '600px'],
            btn: ['取消下载'],
            shade: [0.8, '#393D49'],
            btnAlign: 'c',
            anim: 5,
            end: function () {
                //location=window.location;//
            }
        });
    });
    // 导出六工区月计excel
    $('#btnExp7').click(function () {
        top.layui.admin.open({
            type: 2,
            title: '时间选择',
            content: Feng.ctxPath + '/reports/monthsExcelBySix',
            area: ['800px', '600px'],
            btn: ['取消下载'],
            shade: [0.8, '#393D49'],
            btnAlign: 'c',
            anim: 5,
            end: function () {
                //location=window.location;//
            }
        });
    });
    // 导出六工区季计excel
    $('#btnExp8').click(function () {
        top.layui.admin.open({
            type: 2,
            title: '时间选择',
            content: Feng.ctxPath + '/reports/seasonExcelBySix',
            area: ['800px', '600px'],
            btn: ['取消下载'],
            shade: [0.8, '#393D49'],
            btnAlign: 'c',
            anim: 5,
            end: function () {
                //location=window.location;//
            }
        });
    });
    // 导出六工区半年计excel
    $('#btnExp9').click(function () {
        top.layui.admin.open({
            type: 2,
            title: '时间选择',
            content: Feng.ctxPath + '/reports/halfExcelBySix',
            area: ['800px', '600px'],
            btn: ['取消下载'],
            shade: [0.8, '#393D49'],
            btnAlign: 'c',
            anim: 5,
            end: function () {
                //location=window.location;//
            }
        });
    });
    // 导出六工区年计excel
    $('#btnExp10').click(function () {
        top.layui.admin.open({
            type: 2,
            title: '时间选择',
            content: Feng.ctxPath + '/reports/yearExcelBySix',
            area: ['800px', '600px'],
            btn: ['取消下载'],
            shade: [0.8, '#393D49'],
            btnAlign: 'c',
            anim: 5,
            end: function () {
                //location=window.location;//
            }
        });
    });
    // 导出废料运输车辆excel
    $('#btnExp11').click(function () {
        layer.confirm('将会下载全部车辆信息', {icon: 6, title:'提示'}, function(index){
            location.href = Feng.ctxPath + "/reports/allCarsExcel";
            layer.close(index); //关闭confirm框
        });
    });
    // 导出六工区年计excel
    $('#btnExp12').click(function () {
        top.layui.admin.open({
            type: 2,
            title: '车辆选择',
            content: Feng.ctxPath + '/reports/carExcel',
            area: ['800px', '600px'],
            btn: ['取消下载'],
            shade: [0.8, '#393D49'],
            btnAlign: 'c',
            anim: 5,
            end: function () {
                //location=window.location;//
            }
        });
    });

});