<#import "./common.ftlh" as c>

<@c.page>
    <#if user_id?has_content>
        <#if user_id?is_number>
            <#assign user = c.findBy(users, "id", user_id)!/>
            <#if !user?has_content>
                <h1>No such user</h1>
            <#else>
                <#list posts?reverse?filter(p -> p.userId == user_id) as post>
                    <@c.pagePost post/>
                </#list>
            </#if>
        <#else>
            <h1>incorrect user id</h1>
        </#if>
    <#else>
        <h1>missing user id</h1>
    </#if>
</@c.page>
