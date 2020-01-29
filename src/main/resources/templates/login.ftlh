<#import "parts/common.ftlh" as c>
<#import "parts/loginmacro.ftlh" as lm>
<@c.page>
${message!}
<@lm.login "/login" false/>
</@c.page>