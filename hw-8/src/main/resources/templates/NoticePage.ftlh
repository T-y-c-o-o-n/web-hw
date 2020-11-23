<#-- @ftlvariable name="text" type="String" -->
<#import "./commons.ftlh" as c>

<@c.page>
    <form method="post" action="">
        <label for="content">
            <textarea name="content" id="content" cols="60" rows="20">${noticeForm.content!}</textarea>
        </label>
        <@c.error "noticeForm.content"/>
        <button type="submit">Add</button>
    </form>
</@c.page>