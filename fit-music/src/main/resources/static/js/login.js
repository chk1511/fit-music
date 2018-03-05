/**
 * Hyun-Kyung Choi
 */

/**
 * Login
 */
function login_action(){
	
	var inputId = $('#inputId').val();
	var inputPass = $('#inputPass').val();
	
	 $.ajax({
	 	type:"POST",
	 	url:"/login_action",
	 	data : JSON.stringify({
	 		id : inputId,
	 		password : inputPass
	 	}),
	 	dataType: "JSON",
	 	contentType: "application/json",
	 	success: function(result){
	 		if(result.user){
	 			alert(result.user.name+" 님 로그인 되었습니다.");
	 		}else{
	 			alert(result.error);
	 		}
	 	},error: function(xhr, status, error){
			
	 	}
	 });
}