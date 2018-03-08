var data_p = [];
var data_t = [];
var data_a = [];

var lastPressure = 0;
var prePieces = [];
var preLastPoint = 1519982184000;
var preCurrentPoint   = 1519982184000;

var lastTempera = 0;
var tmpPieces = [];
var tmpLastPoint = 1519982184000;
var tmpCurrentPoint   = 1519982184000;


var lastAcceler = 0;
var accPieces = [];
var accLastPoint = 1519982184000;
var accCurrentPoint   = 1519982184000;

var nIntervId;

var accOption = {

    grid:[

        {x: '0%', y: '20%', width: '110%', height: '73%'}

    ],
    title : {
        text : '加速度实时数据',
        left: 'center',
        top: '20',
        textStyle : {
            color : 'rgba(255, 255, 255, 0.8)',
            fontWeight : 'lighter',
            fontSize : 23,
            fontFamily: 'Microsoft YaHei UI'
        }
    },
    tooltip : {
        trigger : 'axis',
        snap:true,
        formatter : function(params) {
            params = params[0];
            var date = new Date(params.name);
            return params.value[1] + " m/s²";
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
        type : 'time',
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
        }
    }],
    yAxis : [{
        gridIndex: 0,
        offset:0,
        type : 'value',
        boundaryGap : [ 0, '100%' ],
        minInterval : 0.5,
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
    visualMap: {
        show: false,
        dimension: 0,
        pieces: [{
            gt: 1519982184000,
            lte: 2519982194000,
            color: '#5e8c8a'
        }]
    },
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
                //color : "#5e8c8a", //连线颜色
                opacity:1
            }
        },

        data : data_a
    } ]
};

var optionPressure = {
    grid:[

        {x: '0%', y: '20%', width: '110%', height: '73%'}

    ],
    title : {
        text : '胎压实时数据',
        left: 'center',
        top: '20',
        textStyle : {
            color : 'rgba(255, 255, 255, 0.8)',
            fontWeight : 'lighter',
            fontSize : 23,
            fontFamily: 'Microsoft YaHei UI'
        }
    },
    tooltip : {
        trigger : 'axis',
        snap:true,
        formatter : function(params) {
            params = params[0];
            var date = new Date(params.name);
            return params.value[1] + " Pa";
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
        type : 'time',
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
        }
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
    visualMap: {
        show: false,
        dimension: 0,
        pieces: [{
            gt: 1519982184000,
            lte: 2519982194000,
            color: '#5e8c8a'
            }]
    },
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
                //color : "#5e8c8a", //连线颜色
                opacity:1
            }
        },

        data : data_p
    } ]
};

var optionTemperature = {
    grid:[

        {x: '0%', y: '20%', width: '110%', height: '73%'}

    ],
    title : {
        text : '温度实时数据',
        left: 'center',
        top: '20',
        textStyle : {
            color : 'rgba(255, 255, 255, 0.8)',
            fontWeight : 'lighter',
            fontSize : 23,
            fontFamily: 'Microsoft YaHei UI'
        }
    },
    tooltip : {
        trigger : 'axis',
        snap:true,
        formatter : function(params) {
            params = params[0];
            var date = new Date(params.name);
            return params.value[1] + " ℃";
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
    visualMap: {
        show: false,
        dimension: 0,
        pieces: [{
            gt: 1519982184000,
            lte: 2519982194000,
            color: '#5e8c8a'
        }]
    },
    xAxis : [{
        gridIndex: 0,
        type : 'time',
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
        }
    }],
    yAxis : [{
        gridIndex: 0,
        offset:0,
        type : 'value',
        boundaryGap : [ 0, '100%' ],
        minInterval : 0.2,
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
                //color : "#5e8c8a", //连线颜色
                opacity:1
            }
        },

        data : data_t
    } ]
};

