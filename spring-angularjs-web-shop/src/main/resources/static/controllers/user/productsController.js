(function(){
	'use strict';
	
	angular
		.module('jamesAuth')
		.controller('ProductsController', [
		    '$scope',
			'$http',
			'authService',
		    productsController
        ]);
	
	function productsController($scope, $http, authService) {
		var vm = this;
		
		console.log('PRODUCTS CONTROLLER');
		
		$scope.products = '';
		
		vm.getProducts = getProducts;
		vm.isAdmin = isAdmin;
		vm.saveProductToCart = saveProductToCart;
		
		function getProducts(){
			$http({
				method: 'GET', 
				url: '/bla/products'
			})			
			.then(function (response) {
				if(response && response.data) {
					$scope.products = response.data;
				}
			})
			.catch(function(response) {
				//console.log(response.status);
			});
		
		}
		
		function isAdmin() {
			return authService.isAdmin();
		}
		
		function Item(code, name, buyingPrice, price, quantity) {
			this.code = code;
			this.name = name;
			this.buyingPrice = buyingPrice;
			this.price = price;
			this.quantity = quantity;
			this.revenue = price * quantity;
		}
		
		function saveProductToCart(product, quantity) {
			if(!quantity) {
				quantity = 1;
			}
			
			var item = new Item(product.id, product.name, product.buyingPrice, product.price, quantity);
			
			$http({
				method: 'POST', 
				url: '/api/cart',
				data: item
			})
			.then(function (response) {
				//console.log(response.status);
			})
			.catch(function (response) {
				//console.log(response.status);
			});
		} 
		
		getProducts();
	}
})();