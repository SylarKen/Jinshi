function isValidIp(ip) {

}
layui.use(['form', 'layer', 'laydate', 'layer', 'admin'], function () {
    let form = layui.form,
        layer = layui.layer,
        $ = layui.jquery;
    const admin = layui.admin;
    form.verify({

    });
    form.on('submit(addSubmit)', function (data) {
        let str = "";
        $.ajax({
            url: '/vehicle/addVehicle',
            type: 'post',
            async: false,
            data: data.field,
            dataType: 'text',
            success: function (data) {
                var parse = JSON.parse(data);
                str = parse.success;
                return;
            },
            error: function (err) {
                console.log(err);
            }
        });
        if (str != true) {
            layer.alert(JSON.stringify("添加失败, 可能有重复车牌"), {
                title: '添加信息'
            });
            return false;
        } else {
            admin.putTempData('formOk', true);
            parent.layer.close(parent.layer.getFrameIndex(window.name));
        }
    });
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
            }
        }
    });
});