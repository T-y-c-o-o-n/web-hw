<#-- @ftlvariable name="text" type="String" -->
<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="talks" type="ru.itmo.wp.model.domain.Talk[]" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->

<#import "commons.ftlh" as c>

<@c.page>

    <form method="post" action="" class="new-message">
        <input type="hidden" name="action" value="send"/>
        <label for="message">Message</label>
        <select name="targetLogin">
            <option value="none" hidden="">choose user</option>
            <#if users?has_content>
                <#list users as u>
                    <option value="${u.login}">${u.login}</option>
                </#list>
            </#if>
        </select>
        <input type="submit" value="send message">
        <div>
            <textarea name="text" cols="30" rows="10">${text!}</textarea>
        </div>
        <#if error??>
            <div class="talkError">
                ${error!}
            </div>
        </#if>
    </form>

    <div class="datatable talks">
        <div class="caption">Messages</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>From</th>
                <th>To</th>
                <th>Text</th>
                <th>Created</th>
            </tr>
            </thead>
            <tbody>
            <#if talks?has_content>
                <#list talks as talk>
                    <tr>
                        <td>${talk.id!}</td>
                        <td>${c.findBy(users "id" talk.sourceUserId).login!}</td>
                        <td>${c.findBy(users "id" talk.targetUserId).login!}</td>
                        <td>${talk.text!}</td>
                        <td>${talk.creationTime!}</td>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>