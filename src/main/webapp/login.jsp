<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login | Nanobank </title>
    <link rel="stylesheet" href="https://getbootstrap.com/docs/5.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/main.css">
</head>
<body class="text-center">

<main class="form-signin">
    <form action="login" method="POST">
        <img class="mb-4" src="assets/img/logo.png" alt="" width="72" height="57">
        <h1 class="h3 mb-3 fw-normal">Please sign in</h1>
        <div class="form-floating">
            <input type="tel" name="phone" class="form-control" id="floatingPhone" placeholder="Phone">
            <label for="floatingPhone">Phone</label>
        </div>
        <div class="form-floating">
            <input type="password" name="password" class="form-control" id="floatingPassword" placeholder="Password">
            <label for="floatingPassword">Password</label>
        </div>
        <span class="text-danger">${sessionScope.error}</span>
        <button class="w-100 btn btn-lg btn-primary mt-2" type="submit">Sign in</button>
        <p class="mt-5 mb-3 text-muted">© 2022 Nanobank</p>
    </form>
</main>
</body>
</html>