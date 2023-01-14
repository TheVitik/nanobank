<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Users | Nanobank </title>
    <link rel="stylesheet" href="https://getbootstrap.com/docs/5.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="../assets/css/main.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"></script>
</head>
<body>
<main>
    <div class="container">
        <jsp:include page="../templates/header.jsp" />
        <c:if test="${sessionScope.error != null}">
            <div class="alert alert-danger" role="alert">
                    ${sessionScope.error}
            </div>
        </c:if>
        <c:if test="${sessionScope.success != null}">
            <div class="alert alert-success" role="alert">
                    ${sessionScope.success}
            </div>
        </c:if>
    </div>
    <div class="container">
        <div class="d-flex justify-content-between">
            <h2 class="py-2">Users</h2>
        </div>
        <div class="payments">
            <c:forEach items="${users}" var="user">
                <div class="payment">
                    <div class="d-flex align-items-center block ps-3 pe-2 py-3 justify-content-between">
                        <small class="font-monospace text-muted text-uppercase">User #${user.getId()}</small>
                        <c:choose>
                            <c:when test="${user.getId() == sessionScope.user.getId()}">
                                <h6 class="text-warning">YOU</h6>
                            </c:when>
                            <c:when test="${user.isBanned() == false}">
                                <form action="/admin/users" method="POST">
                                    <input type="hidden" name="user_id" value="${user.getId()}">
                                    <input type="submit" name="ban" class="btn btn-outline-danger" value="Ban">
                                </form>
                            </c:when>
                            <c:when test="${user.isBanned() == true}">
                                <form action="/admin/users" method="POST">
                                    <input type="hidden" name="user_id" value="${user.getId()}">
                                    <input type="submit" name="unban" class="btn btn-outline-success" value="Unban">
                                </form>
                            </c:when>
                        </c:choose>
                    </div>
                    <div class="block p-3">
                        <div class="mb-3">
                            <p><strong>First name: </strong> ${user.getFirstName()}</p>
                            <p><strong>Last name: </strong> ${user.getLastName()}</p>
                            <p><strong>Phone: </strong> ${user.getPhone()}</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</main>
</body>
</html>