/**
 * 
 */

function musicUpdate(){
	
	var currentPage = $('#currentPage').val();
	var data = new FormData($('#form')[0]);
	
	$.ajax({
		type:'POST',
	 	url:'/music_update',
	 	data : data,
	 	processData : false,
	 	contentType : false,
	 	success: function(result){
 			alert('수정되었습니다.');
 			window.location='/music_list?page='+currentPage;
	 	},error: function(xhr, status, error){
			alert(status);
	 	}
	});
}

function imgChangeBtnClick() {
	$('#imgChangeBtn').css('display', 'none');
	$('#file').css('display', 'block');
}

/**
 * 이미지 미리보기
 * @param e
 * @returns
 */
function imgChanged(e) {
	var file = e.value;
	$('#albumImg').attr('src', file);
	
	var inputFile = e;
	
	// image 파일만
    if (!inputFile.files[0].type.match(/image\//)) return;

    // preview
    try {
        var reader = new FileReader();
        reader.onload = function(e){
            $('#albumImg').attr('src', e.target.result);
        }
        reader.readAsDataURL(inputFile.files[0]);
    } catch (e) {
        console.log(e);
    }
}

/**
 * 페이지 로드 시 이미지 삽입
 * @returns
 */
function insertImg(){
	
	// img file 있는지 확인
	if($('#imgPath').val()){
		
		// 앨범 이미지 출력하고, 패스 지정
		$('#albumImg').css('display', 'block');
		$('#albumImg').attr('src', $('#imgPath').val());
		
		// 이미지바꾸기 버튼 출력
		$('#imgChangeBtn').css('display', 'block');
	}
}

$(document).ready(function(){
	$( "#releaseDate" ).datepicker({
	    dateFormat: 'yy-mm-dd'
	});
	
	$('#imgChangeBtn').click(function() {
		$('#fileDiv').css('display','block');
		$('#imgChangeBtn').css('display','none');
	});
	
	insertImg();
});