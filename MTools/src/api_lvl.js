const androids = require("./res/ApiLevel");

const Grid = require("console-grid");
const Colors = Grid.Style;
const grid = new Grid();

function getApi(num) {
  const data = {
    columns: [{ id: "id", name: "api level" }, { id: "name", name: "name" },
      { id: "version", name: "version" }, { id: "date", name: "release @" }],
    rows: []
  };

  androids.filter(it => it.id === num)
    .forEach(it => console.log(it));
  grid.render(data);

}

module.exports = { getApi };
