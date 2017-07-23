(function ($) {
    // Telefone Sao Paulo
    var maskBehavior = function (val) {
            return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
        },
        options = {
            onKeyPress: function (val, e, field, options) {
                field.mask(maskBehavior.apply({}, arguments), options);
            }
        };

    $('#telefone').mask(maskBehavior, options);
    $('#cpf').mask('000.000.000-00', {reverse: true});
    $('#cep').mask('00000-000');
    $('#data-nascimento').mask('00/00/0000');
})(jQuery);