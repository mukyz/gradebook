function getCount(){
    var xhttpCountRequest = new XMLHttpRequest();
    xhttpCountRequest.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            proccessCountResponse(this.responseText);
        }
    };
    xhttpCountRequest.open("GET", "/api/messages/count", true);
    xhttpCountRequest.send();    
};

function proccessCountResponse(param){                                        
    var outputList = document.getElementsByClassName("unread-count");

    for( i = 0; i < outputList.length; i++){
        var node = document.createTextNode(param);
        outputList[i].innerHTML = param;
    }
};

window.setInterval(getCount, 10000);
document.addEventListener("load", getCount());
