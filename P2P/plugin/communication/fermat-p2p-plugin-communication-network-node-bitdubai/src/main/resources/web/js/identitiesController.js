angular.module("serverApp").controller('IdentitiesCtrl', ['$scope', '$http', '$interval', '$filter', '$window', '$location', '$timeout', 'NgMap', function($scope, $http, $interval, $filter, $window, $location, $timeout, NgMap) {

      $scope.onlineIdentities = false;
      $scope.offSet      = 0;
      $scope.max         = 20;
      $scope.total       = 0;
      $scope.currentPage = 1;
      $scope.identities  = [];
      $scope.geoPoints   = [];
      $scope.markers     = [];

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

     var requestIdentitiesData = function() {
        console.log("online = "+$scope.onlineIdentities);
        $scope.total       = 0;
        $scope.identities.splice(0, $scope.identities.length);
        clearMarkers();
        if($scope.onlineIdentities === true){
            requestCheckInData();
        }else{
            requestCatalogData();
        }
     }

     var requestCatalogData = function() {

        $http({
            method: 'GET',
            url: '/fermat/rest/api/v1/admin/actors/catalog?offSet='+$scope.offSet+'&max='+$scope.max
        }).then(function successCallback(response) {

          var data = response.data;
          var success = data.success;

          if(success === true){

            angular.forEach(angular.fromJson(data.identities), function(value, key) {

              var identity = angular.fromJson(value);
              $scope.identities.push(identity);

              var location = angular.fromJson(identity.location);
              if(location.latitude != 0 && location.longitude != 0){

                   $scope.markers.push(new google.maps.Marker({
                                                              position: new google.maps.LatLng(parseFloat(location.latitude), parseFloat(location.longitude)),
                                                              title: identity.name,
                                                              animation: google.maps.Animation.DROP
                                                            }));

              }

            });

            $scope.total        = data.total;
            $scope.numPages = Math.ceil($scope.total/$scope.max);
            addMarkers();
          }

       }, function errorCallback(response) {
            var message = "";
            if(response.status === -1){message = "Server no available";}
            if(response.status === 401){message = "You must authenticate again";}
            alert(response.status+" - Identities Service error 1: "+response.statusText+" "+message);
            $window.location.href = '../index.html';
       });

     };

     var requestCheckInData = function() {

         $http({
             method: 'GET',
             url: '/fermat/rest/api/v1/admin/actors/check_in?offSet='+$scope.offSet+'&max='+$scope.max
         }).then(function successCallback(response) {

             var data = response.data;
             var success = data.success;

             if(success === true){

               angular.forEach(angular.fromJson(data.identities), function(value, key) {

                 var identity = angular.fromJson(value);
                 $scope.identities.push(identity);

                 var location = angular.fromJson(identity.location);
                 if(location.latitude != 0 && location.longitude != 0){

                      $scope.markers.push(new google.maps.Marker({
                                                                 position: new google.maps.LatLng(parseFloat(location.latitude), parseFloat(location.longitude)),
                                                                 title: identity.name,
                                                                 animation: google.maps.Animation.DROP
                                                               }));

                 }

               });

               $scope.total        = data.total;
               $scope.numPages = Math.ceil($scope.total/$scope.max);
               addMarkers();
             }

        }, function errorCallback(response) {
             var message = "";
             if(response.status === -1){message = "Server no available";}
             if(response.status === 401){message = "You must authenticate again";}
             alert(response.status+" - Identities Service error 1: "+response.statusText+" "+message);
             $window.location.href = '../index.html';
        });

     };

     $scope.nextPage = function() {
         $scope.offSet = parseInt($scope.offSet) + parseInt($scope.max);
         $scope.currentPage = $scope.currentPage + 1;
         requestIdentitiesData();
     };

     $scope.previousPage = function() {
        $scope.offSet = parseInt($scope.offSet) - parseInt($scope.max);
        $scope.currentPage = $scope.currentPage - 1;
        requestIdentitiesData();
     };

     $scope.getImage = function(photo){

        if(photo){
            return 'data:image/JPEG;base64,' + photo;
        }else {
            return 'https://raw.githubusercontent.com/Fermat-ORG/media-kit/master/MediaKit/Fermat%20Branding/Fermat%20Logotype/Fermat_Logo_3D.png';
        }

     }

     var clearMarkers = function() {
       for (var i = 0; i < $scope.markers.length; i++) {
         $scope.markers[i].setMap(null);
       }
       $scope.markers = [];
     }

     var addMarkers = function() {
         NgMap.getMap().then(function(map) {
            for (var i=0; i < $scope.markers.length; i++) {
               addMarkerWithTimeout($scope.markers[i],  i * 200, map);
            }
         });
     }

      var addMarkerWithTimeout = function (mark, timeout, map) {
           $timeout(function() {
                mark.setMap(map);
           }, timeout);
     }


     if(isAuthenticate() === false){
         alert("Service error: You must authenticate again");
         $location.url('../index.html');
     }else{

         if(window.localStorage['jwtAuthToke'] !== null){
               $http.defaults.headers.common['Authorization'] = "Bearer "+ $window.localStorage['jwtAuthToke'];
         }

         requestIdentitiesData();
     }


       $scope.reloadOnlineChange = function() {
         requestIdentitiesData();
       };

}]);
