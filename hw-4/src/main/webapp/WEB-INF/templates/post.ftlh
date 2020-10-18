<#import "./common.ftlh" as c>

<@c.page>
    <#if post_id?has_content && post_id?is_number>
        <#assign post = c.findBy(posts, "id", post_id)!/>
        <#if post?has_content>
            <@c.renderPost post true/>
        <#else>
            <h1>No such post</h1>
        </#if>
    <#else>
        <h1>incorrect/missing post id</h1>
    </#if>
</@c.page>
