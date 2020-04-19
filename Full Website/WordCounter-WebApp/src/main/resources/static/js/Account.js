/* 
    Name: Account.js
    Project: Word Counter
    Date Created: 19 April 2020
    Date Updated: 19 April 2020
    Author: Ben Lebout
*/

$(document).ready(function () {
    closeAllModals();
    clearAllErrors();
})

function closeAllModals() {
    closeModalChangeEmail();
    closeModalChangePassword();
    closeModalDeleteAccount();
}

function clearAllErrors() {
    $(".div-error-big").text("");
    $(".div-error-small").text("");
}

window.onclick = function(event) {
    if (event.target == modalChangeEmail) {
        closeModalChangeEmail();
    }
    if (event.target == modalChangePassword) {
        closeModalChangePassword();
    }
    if (event.target == modalDeleteAccount) {
        closeModalDeleteAccount();
    }
}

/*Change Email ================================================== */
const modalChangeEmail = document.getElementById("modal-change-email");

function displayModalChangeEmail() {
    closeAllModals();
    clearAllErrors();
    modalChangeEmail.style.display = "block";
    $("#modal-form-change-email-input-email").focus();
}

function closeModalChangeEmail() {
    clearModalChangeEmail();
    modalChangeEmail.style.display = "none";
}

function clearModalChangeEmail() {
    $("#modal-form-change-email-input-email").val("");
    $("#modal-form-change-email-input-password").val("");
}

function submitChangeEmail() {
    //Prevent form from submitting on its own and refreshing the page
    event.preventDefault();

    //Clear errors
    clearAllErrors();

    //Create variable to determine whether or not to make AJAX call
    var proceed = true;

    //Create a variable to focus the cursor after errors
    const emailField = $("#modal-form-change-email-input-email");
    const passField = $("#modal-form-change-email-input-password");
    var cursor = passField;

    //Grab input from user
    var email = $("#modal-form-change-email-input-email").val().trim();
    var pass = $("#modal-form-change-email-input-password").val().trim();

    //Clear password field
    $("#modal-form-change-email-input-password").val("");

    //validate email properties
    var emailReturn = validateEmail(email);
    if (emailReturn != "good") {
        proceed = false;
        $("#modal-change-email-form-email-errors").text(emailReturn);
        cursor = emailField;
    }

    //validate password properties
    var passReturn = validatePassword(pass);
    if (passReturn != "good") {
        proceed = false;
        $("#modal-change-email-form-password1-errors").text(passReturn);
    }

    cursor.focus();

    //make AJAX call
    if (proceed) {
        $.ajax({
            type: 'POST',
            url: '/account/change-email',
            data: {
                formEmail: email,
                formPass: pass
            },
            success: function(data, status) {
                displayModalChangeEmailSuccess();
            },
            error: function(xhr, status, error) {
                var err = eval("(" + xhr.responseText + ")");
                clearModalChangeEmail();
                $("#modal-change-email-div-errors").text(err.message);
                emailField.focus();
            }
        });        
    }

}

function displayModalChangeEmailSuccess() {
    
}


/*Change Password ================================================== */
const modalChangePassword = document.getElementById("modal-change-password");

function displayModalChangePassword() {

}

function closeModalChangePassword() {

}


/*Change Password ================================================== */
const modalDeleteAccount = document.getElementById("modal-delete-account");

function displayModalDeleteAccount() {

}

function closeModalDeleteAccount() {

}


/*Validation ========================================== */
function validateEmail(email) {
    const maxLength = 50;
    const minLength = 8;
    
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
    const maxLength = 64;
    const minLength = 4;

    if (password.length > maxLength) {
        return "Please use a password that is no more than " + maxLength + " characters in length.";
    }
    
    if (password.length < minLength) {
        return "Please use a password that is at least " + minLength + " characters in length.";
    }

    return "good";
}