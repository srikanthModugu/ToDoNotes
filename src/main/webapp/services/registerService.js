var toDo = angular.module('ToDoNotes');

toDo.factory('registerService', function($http, $location) {
	console.log("in register service");
	var details = {};
	
	details.registerUser = function(user) {
		return $http({
			method : "POST",
			url : 'userRegister',
			data : user
		})
	}
	return details;

});