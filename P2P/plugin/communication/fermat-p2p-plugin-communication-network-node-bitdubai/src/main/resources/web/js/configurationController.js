angular.module("serverApp").controller('ConfCtrl', ['$scope', '$http', '$window',  function ($scope, $http, $window) {
  $scope.message = '';
  $scope.configuration = {
       port: '',
       user: '',
       password: '',
       monitInstalled: '',
       monitUser: '',
       monitPassword: '',
       monitUrl: ''
  };

  $scope.save = function () {
    $scope.configuration.password = new String(CryptoJS.SHA256($scope.configuration.password))
    $http.post('/fermat/rest/api/v1/admin/configuration/save', angular.toJson($scope.configuration))
      .then(function successCallback(response) {
             $scope.message = response.data;
       }, function errorCallback(response) {
            var message = "";
            if(response.status === -1){message = "Server no available";}
            if(response.status === 401){message = "You must authenticate again";}
            alert(response.status+" - Conf Service error: "+response.statusText+message);
            $window.location.href = '../index.html';
       });
  };
  
  $scope.get = function () {
      $http.get('/fermat/rest/api/v1/admin/configuration/get', angular.toJson($scope.credentials))
        .then(function successCallback(response) {
                $scope.configuration = response.data;
         }, function errorCallback(response) {
            var message = "";
            if(response.status === -1){message = "Server no available";}
            if(response.status === 401){message = "You must authenticate again";}
            alert(response.status+" - Conf  Service error: "+response.statusText+" "+message);
            $window.location.href = '../index.html';
         });
    };

    this.monitInstalled = function () {
        return $scope.configuration.monitInstalled;
    }

    $scope.get();

}]);