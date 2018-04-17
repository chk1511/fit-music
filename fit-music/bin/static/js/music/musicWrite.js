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
	var preferenceTf = $('input:radio[name=preferenceTf]:checked').val();
	
	var data = new FormData($('#form')[0]);
	
	$.ajax({
		type:'POST',
	 	url:'/music_create',
	 	data : data,
	 	processData : false,
	 	contentType : false,
	 	success: function(result){
	 		if(result){
	 			alert('새로운 음악이 등록되었습니다.');
	 			window.location='music_list';
	 		}
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