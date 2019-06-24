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
                                <th>订单id</th>
                                <th>姓名</th>
                                <th>手机号</th>
                                <th>地址</th>
                                <th>金额</th>
                                <th>订单状态</th>
                                <th>支付状态</th>
                                <th>创建时间</th>
                                <th colspan="2">操作</th>
                            </tr>
                            </thead>
                            <tbody>

                    <#list orderDTOPage.records as orderDTO>
                    <tr>
                        <td>${orderDTO.orderId}</td>
                        <td>${orderDTO.buyerName}</td>
                        <td>${orderDTO.buyerPhone}</td>
                        <td>${orderDTO.buyerAddress}</td>
                        <td>${orderDTO.orderAmount}</td>
                        <td>${orderDTO.getOrderStatusEnum().getMsg()}</td>
                        <td>${orderDTO.getPayStatusEnum().getMsg()}</td>
                        <td>${orderDTO.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                        <td><a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a></td>
                        <td>
                            <#if orderDTO.getOrderStatusEnum().msg=="新订单">
                                <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
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

                    <#if orderDTOPage.current lte 1>
                        <li class="disabled"><a href="#">上一页</a></li>
                    <#else>
                        <li><a href="http://127.0.0.1:8080/sell/seller/order/list?page=${orderDTOPage.current-1}&size=${size}">上一页</a></li>
                    </#if>

                    <#list  1..pages as index>
                        <#if orderDTOPage.current==index>
                         <li class="disabled">
                             <a href="http://127.0.0.1:8080/sell/seller/order/list?page=${index}&size=${size}">${index}</a>
                         </li>
                        <#else>
                        <li>
                            <a href="http://127.0.0.1:8080/sell/seller/order/list?page=${index}&size=${size}">${index}</a>
                        </li>
                        </#if>
                    </#list>

                    <#if orderDTOPage.current gte pages >
                    <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                     <li><a href="http://127.0.0.1:8080/sell/seller/order/list?page=${orderDTOPage.current+1}">下一页</a></li>
                    </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

    </div>

</body>
</html>