<#macro setFirstPageSettings numberOfAllPages>
    <li class="page-item disabled">
        <span class="page-link">Previous</span>
    </li>
    <li class="page-item active">
        <span class="page-link">1</span>
    </li>

    <#if numberOfAllPages == 1>
        <li class="page-item disabled">
            <span class="page-link">2</span>
        </li>
        <li class="page-item disabled">
            <span class="page-link">3</span>
        </li>
        <li class="page-item disabled">
            <span class="page-link">Next</span>
        </li>
    <#elseif numberOfAllPages == 2>
        <li class="page-item">
            <a class="page-link" href="?page=2">2</a>
        </li>
        <li class="page-item disabled">
            <span class="page-link">3</span>
        </li>
        <li class="page-item">
            <a class="page-link" href="?page=2">Next</a>
        </li>
    <#elseif numberOfAllPages gte 3>
        <li class="page-item">
            <a class="page-link" href="?page=2">2</a>
        </li>
        <li class="page-item">
            <a class="page-link" href="?page=3">3</a>
        </li>
        <li class="page-item">
            <a class="page-link" href="?page=2">Next</a>
        </li>
    </#if>
</#macro>


<#macro setSecondPageSettings numberOfAllPages>
    <li class="page-item">
        <a class="page-link" href="?page=1">Previous</a>
    </li>
    <li class="page-item">
        <a class="page-link" href="?page=1">1</a>
    </li>
    <li class="page-item active">
        <span class="page-link">2</span>
    </li>
    <#if numberOfAllPages == 2>
        <li class="page-item disabled">
            <span class="page-link">3</span>
        </li>
        <li class="page-item disabled">
            <span class="page-link">Next</span>
        </li>
    <#elseif numberOfAllPages gte 3>
        <li class="page-item">
            <a class="page-link" href="?page=3">3</a>
        </li>
        <li class="page-item">
            <a class="page-link" href="?page=3">Next</a>
        </li>
    </#if>
</#macro>


<#macro setThirdOrMorePageSettings numberOfAllPages, currentPage>
    <li class="page-item">
        <a class="page-link" href="?page=${currentPage - 1}">Previous</a>
    </li>
    <#if numberOfAllPages == currentPage>
        <li class="page-item">
            <a class="page-link" href="?page=${currentPage - 2}">${currentPage - 2}</a>
        </li>
        <li class="page-item">
            <a class="page-link" href="?page=${currentPage - 1}">${currentPage - 1}</a>
        </li>
        <li class="page-item active">
            <span class="page-link">${currentPage}</span>
        </li>
        <li class="page-item disabled">
            <span class="page-link">Next</span>
        </li>
    <#else>
        <li class="page-item">
            <a class="page-link" href="?page=${currentPage - 1}">${currentPage - 1}</a>
        </li>
        <li class="page-item active">
            <span class="page-link">${currentPage}</span>
        </li>
        <li class="page-item">
            <a class="page-link" href="?page=${currentPage + 1}">${currentPage + 1}</a>
        </li>
        <li class="page-item">
            <a class="page-link" href="?page=${currentPage + 1}">Next</a>
        </li>
    </#if>
</#macro>