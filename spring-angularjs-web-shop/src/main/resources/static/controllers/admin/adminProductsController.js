(function(){
	'use strict';
	
	angular
		.module('jamesAuth')
		.controller('AdminProductsController', [
			'$http',
			'$scope',
			'$state',
			'$log',
			'dataService',
			adminProductsController
		]);
	
	function adminProductsController($http, $scope, $state, $log, dataService){
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
					$log.info(response.status);
					$scope.products = response.data;
				}
			})
			.catch(function (response) {
				$log.info(response.status);
			});
		
		}
		
		function toggleEnabled(product){
			var enabled = !product.enabled;
			$http({
				method: 'POST',
				url: '/bla/products/' + product.id + '/enabled/' + enabled 
			})
			.then(function (response) {
				$log.info(response.status);
				getProducts();
			})
			.catch(function (response) {
				$log.info(response.status);
			});
		}
		
		getProducts();
		
		function change(product) {
			dataService.saveProduct(product);
			$state.go('admin-product');
		}		
	}
})();