<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<nav class="navbar navbar-expand-lg">
	<div class="container-fluid">
		<a class="navbar-brand" href="homeServlet">Tamagotchi</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<!-- Link para registrar novo animal -->
				<li class="nav-item">
					<a class="nav-link" href="animal-register.jsp">Registrar Animal</a>
				</li>
				<!-- Link para estatísticas -->
				<li class="nav-item">
					<a class="nav-link" href="statistics.jsp">Estatísticas</a>
				</li>
				
				<!-- Menu do Usuário -->
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
						role="button" data-bs-toggle="dropdown" aria-expanded="false">
						${sessionScope.user.name} 
					</a>
					<ul class="dropdown-menu">
						<li><a class="dropdown-item" href="#">Minha Conta</a></li>
						<li><hr class="dropdown-divider"></li>
						<li><a class="dropdown-item" href="logoutServlet">Sair</a></li>
					</ul>
				</li>
			</ul>
			
			<!-- Botão de troca de tema -->
			<button id="switchTheme" class="btn btn-dark">Trocar Tema</button>
		</div>
	</div>
</nav>