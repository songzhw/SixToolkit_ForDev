const androids = require("./res/ApiLevel");

const Grid = require("console-grid");
const Colors = Grid.Style;
const grid = new Grid();

function getApi(num) {
  const data = {
    columns: [{ id: "id", name: "api level" }, { id: "name", name: "name" },
      { id: "version", name: "version" }, { id: "date", name: "release @" }],
    rows: [{ id: num, name: "i'm" + Colors.bg.green("hello"), version: "v1.0" },
      { id: 22, name: "xx", version: "v1.1" },
      { id: 33, name: "oo", version: "v2.0" }
    ]
  };
  grid.render(data);
}

module.exports = { getApi };
