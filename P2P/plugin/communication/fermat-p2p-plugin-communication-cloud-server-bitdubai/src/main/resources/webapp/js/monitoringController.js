angular.module("serverApp").controller("MonitCtrl", ['$scope', '$http', '$interval', '$filter', '$window', '$location',  function($scope, $http, $interval, $filter, $window, $location) {

      $scope.labels = [];
      $scope.series = ['Client Connections', 'Actives VPN'];
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
                alert(response.status+" - Service error: "+response.statusText+" "+message);
                $window.location.href = '../index.html';
           });

      };


      var requestMonitInfo = function() {

              $http({
                      method: 'GET',
                      url: '/fermat/api/admin/monitoring/system/data'
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
                  alert(response.status+" - Service error: "+response.statusText+" "+message);
                  $window.location.href = '../index.html';
             });

       };
 
       $scope.saveplatformcloudserver = function() {

          if ($scope.networkservicetype && $scope.ipserver) {

               $http({
                        method: 'POST',
                        url: '/fermat/api/serverplatform/saveserverconfbyplatform',
                        data: {networkservicetype:$scope.networkservicetype, ipserver:$scope.ipserver},
                        transformRequest: function(obj) {
                            var str = [];
                            for(var p in obj)
                            str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                            return str.join("&");
                        },
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                  }).then(function successCallback(response) {
                      var respond = angular.fromJson(response.data);
                      var success = respond.success;

                      if(success === true){
                          alert(respond.data);
                      }

                  }, function errorCallback(response) {
                      console.log(response);

                  });
             
              $scope.networkservicetype = '';
              $scope.ipserver = '';
          }

       };


   $scope.deletecloudserver = function() {

          if ($scope.idserver) {

               $http({
                        method: 'POST',
                        url: '/fermat/api/serverplatform/deleteserver',
                        data: { idserver:$scope.idserver},
                        transformRequest: function(obj) {
                            var str = [];
                            for(var p in obj)
                            str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                            return str.join("&");
                        },
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                  }).then(function successCallback(response) {
                      var respond = angular.fromJson(response.data);
                      var success = respond.success;

                      if(success === true){
                          alert(respond.data);
			  window.location="monitoring.html";	
                      }

                  }, function errorCallback(response) {
                      console.log(response);

                  });
             
              $scope.networkservicetype = '';
              $scope.ipserver = '';
          }

       };

      
       $http({
             method: 'GET',
             url: '/fermat/api/serverplatform/listplatforms'
            }).then(function successCallback(response) {

            var Aux =   angular.fromJson(response.data);
	        var dataAux =  angular.fromJson(Aux.data);
     	    var arraydata = [];
	        var vectorplatform = [];
     	    var i;
       		  
     	    for(i = 0; i < dataAux.length; i++){

     	        var platform;
                       
                if(dataAux[i] == "ARTIST_ACTOR")
                    platform = "ARTIST";
                         
                if(dataAux[i] == "CRYPTO_BROKER")
                    platform = "CBP";
                        
                if(dataAux[i] == "INTRA_USER")
                    platform = "CCP";
                          
                if(dataAux[i] == "CHAT")
                    platform = "CHAT";
                         
                if(dataAux[i] == "ASSET_USER_ACTOR")
                    platform = "DAP";
                         
                if(dataAux[i] == "FERMAT_MONITOR")
                    platform = "FERMAT-MONITOR";
                         
			    vectorplatform[i] = platform;
     	         
     	    }

            vectorplatform.sort();

            for(i = 0; i < vectorplatform.length; i++){

                var ns;

                if(vectorplatform[i] == "ARTIST")
                   ns = "ARTIST_ACTOR";

                if(vectorplatform[i] == "CBP")
                   ns = "CRYPTO_BROKER";

                if(vectorplatform[i] == "CCP")
                   ns = "INTRA_USER";

                if(vectorplatform[i] == "CHAT")
                   ns = "CHAT";

                if(vectorplatform[i] == "DAP")
                   ns = "ASSET_USER_ACTOR";

                if(vectorplatform[i] == "FERMAT-MONITOR")
                   ns = "FERMAT_MONITOR";

                arraydata.push({ns:ns,platform:vectorplatform[i]});

            }
		  
     		$scope.listplatforms  = arraydata;

           }, function errorCallback(response) {
                   console.log(response);
        });


        

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