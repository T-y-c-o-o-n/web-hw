<#import "./common.ftlh" as c>

<@c.page>
    <div class="users-table">
        <div class="caption">Users</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Handle</th>
                <th>Name</th>
            </tr>
            </thead>
            <tbody>
            <#list users as u>
                <tr>
                    <td>${u.id}</td>
                    <td>${u.handle}</td>
                    <td>
                        <#if user?? && user.id == u.id>*</#if>
                        <@c.userlink u/>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

</@c.page>
