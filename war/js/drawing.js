var page = 0;

$(document).ready(function(e){
	if (typeof userId != undefined && typeof drawingId != undefined && userId != -1){
		updateLike();
		$('#btn_post').button().click(function(e){
			$.ajax({
				url : '/do/drawing/comment',
				type : 'POST',
				data : {drawingId : drawingId, title : $('#tb_title').val(), text: $('#tb_comment').val()},
				success: function(data){
					data = jQuery.parseJSON(data);
					if (data.status != 'ok'){
						/* Status Error */
						popFail(data.error.displayMessage);
					} else {
						popSuccess('Posted Comment');
					}
					$('#div_commentList').empty();
					page = 0;
					updateComment();
				},
				error:function (xhr, ajaxOptions, thrownError){
					popFail(SERVER_ERROR);
		        }
			});
		});
	}
	updateComment();
	$('#a_loadMore').click(function(e){
		updateComment();
	});
	updateLikeCount();
});

function updateLike(){
	$.ajax({
		url : '/do/drawing/isliked',
		type : 'POST',
		data : {userId : userId, drawingId : drawingId},
		success: function(data){
			data = jQuery.parseJSON(data);
			if (data.status != 'ok'){
				/* Status Error */
				popFail(data.error.displayMessage);
			} else {
				$('#div_like').empty();
				if (!data.isLiked){
					var btn = $('<div />').html('Like').button({icons: {
						primary: "ui-icon-check"
					}}).click(sendLike).appendTo($('#div_like'));		
				} else {
					var btn = $('<div />').html('Unlike').button({icons: {
						primary: "ui-icon-close"
					}}).click(sendUnlike).appendTo($('#div_like'));
				}
			}
		},
		error:function (xhr, ajaxOptions, thrownError){
			popFail(SERVER_ERROR);
        }
	});
	updateLikeCount();
}

function sendLike(){
	$.ajax({
		url : '/do/drawing/like',
		type : 'POST',
		data : {userId : userId, drawingId : drawingId},
		success: function(data){
			data = jQuery.parseJSON(data);
			if (data.status != 'ok'){
				/* Status Error */
				popFail(data.error.displayMessage);
			} else {
				updateLike();
			}
		},
		error:function (xhr, ajaxOptions, thrownError){
			popFail(SERVER_ERROR);
        }
	});
}

function sendUnlike(){
	$.ajax({
		url : '/do/drawing/unlike',
		type : 'POST',
		data : {userId : userId, drawingId : drawingId},
		success: function(data){
			data = jQuery.parseJSON(data);
			if (data.status != 'ok'){
				/* Status Error */
				popFail(data.error.displayMessage);
			} else {
				updateLike();
			}
		},
		error:function (xhr, ajaxOptions, thrownError){
			popFail(SERVER_ERROR);
        }
	});
}

function updateComment(){
	$.ajax({
		url : '/do/drawing/getComments',
		type : 'POST',
		data : {drawingId : drawingId, start : page, end : page+10, orderBy : 'time', order: 'desc'},
		success: function(data){
			data = jQuery.parseJSON(data);
			if (data.status != 'ok'){
				/* Status Error */
				popFail(data.error.displayMessage);
			} else {
				if (data.comments.length < 10){
					$('#a_loadMore').html('No More Comment');
				}
				$.each(data.comments, function(idx, comment){
					$('<div />').html('<div class="commentTitle">' + comment.title + '</div><b class="commentName">' + comment.user.username + ' : </b><br /><div class="comment">' + comment.text + '</div><hr />').appendTo($('#div_commentList'));
				});
				page += data.comments.length;
			}
		},
		error:function (xhr, ajaxOptions, thrownError){
			popFail(SERVER_ERROR);
        }
	});
}

function updateLikeCount(){
	$.ajax({
		url : '/do/drawing/getLikeCount',
		type : 'POST',
		data : {drawingId : drawingId},
		success: function(data){
			data = jQuery.parseJSON(data);
			if (data.status != 'ok'){
				/* Status Error */
				popFail(data.error.displayMessage);
			} else {
				$('#div_likeCount').html(data.likeCount);
			}
		},
		error:function (xhr, ajaxOptions, thrownError){
			popFail(SERVER_ERROR);
        }
	});
}