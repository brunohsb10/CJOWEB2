<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Adotantes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    <div class="container mt-4">
        <div class="d-flex justify-content-between mb-3">
            <h2>Adotantes</h2>
            <a href="adopters/register" class="btn btn-primary">Novo Adotante</a>
        </div>
        <table class="table table-hover">
            <thead><tr><th>Nome</th><th>Email</th><th>Telefone</th></tr></thead>
            <tbody>
                <c:forEach var="a" items="${adopters}">
                    <tr><td>${a.name}</td><td>${a.email}</td><td>${a.phone}</td></tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <script type="text/javascript" src="<c:url value='/js/theme.js'/>"></script> 
</body>
</html>
</body>
</html>