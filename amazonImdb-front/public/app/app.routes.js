
(function(){
  angular.module('amazonImdb').config(['$routeProvider',function($routeProvider) {
    $routeProvider
    .when("/", {
      templateUrl : "app/views/home.html",
      controller: 'homeController',
      controllerAs: 'vm'
    })
    .when("/actor/:id", {
      templateUrl : "app/views/actor.html",
      controller: 'actorController',
      controllerAs: 'vm'
    })
    .when("/movie/:id", {
      templateUrl : "app/views/movie.html",
      controller: 'movieController',
      controllerAs: 'vm'
    }).when("/404", {
      templateUrl : "app/views/404.html"
    }).when("/unavailable", {
      templateUrl : "app/views/unavailable.html"
    }).otherwise({
          redirectTo: '/'
    });
  }]);
})();