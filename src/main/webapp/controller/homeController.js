var toDo = angular.module('ToDoNotes');
toDo.controller('homeController', function($scope,homeService,$location){
	$scope.signout = function() {

		localStorage.removeItem('token');
		$location.path("/login");
           }
$scope.addNote=function(){
	
	var message= homeService.service('POST','user/addNote',$scope.note);
    console.log("its coming here");

	message.then(function(response) {
		$scope.note="";
	    
		getNotes();
		
		},function(response){
			getNotes();
			$scope.note="";
			console.log("some thing happening");
		});
	
}
var getNotes = function() {

	var notes = homeService.Notes('GET','user/getAllNotes');
	console.log("in this function");
	notes.then(function(response) {
        console.log(response.data);
		$scope.notes = response.data;
	}, function(response) {

		$scope.error = response.data.message;
	});
	 
}
$scope.deleteNote = function(note) {
	console.log("inside something");
	console.log(note);
	note.trash = true;
	
	var notes = homeService.service('POST','user/update' , note);
	notes.then(function(response) {
		console.log("inside the nothing");
		getNotes();

	}, function(response) {
        
		console.log(response.data);
		getNotes();

		$scope.error = response.data;

	});
}

getNotes();
	
});