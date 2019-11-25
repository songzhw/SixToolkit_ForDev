Tools for mobile development

## How to install it
1. Make sure you've installed node.js

2. $ git clone https://github.com/songzhw/SixToolkit_ForDev.git

3. go to the cloned project, run `yarn install`. 

4. add a environment variable to your `~/.bash_profile`

```
export PATH=$PATH:~/MTools/src
```

5. you now can run the code everywhere.
`$ mtools -a=21`

p.s. If you have permission, you can do this `$ chmod 755 mtools.js`.


## Modules
### 1. API Levels
the action is "-a", or "--action", value is a number.

```
$ yarn run mtools -a=20
┌───────────┬──────────────────────────────────┬─────────┬────────────┐
│ api level │ code_name                        │ version │ release @  │
├───────────┼──────────────────────────────────┼─────────┼────────────┤
│ 20        │ KitKat, with wearable extensions │ 4.4W    │ 2014-06-25 │
│ 20        │ KitKat, with wearable extensions │ 4.4W.1  │ 2014-09-06 │
│ 20        │ KitKat, with wearable extensions │ 4.4W.2  │ 2014-10-21 │
└───────────┴──────────────────────────────────┴─────────┴────────────┘
```