function locationUpdate(param) {

    $.ajax({

        type: "get",
        url: "/charts/ajaxGPSLocationSingle",
        dataType: "json",
        async: true,
        data: {
            "id": param.boxId,
            //"tmsp": param.epochSecond,
        },
        success: function (result) {

            map = new AMap.Map('locationCon', {
                resizeEnable: true,
                zoom: 15,
                center: [result.longitude, result.latitude],
                mapStyle: 'amap://styles/whitesmoke'
            });

            new AMap.CircleMarker({
                map:param.amap,
                center: [result.longitude, result.latitude],
                radius:10,
                strokeColor:'white',
                strokeWeight:0,
                strokeOpacity:0.0,
                fillColor:'#6dc579',
                fillOpacity:0.8,
                zIndex:10,
                bubble:true,
                cursor:'pointer',
                clickable: true
            });

            /*<![CDATA[*/
            param.sContainer.innerHTML = result.speed + "<span style=\"font-size: 13px;\">&nbsp;&nbsp;&nbsp;km/h</span>";
            /*]]>*/

        },
        error : function(xhr, status, errMsg) {


        }

    });

}

function initCharts(param) {

/*    $.ajax({
        type: "post",
        url: "/charts/ajaxTirePast3min",
        dataType: "json",
        async: true,
        data: {
            "id": param.boxId,
            "tp": param.epochSecond,
            "pl": param.place
        },
        success: function (result) {

            var arr = result;

            for(var i = 0; i < arr.length; i ++) {

                var p = {

                    name: param.epochSecond,
                    value:[arr[i].timestamp * 1000, arr[i].pressure]

                };

                data.push(p);

            }

            nIntervId = setInterval(function() {

                loadTireMessage(param);

            }, 1000);

        }
    });*/

    nIntervId = setInterval(function() {

        locationUpdate(param);
        loadTireMessage(param);

    }, 1000);

}

function loadTireMessage(param) {

    $.ajax({
        type: "post",
        url: "/charts/ajaxTireInfo",
        dataType: "json",
        async: true,
        data: {
            "id": param.boxId,
            //"tp": param.epochSecond,
            "pl": param.place
        },
        success: function (result) {

            drawPressureChart(result, param);
            drawTemperatureChart(result, param);
            drawAccChart(result, param);
            // param.epochSecond ++;

        },
        error : function(xhr, status, errMsg) {



        }

    });

}

function drawAccChart(tireMessage, param) {

    accCurrentPoint = tireMessage.timestamp * 1000;

    if(accPieces.length > 1) accPieces.pop();
    if(accPieces.length == 0) accLastPoint = accCurrentPoint - 1000;

    var clr = '#ce4b44';

    if(tireMessage.accelerate != 0) {

        lastAcceler = tireMessage.accelerate;
        clr = 'rgba(210, 160, 60, 0.8)';

        var piece = {
            gt: accLastPoint - 500,
            lte: accCurrentPoint + 500,
            color: clr
        };

        accPieces.push(piece);

        accPieces[accPieces.length - 2].lte -= 500;

    } else {

        tireMessage.accelerate = lastAcceler;
        clr = 'rgba(169, 170, 181, 0.5)';

        var piece = {
            gt: accLastPoint,
            lte: accCurrentPoint,
            color: clr
        };

        accPieces.push(piece);

    }

    accPieces.push({gt: accCurrentPoint,
        lte: accCurrentPoint, //9519982194000,
        color: clr});

    accLastPoint = accCurrentPoint;

    if(accPieces.length > 31) accPieces.shift();

    accOption.visualMap = {
        show: false,
        dimension: 0,
        pieces: accPieces
    };

    var p = {
        name : accCurrentPoint,
        value : [
            accCurrentPoint,
            tireMessage.accelerate
        ]
    };

    if(data_a.length > 30) data_a.shift();
    data_a.push(p);

    accOption.series[0].data = data_a;

    param.aContainer.setOption(accOption);

}

