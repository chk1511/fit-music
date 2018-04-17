var preferenceList = [];

/**
 * 별점 추가
 * @param star
 * @returns
 */
function initStar(star) {
	star.raty({
		// score : 0,
		half : true,
		starOff : 'img/preference/star-off.png',
		starOn : 'img/preference/star-on.png',
		starHalf : 'img/preference/star-half.png',
		hints : [ '별로', '그럭저럭', '보통', '좋음', '최고' ],
		click : function(score, evt) {
			
			// 리스트에 5개 이상이면 리턴
			if(preferenceList.length >= 5){
				alert('최대 5개까지만 선택 가능합니다!');
				return;
			}
			
			// 이미지 흐린 커버 없앰
			var img = this.previousElementSibling;
			img.style.opacity = 1;
			
			// 점수 얻어서 아이디와 함께 데이터로 묽음
			var rate = Math.ceil(score * 2) / 2;
			var data = {
					musicId : img.getAttribute('id'),
					score : rate
			};
			
			// 이미 리스트에 있는 지 확인
			var check = false;
		
			preferenceList.some((item) => {
				if(item.musicId === data.musicId){
					check = true;
					item.score = data.score;
					alert('점수를 업데이트하였습니다!');
					return check;
				}
			});
			
			// 같은 음악이 없으면 새로 넣어줌
			if(!check){
				preferenceList.push(data);
				
				alert('새로운 음악을 선택하였습니다!');
			}

		}//click
	});
}

/**
 * 완료 버튼 클릭
 * @returns
 */
function finishBtn() {
	
	$.ajax({
		type : "POST",
		url:'/preference_create',
		data : JSON.stringify(preferenceList),
		dataType: 'JSON',
	 	contentType: 'application/json',
		success : function(result) {
			alert('평가가 저장되었습니다.');
		},
		error : function(xhr, status, error) {
			alert(status);
		}
	});
}

$(document).ready(function(){
	initStar($('.star'));
	
	$('#cancelBtn').on('click', function() {
		var img = $(this).siblings('.albumImg');
		alert(img);
	});
	
	$('.finishBtn').on('click', function() {
		finishBtn();
	});
});