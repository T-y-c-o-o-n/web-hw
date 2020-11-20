<#import "commons.ftlh" as c>

<@c.page>
    <div class="article">
        <div class="body">
            <form method="post" action="">
                <label for="">Write an article
                    <input type="text" name="title"/>
                </label>
                <div>
                    <textarea name="text" id="" cols="60" rows="10"></textarea>
                </div>
                <div class="error"></div>
                <div class="button-field">
                    <input type="submit" value="write">
                </div>
            </form>
        </div>
    </div>
    <script>
        $(".article form").submit(
            function () {
                let $form = $(this);
                const title = $form.find("input[name='title']").val();
                const text = $form.find("textarea").val();

                ajax({
                    action: "write",
                    title,
                    text
                }, () => {});

                return false;
            }
        )
    </script>

</@c.page>