(function() {
    'use strict';

    angular
        .module('jamesAuth')
        .controller('OrdersController', [
            '$scope',
        	'$http',
        	'$log',
        	ordersController
        ]);

    function ordersController($scope, $http, $log) {
        var vm = this;

        $scope.orders = '';
        vm.pages = '';
        vm.page = 0;
        
        vm.changePage = changePage;
        vm.ifPrevPageAvailable = ifPrevPageAvailable;
        vm.ifNextPageAvailable = ifNextPageAvailable;

	    function getOrders(){
	    	var parameters = {};
	    	$http({ 
	        	method: 'GET', 
	        	url: '/bla/orders?page=' + vm.page 
	        })
	        .then(function(response) {
	        	$scope.orders = response.data.content;
	        	vm.pages = response.data.totalPages;
	        })
	        .catch(function(response) {
	        	$log.info(response.status);
	        });
	    }	
	    
	    getOrders();
        
        function changePage(i) { 
    		vm.page += i;
    		getOrders();
    	}
        
        function ifPrevPageAvailable() {
        	if(vm.page > 0) {
        		return false
        	} else {
        		return true;
        	}
        }
        
        function ifNextPageAvailable() {
        	if(vm.page < (vm.pages - 1)) {
        		return false
        	} else {
        		return true;
        	}
        }
    }
})();