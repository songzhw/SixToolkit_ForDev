const androids = require("./res/ApiLevel");

const Grid = require("console-grid");
const Colors = Grid.Style;
const grid = new Grid();

function getApi(num) {
  const data = {
    columns: [{ id: "id", name: "api level" }, { id: "name", name: "code_name" },
      { id: "version", name: "version" }, { id: "date", name: "release @" }],
    rows: []
  };

  androids.filter(it => it.id === num)
    .forEach(it => {
      data.rows.push({ id: Colors.red(it.id), name: it.name, version: it.version, date: it.date });
    });
  grid.render(data);

}

module.exports = { getApi };
