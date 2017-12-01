var toDo = angular.module('ToDoNotes');

toDo.factory('loginService', function($http, $location) {

	var details = {};
	
	details.service = function(method,url,login) {
		return $http({
			method : method,
			url : url,
			data : login
		})
	}
	
	return details;
});