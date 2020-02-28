<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div  class="col-md-11 col-md-offset-1">
    <h1 align='center'>Liste des réservations</h1>             
    <table class="table table-bordered table-striped">
        <thead>
            <tr>
                <td>Titre</td>
                <td>Date</td>
                <td>Statut</td>
                <c:if test="${userId == 1 }">
                    <td>Prénom adhérent</td>
                    <td>Nom adhérent</td>
                    <td>Confirmer</td>
                    <td>Supprimer</td>
                </c:if>

            </tr>
        </thead>
        <tbody>
            <c:forEach var ="lst" items="${lstReservationsR}">
                <tr>
                    <td>${lst.getOeuvre().titre}</td>
                    <td><fmt:formatDate value="${lst.date_reservation}" type="date" pattern="dd-MM-yyyy"/></td>
                    <td>${lst.statut}</td>
                    <c:if test="${userId == 1 }">
                        <td>${lst.getAdherent().prenom_adherent}</td>
                        <td>${lst.getAdherent().nom_adherent}</td>
                        <td><a class="btn btn-primary" href="confirmerReservation.res?id=${lst.id_oeuvre}&date=<fmt:formatDate value="${lst.date_reservation}" type="date" pattern="yyyy-MM-dd"/>">Confirmer</a></td>
                        <td><a class="btn btn-primary" href="supprimerReservation.res?id=${lst.id_oeuvre}&date=<fmt:formatDate value="${lst.date_reservation}" type="date" pattern="yyyy-MM-dd"/>">Supprimer</a></td>                    
                    </c:if>

                </tr>
            </c:forEach>                    
        </tbody>
    </table>              
</div>