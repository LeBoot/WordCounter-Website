/* 
    Name: Sidebar.js
    Project: Word Counter
    Date Created: 2 March 2020
    Date Updated: 6 April 2020
    Author: Ben Lebout
*/

function toggleSidebar() {
    $('#sidebar').toggleClass('active');
}
            
function displayTabContent(tabName, element, color) {
    var allTabContents = document.getElementsByClassName("tab-content");
    for (var i = 0; i < allTabContents.length; i++) {
        allTabContents[i].classList.remove("active-tab-content");
        allTabContents[i].classList.add("inactive-tab-content");
    }

    var allTabButtons = document.getElementsByClassName("tab-button");
    for (var i = 0; i < allTabButtons.length; i++) {
        allTabButtons[i].style.backgroundColor = "";
    }
                
    element.classList.add("active-tab-button");
    element.style.backgroundColor = color;

    document.getElementById(tabName).classList.add("active-tab-content");
 }