<%--
  Created by IntelliJ IDEA.
  User: nail
  Date: 05.11.2019
  Time: 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Search</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
</head>
<body style="background-color: #e9e9e9">


<!-- Image and text -->
<nav class="navbar navbar-light bg-secondary">
    <nav class="navbar container col-md-7">
        <a class="navbar-brand" href="#">
            <img src="http://pngimg.com/uploads/palette/palette_PNG68310.png" width="30" height="30" class="d-inline-block align-top" alt="">
            Freedab
        </a>
        <form method="post" action="/navbar" class="form-inline">
            <input name="search" class="form-control mr-2 sm-2" type="search" placeholder="Search" aria-label="Search">
            <input type="submit"
                   style="position: absolute; left: -9999px; width: 1px; height: 1px;"
                   tabindex="-1" />
            <input name="profile" class="btn btn-outline-dark mr-2 my-2 my-sm-0" type="submit" value="Profile">

            <input name="exit" class="btn btn-outline-dark my-2 my-sm-0" type="submit" value="Exit">

        </form>
    </nav>
</nav>

<c:forEach var ="user" items="${listUser}">
    <div class = "container col-md-6">
        <div class="row">
            <div class = "col-md-12">
                <!-- Инфа-->
                <div class="container m-2 p-1 mb-3 border border-secondary" style = "background-color: white">
                    <div class="d-flex flex-row justify-content-between">
                        <div class="text-left">
                            <a class="text-secondary" href="/user?id=${user.id}">
                                <img src="${user.image}" class="img-circle pull-xs-left m-1" width = "150" height="150" alt="Card image cap">
                            </a>
                        </div>
                        <div class="container m-3">
                            <a class="text-secondary" href="/user?id=${user.id}"><h5>${user.name}</h5></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:forEach>

</body>
</html>