function drawTemperatureChart(tireMessage, param) {

    tmpCurrentPoint = tireMessage.timestamp * 1000;

    if(tmpPieces.length > 1) tmpPieces.pop();
    if(tmpPieces.length == 0) tmpLastPoint = tmpCurrentPoint - 1000;

    var clr = '#ce4b44';

    if(tireMessage.temperature != -274) {

        lastTempera = tireMessage.temperature;
        clr = 'rgba(210, 160, 60, 0.8)';

        var piece = {
            gt: tmpLastPoint - 500,
            lte: tmpCurrentPoint + 500,
            color: clr
        };

        tmpPieces.push(piece);

        tmpPieces[tmpPieces.length - 2].lte -= 500;

    } else {

        tireMessage.temperature = lastTempera;
        clr = 'rgba(169, 170, 181, 0.5)';

        var piece = {
            gt: tmpLastPoint,
            lte: tmpCurrentPoint,
            color: clr
        };

        tmpPieces.push(piece);

    }

    tmpPieces.push({gt: tmpCurrentPoint,
        lte: tmpCurrentPoint, //9519982194000,
        color: clr});

    tmpLastPoint = tmpCurrentPoint;

    if(tmpPieces.length > 31) tmpPieces.shift();

    optionTemperature.visualMap = {
        show: false,
        dimension: 0,
        pieces: tmpPieces
    };

    var p = {
        name : tmpCurrentPoint,
        value : [
            tmpCurrentPoint,
            tireMessage.temperature
        ]
    };

    if(data_t.length > 30) data_t.shift();
    data_t.push(p);

    optionTemperature.series[0].data = data_t;

    param.tContainer.setOption(optionTemperature);

}


function drawPressureChart(tireMessage, param) {

    preCurrentPoint = tireMessage.timestamp * 1000;

    if(prePieces.length > 1) prePieces.pop();
    if(prePieces.length == 0) preLastPoint = preCurrentPoint - 1000;

    var clr = '#ce4b44';

    if(tireMessage.pressure >= 0) {

        lastPressure = tireMessage.pressure;
        clr = 'rgba(210, 160, 60, 0.8)';

        var piece = {
            gt: preLastPoint - 500,
            lte: preCurrentPoint + 500,
            color: clr
        };

        prePieces.push(piece);

        prePieces[prePieces.length - 2].lte -= 500;

    } else {

        tireMessage.pressure = lastPressure;
        clr = 'rgba(169, 170, 181, 0.5)';

        var piece = {
            gt: preLastPoint,
            lte: preCurrentPoint,
            color: clr
        };

        prePieces.push(piece);

    }

    prePieces.push({gt: preCurrentPoint,
        lte: preCurrentPoint, //9519982194000,
        color: clr});

    preLastPoint = preCurrentPoint;

    if(prePieces.length > 31) prePieces.shift();

    optionPressure.visualMap = {
        show: false,
        dimension: 0,
        pieces: prePieces
    };

    var p = {
        name : preCurrentPoint,
        value : [
            preCurrentPoint,
            tireMessage.pressure
        ]
    };

    if(data_p.length > 30) data_p.shift();
    data_p.push(p);

    optionPressure.series[0].data = data_p;

    param.pContainer.setOption(optionPressure);

}

function healthChart(score) {

    var myHealthChart = echarts.init(document.getElementById('healthCon'));

    var scale = 1;

    var echartData = [ {
        value : score,
        name : '健康指'
    }, {
        value : 100 - score,
        name : 'empty'
    } ];

    var rich = {
        yellow : {
            color : "#ffc72b",
            fontSize : 20 * scale,
            padding : [ 5, 4 ],
            align : 'center'
        },
        total : {
            color : "#fff",
            fontSize : 70 * scale,
            align : 'center'
        },
        white : {
            color : "#fff",
            align : 'center',
            fontSize : 14 * scale,
            padding : [ 16, 0 ]
        },
        blue : {
            color : '#49dff0',
            fontSize : 16 * scale,
            align : 'center'
        },
        hr : {
            borderColor : '#3074a5',
            width : '100%',
            borderWidth : 1,
            height : 0,
        }
    };

    var option = {
        backgroundColor : '#022431',

        title : {
            text : '健康指数',
            left : 'center',
            top : '55%',
            padding : [ 15, 0 ],
            textStyle : {
                color : '#fff',
                fontSize : 13 * scale,
                align : 'center'
            }
        },
        legend : {
            selectedMode : false,
            formatter : '{total|' + echartData[0].value + '}',
            data : [ echartData[0].name ],
            // data: [''],
            // itemGap: 50,
            left : 'center',
            top : 'center',
            icon : 'none',
            align : 'center',
            textStyle : {
                color : "#fff",
                fontSize : 10 * scale,
                rich : rich
            },
        },
        series : [ {
            name : '总里程',
            type : 'pie',
            radius : [ '60%', '80%' ],
            hoverAnimation : false,
            color : [ 'rgba(118, 201, 168, 0.8)', '#022431' ],
            label : {
                normal : {
                    show : false
                },
            },
            labelLine : {
                normal : {
                    show : false
                }
            },
            data : echartData
        } ]
    };

    myHealthChart.setOption(option);

}

