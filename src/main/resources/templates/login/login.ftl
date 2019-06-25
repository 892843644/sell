<html>
<head>
    <meta charset="UTF-8">
    <title>错误提示</title>
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <style>
        .login{
            margin-top:30px;
            width: 250px;
            height: 200px;
        }
        .xy-center{
            display: flex;
            flex-flow: column;
            justify-content: center;
            align-items: center;
        }
        #container{
            margin: 0;
            padding: 0;
            height: 100%;
            width: 100%;
        }
    </style>
</head>
<body>
<div id="container" class="xy-center">
    <div><h1>卖家管理系统</h1></div>
    <div class="login">
        <form method="post" action="/sell/seller/login">
            <div class="form-group">
                <label for="phone">电话</label>
                <input type="text" name="phone" class="form-control" id="phone" placeholder="电话号">
            </div>
            <div class="form-group">
                <label for="exampleInputEmail2">验证码</label>
                <div  style="display: flex">
                    <input type="text" name="inputCode" class="form-control" id="exampleInputEmail2" placeholder="验证码">
                    <button id="btn_getCode" type="button" class="btn btn-primary" ">获取</button>
                </div>
            </div>
            <button type="submit" class="btn btn-success">登陆</button>
        </form>
    </div>
</div>
</body>
<script>
    $(function () {
        $("#btn_getCode").click(function () {
            var phoneNumber= $("#phone").val();
            $.get("/sell/sms/sendsms",{phone:phoneNumber})
        })
    })
</script>
</html>