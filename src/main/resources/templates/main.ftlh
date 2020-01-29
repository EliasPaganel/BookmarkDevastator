<#import "parts/common.ftlh" as c>

<@c.page>
<div class="form-row">
    <div class=form-group col-md-6">
        <form method="get" action="/main" class="form-inline">
            <input type="text" name="filter" class="form-control" value="${filter!}" placeholder="Поиск"/>
            <button type="submit" class="btn btn-primary ml-2">Найти</button>
        </form>
    </div>
</div>
  <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    Add new bookmark
  </a>
<div class="collapse" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data" target="_blank">
            <div class="form-group">
                <input type="text" class="form-control" name="link" placeholder="Введите ссылку, ктр хотите сохранить."/>
            </div>
            <div class="form-group">
                <input type="text" class="form-control" name="text" placeholder="Введите напоминание."/>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name = "file" id="customFile">
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Добавить закладку</button>
            </div>
        </form>
    </div>
</div>



<div class="card-columns">
    <#list bookmarks as bookmark>
    <div class="card my-3">

        <div class="m-2">
            <span>${bookmark.link}</span><br/>
            <i>${bookmark.text}</i>
            <div>
                <#if bookmark.fileName??>
                    <a href="/documents/${bookmark.fileName}" download>file</a>
                </#if>
            </div>
        </div>

        <div class="card-footer text-muted">${bookmark.authorName}</div>
    </div>



    <#else>
    No bookmarks
    </#list>
</div>
</@c.page>