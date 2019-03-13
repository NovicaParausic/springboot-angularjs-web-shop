(function() {
	'use strict';
	
	angular
		.module('jamesAuth')
		.controller('ProductsController', [
		    '$scope',
			'$http',
			'$log',
			'authService',
		    productsController
        ]);
	
	function productsController($scope, $http, $log, authService) {
		var vm = this;
		
		$scope.products = '';
		vm.page = 0;
		vm.pages = 0;
		
		vm.changePage = changePage;
		vm.getProducts = getProducts;
		vm.isAdmin = isAdmin;
		vm.ifNextPageAvailable = ifNextPageAvailable;
		vm.ifPrevPageAvailable = ifPrevPageAvailable;
		vm.saveProductToCart = saveProductToCart;
		
		function getProducts(){
			$http({
				method: 'GET', 
				url: '/bla/products?page=' + vm.page
			})			
			.then(function (response) {
				if(response && response.data) {
					vm.pages = response.data.totalPages;
					$scope.products = response.data.content;
				}
			})
			.catch(function(response) {
				$log.info(response.status);
			});
		
		}
		
		getProducts();
		
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
				$log.info(response.status);
			})
			.catch(function (response) {
				$log.info(response.status);
			});
		} 
		
		function changePage(i){
			vm.page += i;
			getProducts();
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