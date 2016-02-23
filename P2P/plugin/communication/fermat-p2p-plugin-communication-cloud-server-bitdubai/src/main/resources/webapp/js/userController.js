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

  self.login = function () {
    $http.post('/fermat/api/user/login', angular.toJson($scope.credentials))
      .success(function (data, status, headers, config) {
        $scope.loginResponse = data;
        saveToken($scope.loginResponse.authToken);
        alert(angular.toJson(parseJwtToken($scope.loginResponse.authToken)));
      })
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

  self.logout = function() {
    $window.localStorage.removeItem('jwtAuthToke');
  };

}]);