(function() {
	
	'use strict';
	
	angular
		.module('jamesAuth')
		.factory('authService', [
		    '$http',
		    '$cookies',
		    '$state',
		    '$window',	
		    '$log',
		    authService
         ]);
	
	function authService($http, $cookies, $state, $window, $log) {
		
		var authService = {
				login: login,
				logout: logout,
				signup: signup,
				getUserData: getUserData,
				isAuthenticated: isAuthenticated,
				isAdmin: isAdmin,
				isUser: isUser,
				createAuthorizationToken: createAuthorizationToken
		};
		
		return authService;
		
		function login(user, pass) {
			var reqObj = {
					method: 'POST',
					url: '/auth',
					data: {
						username: user,
						password: pass
					}
			};
			
			return $http(reqObj).then(function(response) {
				if(response && response.data) {
					response = response.data;
					
					var expires = new Date(), 
									  user ={};
					
					user.token = 'Bearer ' + response.token;
					
					var decoded = decodeJwt(user.token);
					user.username = decoded.sub;
					
					user.role = roleToNumber(decoded.role);
					
					expires.setTime(expires.getTime() + (30 * 60 * 1000));
					
					$cookies.put(
							'user',
							JSON.stringify(user),
							{expires: expires}
					);
				}
			});
		}
		
		function logout() {
			$http({
				method: 'POST',
				url: '/logout'
			})
			.then(function(response) {
				$cookies.remove('user');
				$state.go('index');
			})
			.catch(function(response) {
				$log.info(response.status);
			});
			
		}
		
		function decodeJwt(token) {
			var base64Url = token.split('.')[1];
			var base64 = base64Url.replace('-', '+').replace('_', '/');			
			var decodedbase64 = window.atob(base64);
			return JSON.parse(decodedbase64);
		}
				
		function roleToNumber(role) {
			var number;
			
			switch(role) {
 				case 'ROLE_USER':
 					number = 2;
 				break;
 				case 'ROLE_ADMIN':
 					number = 4;
 				break;
 				default:
 					number = 1;
 			}
			return number;
		}
		
		
		function isAuthenticated() {
			var user = $cookies.get('user');
			return user && user !== 'undefined';
		}
		
		function isAdmin() { 
			var user = $cookies.get('user');
			if(user) {
				user = JSON.parse(user);
				if(user.role == 4) {
					return true;
				} 
			}
			return false;
		}
		
		function isUser() {
			var user = $cookies.get('user');
			if(user) {
				user = JSON.parse(user);
				if(user.role == 2) {
					return true;
				} 
			}
			return false;
		}
		
		function getUserData() {
			if(isAuthenticated()) {
				return JSON.parse($cookies.get('user'));
			}
			return false;
		}
		
		function getJwtToken() {
			var user = getUserData();
			if(user && user.token) {
				return user.token;
			}
			return false;
		}
		
		function createAuthorizationToken() {
			var token = getJwtToken();
		      if (token) {
		          return {
		            "Authorization": "Bearer " + token,
		            'Content-Type': 'application/json'
		          };
		      } else {
		          return {
		            'Content-Type': 'application/json'
		          };
		      }			
		}
		
		function signup(username, password, firstname, lastname, email) {
			var reqObj = {
				method: 'POST',
				url: '/registration',
				data: {
					username: username,
					password: password,
					firstname: firstname,
					lastname: lastname,
					email: email
				}
			};
			
			return $http(reqObj);
		}
	}
})();