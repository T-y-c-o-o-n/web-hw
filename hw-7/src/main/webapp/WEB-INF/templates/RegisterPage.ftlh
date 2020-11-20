<#-- @ftlvariable name="login" type="java.lang.String" -->
<#-- @ftlvariable name="email" type="java.lang.String" -->
<#-- @ftlvariable name="password" type="java.lang.String" -->
<#-- @ftlvariable name="passwordConfirmation" type="java.lang.String" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="registration form-box">
        <div class="header">Register</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="register"/>
                <div class="field">
                    <div class="name">
                        <label for="login">Login</label>
                    </div>
                    <div class="value">
                        <input id="login" name="login" value="${login!}"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="email">Email</label>
                    </div>
                    <div class="value">
                        <input id="email" name="email" value="${email!}">
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="password">Password</label>
                    </div>
                    <div class="value">
                        <input id="password" name="password" type="password" value="${password!}"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="passwordConfirmation">Password Confirmation</label>
                    </div>
                    <div class="value">
                        <input id="passwordConfirmation" name="passwordConfirmation"
                               type="password" value="${passwordConfirmation!}"/>
                    </div>
                </div>
                <div class="error"></div>
                <div class="button-field">
                    <input type="submit" value="Register">
                </div>
            </form>
        </div>
    </div>

    <script>
        $(function () {
            $(".registration form").submit(function () {
                let $form = $(this);
                const login = $form.find("input[name='login']").val();
                const email = $form.find("input[name='email']").val();
                const password = $form.find("input[name='password']").val();
                const passwordConfirmation = $form.find("input[name='passwordConfirmation']").val();
                const $error = $form.find(".error");

                let data = {
                    action: "register",
                    login,
                    email,
                    password,
                    passwordConfirmation
                };
                ajax(data, (response) => {});

                return false;
            });
        })
    </script>
</@c.page>
