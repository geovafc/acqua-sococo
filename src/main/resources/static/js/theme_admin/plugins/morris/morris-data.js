// Morris.js Charts sample data for SB Admin template

$(function() {
	
	$.ajax({
		url : "/movimentacoes/countMesAno",
		success : function(data) {
			//$("#movimentacaoHolter").html(data);
			console.log(data);

		    // Area Chart
		    Morris.Area({
		        element: 'morris-area-chart',
		        data,
		        xkey: 'mes',
		        ykeys: ['quantidadeMovimentacoes'],
		        labels: ['Movimentações'],
		        pointSize: 2,
		        hideHover: 'auto',
		        resize: true
		    });
			
		}
	});
	
	

   
});
