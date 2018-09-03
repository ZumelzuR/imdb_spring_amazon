(function(){
	angular.module('amazonImdb').controller('actorController',['$routeParams','dataService','baseConfiguration','$location',actorController]);

	function actorController($routeParams,dataService,baseConfiguration,$location){
		var vm=this;

		init();

		function init(){
			vm.actorId=$routeParams.id;
			vm.actorDefaultImg=baseConfiguration.IMAGE_PATH+baseConfiguration.ACTOR_DEFAULT_IMG;
			dataService.getActor(vm.actorId).then(function success(response) {
				vm.image=baseConfiguration.IMAGE_PATH+vm.actorId+".jpg";
		    	vm.name=response.data.name;
		    	vm.dateBorn=response.data.dateBorn;
		    	vm.description=response.data.description;
		    	vm.movieList=response.data.movieList;
		  	}, function errorCallback(response) {
		  		console.debug(response.status=='404');
		  		if(response.status=='404'){
		  			$location.path('/404');
		  		}else{
		  			$location.path('/unavailable');
		  		}
		  	});
		}
	}
})();