<#-- @ftlvariable name="userInfo" type="ru.itmo.wp.domain.User" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if userInfo??>
        <section class="user-section">
            <div class="username">${userInfo.login}</div>
            <div class="user-info">
                <ul>
                    <li>id: ${userInfo.id}</li>
                    <li>login: ${userInfo.login}</li>
                    <li>creation: ${userInfo.creationTime}</li>
                </ul>
            </div>
            <img src="https://userpic.codeforces.com/no-title.jpg" alt="picture">
        </section>
    <#else>
        <h1>
            No such user
        </h1>
    </#if>
</@c.page>
