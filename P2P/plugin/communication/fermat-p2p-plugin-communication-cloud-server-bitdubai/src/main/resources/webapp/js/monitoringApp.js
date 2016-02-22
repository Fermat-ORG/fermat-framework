var app = angular.module("monitoringApp",  ["chart.js"]);

  app.config(function (ChartJsProvider) {
    // Configure all charts
    ChartJsProvider.setOptions({
      colours: ['#97BBCD', '#DCDCDC', '#F7464A', '#46BFBD', '#FDB45C', '#949FB1', '#4D5360'],
      responsive: true
    });
    // Configure all doughnut charts
    ChartJsProvider.setOptions('Doughnut', {
      animateScale: true
    });
  });

app.controller("monitoringCtrl", ['$scope', '$http', '$interval', '$filter', function($scope, $http, $interval, $filter) {

      $scope.labels = [];
      $scope.series = ['Client Connections', 'Actives VPN'];
      $scope.charData = [[],[]];

      var requestMonitoringData = function() {

            $http({
                    method: 'GET',
                    url: 'http://127.0.1.1:9090/fermat/api/monitoring/current/data?callback=JSON_CALLBACK'
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
                  });

      };

     requestMonitoringData();

    $interval(requestMonitoringData, 20000);

}]);