(function() {
	'use strict';
	
	angular
		.module('jamesAuth')
		.controller('SignupController', [
		     '$scope',
		     '$state',
		     'authService',
		     signupController
        ]);
	
	function signupController($scope, $state, authService) {
		var vm = this;
		
		vm.signupSuccess = false;
		vm.signupError = false;
		vm.signupErrorMessage = null;
		
		vm.signup = signup;
		
		function signup() {
			vm.signupSuccess = false;
			vm.signupError = false;
			vm.signupErrorMessage = null;
			
			if(!vm.username || !vm.password) {
				vm.signupError = true;
				vm.signupErrorMessage = 'Username and password required';
				return
			}
			
			if(!vm.firstname || !vm.lastname) {
				vm.signupError = true;
				vm.signupErrorMessage = 'Firstname and lastname required';
				return
			}
			
			authService.signup(vm.username, vm.password, vm.firstname, vm.lastname, vm.email)
				.then(handleSuccessfulSignup)
				.catch(handleFailedSignup);
		}
		
		function handleSuccessfulSignup(response) {
			vm.signupSuccess = true;
		}
		
		function handleFailedSignup(response) {
			vm.signupSuccess = false;
			vm.signupError = true;
			console.log(response);
			if(response.status == 422) {
				vm.signupErrorMessage = "Username already exists";
			} else {
				vm.signupErrorMessage = "Something went wrong"
			}	
		}
	}
})();