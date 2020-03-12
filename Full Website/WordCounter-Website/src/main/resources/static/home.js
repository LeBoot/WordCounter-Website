/* 
Author: Ben Leboutilllier
Date created: 2 March 2020
Date updated: 11 March 2020
 */


$(document).ready(function () {
	
	emptyErrorDiv()

}) //End Document Ready

function analyzetext() {
    //Prevent form from submitting on its own and refreshing the page
    event.preventDefault();
    
    emptyErrorDiv();

    var isInputValid = validateForm1();

    if (isInputValid == true) {
        var myForm = document.getElementById("form1");
        var myData = new FormData(myForm);

        replaceButtonWithSpinner();

        $.ajax({
            type: "POST",
            url: "/analyze-2",
            data: myData,
            contentType: false,
            processData: false,
            cache: false,
            success: function(myList) {
                replaceSpinnerWithResetButton();
                displayPageTabs();
                alert("Call Graphing Functions Now")
                // displayGraph(myList);
            },
            error: function(xhr, status, error) {
                var err = eval("(" + xhr.responseText + ")");
                console.log("Error: " + err.message);
            }
        });
    }   

}

function validateForm1() {
    var input = $("#form1-text-area").val().trim();
    var isInputValid = true;
    var maxChar = 5000;
    
    if (input.length < 1) {
        $("#form1-text-area").val("");
        isInputValid = false;
        var message = "You must enter something before analysis can occur.";
        displayError(message);
    }
    if (input.length > maxChar) {
        isInputValid = false;
        var message = "Sorry, but right now we cannot handle more than " + maxChar + " characters.";
        displayError(message);
    }

    return isInputValid;
}

function emptyErrorDiv() {
    $("#form1-error-div").html("");
}

function displayError(message) {
    $("#form1-error-div").html(
        `<p id="form1-error"></p>`
    );
    $("#form1-error").text(message);
}

function replaceButtonWithSpinner() {
    $("#div-form1-buttons").html(
        `<div class="spin"></div>`
    );
}

function replaceSpinnerWithResetButton() {
    $("#div-form1-buttons").html(
        `<button type="button" id="form1-button-reset" onClick="resetForm1()">Reset</button>`
    );
}

function resetForm1() {
    $("#form1-text-area").val("");
    $("#div-form1-buttons").html(
        `<button type="submit" id="form1-button">Analyze</button>`
    );
    $("#container-for-page-tabs").addClass("inactive-tab-content");
}

function displayPageTabs() {
    $("#container-for-page-tabs").removeClass("inactive-tab-content");
    $("#defaultOpen").click();
}

function configureDataForDisplay(myList) {
    //Create myData array
    var myData;
    
    //Create the first entry in the myData array
    for (var key in myList[0]) {
        if (myList[0].hasOwnProperty(key)) {
            myData = [{y: myList[0][key], label: key}];
        }
    }

    //If myList has more than one index, add the rest
    if (myList.length > 1) {
        for (var i = 1; i < myList.length; i++) {
            for (var key in myList[i]) {
                if (myList[i].hasOwnProperty(key)) {
                    var nextValue = {y: myList[i][key], label: key};
                    myData.push(nextValue);
                }
            }
        }
    }
    
    return myData;
}


function displayGraph(myList) {
    var configuredData = configureDataForDisplay(myList);

    var chart = new CanvasJS.Chart("my-chart", {
        animationEnabled: true,
        title:{
            text:"Words by Occurance"
        },
        axisX:{
            interval: 1
        },
        axisY:{
//            logarithmic: true,
            title: "Number of Occurances"  
        },
        data: [{
            type: "column",
            color: "#014D65",
            dataPoints: configuredData
        }]
    });
    chart.render();
}