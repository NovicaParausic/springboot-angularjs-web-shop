(function() {
    'use strict';

    var jamesAuth = angular.module('jamesAuth', [
        'ui.router',
        'ngCookies'       
    ]);

    jamesAuth.factory('requestInterceptor', [
        '$cookies', '$q', '$log',
        function ($cookies, $q, $log) {
        	return {
                request: function(config) {
                    var user = $cookies.get('user'),
                        token = null;
                    
                    if(user) {
                        user = JSON.parse(user);
                        token = user.token ? user.token : null;
                    }

                    if(token) {
                        config.headers = config.headers || {};
                        config.headers.Authorization = token;
                    }

                    return config; 
                },
                
                response: function(response){
                	return response || $q.when(response);
                },
                
                responseError: function(rejection){
                	console.log(rejection.status);
                	return $q.reject(rejection);
                }
            };
        }
    ]);

    var staticData = {};

    var userRoles = staticData.userRoles = {
        guest: 1,
        user: 2,
        admin: 4
    };

    staticData.accessLevels = {
        guest: userRoles.guest | userRoles.user | userRoles.admin,
        user: userRoles.user | userRoles.admin,
        admin: userRoles.admin
    };

    jamesAuth.constant('staticData', staticData);

    jamesAuth.config([
        '$stateProvider',
        '$urlRouterProvider',
        '$httpProvider',
        '$locationProvider',
        'staticData',
        authConfig
    ]);

    function authConfig(
        $stateProvider,
        $urlRouterProvider,
        $httpProvider,
        $locationProvider,
        staticData ) {
        
        $stateProvider.state('index', {
            url: '/',
            templateUrl: 'views/partial-index.html'
        });

        $stateProvider.state('login', {
            url: '/login',
            templateUrl: 'views/partial-login.html',
            controller: 'LoginController as lc'
        });
      
        $stateProvider.state('signup', {
            url: '/signup',
            templateUrl: 'views/partial-signup.html',
            controller: 'SignupController as sc'
        });
        
        $stateProvider.state('cart', {
            url: '/cart',
            templateUrl: 'views/user/partial-cart.html',
            controller: 'CartController as pc',
            data: {
                accessLevel: staticData.accessLevels.user
            }
        });
        
        $stateProvider.state('products', {
            url: '/products',
            templateUrl: 'views/user/partial-products.html',
            controller: 'ProductsController as prc',
            data: {
                accessLevel: staticData.accessLevels.user
            }
        });
        
        $stateProvider.state('admin-product', {
            url: '/admin-product',
            templateUrl: 'views/admin/partial-admin-product.html',
            controller: 'AdminProductController as arc',
            params: {
                obj: null
            },
            data: {
                accessLevel: staticData.accessLevels.admin
            }
        });
        
        $stateProvider.state('admin-products', {
            url: '/admin-products',
            templateUrl: 'views/admin/partial-admin-products.html',
            controller: 'AdminProductsController as apc',
            data: {
                accessLevel: staticData.accessLevels.admin
            }
        });
                
        $stateProvider.state('orders', {
            url: '/orders',
            templateUrl: 'views/admin/partial-orders.html',
            controller: 'OrdersController as rc',
            data: {
                accessLevel: staticData.accessLevels.admin
            }
        });
        
        $stateProvider.state('users', {
            url: '/users',
            templateUrl: 'views/admin/partial-users.html',
            controller: 'UsersController as uc',
            data: {
                accessLevel: staticData.accessLevels.admin
            }
        });
   
        $urlRouterProvider.otherwise('/');
        
        $httpProvider.interceptors.push('requestInterceptor');
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    }

    jamesAuth.run([
    	'$http',
        '$rootScope',
        '$state',
        '$log',
        'authService',
        authRun
    ]);

    function authRun($http, $rootScope, $state, $log, authService) {
        $rootScope.$on('$stateChangeStart', function(event, toState) {
        	var user = authService.getUserData();
            if(toState.data && toState.data.accessLevel && user) {
                //var user = authService.getUserData();
                if(!(toState.data.accessLevel & user.role)) {
                    event.preventDefault();
                    $state.go('index');
                    return;
                }
            }
        });
    }
})();