<#import "parts/common.ftlh" as c>
<#import "parts/loginmacro.ftlh" as lm>
<@c.page>
<div class="mb-1">Registration form</div>
${message!}
<@lm.login "/registration" true/>
</@c.page>