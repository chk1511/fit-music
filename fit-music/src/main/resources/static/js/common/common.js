/**
 * 유효성 검사
 * @returns
 */
function validate(){
	
    for(var i=0; i<arguments.length; i++){
          if(!arguments[i]){
        	  alert(arguments[i]+'를 입력해주세요');
          }else{
        	  alert('...');
          }
    }
}