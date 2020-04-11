/* 
    Name: Home.js
    Project: Word Counter
    Date Created: 7 April 2020
    Date Updated: 11 April 2020
    Author: Ben Lebout
*/

$(document).ready(function () {
    closeAllModals();
    clearAllErrors();
})

function closeAllModals() {
    closeModalLogIn();
    closeModalSignUp();
    closeModalLogInSuccess();
    closeModalSignUpSuccess();
    closeModalForgotPassword();
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
    if (event.target == modalSignUpSuccess) {
        closeModalSignUpSuccess();
    }
    if (event.target == modalLogInSuccess) {
        closeModalLogInSuccess();
    }
    if (event.target == modalForgotPassword) {
        closeModalForgotPassword();
    }
}


/*Log In Modal ========================================== */
var modalLogIn = document.getElementById("modal-log-in");

function displayModalLogIn() {
    closeAllModals();
    clearAllErrors();
    modalLogIn.style.display = "block";
    $("#modal-form-log-in-input-email").focus();
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
    $("#modal-form-sign-up-input-email").focus();
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

/*Sign Up Success Modal ========================================== */
var modalSignUpSuccess = document.getElementById("modal-sign-up-success");

function displayModalSignUpSuccess() {
    closeAllModals();
    clearAllErrors();
    modalSignUpSuccess.style.display = "block";
}

function closeModalSignUpSuccess() {
    modalSignUpSuccess.style.display = "none";
}

/*Log In Success Modal ========================================== */
var modalLogInSuccess = document.getElementById("modal-log-in-success");

function displayModalLogInSuccess() {
    closeAllModals();
    clearAllErrors();
    modalLogInSuccess.style.display = "block";
}

function closeModalLogInSuccess() {
    modalLogInSuccess.style.display = "none";
}

function redirectToHome() {
    window.location="/home";
}

/*Forgot Password Modal ========================================== */
var modalForgotPassword = document.getElementById("modal-forgot-password");

function displayModalForgotPassword() {
    closeAllModals();
    clearAllErrors();
    setModalForgotPasswordInitialHtml();
    modalForgotPassword.style.display = "block";
    $("#modal-form-forgot-password-input-email").focus();
}

function closeModalForgotPassword() {
    clearModalForgotPassword();
    modalForgotPassword.style.display = "none";
}

function clearModalForgotPassword() {
    $("#modal-form-forgot-password-input-email").val("");
}


/*Submit Sign Up ========================================== */
function submitSignUp() {
    
    //Prevent form from submitting on its own and refreshing the page
    event.preventDefault();

    //Clear errors
    clearAllErrors();

    //Create variable to determine whether or not to make AJAX call
    var proceed = true;

    //Create a variable to focus the cursor after errors
    const emailField = $("#modal-form-sign-up-input-email");
    const pass1Field = $("#modal-form-sign-up-input-password1");
    var cursor = pass1Field

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
        cursor = emailField;
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

    cursor.focus();

    //make AJAX call
    if (proceed) {
        $.ajax({
            type: 'POST',
            url: '/account/new',
            data: {
                formEmail: email,
                formPass1: pass1,
                formPass2: pass2
            },
            success: function(data, status) {
                displayModalSignUpSuccess();
            },
            error: function(xhr, status, error) {
                var err = eval("(" + xhr.responseText + ")");
                clearModalSignUp();
                $("#modal-sign-up-div-errors").text(err.message);
                emailField.focus();
            }
        });        
    }

}

/*Submit Log In ========================================== */
function submitLogIn() {
    
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
    clearModalLogIn();
    
    //validate email properties
    var emailReturn = validateEmail(email);
    if (emailReturn != "good") {
        proceed = false;
        $("#modal-log-in-form-email-errors").text(emailReturn);
        $("#modal-form-log-in-input-email").focus();
    }

    //make AJAX call
    if (proceed) {
        $.ajax({
            type: 'POST',
            url: '/session/login',
            data: {
                formEmail: email,
                formPass: pass
            },
            success: function(data, status) {
                displayModalLogInSuccess();
            },
            error: function(xhr, status, error) {
                var err = eval("(" + xhr.responseText + ")");
                $("#modal-log-in-div-errors").text(err.message);
                $("#modal-form-log-in-input-email").focus();
            }
        });        
    }

}

/*Submit Forgot Password ========================================== */
function submitForgotPassword() {
    
    //Prevent form from submitting on its own and refreshing the page
    event.preventDefault();

    //Clear errors
    clearAllErrors();

    //Create variable to determine whether or not to make AJAX call
    var proceed = true;

    //Grab input from user
    var email = $("#modal-form-forgot-password-input-email").val().trim();

    //Clear fields
    clearModalForgotPassword();

    //validate email properties
    var emailReturn = validateEmail(email);
    if (emailReturn != "good") {
        proceed = false;
        $("#modal-forgot-password-div-errors").text(emailReturn);
        $("#modal-form-forgot-password-input-email").focus();
    }

    //if proceed == true
    if (proceed) {
        //Clear modal text and put spinner
        setModalForogtPasswordAsSpinner();
        
        //make AJAX call
        $.ajax({
            type: 'POST',
            url: '/account/forgot-password',
            data: {
                formEmail: email
            },
            success: function(data, status) {
                setModalForgotPasswordSuccess();
            },
            error: function(xhr, status, error) {
                var err = eval("(" + xhr.responseText + ")");
                closeModalForgotPassword();
                displayModalForgotPassword();
                $("#modal-forgot-password-div-errors").text(err.message);
            }
        });        
    }

}

function setModalForgotPasswordInitialHtml() {
    $("#modal-forgot-password").html(`
        <div class="modal-content" id="modal-forgot-password-content">
            <h2 class="modal-header">Forgot Password</h2>
            <div class="div-error-big" id="modal-forgot-password-div-errors"></div>
            <form id="modal-form-forgot-password">
                <div class="form-div-label-and-input">
                    <label for="modal-form-forgot-password-input-email">Email:</label>
                    <input type="email" id="modal-form-forgot-password-input-email">
                </div>

                <div class="form-div-buttons">
                    <button type="button" class="form-button" id="modal-form-forgot-password-button-cancel" onclick="closeModalForgotPassword()">Cancel</button>
                    <button type="submit" class="form-button" id="modal-form-forgot-password-button-submit" onclick="submitForgotPassword()">Submit</button>
                </div>
            </form>
        </div>
    `);
}

function setModalForogtPasswordAsSpinner() {
    $("#modal-forgot-password").html(`
        <div class="modal-content" id="modal-forgot-password-content">
            <h2 class="modal-header">Forgot Password</h2>
            <div class="div-error-big" id="modal-forgot-password-div-errors"></div>
            <div class="spin"></div>
        </div>
    `);
}

function setModalForgotPasswordSuccess() {
    $("#modal-forgot-password").html(`
        <div class="modal-content" id="modal-forgot-password-content" style="text-align:center;">
            <h2 class="modal-header">Forgot Password</h2>
            <div class="div-error-big" id="modal-forgot-password-div-errors"></div>
            
            
            <h4>Your password has been successfully reset.</h4>
            <h4>Please check your email.</h4>
            
            
            <div class="form-div-buttons">
                <button type="button" class="form-button" id="modal-form-forgot-password-button-cancel" onclick="closeModalForgotPassword()">Close</button    
            </div>
        </div>
    `);
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