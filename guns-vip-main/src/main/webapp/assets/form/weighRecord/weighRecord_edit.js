layui.use(['layer', 'form', 'laydate', 'ax', 'jquery', 'admin'], function () {
    let laydate = layui.laydate,
        $ = layui.jquery;
    let layer = layui.layer;
    let form = layui.form;
    let $ax = layui.ax;
    let admin = layui.admin;

    //获取用户信息
    var ajax = new $ax(Feng.ctxPath + "/weighRecord/getInfo?id=" + Feng.getUrlParam("id"));
    var result = ajax.start();
    form.val('DemoFrom', result);
    //拿取值
    let plateNumber;
    $("input[name=plateNumber]").blur(function () {
        plateNumber = $(this).val();
    });
    let weighbridgeCode;
    $("input[name=weighbridgeCode]").blur(function () {
        weighbridgeCode = $(this).val();
    });
    let tare;
    $("input[name=tare]").blur(function () {
        tare = $(this).val();
    });
    let gross;
    $("input[name=gross]").blur(function () {
        gross = $(this).val();
    });
    let timeWeigh;
    $("input[name=timeWeigh]").blur(function () {
        timeWeigh = $(this).val();
    });

    $(":button").click(function () {
        //自动获取焦点
        $("input[type=text]").trigger("blur");
        let str = "";
        $.ajax({
            url: "/weighRecord/update",
            data: "id=" + $('input[name=id]').val()
                + "&plateNumber=" + plateNumber
                + "&tare=" + tare
                + "&weighbridgeCode=" + $("#weighbridgeCode").val()
                + "&gross=" + gross
                + "&timeWeigh=" + timeWeigh,
            async: false,
            dataType: "text",
            type: "post",
            success: function (data) {
                data = JSON.parse(data);
                if (data.success == true) {
                    admin.putTempData('formOk', true);
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
    laydate.render({
        elem: '#timeWeigh',
        type: 'datetime',
        position: 'fixed',
        trigger: 'click'
    });
});