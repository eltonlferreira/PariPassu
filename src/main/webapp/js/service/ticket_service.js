/**
 * Serviços para acesso ao rest-rs de TICKET
 */

'use strict';
 
App.factory('TicketService', ['$http', '$q', function($http, $q){
 
    return {
         
            takeNormal: function() {
                    return $http.get('rest/ticket/normal')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Ocorreu um erro ao gerar um ticket normal');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
            takePriority: function(){
                    return $http.get('rest/ticket/priority')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Ocorreu um erro ao gerar um ticket preferencial');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            
            listAll: function(){
                return $http.get('rest/ticket/all')
                        .then(
                                function(response){
                                    return response.data;
                                }, 
                                function(errResponse){
                                    console.error('Ocorreu um erro ao visualizar a lista de todos os tickets');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            lastOne: function(){
                return $http.get('rest/ticket/last')
                        .then(
                                function(response){
                                    return response.data;
                                }, 
                                function(errResponse){
                                    console.error('Ocorreu um erro ao visualizar o próximo ticket');
                                    return $q.reject(errResponse);
                                }
                        );
            },
             
            nextTicket: function(){
                    return $http.get('rest/ticket/admin/next')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Ocorreu um erro ao chamar o proximo ticket');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
            resetTicket: function(){
                return $http.get('rest/ticket/admin/reset/')
                        .then(
                                function(response){
                                    return response.data;
                                }, 
                                function(errResponse){
                                    console.error('Ocorreu um erro ao reiniciar os numeros de ticket');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            
            resetTicketSequence: function(sequence){
                return $http.get('rest/ticket/admin/reset/'+sequence)
                        .then(
                                function(response){
                                    return response.data;
                                }, 
                                function(errResponse){
                                    console.error('Ocorreu um erro ao reiniciar os numeros de ticket por numero definido');
                                    return $q.reject(errResponse);
                                }
                        );
            },
                    
    };
 
}]);