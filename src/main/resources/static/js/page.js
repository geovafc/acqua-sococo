$(document).ready(function() {
	changePageAndSizeProduto();
	changePageAndSizeMovimentacoes();
	changePageAndSizeUsuarios();
});

function changePageAndSizeProduto() {
	$('#pageSizeSelectProduto').change(function(evt) {
		window.location.replace("/produtos/?pageSize=" + this.value + "&page=1");
	});
}
function changePageAndSizeMovimentacoes() {
	$('#pageSizeSelectMovimentacoes').change(function(evt) {
		window.location.replace("/movimentacoes/?pageSize=" + this.value + "&page=1");
	});
}
function changePageAndSizeUsuarios() {
	$('#pageSizeSelectUsuarios').change(function(evt) {
		window.location.replace("/usuarios/?pageSize=" + this.value + "&page=1");
	});
}
