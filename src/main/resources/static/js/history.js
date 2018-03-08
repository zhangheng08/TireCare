var yhpArr = [];
var yhaArr = [];
var yhtArr = [];

var xhArr = [];

var accHistoryOption = {

    grid:[

        {x: '0%', y: '20%', width: '94%', height: '67%', containLabel: true}

    ],
    title : {
        text : '加速度历史数据',
        left: 'center',
        top: '20',
        textStyle : {
            color : 'rgba(255, 255, 255, 0.8)',
            fontWeight : 'lighter',
            fontSize : 23,
            fontFamily: 'Microsoft YaHei UI'
        }
    },
    tooltip: {
        trigger: 'axis',
        position: function (pt) {
            return [pt[0], '10%'];
        },
        axisPointer : {
            type: 'line',
            animation : true,
            lineStyle:{
                type: 'dotted',
                color:'rgba(255,255,255, 0.3)'
            }
        }
    },
    xAxis : [{
        gridIndex: 0,
        type : 'category',
        boundaryGap: [0, '100%'],
        axisLine : {
            lineStyle : {
                type : 'solid',
                color : 'gray', //左边线的颜色
                width : '1.2', //坐标线的宽度
                opacity:1
            }
        },
        axisLabel : {
            align:'left',
            textStyle : {
                color : '#ccd6d7', //坐标值得具体的颜色

            }
        },
        splitLine : {
            show : false,
            lineStyle: {
                type: 'dashed',
                color: ['rgba(255,255,255,0.1)', 'rgba(255,255,255,0.1)']
            }
        },
        data:xhArr
    }],
    yAxis : [{
        gridIndex: 0,
        offset:0,
        type : 'value',
        boundaryGap : [ 0, '100%' ],
        minInterval : 0.2,
        /*            max:103,
                    min:98,*/
        axisLine : {
            lineStyle : {
                type : 'solid',
                color : 'gray', //左边线的颜色
                width : '1.2', //坐标线的宽度
                opacity:1
            }
        },
        axisLabel : {
            textStyle : {
                color : '#ccd6d7', //坐标值得具体的颜色
            },
            inside:true,
            showMinLabel:false,
            showMaxLabel:true
        },
        axisTick: {
            inside:true
        },
        splitLine : {
            show : true,
            lineStyle: {
                type: 'dashed',
                color: ['rgba(255,255,255,0.06)', 'rgba(255,255,255,0.06)']
            }
        }
    }],
/*    visualMap: {
        show: false,
        dimension: 0,
        pieces: [{
            gt: 1519982184000,
            lte: 2519982194000,
            color: '#5e8c8a'
        }]
    },*/
    dataZoom: [
        {
            type:'slider',
            show: true,
            start: 50,
            end: 100,
            fillerColor:'rgba(255, 255, 255, 0.1)',
            backgroundColor:'rgba(255, 255, 255, 0.0)',
            borderWidth:0.2,
            borderColor:'rgba(0, 0, 0, 0.05)',
            textStyle:{
                color:'rgba(255, 255, 255, 0.4)'
            },
            dataBackground:{
                lineStyle:{
                    width:1,
                    color:'rgba(255, 255, 255, 0.6)'
                },
                areaStyle:{
                    color:'rgba(255, 255, 255, 0.3)'
                }
            }
        }
    ],
    series : [ {
        name : '加速度数据',
        type : 'line',
        showSymbol : false,
        hoverAnimation : false,
        symbolSize : 8, //图标尺寸
        smooth : true,
        itemStyle : {
            emphasis:{

                color:'rgba(255,255,255,0.2)',
                borderColor: '#fff',
                borderWidth: 2,
                opacity: 1

            }
        },
        areaStyle : {
            normal : {
                color : {
                    type : 'linear',
                    x : 0,
                    y : 0,
                    x2 : 0,
                    y2 : 1,
                    colorStops : [ {
                        offset : 0,
                        color : 'rgba(256, 256, 256, 0.2)' // 0% 处的颜色
                    }, {
                        offset : 1,
                        color : 'rgba(256, 256, 256, 0.0)' // 100% 处的颜色
                    } ],
                    globalCoord : true // 缺省为 false
                }
            }
        },
        lineStyle : {
            normal : {
                width : 2, //连线粗细
                color : "#5e8c8a", //连线颜色
                opacity:1
            }
        },

        data : yhaArr
    } ]
};

