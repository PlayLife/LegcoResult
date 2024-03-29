$(document).ready(function(e){
	$('#form_login').ajaxForm({
		dataType : 'json',
		success : function(data){
			if (data.status == 'ok'){
				$('#div_login_success').addClass('div_center');
				$('#form_login').flipContent($('#div_login_success'));
				setTimeout('window.location = "/"', 3000);
			} else {
				var div_error = $('#div_error').removeClass('hide').empty();;
				var div = $('<div />').addClass('alert alert-error').appendTo(div_error);
				var a_close = $('<a data-dismiss="alert"/>').attr({href : '#'}).addClass('close').html('x').appendTo(div);
				$('<strong />').attr({id : 'strong_error'}).html(SERVER_ERROR_TITLE).appendTo(div);
				$('<span />').attr({id : 'span_error'}).html(data.error.displayMessage).appendTo(div);
				$('<a />').addClass('pull-right').click(function(e){
					postToErrorPage(data.error);
				}).attr({href : '#'}).html('Details').appendTo(div);
			}
		},
		error : function(data){
			var div_error = $('#div_error').removeClass('hide').empty();;
			var div = $('<div />').addClass('alert alert-error').appendTo(div_error);
			var a_close = $('<a data-dismiss="alert"/>').attr({href : '#'}).addClass('close').html('x').appendTo(div);
			$('<strong />').attr({id : 'strong_error'}).html(SERVER_ERROR_TITLE).appendTo(div);
			$('<span />').attr({id : 'span_error'}).html(SERVER_ERROR).appendTo(div);
		}
	});
});