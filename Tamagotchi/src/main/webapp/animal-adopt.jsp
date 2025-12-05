<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Finalizar Adoção</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    <div class="container mt-5">
        <h2>Adotar: ${animal.name}</h2>
       <form action="<c:url value='/animals/adopt'/>" method="post">
            <input type="hidden" name="animalId" value="${animal.id}">
            <div class="mb-3">
                <label class="form-label">Quem adotou?</label>
                <select name="adopterId" class="form-select" required>
                    <option value="">Selecione...</option>
                    <c:forEach var="p" items="${adopters}">
                        <option value="${p.id}">${p.name} - ${p.email}</option>
                    </c:forEach>
                </select>
            </div>
            <button type="submit" class="btn btn-success">Finalizar Adoção</button>
        </form>
    </div>
</body>
</html>