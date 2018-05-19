<%--
  Created by IntelliJ IDEA.
  User: hanhongxuan
  Date: 2018/5/12
  Time: 下午3:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>${seckill.name}</h1>
    <div style="display: none;" id="seckill_content">
        <button id="killNow">现在秒杀</button>
        <div>
            <input type="text" id="phone" />
            <button id="loginBtn">登录</button>
        </div>
    </div>
    <div style="display: none;" id="seckill_end">
        秒杀结束
    </div>
    <div style="display: none;" id="seckill_start">
        秒杀即将开始
        <div id="start_time"></div>
        <div id="countDown"></div>
    </div>


    <input id="startTime" type="hidden" value="${seckill.startTime}" />
    <input id="endTime" type="hidden" value="${seckill.endTime}" />
    <input id="endTime" type="hidden" value="${seckill.seckillId}" />

    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script>
        var start_time = new Date($("#startTime").val()).getTime()
        var end_time = new Date($("#endTime").val()).getTime()
        var interval

        var getExposer = function () {
            $.ajax
        }

        var countFun = function (_time) {
            var time = _time
            interval = setInterval(function () {
                var second = (parseInt(time/1000) % 3600) % 60
                var minute = parseInt((parseInt(time/1000) % 3600) / 60)
                var hour = parseInt(parseInt(time/1000) / 3600)

                var result = hour + " : " + minute + " : " + second;
                $("#countDown").text(result)
                if(time <= 0){
                    clearInterval(interval)
                    getExposer()
                }else{
                    time -= 1000
                }
            },1000)

        }


        $(function () {
            $.ajax({
                type : "get",
                url : "/seckill/time/now",
                success : function (data) {
                    var now = data.data
                    var startTime = start_time
                    var endTime = end_time

                    if(now >= endTime){
                        $("#seckill_content").hide();
                        $("#seckill_end").show();
                    }else if(now < startTime){
                        $("#seckill_content").hide();
                        $("#seckill_start").show();
                        $("#start_time").text(new Date(startTime));
                        countFun(startTime - now)
                    }else{
                        $("#seckill_content").show();
                        $("#seckill_end").hide();
                        $("#seckill_start").hide();
                    }
                }
            })
        })

        $("#killNow").click(function () {
            var userPhone = document.cookie.search("killPhone");
            if(userPhone > -1){

            }else{
                alert("请登录")
            }
        })

        $("#loginBtn").click(function () {
            var phone = $("#phone").val();
            document.cookie = "killPhone=" + phone
        })
    </script>
</body>
</html>