var tmpHistoryOption = {

    grid:[

        {x: '0%', y: '20%', width: '94%', height: '67%', containLabel: true}

    ],
    title : {
        text : '温度历史数据',
        left: 'center',
        top: '20',
        textStyle : {
            color : 'rgba(255, 255, 255, 0.8)',
            fontWeight : 'lighter',
            fontSize : 23,
            fontFamily: 'Microsoft YaHei UI'
        }
    },
    tooltip: {
        trigger: 'axis',
        position: function (pt) {
            return [pt[0], '10%'];
        },
        axisPointer : {
            type: 'line',
            animation : true,
            lineStyle:{
                type: 'dotted',
                color:'rgba(255,255,255, 0.3)'
            }
        }
    },
    xAxis : [{
        gridIndex: 0,
        type : 'category',
        boundaryGap: [0, '100%'],
        axisLine : {
            lineStyle : {
                type : 'solid',
                color : 'gray', //左边线的颜色
                width : '1.2', //坐标线的宽度
                opacity:1
            }
        },
        axisLabel : {
            align:'left',
            textStyle : {
                color : '#ccd6d7', //坐标值得具体的颜色

            }
        },
        splitLine : {
            show : false,
            lineStyle: {
                type: 'dashed',
                color: ['rgba(255,255,255,0.1)', 'rgba(255,255,255,0.1)']
            }
        },
        data:xhArr
    }],
    yAxis : [{
        gridIndex: 0,
        offset:0,
        type : 'value',
        boundaryGap : [ 0, '100%' ],
        minInterval : 0.2,
        /*            max:103,
                    min:98,*/
        axisLine : {
            lineStyle : {
                type : 'solid',
                color : 'gray', //左边线的颜色
                width : '1.2', //坐标线的宽度
                opacity:1
            }
        },
        axisLabel : {
            textStyle : {
                color : '#ccd6d7', //坐标值得具体的颜色
            },
            inside:true,
            showMinLabel:false,
            showMaxLabel:true
        },
        axisTick: {
            inside:true
        },
        splitLine : {
            show : true,
            lineStyle: {
                type: 'dashed',
                color: ['rgba(255,255,255,0.06)', 'rgba(255,255,255,0.06)']
            }
        }
    }],
/*    visualMap: {
        show: false,
        dimension: 0,
        pieces: [{
            gt: 1519982184000,
            lte: 2519982194000,
            color: '#5e8c8a'
        }]
    },*/
    dataZoom: [
        {
            type:'slider',
            show: true,
            start: 50,
            end: 100,
            fillerColor:'rgba(255, 255, 255, 0.1)',
            backgroundColor:'rgba(255, 255, 255, 0.0)',
            borderWidth:0.2,
            borderColor:'rgba(0, 0, 0, 0.05)',
            textStyle:{
                color:'rgba(255, 255, 255, 0.4)'
            },
            dataBackground:{
                lineStyle:{
                    width:1,
                    color:'rgba(255, 255, 255, 0.6)'
                },
                areaStyle:{
                    color:'rgba(255, 255, 255, 0.3)'
                }
            }
        }
    ],
    series : [ {
        name : '温度数据',
        type : 'line',
        showSymbol : false,
        hoverAnimation : false,
        symbolSize : 8, //图标尺寸
        smooth : true,
        itemStyle : {
            emphasis:{

                color:'rgba(255,255,255,0.2)',
                borderColor: '#fff',
                borderWidth: 2,
                opacity: 1

            }
        },
        areaStyle : {
            normal : {
                color : {
                    type : 'linear',
                    x : 0,
                    y : 0,
                    x2 : 0,
                    y2 : 1,
                    colorStops : [ {
                        offset : 0,
                        color : 'rgba(256, 256, 256, 0.2)' // 0% 处的颜色
                    }, {
                        offset : 1,
                        color : 'rgba(256, 256, 256, 0.0)' // 100% 处的颜色
                    } ],
                    globalCoord : true // 缺省为 false
                }
            }
        },
        lineStyle : {
            normal : {
                width : 2, //连线粗细
                color : "#5e8c8a", //连线颜色
                opacity:1
            }
        },

        data : yhtArr
    } ]
};


