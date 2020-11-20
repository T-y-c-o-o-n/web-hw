<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#-- @ftlvariable name="admin" type="boolean" -->
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
                <#if user?has_content && user.admin>
                    <th>Admin</th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="user_id"></td>
                    <td class="user_login"></td>
                    <#if user?has_content && user.admin>
                        <td>
                            <span class="status"></span>
                            |
                            <a href="#" class="button"></a>
                        </td>
                    </#if>
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
                    if (user["admin"] === true) {
                        $tr.find(".status").text("Admin");
                        $tr.find(".button").text("Disable");
                    } else if (user["admin"] === false) {
                        $tr.find(".status").text("User");
                        $tr.find(".button").text("Enable");
                    }
                    $tr.find(".status").attr("data_userId", user["id"]);
                    $tr.find(".button").attr("data_userId", user["id"]);
                    $(".users tbody").append($tr);
                });
                $(".users .button").click(function () {
                    let $this = $(this);
                    let admin = $this.text() === "Enable";
                    ajax({action: "updateAdmin", admin, targetUserId: $this.attr("data_userId")},
                        function (response) {
                            if (response["error"]) {
                                window.notify("you don't have admin status")
                            } else {
                                if (admin) {
                                    $this.text("Disable");
                                    $(".users .status[data_userId=" + $this.attr("data_userId") + "]").text("Admin");
                                } else {
                                    $this.text("Enable");
                                    $(".users .status[data_userId=" + $this.attr("data_userId") + "]").text("User");
                                }
                            }
                        }
                    )
                    return false;
                });
            }
            ajax(data, success);
        })
    </script>
</@c.page>
