var app = angular.module("serverApp",  ["ngRoute", "ngMap", "chart.js"]);

 app.config(function ($locationProvider, ChartJsProvider) {

    $locationProvider.html5Mode({
                                   enabled: true,
                                   requireBase: false,
                                   rewriteLinks: false
                                 });

    // Configure all charts
    ChartJsProvider.setOptions({
      colours: ['#FAAC58', '#D0FA58'],
      responsive: true
    });

    // Configure all doughnut charts
    ChartJsProvider.setOptions('Doughnut', {
      animateScale: true
    });

 });