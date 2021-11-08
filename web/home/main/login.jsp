<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Mock Trading - Login</title>
  <meta name="description" content="">
  <meta name="author" content="templatemo">

  <link href='http://fonts.useso.com/css?family=Open+Sans:400,300,400italic,700' rel='stylesheet' type='text/css'>
  <link href="../../css/font-awesome.min.css" rel="stylesheet">
  <link href="../../css/bootstrap.min.css" rel="stylesheet">
  <link href="../../css/templatemo-style.css" rel="stylesheet">

  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="../../js/html5shiv.min.js"></script>
  <script src="../../js/respond.min.js"></script>
  <![endif]-->
</head>
<body class="light-gray-bg">
<div class="templatemo-content-widget templatemo-login-widget white-bg">
  <header class="text-center">
    <h1>Login</h1>
  </header>
  <form action="../../user_file_servlet_action" method="post" class="templatemo-login-form">
    <input type="hidden" id="action" name="action" value="login">
    <div class="form-group">
      <div class="input-group">
        <div class="input-group-addon"><i class="fa fa-user fa-fw"></i></div>
        <input type="text" class="form-control" placeholder="UserName" name="username">
      </div>
    </div>
    <div class="form-group">
      <div class="input-group">
        <div class="input-group-addon"><i class="fa fa-key fa-fw"></i></div>
        <input type="password" class="form-control" placeholder="Password" name="password">
      </div>
    </div>
    <div class="form-group">
      <div class="checkbox squaredTwo">
        <input type="checkbox" id="c1" name="cc" />
        <label for="c1"><span></span>Remember me</label>
      </div>
    </div>
    <div class="form-group">
      <button type="submit" class="templatemo-blue-button width-100">Login</button>
    </div>
  </form>
</div>
<div class="templatemo-content-widget templatemo-login-widget templatemo-register-widget white-bg">
  <p>Not a registered user yet? <strong><a href="#" class="blue-text">Sign up now!</a></strong></p>
</div>
</body>
</html>
