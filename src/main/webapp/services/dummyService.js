var toDo = angular.module('ToDoNotes');

toDo.factory('dummyService', function($http, $location) {
	
var token={};
token.service=function(){
	return $http({
		method:'GET',
		url:'gettoken',
	})
	}
return token;
});