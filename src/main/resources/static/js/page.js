$(document).ready(function() {
	changePageAndSizeProduto();
});

function changePageAndSizeProduto() {
	$('#pageSizeSelectProduto').change(function(evt) {
		window.location.replace("/produtos/?pageSize=" + this.value + "&page=1");
	});
}
