<#import "./common.ftlh" as c>

<@c.page>
    <#if user_id?has_content && user_id?is_number>
        <#list posts?filter(p -> p.userId == user_id) as post>
            <@c.renderPost post/>
        </#list>
    </#if>
</@c.page>
