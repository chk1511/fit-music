/**
 * 
 */

function musicCreate(){
	
	var songTitle = $('#songTitle').val();
	var singer = $('#singer').val();
	var albumTitle = $('#albumTitle').val();
	var releaseDate = $('#releaseDate').val();
	
	$.ajax({
		type:'POST',
	 	url:'/music_create',
	 	data : JSON.stringify({
	 		singer : singer,
	 		songTitle : songTitle,
	 		albumTitle : albumTitle,
	 		releaseDate : releaseDate
	 	}),
	 	dataType: 'JSON',
	 	contentType: 'application/json',
	 	success: function(result){
 			alert('새로운 음악이 등록되었습니다.');
 			window.location='music_list';
	 	},error: function(xhr, status, error){
			
	 	}
	});
}

$(document).ready(function(){
	$( "#releaseDate" ).datepicker({
	    dateFormat: 'yy-mm-dd'
	});
});