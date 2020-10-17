<#import "./common.ftlh" as c>

<@c.page>
    <#list posts as post>
        <@c.renderPost post/>
    </#list>
</@c.page>
