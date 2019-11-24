<%--
  Created by IntelliJ IDEA.
  User: nail
  Date: 23.10.2019
  Time: 20:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/css/login.css">
    <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
    <script src = "/js/login.js"></script>
    <style type="text/css">
        .wrong_parameter {
            padding: 4px;
            margin: 4px;
            background: #F46878;
            color: white;
        }
    </style>
</head>
<body>

<div class="login-page">
    <div class="form">
        <form class="register-form" method="post" action="/register">
            <c:if test="${not empty wrong_mail}">
                <div class="wrong_parameter" id="result">
                    <c:out value="${wrong_mail}"/>
                </div>
            </c:if>
            <input type="text" name="name" placeholder="name"/>
            <input type="password" name="password" placeholder="password"/>
            <input type="text" id = "email" name="mail" placeholder="email address" onblur = "f()"/>
            <button id = "validate">create</button>
            <p class="message">Already registered? <a href="#">Sign In</a></p>
        </form>
        <form class="login-form" method="post" action="/login">
            <c:if test="${not empty wrong_parameter}">
                <div class="wrong_parameter">
                    <c:out value="${wrong_parameter}"/>
                </div>
            </c:if>
            <input type="text" name="mail" placeholder="mail"/>
            <input type="password" name="password" placeholder="password"/>
            <input type="checkbox" name="remember_me"/>
            <button>login</button>
            <p class="message">Not registered? <a href="#">Create an account</a></p>
        </form>
    </div>
</div>
<script id="rendered-js">
    $('.message a').click(function () {
        $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
    });
</script>
<script>
    function f() {

        $.ajax({
            url: "/emailValidate",
            data: {"mail": $("#email").val()
            },
            dataType: "json",
            success: function (msg) {
                alert(msg.content);
            }
        })
    }
</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>
