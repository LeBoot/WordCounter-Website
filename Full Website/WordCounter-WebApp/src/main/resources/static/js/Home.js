/* 
    Name: Home.js
    Project: Word Counter
    Date Created: 7 April 2020
    Date Updated: 7 April 2020
    Author: Ben Lebout
*/

$(document).ready(function () {
    closeAllModals();
    clearAllErrors();
})

function closeAllModals() {
    closeModalLogIn();
    closeModalSignUp();
}

function clearAllErrors() {
    $(".div-error-big").text("");
    $(".div-error-small").text("");
}

window.onclick = function(event) {
    if (event.target == modalSignUp) {
        closeModalSignUp();
    }
    if (event.target == modalLogIn) {
        closeModalLogIn();
    }
}


/*Log In Modal ========================================== */
var modalLogIn = document.getElementById("modal-log-in");

function displayModalLogIn() {
    closeAllModals();
    clearAllErrors();
    modalLogIn.style.display = "block";
}

function closeModalLogIn() {
    clearModalLogIn();
    modalLogIn.style.display = "none";
}

function clearModalLogIn() {
    $("#modal-form-log-in-input-email").val("");
    $("#modal-form-log-in-input-password").val("");
}


/*Sign Up Modal ========================================== */
var modalSignUp = document.getElementById("modal-sign-up");

function displayModalSignUp() {
    closeAllModals();
    clearAllErrors();
    modalSignUp.style.display = "block";
}

function closeModalSignUp() {
    clearModalSignUp();
    modalSignUp.style.display = "none";
}

function clearModalSignUp() {
    $("#modal-form-sign-up-input-email").val("");
    $("#modal-form-sign-up-input-password1").val("");
    $("#modal-form-sign-up-input-password2").val("");
}


/*Submit Sign Up ========================================== */
function submitSignUp() {
    
    //Prevent form from submitting on its own and refreshing the page
    event.preventDefault();

    //Clear errors
    clearAllErrors();

    //Create variable to determine whether or not to make AJAX call
    var proceed = true;

    //Grab input from user
    var email = $("#modal-form-sign-up-input-email").val().trim();
    var pass1 = $("#modal-form-sign-up-input-password1").val().trim();
    var pass2 = $("#modal-form-sign-up-input-password2").val().trim();

    //Clear password fields
    $("#modal-form-sign-up-input-password1").val("");
    $("#modal-form-sign-up-input-password2").val("");
    
    //validate email properties
    var emailReturn = validateEmail(email);
    if (emailReturn != "good") {
        proceed = false;
        $("#modal-sign-up-form-email-errors").text(emailReturn);
    }

    //validate password properties
    var passReturn = validatePassword(pass1);
    if (passReturn != "good") {
        proceed = false;
        $("#modal-sign-up-form-password1-errors").text(passReturn);
    }

    if (pass1 != pass2) {
        proceed = false;
        var error = "Passwords do not match."
        $("#modal-sign-up-form-password2-errors").text(error);
    }

    if (proceed) {
        //MAKE AJAX CALL
    }

}

/*Submit Log In ========================================== */
function submitLogIn() {
    console.log("HERE");
    //Prevent form from submitting on its own and refreshing the page
    event.preventDefault();

    //Clear errors
    clearAllErrors();

    //Create variable to determine whether or not to make AJAX call
    var proceed = true;

    //Grab input from user
    var email = $("#modal-form-log-in-input-email").val().trim();
    var pass = $("#modal-form-log-in-input-password").val().trim();

    //Clear fields
    clearModalSignUp();
    
    //validate email properties
    var emailReturn = validateEmail(email);
    if (emailReturn != "good") {
        proceed = false;
        $("#modal-log-in-form-email-errors").text(emailReturn);
    }

    if (proceed) {
        //MAKE AJAX CALL
    }

}


/*Validation ========================================== */
function validateEmail(email) {
    maxLength = 50;
    minLength = 8;
    
    if (email.length > maxLength) {
        return "Please use an email that is no more than " + maxLength + " characters in length.";
    }
    
    if (
        (email.length < minLength) ||
        (!email.includes(".")) ||
        (!email.includes("@")) ||
        (email.includes(" "))
        ) {
        return "Please use a valid email.";
    }

    return "good";
}

function validatePassword(password) {
    maxLength = 64;
    minLength = 4;

    if (password.length > maxLength) {
        return "Please use a password that is no more than " + maxLength + " characters in length.";
    }
    
    if (password.length < minLength) {
        return "Please use a password that is at least " + minLength + " characters in length.";
    }

    return "good";
}