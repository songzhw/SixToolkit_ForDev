# groovy-toolkit
Some tools that I used in Android Developing or life


## 1. Json2Request ( !!! Not finished. Still working... !!! )
read a json, and parse it to java classes : **Request, **Parser, **Response

### 1.1 usage
1. paster the json strings to the "test2.json" file
2. run the command 
```groovy
groovy json_2_req_resp.groovy
```
<p>
Ok, now we have the java classes
<p>
	
## 1.2 NOTE
this json2Request tool has a strong connnection with the network framework in your app. 
different projects may have to adjust some code to generate the right Request/Response java class.

## 2. 