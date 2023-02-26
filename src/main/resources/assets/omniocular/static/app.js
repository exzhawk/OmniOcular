(function () {

    var chart = Highcharts.stockChart('container', {
        rangeSelector: {
            selected: 1
        },

        title: {
            text: 'Watching'
        },

        series: [{
            name: 'first',
            data: [],
            tooltip: {
                valueDecimals: 0
            }
        }]
    });

    function start() {
        var ws = new WebSocket("ws://" + window.location.host + "/w");
        //noinspection JSUnusedLocalSymbols
        ws.onopen = function (event) {
            console.log("websocket opened");
        };
        ws.onmessage = function (event) {
            var json = JSON.parse(event.data);
            var timestamp = json["timestamp"];
            switch (json["type"]) {
                case "nbt":
                    var nbt = json["data"];
                    document.querySelector("#json").innerText = JSON.stringify(nbt, null, 2);
                    var newPoint = [timestamp, nbt["BurnTime"]];
                    console.log(newPoint);
                    chart.series[0].addPoint(newPoint);
                    break;
                default:
                    break;
            }
        };
        //noinspection JSUnusedLocalSymbols
        ws.onclose = function (event) {
            setTimeout(function () {
                start();
            }, 500);
        };

    }

    start();
})();
