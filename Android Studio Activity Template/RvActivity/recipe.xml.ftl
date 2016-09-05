<?xml version="1.0"?>
<recipe>

    <instantiate from="src/app_package/SimpleActivity.java.ftl"
        to="${escapeXmlAttribute(srcOut)}/${activityClass}.java" />
    <instantiate from="res/layout/item.xml.ftl"
        to="${escapeXmlAttribute(resOut)}/layout/${itemLayoutName}.xml" />               
    <instantiate from="res/layout/layout.xml.ftl"
        to="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />   


    <open file="${escapeXmlAttribute(srcOut)}/${activityClass}.java" />
    <open file="${escapeXmlAttribute(resOut)}/layout/${itemLayoutName}.xml" />
    <open file="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />
</recipe>
