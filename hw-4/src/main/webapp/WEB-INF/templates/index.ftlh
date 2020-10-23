<#import "./common.ftlh" as c>

<@c.page>
    <#list posts?reverse as post>
        <@c.pagePost post/>
    </#list>
</@c.page>
