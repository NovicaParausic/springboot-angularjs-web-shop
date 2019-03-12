(function(){
	'use strict';
	
	angular
		.module('jamesAuth')
		.controller('CartController', [
		    '$scope',
			'$http',
			cartController 
        ]);
	
	function cartController($scope, $http){
		var vm = this;
		
		vm.buyError = false;
		vm.buySuccess = false;
		vm.signupErrorMessage = null
		
		vm.deleteItem = deleteItem;
		vm.buyItems = buyItems;
		
		$scope.items = '';
		$scope.value = 0;
		
		function buyItems(){
			$http({
				method: 'POST',
				url: '/api/cart/buy'
			})
			.then(function(response) {
				//console.log(response.status);
				vm.buySuccess = true;
			})
			.catch(function(response) {
				//console.log(response.status);
				vm.buyError = true;
			});
		}
		
		function getCartItems(){
			$http({
				method: 'GET',
				url: '/api/cart'
			})
			.then(function(response){
				//console.log(response.status);
				$scope.items = response.data;
				countItemsValue($scope.items);
			})
			.catch(function(response){
				//console.log(response.status);
			})
		}
		
		function countItemsValue(items){
			items.forEach(function(item){
				$scope.value += item.revenue;
			})
		}
		
		getCartItems();

		function deleteItem(code){
			$http({
				method: 'DELETE',
				url: '/api/cart/' + code
			})
			.then(function(response){
				//console.log(response);
				getCartItems();
			})
			.catch(function(response){
				//console.log(response);
			})
		}
	}
})();