function kmChart(hadMiles) {

    var myKMChart = echarts.init(document.getElementById('liveCon'));

    var scale = 1;

    var echartData = [ {
        value : hadMiles,
        name : '已行驶公里数'
    }, {
        value : 60000 - hadMiles,
        name : '剩余寿命'
    } ];

    var rich = {
        yellow : {
            color : "#ffc72b",
            fontSize : 20 * scale,
            padding : [ 5, 4 ],
            align : 'center'
        },
        total : {
            color : "#fff",
            fontSize : 30 * scale,
            align : 'center'
        },
        white : {
            color : "#fff",
            align : 'center',
            fontSize : 14 * scale,
            padding : [ 16, 0 ]
        },
        blue : {
            color : '#49dff0',
            fontSize : 16 * scale,
            align : 'center'
        },
        hr : {
            borderColor : '#3074a5',
            width : '100%',
            borderWidth : 1,
            height : 0,
        }
    };

    var option = {
        backgroundColor : '#022431',

        title : {
            text : '总里程（公里）',
            left : 'center',
            top : '53%',
            padding : [ 15, 0 ],
            textStyle : {
                color : '#fff',
                fontSize : 13 * scale,
                align : 'center'
            }
        },
        legend : {
            selectedMode : false,
            formatter : function(name) {
                var total = 0; //里程总和
                var averagePercent; //综合正确率
                echartData.forEach(function(value, index, array) {
                    total += value.value;
                });
                return '{total|' + total + '}';
            },
            data : [ echartData[0].name ],
            // data: [''],
            // itemGap: 50,
            left : 'center',
            top : 'center',
            icon : 'none',
            align : 'center',
            textStyle : {
                color : "#fff",
                fontSize : 10 * scale,
                rich : rich
            },
        },
        series : [ {
            name : '总里程',
            type : 'pie',
            radius : [ '60%', '80%' ],
            hoverAnimation : false,
            color : [ '#9b6abd', '#b89335' ],
            label : {
                normal : {
                    formatter : function(params, ticket, callback) {
                        var total = 0;
                        var percent = 0;
                        echartData.forEach(function(value, index, array) {
                            total += value.value;
                        });
                        percent = ((params.value / total) * 100).toFixed(1);
                        return '{white|' + params.name + '}\n{hr|}\n{yellow|' + params.value + '}\n{blue|' + percent + '%}';
                    },
                    rich : rich
                },
            },
            labelLine : {
                normal : {
                    length : 30 * scale,
                    length2 : 0,
                    lineStyle : {
                        color : '#3074a5'
                    }
                }
            },
            data : echartData
        } ]
    };

    myKMChart.setOption(option);

}

