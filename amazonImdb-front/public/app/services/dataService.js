(function(){
	angular.module('amazonImdb').service('dataService',['$http','baseConfiguration', function($http,baseConfiguration) {
	    var service = {
	        getActor: getActor,
	        getMovie: getMovie,
	        getMovies:  getMovies,
	        getTotalMovies:getTotalMovies
	    };
	    function getActor(id){
	    	return $http.get(baseConfiguration.HOST + ':' + baseConfiguration.PORT + '/actor/' + id);
	    }
	    function getMovie(id){
	    	return $http.get(baseConfiguration.HOST + ':' + baseConfiguration.PORT + '/movie/' + id);
	    }
	    function getMovies(itemsPerPage,pageNumber){
	    	return $http.get(baseConfiguration.HOST + ':' + baseConfiguration.PORT + '/movies/' + itemsPerPage + "/" +pageNumber);
	    }
	    function getTotalMovies(){
	    	return $http.get(baseConfiguration.HOST + ':' + baseConfiguration.PORT + '/movies/total');
	    }
	    return service;

	}]);
})();