// src/index.js --name=tom --id=23 -h=true (参数单字母用-, 多字母用--开头!!!)
// argv的结果就是:  { _: [], name: 'tom', id: 23, h: 'true', '$0': 'src/index.js' }

const { getApi } = require("./api_lvl");

const argv = require("yargs")
  .alias("a", "api")
  .argv;
const api = argv.api;

if (api) {
  getApi(api);
}


