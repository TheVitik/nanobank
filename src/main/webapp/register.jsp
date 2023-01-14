<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register | Nanobank </title>
    <link rel="stylesheet" href="https://getbootstrap.com/docs/5.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/main.css">
</head>
<body class="text-center">

<main class="form-signin">
    <form action="register" method="POST">
        <img class="mb-4" src="assets/img/logo.png" alt="" width="72px" height="57px">
        <h1 class="h3 mb-3 fw-normal">Please sign up</h1>
        <div class="form-floating">
            <input type="text" name="firstname" class="form-control" id="floatingFirstName" placeholder="First name"
                   required>
            <label for="floatingFirstName">First name</label>
        </div>
        <div class="form-floating">
            <input type="text" name="lastname" class="form-control" id="floatingLastName" placeholder="Last name"
                   required>
            <label for="floatingLastName">Last name</label>
        </div>
        <div class="form-floating">
            <input type="tel" name="phone" class="form-control" id="floatingPhone" placeholder="Phone" required>
            <label for="floatingPhone">Phone</label>
        </div>
        <div class="form-floating">
            <input type="password" name="password" class="form-control" id="floatingPassword" placeholder="Password"
                   required>
            <label for="floatingPassword">Password</label>
        </div>
        <span class="text-danger">${sessionScope.error}</span>
        <button class="w-100 btn btn-lg btn-primary mt-2" type="submit">Sign up</button>
        <p class="mt-5 mb-3 text-muted">© 2022 Nanobank</p>
    </form>
</main>
</body>
</html>