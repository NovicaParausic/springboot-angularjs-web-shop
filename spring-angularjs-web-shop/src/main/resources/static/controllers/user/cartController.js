(function() {
	'use strict';
	
	angular
		.module('jamesAuth')
		.controller('CartController', [
		    '$scope',
			'$http',
			'$log',
			cartController 
        ]);
	
	function cartController($scope, $http, $log) {
		var vm = this;
		
		vm.buyError = false;
		vm.buySuccess = false;
		vm.signupErrorMessage = null
		
		vm.buyItems = buyItems;
		vm.deleteItem = deleteItem;
		
		$scope.items = '';
		$scope.value = 0;
		
		function buyItems() {
			$http({
				method: 'POST',
				url: '/api/cart/buy'
			})
			.then(function(response) {
				vm.buySuccess = true;
			})
			.catch(function(response) {
				$log.info(response.status);
				vm.buyError = true;
			});
		}
		
		function getCartItems() {
			$http({
				method: 'GET',
				url: '/api/cart'
			})
			.then(function(response) {
				$scope.items = response.data;
				countItemsValue($scope.items);
			})
			.catch(function(response) {
				$log.info(response.status);
			});
		}
		
		function countItemsValue(items){
			items.forEach(function(item){
				$scope.value += item.revenue;
			})
		}
		
		getCartItems();

		function deleteItem(code) {
			$http({
				method: 'DELETE',
				url: '/api/cart/' + code
			})
			.then(function(response) {
				getCartItems();
			})
			.catch(function(response) {
				$log.info(response);
			});
		}
	}
})();