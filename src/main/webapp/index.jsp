<%@ page contentType="text/html;charset=UTF-8" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<title>Registration</title>

<html>
    <body>
        <div class="container">
            <form action = "servlet" method="post">
              <div class="form-group">
                <label for="name">Login</label>
                <input class="form-control" name = "name" id="name" value = ${name_r}>
              </div>
              <div class="has-error">
                  <p class="help-block" name="incorrect_email" id="incorrect_email">
                        ${incorrect_name}
                  </p>
              </div>
              <div class="form-group">
                <label for="password">Password</label>
                <input type="password" name="password" id="password" class="form-control">
              </div>
               <button type="submit" class="btn btn-default">Submit</button>
            </form>
        </div>
    </body>
</html>
