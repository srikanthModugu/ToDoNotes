var toDo = angular.module('ToDoNotes');
toDo.controller('homeController',
		function($scope,$state, homeService, $location) {
           
	
	if(localStorage.getItem('token')==null){
		$location.path('login');
	}
	
	if ($state.current.name == "home"){
		$scope.navBg="#FFB42E";
		$scope.navName="Fundoo Notes";
	}
	if ($state.current.name == "trash"){
		$scope.navBg="#636363";
		$scope.navName="Trash";
	}
	if ($state.current.name == "archive"){
		$scope.navBg="#607D8B";
		$scope.navName="Archive";
	}
	   $scope.noteFilter = null;
	   
	    $scope.searchFilter = function (note) {
	        var re = new RegExp($scope.nameFilter, 'i');
	        return !$scope.nameFilter || re.test(note.title) || re.test(note.description);
	    };
			$scope.showSidebar = function() {
				if ($scope.width == '0px') {
					$scope.width = '230px';
					$scope.mleft = "200px";
				} else {
					$scope.width = '0px';
					$scope.mleft = "0px";
				}
			}
                     /* Sign out  */
			$scope.signout = function() {

				localStorage.removeItem('token');
				$location.path("/login");
			}
			
			
			$scope.newnote = false;

			$scope.show = function() {
				$scope.newnote = true;
			}

			$scope.hide = function() {
				$scope.newnote = false;
			}
			
			
			        /* Adding note */

			$scope.addNote = function() {
				if ($scope.note.title != '' && $scope.note.description != '') {
					var message = homeService.service('POST', 'user/addNote',
							$scope.note);

					message.then(function(response) {
						$scope.note = "";

						getNotes();

					}, function(response) {
						getNotes();
						$scope.note = "";

					});
				}
			}
               
			            /*Getting the All notes   */
			
			var getNotes = function() {

				var notes = homeService.noteCrud('GET', 'user/getAllNotes');
				console.log("in this function");
				notes.then(function(response) {
					console.log(response.data);
					$scope.notes = response.data;
				}, function(response) {
					console.log("in get notes");
					$location.path('login');
					$scope.error = response.data.message;
				});
				if(notes==null){
					$location.path('login');
				}

			}
                  
			
			
		       /*updating note to trash*/

			$scope.updateTotrash = function(noteId) {

				var notes = homeService.noteCrud('PUT', 'user/updateTotrash/'
						+ noteId);
				notes.then(function(response) {

					getNotes();

				}, function(response) {

					console.log(response.data);
					getNotes();

					$scope.error = response.data;

				});
			}
                 
			
			 /*updating note to Archive*/
			$scope.addToArchive = function(noteId) {

				var notes = homeService.noteCrud('PUT', 'user/updateToArchive/'
						+ noteId);
				notes.then(function(response) {
					console.log("hai");
					getNotes();

				}, function(response) {

				
					getNotes();

					

				});
			}
			
			
			 /*Deleting Note permanently*/
			
			$scope.deleteForever = function(noteId) {

				var notes = homeService.noteCrud('DELETE', 'user/deleteNote/'
						+ noteId);
				notes.then(function(response) {
			
					getNotes();

				}, function(response) {

					console.log(response.data);
					getNotes();

					$scope.error = response.data;

				});
			}

			
			
			 /*updating trashed-note to Notes*/
			$scope.restoreToNotes = function(note) {

				note.trash = false;

				var notes = homeService.service('POST', 'user/update', note);
				notes.then(function(response) {

					getNotes();

				}, function(response) {

					console.log(response.data);
					getNotes();

					$scope.error = response.data;

				});
			}
			 /*updating trashed-note to Notes*/
			$scope.emptyTrash = function() {

	

				var notes = homeService.noteCrud('DELETE', 'user/emptyTrash');
				notes.then(function(response) {

					getNotes();

				}, function(response) {

					console.log(response.data);
					getNotes();

					$scope.error = response.data;

				});
			}
			
			
			getNotes();

		});