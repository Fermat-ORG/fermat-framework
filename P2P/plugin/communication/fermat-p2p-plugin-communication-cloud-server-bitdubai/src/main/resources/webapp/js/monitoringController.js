angular.module("serverApp").controller("MonitoringCtrl", ['$scope', '$http', '$interval', '$filter', '$window', function($scope, $http, $interval, $filter, $window) {

    if(window.localStorage['jwtAuthToke'] !== null){
          $http.defaults.headers.common['Auth-Token'] = $window.localStorage['jwtAuthToke'];
    }

      $scope.labels = [];
      $scope.series = ['Client Connections', 'Actives VPN'];
      $scope.charData = [[],[]];

      var requestMonitoringData = function() {

            $http({
                    method: 'GET',
                    url: '/fermat/api/monitoring/current/data'
                  }).
                  success(function(data){

                     $scope.monitoringData = data;
                     $scope.registeredNetworkServiceDetail = angular.fromJson(data.registeredNetworkServiceDetail);
                     $scope.registerOtherComponentDetail   = angular.fromJson(data.registerOtherComponentDetail);
                     $scope.vpnByNetworkServiceDetails     = angular.fromJson(data.vpnByNetworkServiceDetails);
                     $scope.labels.push($filter('date')(new Date(), 'HH:mm:ss'));
                     $scope.charData[0].push(data.registeredClientConnection);
                     $scope.charData[1].push(data.vpnTotal);

                  }).
                  error(function(data, status, headers, config){
                      console.log('data: ' + data);
                      console.log('status: ' + status);
                      console.log('headers: ' + headers);
                      console.log('config: ' + config);
                      alert(status+" - Service error");
                  });

      };

     requestMonitoringData();

    $interval(requestMonitoringData, 60000);

}]);