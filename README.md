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

#1.3 Result
the script will auto generate three java classes: **Request, **Parser, **Response. <br/>
(If your project are not same as mine, you can custom the script for your own project.)
<br/><br/>


## 2. AutoFindViewById

## 2.1 rules
* the id of view should be "view_page_function". And the name of class member would be like "viewFunction". <br/>
For example: <br/>
```xml
<TextView android:id="@+id/tv_pay_bank_name" ... /> 
```
would be named :
```java
private TextView tvBankName;
```
*  if the id of view ends with ```"_x"```, it means it will not be listed as a member in Class
* if the id of view ends with ```"_c"```, it means it will be listed as a member in Class, and it will be add the OnClickListener
* ``` "android:id" ``` should be the first attribute of a View in the layout xml file

## 2.2 usage
1. paste your layout xml content to "layout.xml"
2. run the "auto_findview.bat",  or "auto_findview.bat"

## 2.3 result
```java
private Button btnSendSms;
private TextView tvBankError;
private EditText etInputPwd;

etInputPwd = (EditText) findViewById(R.id.et_pay_input_pwd);
tvBankError = (TextView) findViewById(R.id.tv_pay_bank_error);
btnSendSms = (Button) findViewById(R.id.btn_pay_send_sms_c);
btnSendSms.setOnClickListener(this);

@Override
public void onClick(View v) {
	switch (v.getId()) {
		case R.id.btn_pay_send_sms_c:
			
			break;
		default:
			break;
	}
}

```
All you have to do is copy these lines to you own Activity file

## 3. json->Response
	the short version of the first one(json2ReqRespParser)

	
## 4. Count line
It's a tool that count the lines of code. It's not for SVN, and it's only for my own project.