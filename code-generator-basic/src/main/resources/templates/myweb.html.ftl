<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>官网</title>
</head>
<body>
<h1>欢迎来到官网</h1>
<ul>
    <#--    循环渲染导航条-->
    <#list menuItems as item>
        <li>
            <a href="${item.url}">${item.label}</a>
        </li>
    </#list>
</ul>
<footer>
    ${currentYear} 官网。 All rights reserved
</footer>
</body>
</html>