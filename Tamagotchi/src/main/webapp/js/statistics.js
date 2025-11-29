"use strict";

// Cores para os gráficos
var colors = ['#007bff','#28a745','#333333','#c3e6cb','#dc3545','#6c757d'];

window.onload = loadData;

function loadData(){
	getAnimalStatisticsBySpecies();
	getAnimalStatisticsByBirthDate();
}

// 1. Estatísticas por Espécie (Donut)
function getAnimalStatisticsBySpecies(){
	// Ajustado para o Servlet de Animais
	const url = "animalStatistics?category=bySpecies";

	fetch(url)
	.then(response =>{
		return response.json();
	})
	.then(datalist =>{
	   	setChartDonut(datalist);
	})
	.catch(error => console.log('Erro de solicitação', error));
}

// 2. Estatísticas por Data de Nascimento (Barra)
function getAnimalStatisticsByBirthDate(){
	// Ajustado para o Servlet de Animais
	const url = "animalStatistics?category=byBirthDate";

	fetch(url)
	.then(response =>{
		return response.json();
	})
	.then(datalist =>{
	   	setChartBar(datalist);
	})
	.catch(error => console.log('Erro de solicitação', error));
}

function setChartDonut(datalist){
	var donutOptions = {
	  cutoutPercentage: 85, 
	  legend: {position:'bottom', padding:5, labels: {pointStyle:'circle', usePointStyle:true}}
	};
	
	// Prepara os dados: species -> labels, count -> data
	var chDonutData1 = {
	    labels: datalist.map(data => data.species),
	    datasets: [
	      {
	        backgroundColor: colors.slice(0,3),
	        borderWidth: 0,
	        data: datalist.map(data => data.count)
	      }
	    ]
	};
	
	var chDonut1 = document.getElementById("chDonut1");
	if (chDonut1) {
	  new Chart(chDonut1, {
	      type: 'pie',
	      data: chDonutData1,
	      options: donutOptions
	  });
	}
}

function setChartBar(datalist){
	var chBar = document.getElementById("chBar");
	if (chBar) {
	  new Chart(chBar, {
	  type: 'bar',
	  data: {
	    // Formata a data de nascimento para exibir no eixo X
	    labels: datalist.map(data => setDateFormat(data.date)),
	    datasets: [{
	      // Mapeia a contagem de nascimentos
	      data: datalist.map(data => data.count),
	      backgroundColor: colors[0],
	      label: 'Quantidade' // Adicionei label para tooltip ficar claro
	    }]
	  },
	  options: {
	    legend: {
	      display: false
	    },
	    scales: {
	      yAxes: [{
	          ticks: {
	              beginAtZero: true, // Garante que comece do 0
	              stepSize: 1        // Garante números inteiros (não existe 1.5 animal)
	          }
	      }],
	      xAxes: [{
	        barPercentage: 0.4,
	        categoryPercentage: 0.5
	      }]
	    }
	  }
	  });
	}
}

function setDateFormat(date){
	const dateObjet = new Date(date);
	const formattedDate = dateObjet.toLocaleDateString('pt-BR', {
  		timeZone: 'UTC',
	});
 	return formattedDate;
}/**
 * 
 */