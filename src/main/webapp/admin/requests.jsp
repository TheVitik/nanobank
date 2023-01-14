<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Cards | Nanobank </title>
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
            <h2 class="py-2">Unblock Requests</h2>
        </div>
        <div class="payments">
            <c:forEach items="${requests}" var="request" varStatus="status">
                <div class="payment">
                    <div class="d-flex align-items-center block ps-3 pe-2 py-3 justify-content-between">
                        <small class="font-monospace text-muted text-uppercase">Request #${request.getId()}</small>
                        <form action="/admin/requests" method="POST">
                            <input type="hidden" name="request_id" value="${request.getId()}">
                            <input type="submit" name="unblock" class="btn btn-outline-success" value="Unblock">
                        </form>
                    </div>
                    <div class="block p-3">
                        <div class="mb-3">
                            <p><strong>Number: </strong> ${request.getCard().getFNumber()}</p>
                            <p><strong>Expiration date: </strong> ${request.getCard().getFDate()}</p>
                            <p><strong>Owner: </strong> ${request.getCard().getOwner().getName()}</p>
                            <p><strong>Phone: </strong> ${request.getCard().getOwner().getPhone()}</p>
                            <p><strong>Balance: </strong> ${request.getCard().getFBalance()}</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</main>
</body>
</html>