$(document).ready(function(e){
	$('#btn_report').click(function(e){
		$.ajax({
			url : '/error/log.json',
			type : 'POST',
			data : {s_log : s_log},
			dataType : 'JSON',
			success : function(data){
				if (data.status == 'ok'){
					$('#div_success').addClass('div_center');
					$('#div_show').flipContent($('#div_success'));
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
});
