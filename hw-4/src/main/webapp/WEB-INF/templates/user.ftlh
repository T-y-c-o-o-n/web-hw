<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#if handle?has_content>
        <#assign u = c.findBy(users, "handle", handle)!/>
    </#if>
    <#if !u?has_content>
        <#if user_id?has_content>
            <#if user_id?is_number>
                <#assign u = c.findBy(users, "id", user_id)!/>
            </#if>
        </#if>
    </#if>

    <#if u?has_content>
        <section class="user-section">
            <div>
                <@c.userlink u/>
                <ul>
                    <li>${u.id}</li>
                    <li>${u.handle}</li>
                    <li>${u.name}</li>
                </ul>
            </div>
            <img src="https://userpic.codeforces.com/no-title.jpg" alt="picture">
        </section>
        <#assign prevUser = c.findPrevBy(users, "id", u.id)!/>
        <@userArrow prevUser "←"/>
        <#assign nextUser = c.findNextBy(users, "id", u.id)!/>
        <@userArrow nextUser "→"/>

    <#else>
        No such user
    </#if>
</@c.page>

<#macro userArrow user content>
    <#if user?has_content>
        <a class="usr_link_arrow" href="/user?handle=${user.handle}">${content}</a>
    <#else>
        <a class="just_arrow">${content}</a>
    </#if>
</#macro>
