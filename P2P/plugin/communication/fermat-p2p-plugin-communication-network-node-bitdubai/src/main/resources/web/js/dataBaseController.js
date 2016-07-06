angular.module("serverApp").controller('DataBaseCtrl', ['$scope', '$http', '$interval', '$filter', '$window', '$location',  function($scope, $http, $interval, $filter, $window, $location) {

       $scope.tableList = [];

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

    var requestDataBaseTableList = function() {

           $http({
               method: 'GET',
               url: '/fermat/rest/api/v1/admin/databases/list'

           }).then(function successCallback(response) {

              var data = response.data;
              $scope.tableList = angular.fromJson(data.list);

           }, function errorCallback(response) {
                var message = "";
                if(response.status === -1){message = "Server no available";}
                if(response.status === 401){message = "You must authenticate again";}
                alert(response.status+" - Monitoring Service error 1: "+response.statusText+" "+message);
                 $window.location.href = '../index.html';
           });

      };

     if(isAuthenticate() === false){
         alert("Service error: You must authenticate again");
         $location.url('../index.html');
     }else{

         if(window.localStorage['jwtAuthToke'] !== null){
               $http.defaults.headers.common['Authorization'] = "Bearer "+ $window.localStorage['jwtAuthToke'];
         }

         requestDataBaseTableList();
     }

}]);