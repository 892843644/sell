<html>
<#include "../common/header.ftl">
<body>
    <div id="wrapper" class="toggled">
        <#--边栏sidebar-->
        <#include "../common/nav.ftl">

        <#--主要内容content-->
        <div id="page-content-wrapper">
            <div class="container-fluid">
                <div class="row clearflx">
                    <div class="col-md-12 column">
                        <table class="table table-bordered table-condensed">
                            <thead>
                            <tr>
                                <th>商品id</th>
                                <th>名称</th>
                                <th>图片</th>
                                <th>单价</th>
                                <th>库存</th>
                                <th>描述</th>
                                <th>类目</th>
                                <th>创建时间</th>
                                <th>修改时间</th>
                                <th colspan="2">操作</th>
                            </tr>
                            </thead>
                            <tbody>

                    <#list productInfoEntityPage.records as productInfo>
                    <tr>
                        <td>${productInfo.productId}</td>
                        <td>${productInfo.productName}</td>
                        <td><img height="100" width="100" src="${productInfo.productIcon}" alt=""></td>
                        <td>${productInfo.productPrice}</td>
                        <td>${productInfo.productStock}</td>
                        <td>${productInfo.productDescription}</td>
                        <td>${productInfo.categoryType}</td>
                        <td>${productInfo.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                        <td>${productInfo.updateTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                        <td><a href="/sell/seller/product/index?productId=${productInfo.productId}">修改</a></td>
                        <td>
                            <#if productInfo.getProductStatusEnum().message=="在架">
                                <a href="/sell/seller/product/off_sale?productId=${productInfo.productId}">下架</a>
                            <#else>
                                <a href="/sell/seller/product/on_sale?productId=${productInfo.productId}">上架</a>
                            </#if>

                        </td>
                    </tr>
                    </#list>

                            </tbody>
                        </table>
                    </div>
                <#--分页-->
                    <div class="col-md-12 column">
                        <ul class="pagination pull-right">

                    <#if productInfoEntityPage.current lte 1>
                        <li class="disabled"><a href="#">上一页</a></li>
                    <#else>
                        <li><a href="http://127.0.0.1:8080/sell/seller/order/list?page=${productInfoEntityPage.current-1}&size=${size}">上一页</a></li>
                    </#if>

                    <#list  1..pages as index>
                        <#if productInfoEntityPage.current==index>
                         <li class="disabled">
                             <a href="http://127.0.0.1:8080/sell/seller/order/list?page=${index}&size=${size}">${index}</a>
                         </li>
                        <#else>
                        <li>
                            <a href="http://127.0.0.1:8080/sell/seller/order/list?page=${index}&size=${size}">${index}</a>
                        </li>
                        </#if>
                    </#list>

                    <#if productInfoEntityPage.current gte pages >
                    <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                     <li><a href="http://127.0.0.1:8080/sell/seller/order/list?page=${productInfoEntityPage.current+1}">下一页</a></li>
                    </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

    </div>

</body>
</html>