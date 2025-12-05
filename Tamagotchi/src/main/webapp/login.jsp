<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<!doctype html>
<html lang="pt-BR" data-bs-theme="light">
  <head>
   
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" xintegrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="css/styles.css" rel="stylesheet">
    <title>Tamagotchi - Login</title>
  </head>
  <body>
  	<div class="container mt-5">
		<div class="col-lg-4 offset-lg-4 col-sm-12">
			
			
			<c:choose>
				<c:when test="${result == 'registered'}">
					<div class="alert alert-success alert-dismissible fade show" role="alert">
						Usuário cadastrado com sucesso. Faça o login.
						<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
					</div>
				</c:when>
				<c:when test="${result == 'loginError'}">
					<div class="alert alert-danger alert-dismissible fade show" role="alert">
						E-mail e/ou senha inválidos.
						<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
					</div>
				</c:when>
			</c:choose>
			
			
			<div class="card shadow-sm">
				<div class="card-body p-4">
			  		<form action="loginServlet" method="post">
			  				<h1 class="text-center mb-4">Login</h1>
			  				
			  				
			  				<div class="input-group mb-3">
			  					<span class="input-group-text">
			  						
			  						<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-envelope" viewBox="0 0 16 16">
									  <path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4Zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2Zm13 2.383-4.708 2.825L15 11.105V5.383Zm-.034 6.876-5.64-3.471L8 9.583l-1.326-.795-5.64 3.47A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.741ZM1 11.105l4.708-2.897L1 5.383v5.722Z"/>
									</svg>
			  					</span>
			  					<input type="email" name="email" id="email"
			  						placeholder="E-mail" required="required"
			  						class="form-control">
			  				</div>
			  				
			  				
			  				<div class="input-group mb-3">
			  					<span class="input-group-text">
			  						<!-- Ícone Cadeado SVG -->
			  						<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-lock" viewBox="0 0 16 16">
									  <path d="M8 1a2 2 0 0 1 2 2v4H6V3a2 2 0 0 1 2-2zm3 6V3a3 3 0 0 0-6 0v4a2 2 0 0 0-2 2v5a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2zM5 8h6a1 1 0 0 1 1 1v5a1 1 0 0 1-1 1H5a1 1 0 0 1-1-1V9a1 1 0 0 1 1-1z"/>
									</svg>
			  					</span>
			  					<input type="password" name="password" id="password"
			  						placeholder="Senha" required="required"
			  						class="form-control">
			  				</div>
			  				
			  			
			  				<div class="d-grid gap-2">
			  					<button type="submit" class="btn btn-primary">Entrar</button>
			  					<a href="user-register.jsp" class="btn btn-outline-secondary">Criar nova conta</a>
			  				</div>
			  		</form>
		  		</div>
	  		</div>
  		</div>
  	</div>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" xintegrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
	<!-- <script type="text/javascript" src="js/theme.js"></script> -->
  </body>
</html>
