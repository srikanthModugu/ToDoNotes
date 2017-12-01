var toDo = angular.module('ToDoNotes');
toDo.controller('registerController', function($scope, registerService,$location){
	console.log("in register controller");
	$scope.registerUser = function(){
		var a=registerService.registerUser($scope.user,$scope.error);
			a.then(function(response) {
				console.log(response.data);
				$location.path('/login')
			},function(response){
				$scope.error=response.data;
			});
	}
});