angular.module("serverApp").controller('ConfCtrl', ['$scope', '$http', '$window', '$timeout', 'NgMap', function ($scope, $http, $window, $timeout, NgMap) {
  $scope.message = '';
  $scope.pauseLoading=true;
  $scope.configuration = {
         ipk: '',
         nodeName: '',
         internalIp: '',
         publicIp: '',
         latitude: '',
         longitude: '',
         port: '',
         user: '',
         password: '',
         monitInstalled: '',
         monitUser: '',
         monitPassword: '',
         monitUrl: '',
         googleMapApiKey: '',
         registerInCatalog: '',
         lastRegisterNodeProfile: ''
    };

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

  $scope.save = function () {
    $scope.configuration.password = new String(CryptoJS.SHA256($scope.configuration.password))
    $scope.busy = $http.post('/fermat/rest/api/v1/admin/configuration/save', angular.toJson($scope.configuration))
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
      $scope.busy = $http.get('/fermat/rest/api/v1/admin/configuration/get', angular.toJson($scope.credentials))
        .then(function successCallback(response) {
                $scope.configuration = response.data;
                $scope.googleMapsUrl = 'https://maps.googleapis.com/maps/api/js?v=3&amp;key='+$scope.configuration.googleMapApiKey;

                if($scope.configuration.latitude != 0 && $scope.configuration.longitude != 0){

                  $scope.geoMark = new google.maps.Marker({
                                                          position: new google.maps.LatLng(parseFloat($scope.configuration.latitude), parseFloat($scope.configuration.longitude)),
                                                          title: $scope.configuration.nodeName,
                                                          animation: google.maps.Animation.DROP
                                                         });

                 NgMap.getMap().then(function(map) {
                             $timeout(function() {
                                             $scope.geoMark.setMap(map);
                                        }, 300);
                 });

                 $timeout(function() {
                     console.debug("Showing the map. The google maps api should load now.");
                     $scope.pauseLoading=false;
                 }, 2000);

             }

         }, function errorCallback(response) {
            var message = "";
            if(response.status === -1){message = "Server no available";}
            if(response.status === 401){message = "You must authenticate again";}
            if(response.status === 500){message = "Server Error";}

            alert(response.status+" - Conf  Service error: "+response.statusText+" "+message);
            $window.location.href = '../index.html';
         });
    };

    this.monitInstalled = function () {
        return $scope.configuration.monitInstalled;
    }

    if(isAuthenticate() === false){
        alert("Service error: You must authenticate again");
        $window.location.href = '../index.html';
    }else{

        if(window.localStorage['jwtAuthToke'] !== null){
           $http.defaults.headers.common['Authorization'] = "Bearer "+ $window.localStorage['jwtAuthToke'];
        }
        $scope.get();
    }

}]);