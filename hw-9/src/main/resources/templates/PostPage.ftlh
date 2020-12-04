<#-- @ftlvariable name="comment" type="ru.itmo.wp.domain.Comment" -->
<#-- @ftlvariable name="post" type="ru.itmo.wp.domain.Post" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if post??>
        <@c.post post=post/>
        <#if comment??>
            <form method="post" action="" class="form">
                <div class="field">
                    <div class="name">
                        <label for="text"></label>
                    </div>
                    <div class="value">
                        <textarea name="text" id="text" cols="30" rows="3">${comment.text!}</textarea>
                        <@c.error "comment.text"/>
                    </div>
                </div>
                <button type="submit">Add</button>
            </form>
        </#if>
        <div>Comments:</div>
        <#list post.comments as comment>
            <div class="comment">
                <div class="commentator">
                    <div class="avatar">
                        <img src="<@c.spring.url "/img/empty-ava.jpg"/>" alt="">
                    </div>
                    <div class="login">
                        ${comment.user.login!}
                    </div>
                </div>
                <div class="comment-text">
                    ${comment.text!}
                </div>
            </div>
        </#list>
    <#else>
        <h1>No such post</h1>
    </#if>
</@c.page>
