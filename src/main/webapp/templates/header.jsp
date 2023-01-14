<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
    <a href="/home" class="d-flex align-items-center col-md-1 mb-2 mb-md-0 text-dark text-decoration-none">
        <img src="../assets/img/logo.png" width="50px">
    </a>

    <ul class="nav col-3 col-md-auto mb-2 justify-content-center mb-md-0">
        <li><a href="/home" class="nav-link px-2 link-secondary">Home</a></li>
        <li><a href="/cards" class="nav-link px-2 link-dark">Cards</a></li>
        <li><a href="/payments" class="nav-link px-2 link-dark">Payments</a></li>
    </ul>

    <div class="col-md-4 text-end d-flex justify-content-end">
        <c:if test="${sessionScope.user.getRole() == 2}">
            <a href="/admin/cards" class="btn btn-outline-warning me-2">Cards</a>
            <a href="/admin/users" class="btn btn-outline-warning me-2">Users</a>
            <a href="/admin/requests" class="btn btn-outline-warning me-2">Requests</a>
        </c:if>
        <form action="/logout" method="POST">
            <button class="btn btn-danger">Log out</button>
        </form>
    </div>
</header>