//quando carregar a pagina excuta essa função
$(function(){
	$('.fileinput').fileinput();
	
	$('[rel="tooltip"]').tooltip();
	
	$('#dataTable') //.DataTable();
	  
 });


$('#confirmacaoExclusaoModal').on(
		'show.bs.modal',
		function(event) {
			var button = $(event.relatedTarget);

			var codigo = button.data('id');
			var nome = button.data('nome');
			// sempre quando o nome for undefined, é por que está sendo
			// chamado o dialog para excluir movimentação
			if (nome == undefined) {
				nome = "a movimentação";
			}

			var modal = $(this);
			var form = modal.find('form');
			var action = form.data('url-base');
			if (!action.endsWith('/')) {
				action += '/';
			}

			form.attr('action', action + codigo);
			
			modal.find('.modal-body span').html(
					'Tem certeza que deseja excluir <strong>' + nome
							+ '</strong>?');
		});

$('#confirmacaoEnabledModal').on(
		'show.bs.modal',
		function(event) {
			var button = $(event.relatedTarget);

			var codigo = button.data('id');
			var nome = button.data('nome');
			// sempre quando o nome for undefined, é por que está sendo
			// chamado o dialog para excluir movimentação
			if (nome == undefined) {
				nome = "produto";
			}

			var modal = $(this);
			var form = modal.find('form');
			var action = form.data('url-base');
			if (!action.endsWith('/')) {
				action += '/';
			}

			form.attr('action', action + codigo);
			
			console.log("URL: " + action + codigo);

			modal.find('.modal-body span').html(
					'Tem certeza que deseja excluir o produto <strong>' + nome
							+ '</strong>?');
		});

// INICIO JAVASCRIPT RELACIONADOS A MOVIMENTACAO
function obterProdutoPorCodigo(codigo) {
	$.ajax({
		url : "/movimentacoes/registrar/codigo/" + codigo,
		success : function(data) {
			$("#movimentacaoHolter").html(data);
		}
	});
};



/* Este método foi colocado dentro da função function no inicio.
//Call the dataTables jQuery plugin
$(document).ready(function() {
	$('#dataTable').DataTable();	
});
*/



