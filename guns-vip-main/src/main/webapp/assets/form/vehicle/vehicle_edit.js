layui.use(['layer', 'form', 'laydate', 'ax', 'jquery', 'admin'], function () {
    let laydate = layui.laydate,
        $ = layui.jquery;
    let layer = layui.layer;
    let form = layui.form;
    let $ax = layui.ax;
    let admin = layui.admin;
    //获取用户信息
    var ajax = new $ax(Feng.ctxPath + "/vehicle/getInfo?id=" + Feng.getUrlParam("id"));
    var result = ajax.start();
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
                $("#workArea").html(html);
                form.render();
                form.val('carFrom', result);
            }
        }
    });



    //拿取值
    let plateNumber;
    $("input[name=plateNumber]").blur(function () {
        plateNumber = $(this).val();
    });
    let tare;
    $("input[name=tare]").blur(function () {
        tare = $(this).val();
    });
    let loads;
    $("input[name=loads]").blur(function () {
        loads = $(this).val();
    });
    $(":button").click(function () {
        //自动获取焦点
        $("input[type=text]").trigger("blur");
        let str = "";
        $.ajax({
            url: "/vehicle/update",
            data: "id=" + $('input[name=id]').val()
                + "&plateNumber=" + plateNumber
                + "&tare=" + tare
                + "&loads=" + loads
                + "&workArea=" + $("select[name=workArea]").val()
                + "&status=" + $("select[name=status]").val()
                + "&vehicleType=" +  $("select[name=vehicleType]").val(),
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

});