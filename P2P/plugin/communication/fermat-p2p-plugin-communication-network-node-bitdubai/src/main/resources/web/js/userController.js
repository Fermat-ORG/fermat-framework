angular.module("serverApp").controller('UserCtrl', ['$scope', '$http', '$window',  function ($scope, $http, $window) {

  $scope.credentials = {
       user: '',
       password: ''
  };

  $scope.loginResponse = {
      success: '',
      message: '',
      authToken : ''
  };

  $scope.login = function () {

    $scope.credentials.password = new String(CryptoJS.SHA256($scope.credentials.password));
    $http.post('/fermat/rest/api/v1/user/login', angular.toJson($scope.credentials))
        .then(function successCallback(response) {
            $scope.loginResponse = response.data;

            if($scope.loginResponse.success === true){
              saveToken($scope.loginResponse.authToken);
              $window.location.href = './private/monitoring.html';
            }
         }, function errorCallback(response) {
            var message = "";
            if(response.status === -1){message = "Server no available";}
            if(response.status === 401){message = "You must authenticate again";}
            $scope.loginResponse.message = (response.status+" - Auth Service error : "+response.statusText+" "+message);
         });

  };

  self.parseJwtToken = function(token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace('-', '+').replace('_', '/');
    return JSON.parse($window.atob(base64));
  };

  self.saveToken = function(token) {
    $window.localStorage['jwtAuthToke'] = token;
  };

  self.getToken = function() {
    return $window.localStorage['jwtAuthToke'];
  };

  self.isAuthenticate = function() {
    var token = self.getToken();
    if(token) {
      var params = self.parseJwt(token);
      return Math.round(new Date().getTime() / 1000) <= params.exp;
    } else {
      return false;
    }
  };

  $scope.logout = function() {
    $window.localStorage.removeItem('jwtAuthToke');
    $window.location.href = '../index.html';
  };

}]);