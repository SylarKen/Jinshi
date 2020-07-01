function isValidIp(ip) {
    const reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
    return reg.test(ip);
}
layui.use(['form', 'layer', 'laydate', 'layer', 'admin'], function () {
    let form = layui.form,
        layer = layui.layer,
        $ = layui.jquery;
    const admin = layui.admin;
    form.verify({
        ipMeter: function (value, item) {
            if (!isValidIp(value)) {
                return '请输入正确的IP地址';
            }
        },
        ipCamera: function (value, item) {
            if (!isValidIp(value)) {
                return '请输入正确的IP地址';
            }
        },
        ipLed: function (value, item) {
            if (!isValidIp(value)) {
                return '请输入正确的IP地址';
            }
        },
        ipControlbox: function (value, item) {
            if (!isValidIp(value)){
                return '请输入正确的IP地址';
            }
        }

    });
    form.on('submit(addSubmit)', function (data) {
        let str = "";
        $.ajax({
            url: '/weighbridgeConfig/addWeighbridgeConfig',
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
            layer.alert(JSON.stringify("添加失败"), {
                title: '添加信息'
            });
            return false;
        } else {
            admin.putTempData('formOk', true);
            //关掉对话框
            admin.closeThisDialog();
            //parent.layer.close(parent.layer.getFrameIndex(window.name));
        }
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