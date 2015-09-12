<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>  
    <title>Ticket APP - PariPassu</title>  
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link href="<c:url value='/css/app.css' />" rel="stylesheet"></link>
  </head>
  <body ng-app="myApp" class="ng-cloak">
      <div class="generic-container" ng-controller="TicketController as ctrl">
          <div class="panel panel-default">
              <div class="panel-heading"><span class="lead">Acesso Administrativo ao TICKET </span>
              <a class="floatRight" href="swagger/index.html">Swagger API DOC</a>	                              
              
              </div>
              <div class="formcontainer">
                  <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="file">Sequencia</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="ctrl.sequence" name="seque" class="sequence form-control input-sm" placeholder="Entre com um inteiro da nova sequencia" required ng-minlength="1" value="0"/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.seque.$error.required">Este campo é requerido</span>
                                      <span ng-show="myForm.seque.$error.minlength">Tamanho mínimo de 1 numero</span>
                                      <span ng-show="myForm.seque.$invalid">Campo inválido</span>
                                  </div>
                             </div>
                                                       <div class="form-actions floatRight">
	                              <input type="submit"  value="Reinicia" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
	                              <button type="button" ng-click="ctrl.resetTicket()" class="btn btn-primary btn-sm">Reiniciar ZERO</button>
	                              <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm">Limpar</button>
                          </div>
                             
                          </div>
                      </div>
 
                  </form>
              </div>

              <div class="panel-heading">
				<button type="button" ng-click="ctrl.nextTicket()" class="btn btn-primary btn-lg">CHAMAR TICKET</button>
              	<h2 class="lead floatRight" ng-hide="!ctrl.ticket.value">Chamando senha {{ctrl.ticket.value}}</h2>
              </div>
              
          </div>


          <div class="panel panel-default">
              <div class="panel-heading">
                    <button type="button" ng-click="ctrl.takePriority()" class="btn btn-primary btn-lg">Preferencial</button>
	                <button type="button" ng-click="ctrl.takeNormal()" class="btn btn-primary btn-lg">Normal</button>
              		<h2 class="lead floatRight" ng-hide="!ctrl.ticketAtual.value">Senha {{ctrl.ticketAtual.value}}</h2>
              </div>

              <div class="tablecontainer">
                  <table class="table table-hover">
                      <thead>
                          <tr>
                              <th>Senhas/número</th>
                          </tr>
                      </thead>
                      <tbody>
                          <tr ng-repeat="t in ctrl.tickets">
                              <td><span ng-bind="t.value"></span></td>
                          </tr>
                      </tbody>
                  </table>
              </div>
          </div>
      </div>
       
      <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
      <script src="<c:url value='/js/app.js' />"></script>
      <script src="<c:url value='/js/service/ticket_service.js' />"></script>
      <script src="<c:url value='/js/controller/ticket_controller.js' />"></script>
  </body>
</html>