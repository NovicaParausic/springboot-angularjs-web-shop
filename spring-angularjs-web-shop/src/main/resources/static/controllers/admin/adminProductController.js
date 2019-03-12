(function(){
	'use strict';
	
	angular
		.module('jamesAuth')
		.controller('AdminProductController', [
		    '$scope',
			'$http',
		    '$stateParams',
		    'authService',
		    adminProductController
        ]);
	
	function adminProductController($scope, $http, $stateParams, authService){
		var vm = this;
		
		vm.message = '';
		
		vm.saveProduct = saveProduct;
		
		$scope.product = $stateParams.obj;
		$scope.token = '';
		
		function saveProduct(product) {
			var user = authService.getUserData();
			$scope.token = authService.createAuthorizationToken();
						
			if($scope.product.id){
				$http({
					method: 'PUT', 
					url: '/bla/products/' + $scope.product.id,
					data: $scope.product
				})
				.then(function (response) {
					//console.log(response.status);
				})
				.catch(function (response) {
					//console.log(response.status);
				});
			} 
			else {
				$http({
					method: 'POST',
					url: '/bla/products',
					data: $scope.product
				})
				.then(function (response) {
					//console.log(response.status);
				})
				.catch(function (response) {
					//console.log(response.status);
				});
			}
		}
	}
})();