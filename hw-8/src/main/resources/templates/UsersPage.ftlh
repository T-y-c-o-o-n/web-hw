<#-- @ftlvariable name="users" type="ru.itmo.wp.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Enable/Disable</th>
                <th>Created</th>
            </tr>
            </thead>
            <tbody>
            <#if users?size==0>
                <tr class="noData">
                    <td colspan="3">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as user>
                <tr>
                    <td class="id">${user.id}</td>
                    <td class="login">${user.login}</td>
                    <td>
                        <form method="post" action="">
                            <input type="hidden" name="idString" value="${user.id}">
                            <button type="submit" name="action" value="<#if user.disabled>Enable<#else>Disable</#if>">
                                <#if user.disabled>Enable<#else>Disable</#if>
                            </button>
                        </form>
                    </td>
                    <td class="creationTime">${user.creationTime}</td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
