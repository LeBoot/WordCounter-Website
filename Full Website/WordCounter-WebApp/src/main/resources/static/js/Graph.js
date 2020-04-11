/* 
    Name: Graph.js
    Project: Word Counter
    Date Created: 11 March 2020
    Date Updated: 11 April 2020
    Author: Ben Lebout
*/

function clearAllErrors() {
    $(".div-error-big").text("");
    $(".div-error-small").text("");
}

function clearForm() {
    $("#analysis-form-text-area").val("");
    clearAllErrors();
}

function analyzeText() {
    //Prevent form from submitting on its own and refreshing the page
    event.preventDefault();
    
    clearAllErrors();

    var input = $("#analysis-form-text-area").val().trim();
    var isInputValid = validateForm(input);

    if (isInputValid == true) {
        
        $("#analysis-form-spinner").removeClass("inactive-tab-content");

        $.ajax({
            type: 'POST',
            url: "/text/analyze",
            data: {
                textContent: input
            },
            success: function(myList) {
                displayGraph(myList);
            },
            error: function(xhr) {
                var err = eval("(" + xhr.responseText + ")");
                clearForm();
                $("#analysis-form-spinner").addClass("inactive-tab-content");
                displayError(err.message);
            }
        });
    }   

}

function validateForm(input) {
    var maxChar = 5000;
    
    if (input.length < 1) {
        $("#form1-text-area").val("");
        var message = "You must enter something before analysis can occur.";
        displayError(message);
        return false;
    }
    if (input.length > maxChar) {
        var message = "Sorry, but right now we cannot handle more than " + maxChar + " characters.";
        displayError(message);
        return false
    }

    return true;
}

function displayError(message) {
    $("#analysis-form-error-div").text(message);
}

function resetForm() {
    clearForm();
    $("#analysis-form-buttons-div").removeClass("inactive-tab-content");
    $("#analysis-form-spinner").addClass("inactive-tab-content");
    $("#analysis-form-reset-button-div").addClass("inactive-tab-content");
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

    var chart = new CanvasJS.Chart("chart-page-1", {
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

    $("#analysis-form-spinner").addClass("inactive-tab-content");
    $("#analysis-form-reset-button-div").removeClass("inactive-tab-content");
    displayPageTabs();

    chart.render();
}