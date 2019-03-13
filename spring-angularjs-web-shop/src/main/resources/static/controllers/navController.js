(function(){
	'use strict';
	
	angular
		.module('jamesAuth')
		.controller('NavController', [
			'$scope',
            'authService',
            navController
	    ]);
	
	function navController($scope, authService){
		var vm = this;
		
		vm.isAdmin = authService.isAdmin;
		vm.isAuthenticated = authService.isAuthenticated;
		vm.isUser = authService.isUser;
		vm.logout = authService.logout;
		
			
	}
})();