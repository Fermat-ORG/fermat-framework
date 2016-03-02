var app = angular.module("serverApp",  ["chart.js"]);

 app.config(function (ChartJsProvider) {

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