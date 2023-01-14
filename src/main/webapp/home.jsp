<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <c:if test="${error != null}">
            <div class="alert alert-danger" role="alert">
                ${error}
            </div>
        </c:if>
        <div class="carousel-indicators">
            <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
            <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="1" aria-label="Slide 2"></button>
            <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="2" aria-label="Slide 3"></button>
        </div>
    </div>
    <div id="carouselExampleDark" class="carousel carousel-dark slide" data-bs-ride="carousel">
        <div class="carousel-inner">
            <c:forEach items="${cards}" var="card" varStatus = "status">
                <div class="carousel-item ${status.first ? 'active' : ''}" data-bs-interval="10000">
                    <div class="container">
                        <div class="row">
                            <div class="col-lg">
                                <div class="alert alert-primary">
                                    <h3>Balance: ${card.getFBalance()} UAH</h3>
                                </div>
                                <div class="d-flex justify-content-around">
                                    <button type="button" class="btn btn-primary btn-lg px-5" data-bs-toggle="modal" data-bs-target="#deposit" data-bs-number="${card.getFNumber()}" data-bs-id="${card.getId()}">Deposit</button>
                                    <a href="/payments?card=${card.getId()}" type="button" class="btn btn-warning btn-lg px-5">Payments</a>
                                    <button type="button" class="btn btn-secondary btn-lg px-5" data-bs-toggle="modal" data-bs-target="#transfer" data-bs-id="${card.getId()}">Transfer</button>
                                </div>
                            </div>
                            <div class="col-lg cards">
                                <div class="credit-card bg-dark shadow-sm mx-auto" style="z-index: 1">
                                    <h6>Nanobank | <span style="font-weight: 300">Future bank</span></h6>
                                    <h2 class="text-center my-5" style="letter-spacing: 1.5px;font-weight: 400">
                                            ${card.getFNumber()}</h2>
                                    <h5>${card.getOwner().getName()}</h5>
                                    <h5 style="font-weight: 300">${card.getFDate()}</h5>
                                    <img src="assets/img/mastercard.png" alt="Mastercard">
                                </div>
                                <div class="credit-card shadow-sm mx-auto"
                                     style="right:-50px;top:100px; background: rgba(33,37,41,0.93)">
                                    <p class="cvv">${card.getCvv()}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleDark" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleDark" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>
    <!-- DEPOSIT MODAL -->
    <div class="modal fade" id="deposit" tabindex="-1" aria-labelledby="depositLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="depositLabel">Deposit</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="deposit" id="depositForm" method="POST">
                        <label for="balanceInput" class="form-label">Payment sum</label>
                        <input type="number" name="balance" class="form-control" id="balanceInput" min="1" max="99999" required>
                        <input type="hidden" name="card_id" id="card-id" required>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary" form="depositForm">Pay</button>
                </div>
            </div>
        </div>
    </div>
    <!-- TRANSFER MODAL -->
    <div class="modal fade" id="transfer" tabindex="-1" aria-labelledby="transferLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="transferLabel">Transfer</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="/payments/create" id="transferForm" method="POST">
                        <label for="numberInput" class="form-label">Receiver number</label>
                        <input type="number" name="receiver" id="numberInput" class="form-control" minlength="16" maxlength="16" required>
                        <label for="sumInput" class="form-label">Sum</label>
                        <input type="number" name="balance" id="sumInput" class="form-control" min="1" max="99999" required>
                        <input type="hidden" name="sender_id" id="sender-id" required>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <input type="submit" class="btn btn-warning" name="prepare" value="Save" form="transferForm">
                    <button type="submit" class="btn btn-primary" form="transferForm">Transfer</button>
                </div>
            </div>
        </div>
    </div>
</main>
<script>
    let depositModal = document.getElementById('deposit');
    depositModal.addEventListener('show.bs.modal', function (event) {
        let button = event.relatedTarget;
        let cardNumber = button.getAttribute('data-bs-number');
        let cardId = button.getAttribute('data-bs-id')
        let modalTitle = depositModal.querySelector('.modal-title');
        modalTitle.textContent = 'Deposit ' + cardNumber;
        let cardIdInput = document.querySelector('#card-id');
        cardIdInput.value = cardId;
    });

    let transferModal = document.getElementById('transfer');
    transferModal.addEventListener('show.bs.modal', function (event) {
        let button = event.relatedTarget;
        let cardId = button.getAttribute('data-bs-id')
        let cardIdInput = document.querySelector('#sender-id');
        cardIdInput.value = cardId;
    });
</script>
</body>
</html>