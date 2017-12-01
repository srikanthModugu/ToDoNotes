var toDo = angular.module('ToDoNotes');
toDo.controller('dummyController',function($location,dummyService){
	var dummyService =dummyService.service();
	dummyService.then(function(response){
		localStorage.setItem('token',response.data.message);
		$location.path('/user/home');
	},function(response){
		$scope.error="some thing went wrong Please Try again!!";
		$location.path('/login');
	});
	dummyService();
});