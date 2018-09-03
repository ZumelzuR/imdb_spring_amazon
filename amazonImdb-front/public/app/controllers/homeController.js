(function(){
	angular.module('amazonImdb').controller('homeController',['dataService','baseConfiguration','$location', homeController]);
	function homeController(dataService,baseConfiguration,$location){
		var vm=this;
		vm.pageChanged=pageChanged;

		init();

		function init(){
			vm.paginator={
				totalItems:0,
				currentPage:1,
				itemsPerPage:2,
			}
			getTotalMovies();
		  	getMovies();


		}	
		function pageChanged(){
			getMovies();
		}
		function getMovies(){
			dataService.getMovies(vm.paginator.itemsPerPage,vm.paginator.currentPage).then(function success(response) {
					vm.movieList=response.data;
		  	}, function errorCallback(response) {
		  		$location.path('/unavailable');
		  	});
		}
		function getTotalMovies(){
			dataService.getTotalMovies().then(function success(response) {
				vm.paginator.totalItems=response.data.totalMovies;
			}, function errorCallback(response) {
		  		$location.path('/unavailable');
		  	});
		}
	}
})();