var preHistoryOption = {

    grid:[

        {x: '0%', y: '20%', width: '94%', height: '67%', containLabel: true}

    ],
    title : {
        text : '胎压历史数据',
        left: 'center',
        top: '20',
        textStyle : {
            color : 'rgba(255, 255, 255, 0.8)',
            fontWeight : 'lighter',
            fontSize : 23,
            fontFamily: 'Microsoft YaHei UI'
        }
    },
    tooltip: {
        trigger: 'axis',
        position: function (pt) {
            return [pt[0], '10%'];
        },
        axisPointer : {
            type: 'line',
            animation : true,
            lineStyle:{
                type: 'dotted',
                color:'rgba(255,255,255, 0.3)'
            }
        }
    },
    xAxis : [{
        gridIndex: 0,
        type : 'category',
        boundaryGap: [0, '100%'],
        axisLine : {
            lineStyle : {
                type : 'solid',
                color : 'gray', //左边线的颜色
                width : '1.2', //坐标线的宽度
                opacity:1
            }
        },
        axisLabel : {
            align:'left',
            textStyle : {
                color : '#ccd6d7', //坐标值得具体的颜色

            }
        },
        splitLine : {
            show : false,
            lineStyle: {
                type: 'dashed',
                color: ['rgba(255,255,255,0.1)', 'rgba(255,255,255,0.1)']
            }
        },
        data:xhArr
    }],
    yAxis : [{
        gridIndex: 0,
        offset:0,
        type : 'value',
        boundaryGap : [ 0, '100%' ],
        minInterval : 0.2,
        /*            max:103,
                    min:98,*/
        axisLine : {
            lineStyle : {
                type : 'solid',
                color : 'gray', //左边线的颜色
                width : '1.2', //坐标线的宽度
                opacity:1
            }
        },
        axisLabel : {
            textStyle : {
                color : '#ccd6d7', //坐标值得具体的颜色
            },
            inside:true,
            showMinLabel:false,
            showMaxLabel:true
        },
        axisTick: {
            inside:true
        },
        splitLine : {
            show : true,
            lineStyle: {
                type: 'dashed',
                color: ['rgba(255,255,255,0.06)', 'rgba(255,255,255,0.06)']
            }
        }
    }],
    /*visualMap: {
        show: false,
        dimension: 0,
        pieces: [{
            gt: 1519982184000,
            lte: 2519982194000,
            color: '#5e8c8a'
        }]
    },*/
    dataZoom: [
        {
            type:'slider',
            show: true,
            start: 50,
            end: 100,
            fillerColor:'rgba(255, 255, 255, 0.1)',
            backgroundColor:'rgba(255, 255, 255, 0.0)',
            borderWidth:0.2,
            borderColor:'rgba(0, 0, 0, 0.05)',
            textStyle:{
                color:'rgba(255, 255, 255, 0.4)'
            },
            dataBackground:{
                lineStyle:{
                    width:1,
                    color:'rgba(255, 255, 255, 0.6)'
                },
                areaStyle:{
                    color:'rgba(255, 255, 255, 0.3)'
                }
            }
        }
    ],
    series : [ {
        name : '胎压数据',
        type : 'line',
        showSymbol : false,
        hoverAnimation : false,
        symbolSize : 8, //图标尺寸
        smooth : true,
        itemStyle : {
            emphasis:{

                color:'rgba(255,255,255,0.2)',
                borderColor: '#fff',
                borderWidth: 2,
                opacity: 1

            }
        },
        areaStyle : {
            normal : {
                color : {
                    type : 'linear',
                    x : 0,
                    y : 0,
                    x2 : 0,
                    y2 : 1,
                    colorStops : [ {
                        offset : 0,
                        color : 'rgba(256, 256, 256, 0.2)' // 0% 处的颜色
                    }, {
                        offset : 1,
                        color : 'rgba(256, 256, 256, 0.0)' // 100% 处的颜色
                    } ],
                    globalCoord : true // 缺省为 false
                }
            }
        },
        lineStyle : {
            normal : {
                width : 2, //连线粗细
                color : "#5e8c8a", //连线颜色
                opacity:1
            }
        },

        data : yhpArr
    } ]
};

function fetchWithTimeslice(from, to, index) {/*秒*/

    $.ajax({

        type: "get",
        url: "/charts/ajaxTimeslice",
        dataType: "json",
        async: true,
        data: {
            "from": from,
            "to": to,
            "index": index
        },
        success: function (result) {

            xhArr.length = 0;
            yhpArr.length = 0;
            yhtArr.length = 0;
            yhaArr.length = 0;

            var tireArr = result.tire;

            var timeslice = to - from;

            for(var i = 0; i <= timeslice; i ++) {

                xhArr.push(getDateStr((from + i) * 1000));

                var val_p = 0;
                var val_t = 0;
                var val_a = 0;

                if(tireArr.hasOwnProperty("" + (from + i))) {

                    var tireMessage = tireArr["" + (from + i)];
                    val_p = tireMessage.pressure;
                    val_t = tireMessage.temperature;
                    val_a = tireMessage.accelerate;

                }

                yhpArr.push(val_p);
                yhtArr.push(val_t);
                yhaArr.push(val_a);

            }

            pHistoryContainer.setOption(preHistoryOption);
            tHistoryContainer.setOption(tmpHistoryOption);
            aHistoryContainer.setOption(accHistoryOption);

        },

        error : function(xhr, status, errMsg) {


        }

    });

}
