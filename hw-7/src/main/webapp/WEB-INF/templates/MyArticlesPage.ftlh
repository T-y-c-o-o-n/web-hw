<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]" -->
<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles datatable">
        <div class="caption">Article</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>HIDE/SHOW</th>
            </tr>
            </thead>
            <tbody>
            <#if articles?has_content>
                <#list articles as article>
                    <tr class="line">
                        <td class="article_id">${article.id}</td>
                        <td class="article_title">${article.title}</td>
                        <td class="article_hide_show">
                            <button data_articleId="${article.id}"><#if article.hidden>SHOW<#else>HIDE</#if></button>
                        </td>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            $(".articles .line .article_hide_show button").click(function () {
                let $this = $(this);
                let hidden = $this.text() === "HIDE";
                ajax({
                        action: "updateHidden",
                        hidden,
                        articleId: $this.attr("data_articleId")
                    },
                    function () {
                        $this.text(hidden ? "SHOW" : "HIDE");
                    });

                return false;
            });
        })
    </script>
</@c.page>
