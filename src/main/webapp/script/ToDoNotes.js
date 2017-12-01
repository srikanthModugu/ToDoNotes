var toDoNotes = angular.module('ToDoNotes', [ 'ui.router']);

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
		$urlRouterProvider.otherwise('login');
		
}]);