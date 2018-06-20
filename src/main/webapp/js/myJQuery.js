$( window ).on( "load", function() {
	$('#logOut').click(function(){
			$('#navigation').hide(1000,function(){
				window.location.href="LogOutServlet";
			});
	});
});