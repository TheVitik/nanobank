<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home | Nanobank </title>
    <link rel="stylesheet" href="https://getbootstrap.com/docs/5.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/main.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"></script>
</head>
<body>
<main>
    <div class="container">
        <jsp:include page="templates/header.jsp" />
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
            <h2 class="py-2">Payments</h2>
            <div>
                <a href="?sort=id-desc" class="btn btn-dark">Number</a>
                <a href="?sort=sent_date-asc" class="btn btn-dark">Date Asc</a>
                <a href="?sort=sent_date-desc" class="btn btn-dark">Date Desc</a>
            </div>
        </div>

        <div class="payments">
            <c:forEach items="${payments}" var="payment">
                <div class="payment">
                    <div class="d-flex align-items-center block ps-3 pe-2 py-3 justify-content-between">
                        <small class="font-monospace text-muted text-uppercase">Payment #${payment.getId()}</small>
                        <c:if test="${payment.getStatus() == 1}">
                        <form action="/payments/update" method="POST">
                            <input type="hidden" name="payment_id" value="${payment.getId()}">
                            <button class="btn btn-success">Confirm</button>
                        </form>
                        </c:if>
                    </div>
                    <div class="block p-3">
                        <div class="mb-3">
                            <p><strong>Sender: </strong> ${payment.getSender().getOwner().getName()}</p>
                            <p><strong>Sender card: </strong> ${payment.getSender().getNumber()}</p>
                        </div>
                        <div class="mb-3">
                            <p><strong>Receiver: </strong> ${payment.getReceiver().getOwner().getName()}</p>
                            <p><strong>Receiver card: </strong> ${payment.getReceiver().getNumber()}</p>
                        </div>
                        <div class="mb-3">
                            <p><strong>Amount (UAH): </strong> ${payment.getBalance()}.00</p>
                            <c:if test="${payment.getStatus() == 2}">
                                <p><strong>Payment date: </strong> ${payment.getSentDate()}</p>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</main>
</body>
</html>