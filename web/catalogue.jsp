<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div  class="col-md-11 col-md-offset-1">
    <h1 align='center'>Catalogue des oeuvres</h1>
    <table class="table table-bordered table-striped">
        <thead>
            <tr>
                <td>Titre</td>
                <td>Prix</td>
                <td>Pr�nom propri�taire</td>
                <td>Nom propri�taire</td>
                <c:if test="${userId == 1 }">
                    <td>R�server</td>
                    <td>Modifier</td>
                    <td>Supprimer</td>       
                </c:if>
            </tr>  
        </thead>
        <tbody>
            <c:forEach var="lst" items="${lstOeuvresR}">
                <tr>
                    <td>${lst.titre}</td>
                    <td>${lst.prix}</td>
                    <td>${lst.getProprietaire().prenom_proprietaire}</td>
                    <td>${lst.getProprietaire().nom_proprietaire}</td>
                    <c:if test="${userId == 1 }">
                        <td><a class="btn btn-primary" href="">R�server</a></td>
                        <td><a class="btn btn-primary" href="">Modifier</a></td> 
                        <td><a class="btn btn-primary" onclick="javascript:if (confirm('Suppression confirm�e ?')) {
                                        window.location = '';
                                    }">Supprimer</a></td>                     
                        </c:if>
                </tr>
            </c:forEach>
        </tbody>
    </table>          
</div>
