/**
 * 
 */
function checkCookie(name) {
	var ca = document.cookie.split('; ');			//	split(';'); --> will not work because after 1st cookie ; comes and then comes space so, 2nd cookie starts actually after space so split from there.
	  for(var i=0;i < ca.length;i++) {
	    var c = ca[i];
	    var cnames = c.split('=');
	  		if(cnames[0] == name.replace("@",'_')){
	  			return cnames[1];
	  		}
	   }
	  return null;
}

function validateEmailIdById(id){
    var email = document.getElementById(id).value;
    var pmail=/^[A-Za-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/;
    if(email.length == 0)
    	{
    	alert("Email is Required");
    	return false;
    	}
    else if (!pmail.test(email)) {
        alert("Wrong Email Format");
		document.getElementById(id).setAttribute("value","");
        return false;
    }
    else if(pmail.test(email)){
    	   return true;
     }
}

function validateEmailIdByEmail(emailId){
    var email = emailId;
    var pmail=/^[A-Za-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/;
    if(email.length == 0)
    	{
    	alert("Email is Required");
    	return false;
    	}
    else if (!pmail.test(email)) {
        alert("Wrong Email Format");
        return false;
    }
    else if(pmail.test(email)){
    	   return true;
     }
}

function checkIfPasswordRemembered() {
	var email = document.getElementById("txtLogInEmailId").value;
	if(validateEmailIdByEmail(email))
	{
		var cValue = checkCookie(email);
		if(cValue != null)
		{
			var finalCValue = cValue.split('"');
			var a = finalCValue.length;
			if(a == 1){
				// Do Nothing
			}
			else{
				cValue = finalCValue[1];
			}
			if(cValue != null){
				document.getElementById("txtLogInPassword").setAttribute("value",cValue);
			}
		}
		else if(cValue == null){
			document.getElementById("txtLogInPassword").setAttribute("value","");
		}
	}
}

function controlToMailService(){
	var toEmailId = document.getElementById("txtLogInEmailId").value;
	if(validateEmailIdByEmail(toEmailId))
		{
		alert("Inside controlToMailService");
		document.getElementById("forgetPassword").setAttribute("href","MailServlet?forgetEmailId="+toEmailId);
		}
}

function addTextbox(id){
	var EmailIdTextboxNumber = document.getElementById(id).childElementCount;
	var addedEmailIdTextboxNumberId = EmailIdTextboxNumber + 1;
	var tempTextboxNumber = 0;
	var tempEmails= new Array();
	if(EmailIdTextboxNumber -1 == 0){
		document.getElementById("removeEmailBtn").removeAttribute("style");
	}
	while(tempTextboxNumber != EmailIdTextboxNumber){
		tempEmails[tempTextboxNumber++] = document.getElementById("txtEmailId"+tempTextboxNumber).value;
	}
	document.getElementById(id).innerHTML += '<input type="email" class="form-control" id="txtEmailId'+addedEmailIdTextboxNumberId+'" name="txtEmailId'+addedEmailIdTextboxNumberId+'" onblur="validateEmailIdById(\'txtEmailId'+addedEmailIdTextboxNumberId+'\');" placeholder="Enter email" required/>';
	tempTextboxNumber = EmailIdTextboxNumber;
	while(tempTextboxNumber != 0){
		document.getElementById("txtEmailId" + tempTextboxNumber--).value = tempEmails[tempTextboxNumber];
	}
}

function removeTextbox(id){
	var EmailIdTextboxNumber = document.getElementById(id).childElementCount;
	if(EmailIdTextboxNumber == 2){
		document.getElementById("removeEmailBtn").setAttribute("style","display:none;");
	}
	var parent = document.getElementById(id);
	var child = document.getElementById(id).lastElementChild;
	parent.removeChild(child);
}

function checkIfExcelFile(file) {
    var validExts = new Array(".xlsx", ".xls");
    var fileExt = file.value;
    fileExt = fileExt.substring(fileExt.lastIndexOf('.'));
    if (validExts.indexOf(fileExt) < 0) {
      alert("Invalid file selected or File not selected, Valid files are of " +
               validExts.toString() + " types.");
      return false;
    }
    else return true;
}