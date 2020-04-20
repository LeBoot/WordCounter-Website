/* 
    Name: Account.js
    Project: Word Counter
    Date Created: 19 April 2020
    Date Updated: 20 April 2020
    Author: Ben Lebout
*/

$(document).ready(function () {
    closeAllModals();
    clearAllErrors();
    callForTexts();
})

function callForTexts() {
    $.ajax({
        type: 'GET',
        url: '/account/get-list',
        success: function(data, status) {
            fillTable(data);
        },
        error: function(xhr, status, error) {
            var err = eval("(" + xhr.responseText + ")");
            $("#modal-change-email-div-errors").text(err.message); //This
        }
    });
}

function fillTable(data) {
    //Write THIS
}

/*Modal Helpers ============================================== */

function closeAllModals() {
    closeModalChangeEmail();
    closeModalChangePassword();
    closeModalDeleteAccount();
    closeModalChangePassword();
    closeModalChangePasswordSuccess();
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
    if (event.target == modalChangeEmailSuccess) {
        closeModalChangeEmailSuccess();
    }
    if (event.target == modalChangePassword) {
        closeModalChangePassword();
    }
    if (event.target == modalChangePasswordSuccess) {
        closeModalChangePasswordSuccess();
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

const modalChangeEmailSuccess = document.getElementById("modal-change-email-success");

function displayModalChangeEmailSuccess() {
    closeAllModals();
    clearAllErrors();
    modalChangeEmailSuccess.style.display = "block";
}

function closeModalChangeEmailSuccess() {
    modalChangeEmailSuccess.style.display = "none";

    //refresh page to update sidebar message
    window.location="/account/view";
}


/*Change Password ================================================== */
const modalChangePassword = document.getElementById("modal-change-password");
const modalChangePasswordSuccess = document.getElementById("modal-change-password-success");

function displayModalChangePassword() {
    closeAllModals();
    clearAllErrors();
    modalChangePassword.style.display = "block";
    $("#modal-form-change-password-input-old-pass").focus();
}

function closeModalChangePassword() {
    clearModalChangePassword();
    modalChangePassword.style.display = "none";
}

function clearModalChangePassword() {
    $("#modal-form-change-password-input-old-pass").val("");
    $("#modal-form-change-password-input-new-password-1").val("");
    $("#modal-form-change-password-input-new-password-2").val("");
}

function displayModalChangePasswordSuccess() {
    closeAllModals();
    clearAllErrors();
    modalChangePasswordSuccess.style.display = "block";
}

function closeModalChangePasswordSuccess() {
    modalChangePasswordSuccess.style.display = "none";
}

function submitChangePassword() {
    //Prevent form from submitting on its own and refreshing the page
    event.preventDefault();

    //Clear errors
    clearAllErrors();

    //Create variable to determine whether or not to make AJAX call
    var proceed = true;

    //Create a variable to focus the cursor after errors
    const oldPassField = $("#modal-form-change-password-input-old-pass");
    const newPass1Field = $("#modal-form-change-password-input-new-passsword-1");
    var cursor = newPass1Field;

    //Grab input from user
    var oldPass = $("#modal-form-change-password-input-old-pass").val().trim();
    var newPass1 = $("#modal-form-change-password-input-new-password-1").val().trim();
    var newPass2 = $("#modal-form-change-password-input-new-password-2").val().trim();

    //Clear all fields
    clearModalChangePassword();

    //validate new password properties
    var passReturn = validatePassword(newPass1);
    if (passReturn != "good") {
        proceed = false;
        $("#modal-change-password-form-new-password-1-errors").text(passReturn);
    }

    if (newPass1 != newPass2) {
        proceed = false;
        var error = "Passwords do not match."
        $("#modal-change-password-form-new-password-2-errors").text(error);
    }

    cursor.focus();

    //make AJAX call
    if (proceed) {
        $.ajax({
            type: 'POST',
            url: '/account/change-password',
            data: {
                oldPass: oldPass,
                newPass1: newPass1,
                newPass2: newPass2
            },
            success: function(data, status) {
                displayModalChangePasswordSuccess();
            },
            error: function(xhr, status, error) {
                var err = eval("(" + xhr.responseText + ")");
                clearModalChangePassword();
                $("#modal-change-password-div-errors").text(err.message);
                oldPassField.focus();
            }
        });        
    }

}


/*Delete Account ================================================== */
const modalDeleteAccount = document.getElementById("modal-delete-account");

function displayModalDeleteAccount() {
    closeAllModals();
    clearAllErrors();
    modalDeleteAccount.style.display = "block";
    $("#modal-form-delete-account-input-password").focus();

}

function closeModalDeleteAccount() {
    clearModalDeleteAccount();
    modalDeleteAccount.style.display = "none";
}

function clearModalDeleteAccount() {
    $("#modal-form-delete-account-input-password").val("");
}

function submitDeleteAccount() {
    //WRITE THIS NEXT
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