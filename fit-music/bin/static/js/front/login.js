/**
 * Hyun-Kyung Choi
 */

/**
 * 로그인 통신
 */
function loginAjax(inputId, inputPass){
	
  $.ajax({
 	type:'POST',
 	url:'/login_action',
 	data : JSON.stringify({
 		id : inputId,
 		password : inputPass
 	}),
 	dataType: 'JSON',
 	contentType: 'application/json',
 	success: function(result){
 		if(result.user){
 			alert(result.user.name+' 님 로그인 되었습니다.');
 			window.location='main';
 		}else{
 			alert(result.error);
 		}
 	},error: function(xhr, status, error){
		
 	}
  });
}

/**
 * Login
 */
function loginAction(){
	
	var inputId = $('#inputId').val();
	var inputPass = $('#inputPass').val();
	
	// 유효성 검사 후 로그인
	if(loginValidate(inputId, inputPass)){
		loginAjax(inputId, inputPass);
	}
}

/**
 * 로그인 유효성 검사
 */
function loginValidate(inputId, inputPass){
	if(!inputId){
		alert('아이디를 입력해주세요.');
		return false;
	}else if(!inputPass){
		alert('패스워드를 입력해주세요.');
		return false;
	}else{
		return true;
	}
}