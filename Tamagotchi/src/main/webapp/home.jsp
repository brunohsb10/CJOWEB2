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
    
    .btn-filter-container { margin-top: 30px; }
    @media (max-width: 991px) { .btn-filter-container { margin-top: 10px; } }
</style>
</head>
<body>
	
	<jsp:include page="navbar.jsp" />

	<div class="container mt-5">
		<div class="center col-lg-12 col-sm-12">
			<div class="col-12">
			<div class="col-12 d-flex justify-content-between align-items-center mb-4">
    <h1 class="text-center">Gestão de Animais</h1>
    <a href="animals/register" class="btn btn-primary btn-lg">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-plus-circle" viewBox="0 0 16 16">
          <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
          <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
        </svg>
        Novo Animal
    </a>
</div>
				<h1 class="text-center mb-4">Lista de Animais</h1>
			</div>
			
			
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

			
			<c:choose>
				<c:when test="${fn:length(userAnimals) > 0}">
					<div class="table-responsive">
						<table class="table table-striped table-hover align-middle">
    <thead class="table-dark">
        <tr>
            <th>#</th><th>Nome</th><th>Espécie</th><th>Status</th><th class="text-center">Ações</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="animal" items="${userAnimals}" varStatus="index">
            <tr>
                <td>${index.count}</td>
                <td>${animal.name}</td>
                <td>${animal.species}</td>
                <td>
                     <c:choose>
                        <c:when test="${animal.status == 'Adopted'}"><span class="badge bg-success">Adotado</span></c:when>
                        <c:otherwise>${animal.status}</c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">
                    <c:if test="${animal.status != 'Adopted'}">
                        <a href="animals/adopt?id=${animal.id}" class="btn btn-sm btn-info me-1">Adotar</a>
                        <a href="animals/register?id=${animal.id}" class="btn btn-sm btn-warning me-1">Editar</a>
                    </c:if>
                    <a href="animals/delete?id=${animal.id}" class="btn btn-sm btn-danger" onclick="return confirm('Tem certeza?')">Excluir</a>
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
	
	
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	<script type="text/javascript" src="js/home.js"></script>
	<script type="text/javascript" src="js/theme.js"></script>
		
	
</body>
</html>
