<#macro login path isRegistrForm>
<form action="${path}" method="post">
    <div class="form-group row">
        <label class="col-sm-1 col-form-label"> Username: </label>
        <div class="col-sm-4">
            <input type="text" name="username" value="<#if user??>${user.username!}</#if>"
            class="form-control ${(usernameError??)?string('is-invalid', '')}"
            placeholder="User name"/>
            <#if usernameError??>
                <div class="invalid-feedback">
                  ${usernameError}
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-1 col-form-label"> Password: </label>
        <div class="col-sm-4">
            <input type="password" name="password" class="form-control ${(passwordError??)?string('is-invalid', '')}" placeholder="Password"/>
            <#if passwordError??>
                <div class="invalid-feedback">
                  ${passwordError}
                </div>
            </#if>
        </div>
    </div>
    <#if isRegistrForm>
        <div class="form-group row">
                <label class="col-sm-1 col-form-label"> Password confirmation: </label>
                <div class="col-sm-4">
                    <input type="password" name="password2" class="form-control ${(password2Error??)?string('is-invalid', '')}" placeholder="confirmation password"/>
                    <#if password2Error??>
                        <div class="invalid-feedback">
                          ${password2Error}
                        </div>
                    </#if>
                </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-1 col-form-label"> E-mail: </label>
            <div class="col-sm-4">
                <input type="email" name="email" value="<#if user??>${user.email!}</#if>"
                class="form-control ${(emailError??)?string('is-invalid', '')}"
                placeholder="example@example.com"/>
                <#if emailError??>
                    <div class="invalid-feedback">
                      ${emailError}
                    </div>
                </#if>
            </div>
        </div>
    </#if>

    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <#if !isRegistrForm>
        <a href="/registration">Add new user</a>
    </#if>
    <button type="submit" class="btn btn-primary"><#if isRegistrForm>Sign Up<#else>Sign In</#if></button>
</form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">Sign Out</button>
    </form>
</#macro>