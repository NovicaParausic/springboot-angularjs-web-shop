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
		
		$scope.profile = getProfile();
				
		function getProfile() {
			var profile = authService.getUserData();
			if(profile && profile.username) {
				//console.log(profile);
				return profile.username;
			} else {
				return 'error';
			}
		}

		vm.isAdmin = authService.isAdmin;
		vm.isUser = authService.isUser;
		vm.isAuthenticated = authService.isAuthenticated;
		vm.logout = authService.logout;
	}
})();