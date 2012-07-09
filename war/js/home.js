$(document).ready(function(e){
	$.ajax({
		url : '/do/drawing/get',
		type : 'POST',
		data : {start : 0, end : 5, orderBy : 'time', order : 'desc'},
		success: function(data){
			data = jQuery.parseJSON(data);
			if (data.status != 'ok'){
				/* Status Error */
				$('#div_error').html(data.error.displayMessage);
			} else {
				$.each(data.drawings, function(idx, item){
					var li = $('<li />').appendTo($('#ul_lastest'));
					var div = $('<div />').addClass('thumbnail pic_div').appendTo(li);
					$('<span />').addClass('topic white pic_title small').html(item.modelname).appendTo(div);
					var a = $('<a />').attr({href : '/drawing/' + item.drawingId}).appendTo(div);
					$('<img />').addClass('pic_img shadow round').attr({src : '/do/drawing/image?key=' + item.drawingId}).appendTo(a);
				});
				cufon();
			}
		},
		error:function (xhr, ajaxOptions, thrownError){
			$('#div_error').html(SERVER_ERROR);
        }
	});
	
	$.ajax({
		url : '/do/drawing/get',
		type : 'POST',
		data : {start : 0, end : 5, orderBy : 'likeCount', order : 'desc'},
		success: function(data){
			data = jQuery.parseJSON(data);
			if (data.status != 'ok'){
				/* Status Error */
				$('#div_error').html(data.error.displayMessage);
			} else {
				$.each(data.drawings, function(idx, item){
					var li = $('<li />').appendTo($('#ul_mostLiked'));
					var div = $('<div />').addClass('thumbnail pic_div').appendTo(li);
					$('<span />').addClass('pic_title topic white small').html(item.modelname).appendTo(div);
					var a = $('<a />').attr({href : '/drawing/' + item.drawingId}).appendTo(div);
					$('<img />').addClass('pic_img shadow round').attr({src : '/do/drawing/image?key=' + item.drawingId}).appendTo(a);
				});
				cufon();
			}
		},
		error:function (xhr, ajaxOptions, thrownError){
			$('#div_error').html(SERVER_ERROR);
        }
	});
});