function roadChart() {

    var myRoadChart = echarts.init(document.getElementById("rectChartsCon"));

    //app.title = '堆叠条形图';

    option = {
        title: {
            text: '',
            textStyle:{
                color:'#fff',
                fontFamily: "Microsoft YaHei UI",
                fontSize: "20",
                fontWeight: "200"
            }
        },
        backgroundColor : '#022431',
        legend : {
            bottom : 20,
            textStyle : {
                color : '#fff',
            },
            data : []
        },
        grid : {
            left : '0%',
            right : '0%',
            bottom : '0%',
            containLabel : true
        },

        tooltip : {
            show : true,
            showContent : false,
            trigger : 'axis',
            axisPointer : { // 坐标轴指示器，坐标轴触发有效
                show : false,
                type : 'line', // 默认为直线，可选为：'line' | 'shadow'
                lineStyle : {
                    color : 'rgba(0,0,0,0)'
                }
            }
        },
        xAxis : {
            type : 'value',
            show:false,
            axisTick : {
                show : false
            },
            axisLine : {
                show : false,
                lineStyle : {
                    color : '#fff',
                }
            },
            splitLine : {
                show : false
            },
        },
        yAxis : [
            {
                type : 'category',
                axisTick : {
                    show : false,
                    lineStyle : {
                        color : '#f0f',
                    }
                },
                axisLine : {
                    show : true,
                    lineStyle : {
                        color : '#fff',
                    }
                },
                axisLabel:{

                    fontSize: "19"

                },
                data : [ '沥青混凝土', '水泥混凝土', '沥青碎石', '石块/条石', '碎石', '炉渣', '砾石土', '沙砾土']
            }

        ],
        series : [
            {
                name : '公里',
                type : 'bar',
                barWidth:80,
                label : {
                    normal : {
                        show : true,
                        rotate : 0,
                        align : 'right',
                        verticalAlign : 'middle',
                        position : 'insideRight',
                        distance : 2,
                        formatter : '{c} {a}',
                        fontFamily : 'Microsoft YaHei UI',
                        fontSize : 18,
                        color : '#fff'
                    }
                },
                /* markPoint : {
                    symbolSize : 1,
                    symbolOffset : [ 0, '50%' ],
                    label : {
                        normal : {
                            formatter : function(obj) {
                                var v = obj.value;
                                var per = v / 1000 * 100;
                                return per + '%';
                            },
                            backgroundColor : 'rgba(242,242,242, 0)',
                            borderColor : '#aaa',
                            borderWidth : 0,
                            borderRadius : 4,
                            padding : [ 4, 10 ],
                            lineHeight : 26,
                            // shadowBlur: 5,
                            // shadowColor: '#000',
                            // shadowOffsetX: 0,
                            // shadowOffsetY: 1,
                            position : 'right',
                            distance : 20,
                            rich : {
                                a : {
                                    align : 'center',
                                    color : '#fff',
                                    fontSize : 18,
                                    textShadowBlur : 2,
                                    textShadowColor : '#000',
                                    textShadowOffsetX : 0,
                                    textShadowOffsetY : 1,
                                    textBorderColor : '#333',
                                    textBorderWidth : 2
                                },
                                b : {
                                    color : '#333'
                                },
                                c : {
                                    color : '#ff8811',
                                    textBorderColor : '#000',
                                    textBorderWidth : 1,
                                    fontSize : 22
                                }
                            }
                        }
                    },
                    data : [
                        {
                            type : 'max',
                            name : 'max days: '
                        },
                        {
                            type : 'average',
                            name : 'min days: '
                        }
                    ]
                }, */
                itemStyle : {
                    normal : {
                        show : true,
                        color : 'rgba(118, 201, 168, 0.8)',
                        barBorderRadius : 0,
                        borderWidth : 0,
                        borderColor : '#333'
                    }
                },
                barGap : '20%',
                barCategoryGap : '30%',
                data : [400, 520, 600, 780, 1200, 3021, 3500, 4500]
            }
        ]
    };

    myRoadChart.setOption(option);
}


function getDateStr(timeStamp) {

    var d = new Date(timeStamp);

    var year = d.getFullYear();
    var month = d.getMonth() + 1;
    var day = d.getDate();
    var h = d.getHours();
    var m = d.getMinutes();
    var s = d.getSeconds();

    var monthStr = month < 10 ? "0" + month : "" + month;
    var dayStr = day < 10 ? "0" + day : "" + day;
    var hStr = h < 10 ? "0" + h : "" + h;
    var mStr = m < 10 ? "0" + m : "" + m;
    var sStr = s < 10 ? "0" + s : "" + s;

    var timeStr = year + "" + monthStr + "" + dayStr + "\n" + hStr + ":" + mStr + ":" + sStr;

    return timeStr;

}