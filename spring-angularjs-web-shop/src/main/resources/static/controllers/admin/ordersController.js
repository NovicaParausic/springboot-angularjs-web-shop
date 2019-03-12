(function() {
    'use strict';

    angular
        .module('jamesAuth')
        .controller('OrdersController', [
            '$scope',
        	'$http',
        	ordersController
        ]);

    function ordersController($scope, $http) {
        var vm = this;

        $scope.orders = '';
        vm.pages = '';
        vm.page = 0;
        
        console.log('REports controller page: ' + vm.page);
        
        vm.changePage = changePage;
        vm.ifPrevPageAvailable = ifPrevPageAvailable;
        vm.ifNextPageAvailable = ifNextPageAvailable;

	    function getOrders(){
	    	var parameters = {};
        	console.log(vm.page);
	    	$http({ 
	        	method: 'GET', 
	        	url: '/bla/orders?page=' + vm.page 
	        })
	        .then(function(response) {
	        	console.log(response.data);
	        	$scope.orders = response.data.content;
	        	vm.pages = response.data.totalPages;
	        	console.log(vm.pages);
	        })
	        .catch(function(response) {
	        	//console.log(response.status);
	        });
	    }	
	    
	    getOrders();
        
        function changePage(i){
    		console.log(vm.page);
    		vm.page += i;
    		getOrders();
    	}
        
        function ifPrevPageAvailable(){
        	if(vm.page > 0){
        		return false
        	} else {
        		return true;
        	}
        }
        
        function ifNextPageAvailable(){
        	if(vm.page < (vm.pages - 1)){
        		return false
        	} else {
        		return true;
        	}
        }
    }
})();