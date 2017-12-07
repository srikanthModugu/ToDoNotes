var toDoNotes = angular.module('ToDoNotes', [ 'ui.router','ngSanitize' ]);

toDoNotes.config([ '$stateProvider', '$urlRouterProvider',
		function($stateProvider, $urlRouterProvider) {

			$stateProvider.state('login', {
				url : '/login',
				templateUrl : 'template/Login.html',
				controller : 'loginController'
			});

			$stateProvider.state('dummy', {
				url : '/dummy',
				templateUrl : 'template/Dummy.html',
				controller : 'dummyController'
			});

			$stateProvider.state('register', {
				url : '/register',
				templateUrl : 'template/Register.html',
				controller : 'registerController'
			});
			$stateProvider.state('home', {
				url : '/user/home',
				templateUrl : 'template/Home.html',
				controller : 'homeController'
			});
			$stateProvider.state('forgot', {
				url : '/forgot',
				templateUrl : 'template/ForgotPassword.html',
				controller : 'forgotPassWordController'
			});
			$stateProvider.state('restPassWord', {
				url : '/restPassWord',
				templateUrl : 'template/ResetPassword.html',
				controller : 'restPassWordController'
			});
			$stateProvider.state('trash', {
				url : '/user/trash',
				templateUrl : 'template/Trash.html',
				controller : 'homeController'
			});
			$stateProvider.state('archive', {
				url : '/user/archive',
				templateUrl : 'template/Archive.html',
				controller : 'homeController'
			});
			$urlRouterProvider.otherwise('login');

		} ]);