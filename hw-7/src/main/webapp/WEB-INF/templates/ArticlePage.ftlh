<#-- @ftlvariable name="title" type="String" -->
<#-- @ftlvariable name="text" type="String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="article">
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="write">
                <label for="">Write an article</label>
                <input type="text" name="title" value="${title!}"/>
                <div>
                    <textarea name="text" id="" cols="60" rows="10">${text!}</textarea>
                </div>
                <div class="error"></div>
                <div class="button-field">
                    <input type="submit" value="write">
                </div>
            </form>
        </div>
    </div>
    <script>
        $("article form").submit(
            function () {
                let $form = $(this);
                const title = $form.find("input[name='title']").val();
                const text = $form.find("textarea").prop("text");

                let data = {
                    title,
                    text
                };
                let success = function (response) {};
                ajax(data, success);
            }
        )
    </script>

</@c.page>