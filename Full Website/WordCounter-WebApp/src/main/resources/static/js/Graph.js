/* 
    Name: Graph.js
    Project: Word Counter
    Date Created: 11 March 2020
    Date Updated: 22 April 2020
    Author: Ben Lebout
*/

$(document).ready(function () {
    evaluateDoAnalyze();
})

function evaluateDoAnalyze() {
    var doAnalyze = $("#home-do-analyze").val();
    if (doAnalyze == 'true') {
        analyzeText(false);
    }
}

function clearAllErrors() {
    $(".div-error-big").text("");
    $(".div-error-small").text("");
}

function clearForm() {
    $("#analysis-form-text-area").val("");
    clearAllErrors();
    $("#container-for-page-tabs").addClass("inactive-tab-content");
}

function clearGraphs() {
    $("#page1").html(`
        <canvas id="chart-scalar"></canvas>
        <div id="page1-spinner">
            <div class="spin"></div>
        </div>
    `);

    $("#page2").html(`
        <canvas id="chart-log"></canvas>
        <div id="page2-spinner">
            <div class="spin"></div>
        </div>
    `);

    $("#page3-table").html(`
        <tr id="page3-table-headrow">
            <th class="page3-table-headerrow" width="50%">Word</th>
            <th class="page3-table-headerrow" width="50%">Occurance</th>
        </tr>
    `);
}

function analyzeText(didEventHappen) {
    var input;

    //If the form was submitted, prevent form from submitting on its own and refreshing the page
    if (didEventHappen == true) {
        event.preventDefault();
        input = $("#analysis-form-text-area").val().trim();
    } else {
        input = $("#home-do-analyze-content").val().trim();
        $("#analysis-form-text-area").val(input);
    }
    
    clearAllErrors();
    clearGraphs();
    
    var isInputValid = validateForm(input);
    if (isInputValid == true) {
        
        $("#analysis-form-spinner").removeClass("inactive-tab-content");

        $.ajax({
            type: 'POST',
            url: "/text/analyze",
            data: {
                textContent: input
            },
            success: function(incomingData) {
                listOfLabels = incomingData.labelList;
                listOfOccurances = incomingData.occuranceList;
                $("#analysis-form-spinner").addClass("inactive-tab-content");
                buildCharts(listOfLabels, listOfOccurances);
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
    var maxChar = 15000;
    
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

function buildCharts(listOfLabels, listOfOccurances) { 
    displayPageTabs();
    displayScalarContents(listOfLabels, listOfOccurances);
    displayLogContents(listOfLabels, listOfOccurances);
    displayTableContents(listOfLabels, listOfOccurances);
}

function displayScalarContents(listOfLabels, listOfOccurances) {   
    var scalarChart = $("#chart-scalar");
    var scalarChart = new Chart(scalarChart, {
        type: 'bar',
        data: {
            labels: listOfLabels,
            datasets: [{
                label: 'occurances',
                backgroundColor: 'brown',
                data: listOfOccurances
            }]
        },
        options: {
            legend: {display: false},
            title: {display: false, text: 'Scalar Bar Graph'},
            scales: {
                yAxes: [{
                    ticks: {
                        min: 0,
                        stepSize: 1
                    }
                }]
            }
        }
    });

    $("#page1-spinner").addClass("inactive-tab-content");
}

function displayLogContents(listOfLabels, listOfOccurances) {
    var logChart = $("#chart-log");
    var logChart = new Chart(logChart, {
        type: 'bar',
        data: {
            labels: listOfLabels,
            datasets: [{
                label: 'occurances',
                backgroundColor: 'brown',
                data: listOfOccurances
            }]
        },
        options: {
            legend: {display: false},
            title: {display: false, text: 'Log Bar Graph'},
            scales: {
                yAxes: [{
                    type: 'logarithmic',
                    ticks: {min: 0}
                }]
            }
        }
    });

    $("#page2-spinner").addClass("inactive-tab-content");
}

function displayTableContents(listOfLabels, listOfOccurances) {
    var table = $("#page3-table");
    for (var i = 0; i < listOfLabels.length; i++) {
        var htmlToAdd = '<tr>';
            htmlToAdd += `<td id="label-row-` + i + `"></td>`;
            htmlToAdd += `<td id="occurance-row-` + i + `"></td>`;
            htmlToAdd += '</tr>';

        table.append(htmlToAdd);

        $("#label-row-" + i).text(listOfLabels[i]);
        $("#occurance-row-" + i).text(listOfOccurances[i]);
    }

    $("#page3-spinner").addClass("inactive-tab-content");
    table.removeClass("inactive-tab-content");
}