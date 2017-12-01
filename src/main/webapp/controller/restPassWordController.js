var toDo = angular.module('ToDoNotes'); 

toDo.controller('restPassWordController', function($scope, resetPWService,$location){
	$scope.reset = function(){
		var message = resetPWService.service('POST','resetPassword',$scope.login);
		message.then(function(response) {
				console.log(response.data);
				
				$location.path('/login');
			},function(response){
				$scope.error=response.data.message;
				console.log(response.data);
			});
	}
});