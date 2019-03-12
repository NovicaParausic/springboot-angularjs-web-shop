(function(){
	'use strict';
	
	angular
		.module('jamesAuth')
		.controller('AdminProductsController', [
			'$http',
			'$scope',
			'$state',
			adminProductsController
		]);
	
	function adminProductsController($http, $scope, $state){
		var vm = this;
		
		vm.change = change;
		vm.toggleEnabled = toggleEnabled;
		
		$scope.products = '';
		
		function getProducts() {
			$http({
				method: 'GET', 
				url: '/bla/products/admin'
			})
			.then(function(response){
				if(response && response.data){
					console.log(response.status);
					$scope.products = response.data;
					console.log($scope.products);
				}
			})
			.catch(function (response) {
				console.log(response.status)
			});
		
		}
		
		function toggleEnabled(product){
			var enabled = !product.enabled;
			console.log(enabled);
			$http({
				method: 'POST',
				url: '/bla/products/' + product.id + '/enabled/' + enabled 
			})
			.then(function (response) {
				console.log(response.status);
				console.log(response.data);
				getProducts();
			})
			.catch(function (response) {
				console.log(response.status);
			});
		}
		
		getProducts();
		
		function change(product) {
			//console.log(product);
			$state.go('admin-product', {obj: product});
		}		
	}
})();