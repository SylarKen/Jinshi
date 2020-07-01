layui.use(['layer', 'form', 'laydate', 'ax', 'jquery', 'admin'], function () {
    let laydate = layui.laydate,
        $ = layui.jquery;
    let layer = layui.layer;
    let form = layui.form;
    let $ax = layui.ax;
    const admin = layui.admin;

    //获取用户信息
    var ajax = new $ax(Feng.ctxPath + "/weighbridgeConfig/getInfo?id=" + Feng.getUrlParam("id"));
    var result = ajax.start();
    form.val('wbcFrom', result);
    //拿取地磅编号
    let weightbridgeCode;
    $("input[name=weightbridgeCode]").blur(function () {
        weightbridgeCode = $(this).val();
    });
    //拿取地磅名称
    let name;
    $("input[name=name]").blur(function () {
        name = $(this).val();
    });
    //拿取地磅类型
    let type;
    $("input[name=type]").blur(function () {
        type = $(this).val();
    });
    //拿取称重上限
    let toplimit;
    $("input[name=toplimit]").blur(function () {
        toplimit = $(this).val();
    });
    //拿取仪表IP地址
    let ipMeter;
    $("input[name=ipMeter]").blur(function () {
        ipMeter = $(this).val();
    });
    //拿取车牌识别摄像机IP地址
    let ipCamera;
    $("input[name=ipCamera]").blur(function () {
        ipCamera = $(this).val();
    });
    //拿取LED指示牌IP地址
    let ipLed;
    $("input[name=ipLed]").blur(function () {
        ipLed = $(this).val();
    });
    //拿取起落杆IP地址
    let ipControlbox;
    $("input[name=ipControlbox]").blur(function () {
        ipControlbox = $(this).val();
    });
    $(":button").click(function () {
        //自动获取焦点
        $("input[type=text]").trigger("blur");
        let str = "";
        $.ajax({
            url: "/weighbridgeConfig/update",
            data: "id=" + $('input[name=id]').val()
                + "&weightbridgeCode=" + weightbridgeCode
                + "&name=" + name
                + "&type=" + type
                + "&toplimit=" + toplimit
                + "&ipMeter=" + ipMeter
                + "&ipCamera=" + ipCamera
                + "&ipLed=" + ipLed
                + "&ipControlbox=" + ipControlbox
                + "&available=" + $("select[name= 'available']").val()
                + "&isOnline=" + $("select[name= 'isOnline']").val(),
            async: false,
            dataType: "text",
            type: "post",
            success: function (data) {
                data = JSON.parse(data);
                if (data.success == true) {
                    admin.putTempData('formOk', true);
                    //关掉对话框
                    //admin.closeThisDialog();
                    parent.layer.close(parent.layer.getFrameIndex(window.name));
                    return;
                }
                layer.msg(data.message);
                return false;
            },
            error: function (err) {
                console.log(err);
            }
        });
    });
    form.on('select(available)', function (data) {
        if (data.value == "0") {
            $("select[name=isOnline]").val("0");
            $("select[name=isOnline]").attr("disabled", "disabled");
            form.render("select");
        } else {
            $("select[name=isOnline]").removeAttr("disabled");
            form.render("select");
        }
    })
});