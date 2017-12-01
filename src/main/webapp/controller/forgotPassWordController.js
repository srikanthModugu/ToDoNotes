var toDo = angular.module('ToDoNotes'); 

toDo.controller('forgotPassWordController', function($scope, forgotPWService,$location){
	$scope.verify = function(){
		var message = forgotPWService.service('POST','forgot',$scope.login);
		message.then(function(response) {
				console.log(response.data);
			
				$location.path('/restPassWord');
			},function(response){
				$scope.error=response.data.message;
				console.log(response.data);
			});
	}

	
});


