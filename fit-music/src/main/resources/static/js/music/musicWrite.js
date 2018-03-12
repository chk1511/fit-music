/**
 * 
 */

function musicCreate(){
	
	var songTitle = $('#songTitle').val();
	var singer = $('#singer').val();
	var albumTitle = $('#albumTitle').val();
	var releaseDate = $('#releaseDate').val();
	var genre = $('#genre').val();
	var singerType = $('#singerType').val();
	var period = parseInt(releaseDate);
	
	// 시대 분기
	if(period >= 2010){
		period = 2010;
	}else if(period < 2010 && period >= 2000){
		period = 2000;
	}else if(period < 2000 && period >= 1990){
		period = 1990;
	}else{
		period = 1980;
	}
	
	$.ajax({
		type:'POST',
	 	url:'/music_create',
	 	data : JSON.stringify({
	 		singer : singer,
	 		songTitle : songTitle,
	 		albumTitle : albumTitle,
	 		releaseDate : releaseDate,
	 		genre : genre,
	 		singerType : singerType,
	 		period : period
	 	}),
	 	dataType: 'JSON',
	 	contentType: 'application/json',
	 	success: function(result){
 			alert('새로운 음악이 등록되었습니다.');
 			window.location='music_list';
	 	},error: function(xhr, status, error){
			alert(status);
	 	}
	});
}

$(document).ready(function(){
	$( "#releaseDate" ).datepicker({
	    dateFormat: 'yy-mm-dd'
	});
});