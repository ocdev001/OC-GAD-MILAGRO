function getInternetExplorerVersion()
{
    var rV = -1; // Return value assumes failure.

    if (navigator.appName == 'Microsoft Internet Explorer' || navigator.appName == 'Netscape') {
        var uA = navigator.userAgent;
        var rE = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");

        if (rE.exec(uA) != null) {
            rV = parseFloat(RegExp.$1);
        }
        /*check for IE 11*/
        else if (!!navigator.userAgent.match(/Trident.*rv\:11\./)) {
            rV = 11;
        }
    }
    return rV;
}


function areYouIE(){
    var alertText = "No, I'm a real browser :)";
    var iEVersion = getInternetExplorerVersion();
    if(iEVersion != -1){
        alertText = "Welcome! You are using a not supported version of Internet Explorer. Please update your browser to a new version. Click OK and you will be redirected to our contact page. Thank you!";
    	//Redirect
    	window.location = "http://www.peakside.com/en/contact.html";
    	alert(alertText);
    }

}
