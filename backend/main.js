var http = require("http");
var fs = require("fs");

http.createServer(function (req, res) {

    res.writeHead(200);
    fs.readFile("./public/file.html", function(err, data) {
        if(err)
            res.write(err.toString());
        else
            res.write(data.toString());

        res.end("HA-HA-HA");
    });

}).listen(8080);
