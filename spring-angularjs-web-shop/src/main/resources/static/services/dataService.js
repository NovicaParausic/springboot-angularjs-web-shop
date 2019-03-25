(function() {
	'use strict';
	
	angular
	.module('jamesAuth')
	.factory('dataService', [
	    '$localStorage',
	    dataService
     ]);

function dataService($localStorage) {
		console.log('hola data service');
		
		var dataService = {
				loadProduct: loadProduct,
				saveProduct: saveProduct
		};
		
		function saveProduct(product) {
	  		$localStorage.product = product;
	  	}
	  	
	  	function loadProduct() {
	  		return $localStorage.product;
	  	}
		
		return dataService;
	}
})();