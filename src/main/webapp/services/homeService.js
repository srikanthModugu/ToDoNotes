var toDo = angular.module('ToDoNotes');

toDo.factory('homeService', function($http) {
console.log("its coming to service");
var notes ={};
	
	notes.service = function(method,url,note) {
		console.log("inside login service method");
		
		return $http({
			method : method,
			url : url,
			data : note,
			  headers: {
			        'token': localStorage.getItem('token')
	}
		})
	}
	notes.noteCrud = function(method,url) {
		console.log("inside login archive service method");
		
		return $http({
			method : method,
			url : url,
			
			  headers: {
			        'token': localStorage.getItem('token')
	}
		})
	}

	return notes;
});