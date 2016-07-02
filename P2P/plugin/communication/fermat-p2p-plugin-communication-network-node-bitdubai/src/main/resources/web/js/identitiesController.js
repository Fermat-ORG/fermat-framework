angular.module("serverApp").controller('IdentitiesCtrl', ['$scope', '$http', '$interval', '$filter', '$window', '$location', function($scope, $http, $interval, $filter, $window, $location) {

       $scope.offSet = 0;
       $scope.max = 24;
       $scope.identities   = [];
       $scope.total        = 0;
       $scope.currentPage  = 1;

      var parseJwtToken = function(token) {
           var base64Url = token.split('.')[1];
           var base64 = base64Url.replace('-', '+').replace('_', '/');
           return JSON.parse($window.atob(base64));
      };

      var isAuthenticate = function() {

         var token = window.localStorage['jwtAuthToke'];
         if(token) {
           var params = parseJwtToken(token);
           return Math.round(new Date().getTime() / 1000) <= params.exp;
         } else {
           return false;
         }

      };

     var requestIdentitiesData = function() {

            $http({
                method: 'GET',
                url: '/fermat/rest/api/v1/admin/actors/check_in?offSet='+$scope.offSet+'&max='+$scope.max
            }).then(function successCallback(response) {

              var data = response.data;
              var success = data.success;
              alert("success = "+success)

              if(success === true){
                $scope.identities   = angular.fromJson(data.identities);
                $scope.total        = data.total;
                $scope.numPages = Math.ceil($scope.totalRows/$scope.max);
              }

           }, function errorCallback(response) {
                var message = "";
                if(response.status === -1){message = "Server no available";}
                if(response.status === 401){message = "You must authenticate again";}
                alert(response.status+" - Identities Service error 1: "+response.statusText+" "+message);
                $window.location.href = '../index.html';
           });

     };

     $scope.nextPage = function() {
         $scope.offSet = parseInt($scope.offSet) + parseInt($scope.max);
         $scope.currentPage = $scope.currentPage + 1;
         requestIdentitiesData();
     };

     $scope.previousPage = function() {
        $scope.offSet = parseInt($scope.offSet) - parseInt($scope.max);
        $scope.currentPage = $scope.currentPage - 1;
        requestIdentitiesData();
     };

     $scope.getImage = function(data){
         return 'data:image/png;base64,' + data.photo;
     }

     if(isAuthenticate() === false){
         alert("Service error: You must authenticate again");
         $location.url('../index.html');
     }else{

         if(window.localStorage['jwtAuthToke'] !== null){
               $http.defaults.headers.common['Authorization'] = "Bearer "+ $window.localStorage['jwtAuthToke'];
         }

         requestIdentitiesData();
     }

}]);
