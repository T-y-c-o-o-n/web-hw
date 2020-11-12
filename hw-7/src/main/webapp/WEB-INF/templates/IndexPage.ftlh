<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <article class="noData">
        No articles yet
    </article>
    <template>
        <article>
            <div class="title"></div>
            <div class="information"></div>
            <div class="body"></div>
            <ul class="attachment">
                <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
                <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
            </ul>
            <div class="footer">
                <div class="left">
                    <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                    <span class="positive-score">+173</span>
                    <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
                </div>
                <div class="right">
                    <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                    2 days ago
                    <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                    <a href="#">68</a>
                </div>
            </div>
        </article>
    </template>

    <script>
        $(function () {
            let $noData = $(".noData");
            $noData.text("Loading...");

            let data = {action: "findAll"}
            let success = function (response) {
                $noData.hide();
                response["articles"].forEach(function (article) {
                    const $article = $($("main template").prop("content")).clone();
                    $article.find(".title").text(article["title"]);
                    $article.find(".body").text(article["text"]);
                    $article.find(".information")
                        .text(article["creationTime"]);
                    ajax({ action: "findAuthor", userId: article["userId"] },
                        function (response) {
                            $article.find(".information")
                                .text("By " + response["author"] + " " + article["creationTime"]);
                        });
                    $("main").append($article);
                })
            }
            ajax(data, success);
        })
    </script>
</@c.page>
