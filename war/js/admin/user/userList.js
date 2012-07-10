var page = 1;
var order = 'email';
var orderBy = 'asc';

$(document).ready(function(e){
	$('.dropdown-toggle').dropdown();
	$('#tb_search').keyup(updateTable);
	$('#select_pageSize').change(function(e){
		updateTable();
	});
	
	$('#btn_older').click(function(e){
		start -= parseInt($('#select_pageSize').val());
		$('#li_pageNum').html(parseInt($('#li_pageNum').html())-1);
		updateTable();
	});
	$('#btn_newer').click(function(e){
		start += parseInt($('#select_pageSize').val());
		$('#li_pageNum').html(parseInt($('#li_pageNum').html())+1);
		updateTable();
	});
	
	updateTable();
});

function updateTable(){
	var start = (page - 1) * parseInt($('#select_pageSize').val());
	var end = start + parseInt($('#select_pageSize').val());
	$.ajax({
		traditional : true,
		url : '/admin/user/userList.json',
		type : 'POST',
		data : {search : $('#tb_search').val(), start : start, end : end},
		dataType : 'JSON',
		success : function(data){
			$('#table_user tbody').empty();
			
			if (data.status == 'ok'){
				if (data.users.length == 0){
					/************************
					 *  					*
					 *		 No Data		* 
					 *						*
					 ************************/
					var tr = $('<tr />').appendTo($('#table_user tbody'));
					var td = $('<td />').attr({colspan : '3'}).appendTo(tr);
					var div = $('<div />').addClass('').appendTo(td);
					$('<strong />').html('No Matched User').appendTo(div);
				} else {
					/************************
					 *  					*
					 *	  Generate Table 	* 
					 *						*
					 ************************/
					$.each(data.users, function(idx, user){
						var tr = $('<tr />').appendTo($('#table_user tbody'));
						$('<td />').html(user.email).appendTo(tr);
						$('<td />').html(user.username).appendTo(tr);
						$('<td />').html(user.userRole).appendTo(tr);
					});
				}
				$('#span_total').html(data.count);
				/****************************
				 *  						*
				 *	  Generate Pagination 	* 
				 *							*
				 ****************************/
				$('#ul_page').empty();
				var totalPage = Math.ceil(data.count / parseInt($('#select_pageSize').val()));
				
				var li_first = $('<li />').appendTo($('#ul_page'));
				var a_first = $('<a />').html('|<').click(function(e){page = 1; updateTable();}).appendTo(li_first);
				var li_prev = $('<li />').appendTo($('#ul_page'));
				var a_prev = $('<a />').html('<').click(function(e){page -= 1; updateTable();}).appendTo(li_prev);
				if (page == 1){
					li_first.addClass('disabled');
					li_prev.addClass('disabled');
				}
				
				for (var i = 0; i < (totalPage<5?totalPage:5); i++){
					var li = $('<li />').appendTo($('#ul_page'))
					$('<a />').attr({page : i+1}).click(function(e){page = parseInt($(this).attr('page')); updateTable();}).html(i+1).appendTo(li);
					if (page == i+1)
						li.addClass('active');
				}
				
				var li_next = $('<li />').appendTo($('#ul_page'));
				$('<a />').html('>').click(function(e){page += 1; updateTable();}).appendTo(li_next);
				var li_last = $('<li />').appendTo($('#ul_page'));
				$('<a />').html('>|').click(function(e){page = Math.ceil(data.count / parseInt($('#select_pageSize').val())); updateTable();}).appendTo(li_last);
				
				if (page == totalPage){
					li_next.addClass('disabled');
					li_last.addClass('disabled');
				}
				
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
}