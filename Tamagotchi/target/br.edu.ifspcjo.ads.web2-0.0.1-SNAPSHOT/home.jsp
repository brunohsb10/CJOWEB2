<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    import = "java.util.List, br.edu.ifspcjo.ads.cjoweb2.model.Animal"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="pt-BR" data-bs-theme="light">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/styles.css">
<title>Tamagotchi - Página Principal</title>
<style>
    /* Ajuste para alinhar o botão Filtrar */
    .btn-filter-container { margin-top: 30px; }
    @media (max-width: 991px) { .btn-filter-container { margin-top: 10px; } }
</style>
</head>
<body>
	<!-- INCLUSÃO DA NAVBAR -->
	<jsp:include page="navbar.jsp" />

	<div class="container mt-5">
		<div class="center col-lg-12 col-sm-12">
			<div class="col-12">
				<h1 class="text-center mb-4">Meus Animais</h1>
			</div>
			
			<!-- FORMULÁRIO DE FILTRO -->
			<div class="card p-4 mb-4 shadow-sm">
				<form action="animalSearchServlet" method="post">
					<div class="row">
						<div class="col-12 col-lg-3">
						  	<div class="mb-2">
								<label for="species" class="form-label">Espécie</label> 
								<select class="form-select" name="species" id="species">
									<option value="" selected>Todas</option>
									<option value="Cachorro">Cachorro</option>
									<option value="Gato">Gato</option>
								</select>
							</div>
						</div>
						<div class="col-12 col-lg-3">
						  	<div class="mb-2">
								<label for="status" class="form-label">Status</label> 
								<select class="form-select" name="status" id="status">
									<option value="" selected>Todos</option>
									<option value="DISPONIVEL">Disponível</option>
									<option value="ADOTADO">Adotado</option>
								</select>
							</div>
						</div>
						<div class="col-12 col-lg-2">
							<label for="initial-date" class="form-label">Data inicial</label> 
							<input type="date" name="initial-date" id="initial-date" class="form-control">
						</div>  
						<div class="col-12 col-lg-2">
							<label for="final-date" class="form-label">Data final</label>
							<input type="date" name="final-date" id="final-date" class="form-control">
						</div>
						<div class="col-12 col-lg-2 btn-filter-container">
							<button type="submit" class="btn btn-primary w-100">Filtrar</button>
						</div>  
					</div>
				</form>
			</div>

			<!-- TABELA DE RESULTADOS -->
			<c:choose>
				<c:when test="${fn:length(userAnimals) > 0}">
					<div class="table-responsive">
						<table class="table table-striped table-hover align-middle">
							<thead class="table-dark">
								<tr>
									<th>#</th><th>Nome</th><th>Espécie</th><th>Nascimento</th><th>Status</th><th class="text-center">Ações</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="animal" items="${userAnimals}" varStatus="index">
									<tr>
										<td>${index.count}</td>
										<td>${animal.name}</td>
										<td>
											<c:choose>
												<c:when test="${animal.species == 'Cachorro'}">🐶 Cachorro</c:when>
												<c:when test="${animal.species == 'Gato'}">🐱 Gato</c:when>
												<c:otherwise>${animal.species}</c:otherwise>
											</c:choose>
										</td>
										<td>
											<fmt:parseDate value="${animal.dateOfBirth}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
											<fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy" />
										</td>
										<td>${animal.status}</td>
										<td class="text-center">
											<!-- Botão Editar -->
											<a class="btn btn-sm btn-outline-primary me-2" 
											   href="animalRegisterServlet?action=update&animal-id=${animal.id}"
											   data-bs-toggle="tooltip" title="Editar">
	                							<!-- Ícone SVG Lápis -->
	                							<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16"><path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/><path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/></svg>
	                						</a>
                							
                							<!-- Botão Excluir (Dentro de SPAN para funcionar o parentNode do JS) -->
                							<span data-bs-toggle="tooltip" title="Excluir">
	                							<button type="button" class="btn btn-sm btn-outline-danger" 
	                									data-bs-toggle="modal" 
	                									data-bs-target="#myModal" 
	                									data-bs-id="${animal.id}">
		                							<!-- Ícone SVG Lixeira -->
		                							<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16"><path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/><path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/></svg>
		                						</button>
	                						</span>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-info text-center">Nenhum animal registrado.</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	
	<!-- MODAL DE EXCLUSÃO -->
	<div class="modal fade" tabindex="-1" id="myModal">
		<div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title">Exclusão</h5>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		      </div>
		      <div class="modal-body">
		        <p>Tem certeza que deseja excluir este animal?</p>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
		        <!-- Botão com ID "delete" para o JS capturar -->
		        <button type="button" id="delete" class="btn btn-danger">Excluir</button>
		      </div>
		    </div>
	  	</div>
	</div>
	
	<!-- BOOTSTRAP JS + SCRIPT FETCH -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	<script type="text/javascript" src="js/home.js"></script>
	<script type="text/javascript" src="js/theme.js"></script>
		
	
</body>
</html>