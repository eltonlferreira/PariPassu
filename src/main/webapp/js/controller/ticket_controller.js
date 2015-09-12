/**
 * 
 */

'use strict';
 
App.controller('TicketController', ['$scope', 'TicketService', function($scope, TicketService) {

		  var self = this;

		  self.ticketAtual = {value:''};
          self.ticket = {value:''};
          self.sequence = 0;
		  
          self.takeNormal = function(){
              TicketService.takeNormal()
                  .then(
                               function(d) {
                            	   self.allTickets();
                                    self.ticketAtual = d;
                               },
                                function(errResponse){
                                    console.error('[C] Erro enquanto recupera o ticket normal');
                                }
                       );
          };
            
          self.takePriority = function(){
              TicketService.takePriority()
                  .then(
                               function(d) {
                     		  	  	self.allTickets();
                                    self.ticketAtual = d;
                               },
                                function(errResponse){
                                    console.error('[C] Erro enquanto recupera o ticket preferencial');
                                }
                       );
          };

          self.nextTicket = function(){
              TicketService.nextTicket()
                  .then(
                               function(d) {
                            	   	self.allTickets();
                                    self.ticket = d;
                               },
                                function(errResponse){
                                    console.error('[C] Erro enquanto recupera o primeiro ticket');
                                }
                       );
          };

          self.allTickets = function(){
              TicketService.listAll()
                  .then(
                               function(all) {
                                    self.tickets = all;
                               },
                                function(errResponse){
                                    console.error('[C] Erro enquanto visualiza o ticket preferencial');
                                }
                       );
          };
          self.takeNormal();
          self.allTickets();

          self.lastTicket = function(){
              TicketService.lastOne()
                  .then(
                               function(d) {
                                    self.ticket = d;
                               },
                                function(errResponse){
                                    console.error('[C] Erro enquanto visualiza o ticket preferencial');
                                }
                       );
          };
          
          self.resetTicket = function(){
              TicketService.resetTicket()
                  .then(
            		  	  self.allTickets(),
                                function(errResponse){
                                    console.error('[C] Erro enquanto visualiza o ticket preferencial');
                                }
                       );
          };

          self.resetTicketSequence = function(sequence){
              TicketService.resetTicketSequence(sequence)
                  .then(
            		  	  self.allTickets(),
                               function(errResponse){
                                    console.error('[C] Erro enquanto reseta o ticket sequence');
                               }
                       );
          };
          
          self.submit = function() {
              if(angular.isUndefined(self.sequence) || self.sequence === null ){
                  console.log('[C] Reiniciando com ZERO');    
                  self.resetTicket();
              }else{
                  self.resetTicketSequence(self.sequence);
                  console.log('[C] Reiniciando com ', self.sequence);
              }
              self.reset();
          };
          
          self.reset = function(){
              self.ticketAtual = {value:''};
              self.ticket = {value:''};
              self.sequence = 0;
              $scope.myForm.$setPristine(); //reset Form
          };

 
      }]);