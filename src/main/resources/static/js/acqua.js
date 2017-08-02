$(function() {
	$('#dataHora').datetimepicker({
		format: "DD/MM/YYYY HH:mm:ss"
		
	});
});

$('.fileinput').fileinput();

$('[rel="tooltip"]').tooltip();

$('#confirmacaoExclusaoModal').on(
		'show.bs.modal',
		function(event) {
			var button = $(event.relatedTarget);

			var codigo = button.data('id');
			var nome = button.data('nome');

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

// INICIO JAVASCRIPT RELACIONADOS A MOVIMENTACAO
function obterProdutoPorCodigo(codigo) {
	$.ajax({
		url : "/movimentacoes/pesquisar/codigo/" + codigo,
		success : function(data) {
			$("#movimentacaoHolter").html(data);
		}
	});
};

// FIM DOS JAVASCRIPTS RELACIONADOS A MOVIMENTACAO
