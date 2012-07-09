$(document).ready(function(e){
	$('#form_register').ajaxForm({
		dataType : 'json',
		success : function(data){
			if (data.status == 'ok'){
				var div_error = $('#div_error').removeClass('hide').empty();;
				var div = $('<div />').addClass('alert alert-success').appendTo(div_error);
				var a_close = $('<a data-dismiss="alert"/>').attr({href : '#'}).addClass('close').html('x').appendTo(div);
				$('<strong />').attr({id : 'strong_error'}).html('Success ').appendTo(div);
				$('<span />').attr({id : 'span_error'}).html('Account Created.').appendTo(div);
				form_clear('#form_register');
			} else {
				var div_error = $('#div_error').removeClass('hide').empty();
				var div = $('<div />').addClass('alert alert-error').appendTo(div_error);
				var a_close = $('<a data-dismiss="alert"/>').attr({href : '#'}).addClass('close').html('x').appendTo(div);
				$('<strong />').attr({id : 'strong_error'}).html(SERVER_ERROR_TITLE).appendTo(div);
				$('<span />').attr({id : 'span_error'}).html(data.error.displayMessage).appendTo(div);
				$('#tb_password, #tb_confirmPassword').val('');
			}
		},
		error : function(data){
			var div_error = $('#div_error').removeClass('hide').empty();;
			var div = $('<div />').addClass('alert alert-error').appendTo(div_error);
			var a_close = $('<a data-dismiss="alert"/>').attr({href : '#'}).addClass('close').html('x').appendTo(div);
			$('<strong />').attr({id : 'strong_error'}).html(SERVER_ERROR_TITLE).appendTo(div);
			$('<span />').attr({id : 'span_error'}).html(SERVER_ERROR).appendTo(div);
			$('#tb_password, #tb_confirmPassword').val('');
		}
	});
});

function form_clear(ele) {

    $(ele).find(':input').each(function() {
        switch(this.type) {
            case 'password':
            case 'select-multiple':
            case 'select-one':
            case 'text':
            case 'textarea':
                $(this).val('');
                break;
            case 'checkbox':
            case 'radio':
                this.checked = false;
        }
    });

}
