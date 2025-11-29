<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR" data-bs-theme="light">
<head>
	<meta charset="UTF-8">
	<title>Tamagotchi - Estatísticas</title>
	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
		rel="stylesheet">
	<link rel="stylesheet" href="css/styles.css">
</head>
<body>
	<!-- INCLUSÃO DA NAVBAR -->
	<jsp:include page="navbar.jsp" />
	
	<div class="container">
	    <div class="row my-3">
	        <div class="col">
	            <h4 class="text-center">Estatísticas dos Animais Cadastrados</h4>
	        </div>
	    </div>
	    <div class="row my-2">
	        <!-- Gráfico 1: Donut (Por Espécie) -->
	        <div class="col-md-6 py-1">
	            <div class="card h-100 shadow-sm">
	            	<div class="card-header bg-white">
	            		<h5 class="card-title mb-0">Animais por Espécie</h5>
	            	</div>
	                <div class="card-body">
	                    <canvas id="chDonut1"></canvas>
	                </div>
	            </div>
	        </div>
	        
	        <!-- Gráfico 2: Barras (Por Data de Nascimento) -->
	        <div class="col-md-6 py-1">
	            <div class="card h-100 shadow-sm">
	            	<div class="card-header bg-white">
	            		<h5 class="card-title mb-0">Nascimentos por Data</h5>
	            	</div>
	                <div class="card-body">
	                    <canvas id="chBar"></canvas>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>

	<!-- Scripts -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Chart.js -->
	<script src='https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.js'></script>
	
	<!-- Lógica dos Gráficos (Você precisará criar/adaptar este arquivo depois) -->
	<script type="text/javascript" src="js/statistics.js"></script>
	
	<!-- Script de tema opcional -->
	<!-- <script type="text/javascript" src="js/theme.js"></script> -->
</body>
</html>