<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div  class="col-md-8 col-md-offset-1">
    <h1 align='center'>Réservation d'une oeuvre</h1>
    <form class="form-signin form-horizontal" role="form" action="enregistrerReservation.res" method="post">
        <div class="form-group">
            <label class="col-md-3 control-label">Titre : </label>
            <label class="col-md-6 form-control-static" name="txtTitre" >${resaR.oeuvre.titre}</label>
            <input type="text" name="id_oeuvre" id="id_oeuvre" value="${resaR.oeuvre.id_oeuvre}" hidden/>
            <input type="text" name="txtTitre" id="txtTitre" value="${resaR.oeuvre.titre}" hidden/>

        </div>
        <div class="form-group">
            <label class="col-md-3 control-label">Prix : </label>
            <label class="col-md-6 form-control-static" name="txtPrix" >${resaR.oeuvre.prix}</label>
            <input type="text" name="txtPrix" id="txtPrix" value="${resaR.oeuvre.prix}" hidden/>
        </div>
        <div class="form-group">
            <label class="col-md-3 control-label">Date réservation : </label>
            <div class="col-md-3">
                <input type="text" name="txtDate" id="txtDate" value="${now}" class="form-control" placeholder="AAAA-MM-JJ" required/>
            </div>
        </div>            
        <div class="form-group">
            <label class="col-md-3 control-label">Adhérent : </label>
            <div class="col-sm-6 col-md-3">
                <select class='form-control' name='lstAdherents' required>
                    <c:forEach var="lst" items="${lAdherentsR}">
                        <option value="${lst.getId_adherent()}"
                                <c:if test="${lst.getId_adherent() == lstAdherents}"> 
                                    SELECTED
                                </c:if> >${lst.getNom_adherent()} ${lst.getPrenom_adherent()}
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>             
        <div class="form-group">
            <div class="col-md-4 col-md-offset-4">
                <button type="submit" class="btn btn-default btn-primary"><span class="glyphicon glyphicon-log-in"></span> Valider</button>
            </div>
        </div>
    </form>
    <script>
        $(function () {
            $("#txtDate").datepicker({dateFormat: "yy-mm-dd"});
        });
    </script>        
</div>