<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="user_id"></td>
                    <td class="user_login"></td>
                </tr>
            </template>
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            const $noData = $(".users .noData");
            $noData.find("td").text("Loading...");

            let data = {
                action: "findAll"
            };
            let success = function (response) {
                $noData.hide();
                response["users"].forEach(function (user) {
                    const $tr = $($(".users template").prop("content")).clone();
                    $tr.find(".user_id").text(user["id"]);
                    $tr.find(".user_login").text(user["login"]);
                    $tr.find("tr").attr("data-userId", user["id"]);
                    $(".users tbody").append($tr);
                });
                $(".users tr").click(function () {
                    $.ajax({
                            action: "findUser",
                            userId: $(this).attr("data-userId")
                        },
                        function (response) {
                            notify(response["user"].creationTime);
                        }
                    );
                });
            }
            ajax(data, success);
        })
    </script>
</@c.page>
