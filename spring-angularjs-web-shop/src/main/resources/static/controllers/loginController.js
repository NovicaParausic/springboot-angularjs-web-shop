(function() {
	'use strict';

    angular
        .module('jamesAuth')
        .controller('LoginController', [
            '$http',
            '$state',
            '$log',
            'authService',
            loginController
        ]);

    function loginController($http, $state, $log, authService) {
        var vm = this;

        vm.loginError = false;
        vm.loginErrorMessage = null;

        vm.login = login;

        function login() {
            vm.loginError = false;
            vm.loginErrorMessage = null;

            if(!vm.username || !vm.password) {
                vm.loginError = true;
                vm.loginErrorMessage = 'Username and password required!';
                return;
            }

            authService.login(vm.username, vm.password)
                .then(handleSuccessfulLogin)
                .catch(handleFailedLogin); 
        }   

        function handleSuccessfulLogin() {
            $state.go('index');
        }

        function handleFailedLogin(response) {
        	$log.info(response.status);

            if(response && response.data) {
                vm.loginErrorMessage = response.data.message;
                vm.loginError = true;
            }
        }
    }
})();