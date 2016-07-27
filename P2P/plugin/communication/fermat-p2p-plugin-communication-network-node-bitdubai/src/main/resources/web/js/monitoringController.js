angular.module("serverApp").controller("MonitCtrl", ['$scope', '$http', '$interval', '$filter', '$window', '$location',  function($scope, $http, $interval, $filter, $window, $location) {

      $scope.labels = [];
      $scope.series = ['Client Connections', 'Identities'];
      $scope.charData = [[],[]];
      $scope.monitInfo = [];

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

      var requestMonitoringData = function() {

            $http({
                    method: 'GET',
                    url: '/fermat/rest/api/v1/admin/monitoring/current/data'
              }).then(function successCallback(response) {

                      var data = response.data;
                      $scope.monitoringData = data;
                      $scope.registeredNetworkServiceDetail = angular.fromJson(data.registeredNetworkServiceDetail);
                      $scope.registerActorsDetail   = angular.fromJson(data.registerActorsDetail);
                      $scope.labels.push($filter('date')(new Date(), 'HH:mm:ss'));

                      if($scope.charData[0].length > 20){
                        $scope.charData[0].splice(0, 18);
                        $scope.charData[1].splice(0, 18);
                        $scope.labels.splice(0, 18);
                      }

                      $scope.charData[0].push(data.registeredClientConnection);
                      $scope.charData[1].push($scope.registerActorsDetail);

           }, function errorCallback(response) {
                var message = "";
                if(response.status === -1){message = "Server no available";}
                if(response.status === 401){message = "You must authenticate again";}
                alert(response.status+" - Monitoring Service error 1: "+response.statusText+" "+message);
                 $window.location.href = '../index.html';
           });

      };


      var requestMonitInfo = function() {

              $http({
                      method: 'GET',
                      url: '/fermat/rest/api/v1/admin/monitoring/system/data'
                }).then(function successCallback(response) {
                        var respond = angular.fromJson(response.data);
                        var success = respond.success;

                        if(success === true){

                            var data    = angular.fromJson(respond.data)
                            var array = [];
                            for(var key in data){
                                var test = {};
                                test[key] = angular.fromJson(data[key]);
                                array.push(test);
                            }

                            $scope.monitInfo = array;
                        }

             }, function errorCallback(response) {
                  var message = "";
                  if(response.status === -1){message = "Server no available";}
                  if(response.status === 401){message = "You must authenticate again";}
                  alert(response.status+" - Monitoring Service error 2: "+response.statusText+" "+message);
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

            requestMonitoringData();
            requestMonitInfo();
            $interval(requestMonitoringData, 30000);
            $interval(requestMonitInfo, 30000);
        }


}]);