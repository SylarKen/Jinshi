layui.use(['echarts', 'jquery'], function () {
    let ws;
    let target = "ws://" + getPath() + "/echo";
    let echarts = layui.echarts;
    let $ = layui.jquery;
    var ip;
    var ip1;
    $.ajax({
        type: "get",
        url: "/weighbridgeConfig/getAllIpCamera",
        dataType: "json",
        async: false,
        success: function (data) {
            ip = data.data[0].ipCamera;
            ip1 = data.data[1].ipCamera;
        }
    });
    function subOpen() {
        //var target = document.getElementById('target').value;
        if (target == '') {
            alert('Please select server side connection implementation.');
            return;
        }
        if ('WebSocket' in window) {
            ws = new WebSocket(target+"/"+ip);
            ws1 = new WebSocket(target+"/"+ip1);
        } else if ('MozWebSocket' in window) {
            ws = new MozWebSocket(target+"/"+ip);
            ws1 = new MozWebSocket(target+"/"+ip1);
        } else {
            alert('WebSocket is not supported by this browser.');
            return;
        }

        /*ws.onopen = function () {
            console.log("客户端打开");
            //subSend();
        }*/
        ws.onmessage = function (msg) {
            console.log("客户端接收后端消息---");
            option.series[0].data[0].value = msg.data;
            option.series[0].data[0].name = "地磅一重量";
            myChart.setOption(option, true);
        }

        ws1.onmessage = function (msg) {
            console.log("客户端接收后端消息---");
            option.series[0].data[0].value = msg.data;
            option.series[0].data[0].name = "地磅二重量";
            myChart1.setOption(option, true);
        }
        function subSend() {
            //var msg = document.getElementById('msg').value;
            //console.log(ws);
            var msg = "123";
            ws.send(msg);
            //document.getElementById('msg').value = "";
        }
    }

    var myChart = echarts.init(document.getElementById('main'));
    var myChart1 = echarts.init(document.getElementById('main1'));
    option = {
        tooltip: {
            formatter: "{a} <br/>{b} : {c} KG"
        },
        series: [
            {
                name: '地磅实时重量',
                type: 'gauge',
                detail: {formatter: '{value}KG'}, //仪表盘显示数据
                axisLine: { //仪表盘轴线样式
                    lineStyle: {
                        width: 20
                    }
                },
                splitLine: { //分割线样式
                    length: 20
                },
                data: [{value: 0, name: '重量'}],
                max: 50000,
                markPoint: {
                    symbol: 'circle',
                    symbolSize: 5,
                    data: [
                        //跟你的仪表盘的中心位置对应上，颜色可以和画板底色一样
                        {x: 'center', y: 'center', itemStyle: {color: '#FFF'}}
                    ]
                },
            }

        ]
    };
    myChart.setOption(option);
    myChart1.setOption(option);
    subOpen();
});
function getPath(){

    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： /uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    //去掉http
    return(localhostPaht.split("//")[1]);

}