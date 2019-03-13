(function() {
	'use strict';
	
	angular
		.module('jamesAuth')
		.controller('UsersController', [
			'$scope',
		    '$http',
		    '$log',
		    usersController
        ]);
	
	function usersController($scope, $http, $log) {
		var vm = this;
		
		vm.changePage = changePage;
		vm.getUsers = getUsers;
		vm.ifNextPageAvailable = ifNextPageAvailable;
		vm.ifPrevPageAvailable = ifPrevPageAvailable;
		vm.toggleEnabled = toggleEnabled;
		
		$scope.users = '';
		vm.pages = '';
        vm.page = 0;
		
		function getUsers() {
			$http({
				method: 'GET',
				url: '/api/users?page=' + vm.page
			})
			.then(function (response) {
				$scope.users = response.data.content;
	        	vm.pages = response.data.totalPages;
	        	$log.info(response.status);
			})
			.catch(function(response) {
				$log.info(response.status)
			});
		}

		getUsers();
		
		function toggleEnabled(user) {
			var enabled = !user.enabled;
			
			$http({
				method: 'POST',
				url: '/api/users/' + user.id + '/enabled/' + enabled				
			})
			.then(function (response) {
				getUsers();
			})
			.catch(function(response) {
				$log.info(response.status);
			});
		}
		
		function changePage(i) {
    		vm.page += i;
    		getUsers();
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