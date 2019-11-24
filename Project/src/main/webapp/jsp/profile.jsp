<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nail
  Date: 23.10.2019
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>TEST</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
</head>
<body style="background-color: #e9e9e9">



<div>
    <!-- Navbar -->
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
                <input name="edit" class="btn btn-outline-dark mr-2 my-2 my-sm-0" type="submit" value="Edit">

                <input name="exit" class="btn btn-outline-dark my-2 my-sm-0" type="submit" value="Exit">

            </form>
        </nav>
    </nav>

</div>
<div class = "container col-md-6">
    <div class="row">
        <div class = "col-md-12">
            <!-- Инфа-->
            <div class="container m-2 p-1 mb-3 border border-secondary" style = "background-color: white">
                <div class="d-flex flex-row justify-content-between">
                    <div class="text-left">
                        <c:set var = "image" value="${Image}"/>
                        <img src="${image}" class="img-circle pull-xs-left m-1" width = "150" height="150" alt="Card image cap">
                    </div>
                    <div class="container m-3">
                        <table class="container">
                            <thead class="thead">
                            <tr>

                                <th scope="col">Имя</th>
                                <th scope="col">Подписчиков</th>
                                <th scope="col">Подписок</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:set var = "uName" value="${userName}"/>
                            <td>${uName}</td>
                            <td><c:out value="${subscribers}"/></td>
                            <td><c:out value="${subscriptions}"/></td>
                            </tbody>
                        </table>

                    </div>
                </div>
            </div>
            <!-- Инфа-->

            <!-- Ввод-->
            <div class="container m-2 p-1 mb-3 border border-secondary" style="background-color: white">
                <div class="form-group">
                    <form method="post" action="/upload" enctype="multipart/form-data">
                        <textarea class="form-control" name = "destination" id="exampleFormControlTextarea1" rows="3" placeholder="Поделитесь чем-нибудь"></textarea>
                        <div>
                            <p><input type="file" name="file" multiple accept="image/*,image/jpeg">
                                <input type="submit" value="Отправить"></p>
                        </div>
                    </form>
                </div>
            </div>
            <!-- Ввод-->


        <c:forEach var ="post" items="${posts}">
            <!-- Контент Начало-->
            <div class ="container m-2 p-1 mb-3 border border-secondary" style = "background-color: white">
                <div class="card ">
                    <img class="card-img-top p-3" src="${post.imagesPath}" >
                    <div class="card-body">

                        <p class="card-text">${post.text}</p>
                        <!--Коммент-->
                    </div>
                </div>
                <!--Начало комментариев-->
                <button class="btn btn-link" type="button" data-toggle="collapse" data-target="#${post.id}" aria-expanded="false" aria-controls="collapseExample">
                    Comments
                </button>
                <div class="collapse" id="${post.id}">
                    <!--Ввод коммента-->
                    <div class="container  p-1 mb-1 border border-secondary" style = "background-color: white">
                        <div class="form-group">
                            <form method="post" action="/uploadComment">
                                <textarea class="form-control" name = "textComment" rows="3" placeholder="Прокоментируйте"></textarea>
                                <input name="postId" type="hidden" value="${post.id}">
                                <input type="submit" value="Отправить">
                            </form>
                        </div>
                    </div>

                    <!-- Комаентарий-->
                    <c:forEach var="comment" items="${post.comments}">
                    <div class="container  p-1 mb-3 border border-secondary" style = "background-color: white">
                        <div class="d-flex flex-row justify-content-between">
                            <div class="text-left">
                                <a class="text-secondary" href="/user?id=${comment.user.id}">
                                    <img src="${comment.user.image}"  class="img-circle pull-xs-left m-1" width = "150" height="150" alt="Card image cap">
                                </a>
                            </div>
                            <div class="container m-3">
                                <table class="container">
                                    <thead class="thead">
                                    <tr>
                                        <th scope="col"><a class="text-secondary" href="/user?id=${comment.user.id}"><h5>${comment.user.name}</h5></a></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <td>
                                            ${comment.text}
                                    </td>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    </c:forEach>
                    <!-- Комаентарий-->

                </div>
            </div>
            <!--Контент-->
        </c:forEach>
        </div>
    </div>

</div>
</body>
</html>
