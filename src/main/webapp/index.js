function getBalance() {
	commonAjaxCaller('http://localhost:8080/DVDStore/rest/appthon/balanceenquiry', 
		{}, 'POST');
}

function branchLocator() {
	commonAjaxCaller('http://localhost:8080/DVDStore/rest/appthon/branchLocator', 
		{}, 'POST');
}

function amountTransfer() {
	commonAjaxCaller('http://localhost:8080/DVDStore/rest/appthon/transferFunds', 
			{'destAccNo' : 9999888877770001,
		'amount' : 1000,
		'payeeDesc' : 'testing'}, 'POST');
	//srcAccount=9999888877770001&destAccount=9999888877770001&amt=1000&payeeDesc=payeedesc&payeeId=1&type_of_transaction=DTH
}


function commonAjaxCaller(url, data, methodType) {
	$.ajax({
		url: url,
		method: methodType,
		data: data,
		success: function(response) {
			var responseText = $.parseJSON(response);
			if (responseText.balanceSummary) {
				var decodedText = $.parseJSON(responseText.balanceSummary);
				var finalObject = $.parseJSON(decodedText);
				if (finalObject[0].code == 200) {
					document.getElementById('balanceSummary').innerHTML = 'Account Number : ' + finalObject[1].accountno +'<br/>' 
					+ 'Account Type: ' + finalObject[1].accounttype	+ '<br/>' 
					+ 'Account Balance: '+finalObject[1].balance;
				}
			} else  {
				initMap();
			}
			
		},
		error: function() {
			alert(2);
		},
		failure: function() {
		}
	});
}

function initMap() {
	document.getElementById('map').className = "map";
		var myOptions = {
			zoom : 11,
			center : {lat:19.144698, lng: 72.93682919999992},
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
		map = new google.maps.Map(document.getElementById('map'),
				myOptions);
		marker = new google.maps.Marker({
			map : map,
			position : new google.maps.LatLng(19.144698, 72.93682919999992)
		});
		infowindow = new google.maps.InfoWindow(
				{
					content : 'ICICI Bank Ltd. - 100 - A, Station Plaza, Bhandup West<br>'
				});
		google.maps.event.addListener(marker, 'click', function() {
			infowindow.open(map, marker);
		});
	}

