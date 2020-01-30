<#import "parts/common.ftlh" as c>

<@c.page>
<h5>${username}</h5>
${message!}
<form method="post">

    <div class="form-group row">
        <label class="col-sm-1 col-form-label">Old password: </label>
        <div class="col-sm-4">
            <input type="password" name="oldPassword" class="form-control" placeholder="Enter old password"/>
        </div>
    </div>

    <div class="form-group row">
        <label class="col-sm-1 col-form-label">New password: </label>
        <div class="col-sm-4">
            <input type="password" name="newPassword" class="form-control" placeholder="New password"/>
        </div>
    </div>

    <div class="form-group row">
        <label class="col-sm-1 col-form-label"> E-mail: </label>
        <div class="col-sm-4">
            <input type="email" name="email" class="form-control" placeholder="example@example.com" value="${email!}"/>
        </div>
    </div>


    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button type="submit" class="btn btn-primary">Save</button>
</form>
</@c.page>