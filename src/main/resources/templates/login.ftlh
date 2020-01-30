<#import "parts/common.ftlh" as c>
<#import "parts/loginmacro.ftlh" as lm>
<@c.page>
<#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
    <div class="alert alert-danger" role="alert">
      ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
    </div>
</#if>
<@lm.login "/login" false/>
</@c.page>