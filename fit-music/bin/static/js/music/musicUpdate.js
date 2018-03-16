/**
 * 
 */

function musicUpdate(){
	
	var id = $('#id').val();
	var songTitle = $('#songTitle').val();
	var singer = $('#singer').val();
	var albumTitle = $('#albumTitle').val();
	var releaseDate = $('#releaseDate').val();
	var genre = $('#genre').val();
	var singerType = $('#singerType').val();
	var period = parseInt(releaseDate);
	var preferenceTf = $('input:radio[name=preferenceTf]:checked').val();
	var imgPath = $('#imgPath').val();
	
	if(preferenceTf == 'true'){
		preferenceTf = true;
	}else{
		preferenceTf = false;
	}
	
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
	 	url:'/music_update/',
	 	data : JSON.stringify({
	 		id : id,
	 		singer : singer,
	 		songTitle : songTitle,
	 		albumTitle : albumTitle,
	 		releaseDate : releaseDate,
	 		genre : genre,
	 		singerType : singerType,
	 		period : period,
	 		preferenceTf : preferenceTf
	 	}),
	 	dataType: 'JSON',
	 	contentType: 'application/json',
	 	success: function(result){
 			alert('수정되었습니다.');
 			window.location='/music_list?page=1';
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