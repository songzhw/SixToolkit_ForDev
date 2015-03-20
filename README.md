# groovy-toolkit
Some tools that I used in Android Developing or life


## 1. Json2Request 
read a json, and parse it to java classes : **Request, **Parser, **Response

### 1.1 usage
1. paster the json strings to the "test2.json" file
2. run the command 
```groovy
groovy json_2_req_resp.groovy
```
<br/>
Ok, now we have the java classes

	
## 1.2 NOTE
this json2Request tool has a strong connnection with the network framework in your app. <br/>
different projects may have to adjust some code to generate the right Request/Response java class.<br/>
<br/><br/>


## 2. AutoFindViewById

## 1.1 rules
* the id of view should be "view_page_function". And the name of class member would be like "viewFunction". <br/>
For example: <br/>
```xml
<TextView android:id="@+id/tv_pay_bank_name" ... /> 
```
would be named :
```java
private TextView tvBankName;
```
*  if the id of view ends with "_x", it means it will not be listed as a member in Class
* if the id of view ends with "_c", it means it will be listed as a member in Class, and it will be add the OnClickListener
* ``` "android:id" ``` should be the first attribute of a View in the layout xml file

## 1.2 usage
1. paste your layout xml content to "layout.xml"
2. run the "auto_findview.bat"

