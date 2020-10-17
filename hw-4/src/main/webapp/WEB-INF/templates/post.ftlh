<#import "./common.ftlh" as c>

<@c.page>
    <#if post_id?has_content>
        <#assign post = c.findBy(posts, "id", post_id)!/>
        <#if post?has_content>
            <@c.renderPost post true/>
        <#else>
            <h1>JavaVirtualMachineError</h1>
            NoSuchPostException
        </#if>
    </#if>
</@c.page>
