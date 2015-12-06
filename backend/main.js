var http = require("http");

http.createServer(function (req, res) {

    res.writeHead(200);
    res.write(req.toString());

}).listen(8080);