
/**
 * 아이디 중복검사
 * @returns
*/
function idCheck(){
	 
	var inputId = $('#writeId').val();
	
	$.ajax({
		type : 'GET',
		url : '/id_check/'+inputId,
		success : function(result) {
			if (result) {
				$('#inputId').val(inputId);
				$('#checkResult').val('사용 가능한 아이디입니다.');
			} else {
				$('#checkResult').val('이미 가입되어 있는 아이디입니다.');
			}
		},
		error : function(xhr, status, error) {
			alert('회원 가입 중 오류가 발생하였습니다.');
		}
	});
}
 
/**
 * 회원 가입 통신
 * @param inputId
 * @param inputName
 * @param inputPass
 * @returns
 */
function joinAjax(inputId, inputName, inputPass) {
	$.ajax({
		type : 'POST',
		url : '/join_action',
		data : JSON.stringify({
			id : inputId,
			name : inputName,
			password : inputPass
		}),
		dataType : 'JSON',
		contentType : 'application/json',
		success : function(result) {
			if (result.user) {
				alert(result.user.name + ' 님 가입되었습니다.');
				window.location='main';
			} else {
				alert(result.error);
			}
		},
		error : function(xhr, status, error) {
			alert('회원 가입 중 오류가 발생하였습니다.');
		}
	});
}

/**
 * 가입 버튼 클릭
 * @returns
 */
function joinAction(){
	 
	var writeId = $('#writeId').val();
	var inputId = $('#inputId').val();
	var inputName = $('#inputName').val();
	var inputPass = $('#inputPass').val();
	 
	if(joinValidate(writeId, inputId, inputName, inputPass)){
		joinAjax(inputId, inputName, inputPass);
	}
}
 
/**
 * 회원가입 유효성 검사
 */
function joinValidate(writeId, inputId, inputName, inputPass){
	
	if(!writeId){
		alert('아이디를 입력해주세요.');
		return false;
	}else if(!inputId){
		alert('아이디 중복검사를 해주세요.');
		return false;
	}else if(writeId != inputId){
		alert('아이디 중복검사를 해주세요.');
		return false;
	}else if(!inputName){
		alert('이름을 입력해주세요.');
		return false;
	}else if(!inputPass){
		alert('패스워드를 입력해주세요.');
		return false;
	}else{
		return true;
	}
}

$(document).ready(function() {
	/**
	 * id 필드에 입력했을 때 중복검사 버튼 활성화
	 * @returns
	 */
	$('#writeId').on('input', function() {
		
		$('#checkResult').val('');
		
		if($('#writeId').val()){
			$('#checkBtn').prop('disabled', false);
		}else{
			$('#checkBtn').prop('disabled', true);
		}
	});
});


