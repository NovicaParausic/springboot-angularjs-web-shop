(function() {
	'use strict';
	
	angular
		.module('jamesAuth')
		.controller('AdminProductController', [
		    '$scope',
			'$http',
		    '$stateParams',
		    '$log',
		    'authService',
		    'dataService',
		    adminProductController
        ]);
	
	function adminProductController($scope, $http, $stateParams, $log, authService, dataService) {
		var vm = this;
		
		vm.message = '';
		
		vm.saveProduct = saveProduct;
		
		$scope.product = dataService.loadProduct();
		$scope.token = '';
		
		function saveProduct(product) {
			var user = authService.getUserData();
			$scope.token = authService.createAuthorizationToken();
						
			if($scope.product.id) {
				$http({
					method: 'PUT', 
					url: '/bla/products/' + $scope.product.id,
					data: $scope.product
				})
				.then(function (response) {
					$log.info(response.status);
				})
				.catch(function (response) {
					$log.info(response.status);
				});
			} 
			else {
				$http({
					method: 'POST',
					url: '/bla/products',
					data: $scope.product
				})
				.then(function (response) {
					$log.info(response.status);
				})
				.catch(function (response) {
					$log.info(response.status);
				});
			}
		}
	}
})();