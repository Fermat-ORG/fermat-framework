angular.module("serverApp").controller("MonitoringCtrl", ['$scope', '$http', '$interval', '$filter', '$window', function($scope, $http, $interval, $filter, $window) {

    if(window.localStorage['jwtAuthToke'] !== null){
          $http.defaults.headers.common['Authorization'] = "Bearer "+ $window.localStorage['jwtAuthToke'];
    }

      $scope.labels = [];
      $scope.series = ['Client Connections', 'Actives VPN'];
      $scope.charData = [[],[]];

      var requestMonitoringData = function() {

            $http({
                    method: 'GET',
                    url: '/fermat/api/admin/monitoring/current/data'
              }).then(function successCallback(response) {

                      var data = response.data;
                      $scope.monitoringData = data;
                      $scope.registeredNetworkServiceDetail = angular.fromJson(data.registeredNetworkServiceDetail);
                      $scope.registerOtherComponentDetail   = angular.fromJson(data.registerOtherComponentDetail);
                      $scope.vpnByNetworkServiceDetails     = angular.fromJson(data.vpnByNetworkServiceDetails);
                      $scope.labels.push($filter('date')(new Date(), 'HH:mm:ss'));

                      if($scope.charData[0].length > 20){
                        $scope.charData[0].splice(0, 18);
                        $scope.charData[1].splice(0, 18);
                        $scope.labels.splice(0, 18);
                      }

                      $scope.charData[0].push(data.registeredClientConnection);
                      $scope.charData[1].push(data.vpnTotal);

           }, function errorCallback(response) {
                var message = "";
                if(response.status === -1){message = "Server no available";}
                if(response.status === 401){message = "You must authenticate again";}
                alert(response.status+" - Service error: "+response.statusText+message);
                $window.location.href = '../index.html';
           });

      };

     requestMonitoringData();

    $interval(requestMonitoringData, 60000);

}]);