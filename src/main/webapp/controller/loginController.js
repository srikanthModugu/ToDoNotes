var toDo = angular.module('ToDoNotes');
toDo.controller('loginController', function($scope, loginService,$location){
	$scope.loginUser = function(){
		var message=loginService.service('POST','login',$scope.login);
		message.then(function(response) {
				console.log(response.data);
				localStorage.setItem('token',response.headers('Authorization'));
				$location.path('/user/home');
			},function(response){
				$scope.error=response.data.message;
				console.log(response.data);
			});
	}
	
});

