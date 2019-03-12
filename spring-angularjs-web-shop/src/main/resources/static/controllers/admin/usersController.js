(function(){
	'use strict';
	
	angular
		.module('jamesAuth')
		.controller('UsersController', [
			'$scope',
		    '$http',
		    usersController
        ]);
	
	function usersController($scope, $http){
		var vm = this;
		
		vm.changePage = changePage;
		vm.getUsers = getUsers;
		vm.toggleEnabled = toggleEnabled;
		vm.ifNextPageAvailable = ifNextPageAvailable;
		vm.ifPrevPageAvailable = ifPrevPageAvailable;
		
		
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

				console.log(response);
			})
			.catch(function(response) {
				console.log(response.status)
			});
		}

		getUsers();
		
		function toggleEnabled(user){
			var enabled = !user.enabled;
			
			$http({
				method: 'POST',
				url: '/api/users/' + user.id + '/enabled/' + enabled				
			})
			.then(function (response) {
				getUsers();
			})
			.catch(function(response){
				//console.log(response.status);
			});
		}
		
		function changePage(i){
    		console.log('Change page' + vm.page);
    		vm.page += i;
    		getUsers();
    	}
        
        function ifPrevPageAvailable(){
        	if(vm.page > 0){
        		return false
        	} else {
        		return true;
        	}
        }
        
        function ifNextPageAvailable(){
        	if(vm.page < (vm.pages - 1)){
        		return false
        	} else {
        		return true;
        	}
        }
	}
})();