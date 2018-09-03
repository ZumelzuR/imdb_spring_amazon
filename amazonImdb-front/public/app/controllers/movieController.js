(function(){
	angular.module('amazonImdb').controller('movieController',['$routeParams','dataService','baseConfiguration','$location' , movieController]);
	
	function movieController($routeParams,dataService,baseConfiguration,$location){
		var vm=this;

		init();

		function init(){
			vm.movieId=$routeParams.id;
			vm.movieDefaultImg=baseConfiguration.IMAGE_PATH+baseConfiguration.MOVIE_DEFAULT_IMG;
			dataService.getMovie(vm.movieId).then(function success(response) {
					vm.image=baseConfiguration.IMAGE_PATH+vm.movieId+".jpg";
			    	vm.name=response.data.name;
			    	vm.year=response.data.year;
			    	vm.description=response.data.description;
			    	vm.actorList=response.data.actorList;
		  	}, function errorCallback(response) {
		  		console.debug(response.status=='404');
		  		if(response.status == '404'){
		  			$location.path('/404');
		  		}else{
		  			$location.path('/unavailable');
		  		}
		  	});
		}
	}
})();