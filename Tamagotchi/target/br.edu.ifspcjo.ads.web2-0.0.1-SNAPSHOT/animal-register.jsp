<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<!DOCTYPE html>
<html lang="pt-BR" data-bs-theme="light">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Tamagotchi - Cadastro de Animal</title>
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="css/styles.css" rel="stylesheet">
</head>
<body>
	<!-- Inclusão da Barra de Navegação -->
	<jsp:include page="navbar.jsp" />
	
	<div class="container mt-5">
		<div class="col-lg-6 offset-lg-3 col-sm-12">
			
			<!-- Alertas de Sucesso/Erro -->
			<c:if test="${result == 'registered'}">
				<div class="alert alert-success alert-dismissible fade show" role="alert">
					Animal salvo com sucesso.
					<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
				</div>
			</c:if>
			<c:if test="${result == 'error'}">
				<div class="alert alert-danger alert-dismissible fade show" role="alert">
					Erro ao salvar. Verifique os dados e tente novamente.
					<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
				</div>
			</c:if>
				
			<div class="card shadow-sm">
				<div class="card-body p-4">
					<form action="animalRegisterServlet" method="post">
						
						<!-- Título Dinâmico (Novo ou Edição) -->
						<h1 class="text-center mb-4">
							<c:out value="${animal.id != null ? 'Editar Animal' : 'Novo Animal'}" />
						</h1>

						<!-- ID Oculto (0 para novo, ID real para edição) -->
						<input type="hidden" name="id" value="${animal.id != null ? animal.id : 0}">
						
						<!-- Campo Nome -->
						<div class="mb-3">
							<label for="name" class="form-label">Nome*</label> 
							<input type="text" name="name" id="name" class="form-control" 
								   value="${animal.name}" required placeholder="Ex: Rex, Mimi...">
						</div>

						<!-- Campo Espécie (Restrito a Cachorro/Gato conforme solicitado) -->
						<div class="mb-3">
							<label for="species" class="form-label">Espécie*</label> 
							<select class="form-select" name="species" id="species" required>
								<option value="" disabled ${animal.species == null ? 'selected' : ''}>Selecione</option>
								<option value="Cachorro" ${animal.species == 'Cachorro' ? 'selected' : ''}>Cachorro</option>
								<option value="Gato" ${animal.species == 'Gato' ? 'selected' : ''}>Gato</option>
							</select>
						</div>
						
						<!-- Campo Data de Nascimento -->
						<div class="mb-3">
							<label for="birth_date" class="form-label">Data de Nascimento*</label> 
							<input type="date" name="birth_date" id="birth_date" class="form-control" 
								   value="${animal.dateOfBirth}" required>
						</div>

						<!-- Campo Status -->
						<div class="mb-3">
							<label for="status" class="form-label">Status*</label> 
							<select class="form-select" name="status" id="status" required>
								<option value="" disabled ${animal.status == null ? 'selected' : ''}>Selecione</option>
								<option value="DISPONIVEL" ${animal.status == 'DISPONIVEL' ? 'selected' : ''}>Disponível para Adoção</option>
								<option value="ADOTADO" ${animal.status == 'ADOTADO' ? 'selected' : ''}>Já Adotado</option>
							</select>
						</div>

						<!-- Campo Descrição -->
						<div class="mb-3">
							<label for="description" class="form-label">Descrição</label> 
							<textarea name="description" id="description" class="form-control" rows="3" 
									  placeholder="Conte um pouco sobre o animal...">${animal.description}</textarea>
						</div>

						<!-- Botões de Ação -->
						<div class="d-grid gap-2 mt-4">
							<button type="submit" class="btn btn-primary">Salvar</button>
							<a href="homeServlet" class="btn btn-secondary">Cancelar</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	<script type="text/javascript" src="js/theme.js"></script> 
</body>
</html>