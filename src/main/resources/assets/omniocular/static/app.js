(function () {
    function start(delay) {
        var ws = new WebSocket("ws://" + window.location.host + "/w");
        ws.onopen = function (event) {
            console.log("websocket opened");
        };
        ws.onmessage = function (event) {
            switch (event.data[0]) {
                case "{":
                    document.querySelector("#json").innerText = JSON.stringify(JSON.parse(event.data), null, 2);
                    break;
                case "H":
                    break;
                default:
                    break;
            }
        };
        ws.onclose = function (event) {
            setTimeout(function () {
                start(delay * 2);
            }, delay * 100);
        };

    }

    start(1);
})();