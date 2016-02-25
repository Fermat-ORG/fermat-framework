angular.module("serverApp").controller('ConfigurationCtrl', ['$scope', '$http', '$window',  function ($scope, $http, $window) {

  $scope.configuration = {
       port: '',
       user: '',
       password: '',
       monit:''
  };

  $scope.save = function () {
    $http.post('/fermat/api/admin/configuration/save', angular.toJson($scope.configuration))
      .then(function successCallback(response) {
             alert("success");
       }, function errorCallback(response) {
            var message = "";
            if(response.status === -1){message = "Server no available";}
            if(response.status === 401){message = "You must authenticate again";}
            alert(response.status+" - Service error: "+response.statusText+message);
            $window.location.href = '../index.html';
       });
  };
  
  $scope.get = function () {
      $http.post('/fermat/api/admin/configuration/get', angular.toJson($scope.credentials))
        .then(function successCallback(response) {
                $scope.configuration = response.data;


         }, function errorCallback(response) {
            var message = "";
            if(response.status === -1){message = "Server no available";}
            if(response.status === 401){message = "You must authenticate again";}
            alert(response.status+" - Service error: "+response.statusText+message);
            $window.location.href = '../index.html';
         });
    };

}]);