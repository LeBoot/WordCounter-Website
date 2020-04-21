/* 
    Name: Account.js
    Project: Word Counter
    Date Created: 19 April 2020
    Date Updated: 21 April 2020
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
        url: '/text/get-list',
        success: function(data, textStatus, xhr) {
            $("#account-page-spinner").addClass("inactive-tab-content");            
            if (xhr.status == 204) {
                $("#account-table-no-content-div").removeClass("inactive-tab-content");
            } else {
                fillTable(data);
            }
        },
        error: function(xhr, textStatus) {
            $("#account-page-spinner").addClass("inactive-tab-content");
            const msg = "Sorry, but we're having trouble on our end and cannot retrieve your texts.  Please try again later.";
            $("#account-page-div-errors").text(msg);
        }
    });
}

function fillTable(data) {
    $.each(data, function(index, text) {
        var htmlToAdd = '<tr>';
        htmlToAdd += '<td id="table-row-title-' + index + '"></td>';
        htmlToAdd += '<td id="table-row-content-' + index + '"></td>';
        htmlToAdd += '<td><button type="button" class="button-account-table" onclick="displayModalViewEditText(' + text.id + ')">View/Edit</button></td>';
        htmlToAdd += '<td><button type="button" class="button-account-table" onclick="analyzeText(' + text.id + ')">Analyze</button></td>';
        htmlToAdd += `<td><button type="button" class="button-account-table" onclick="displayModalDeleteText(` + text.id + `, '` + text.title + `')">Delete</button></td>`;
        htmlToAdd += '</tr>';

        $("#account-table").append(htmlToAdd);

        $("#table-row-title-" + index).text(text.title);
        $("#table-row-content-" + index).text(text.content);
    });
}


/*Modal Helpers ============================================== */

function closeAllModals() {
    closeModalChangeEmail();
    closeModalChangePassword();
    closeModalDeleteAccount();
    closeModalChangePassword();
    closeModalChangePasswordSuccess();
    closeModalDeleteAccount();
    closeModalDeleteText();
    closeModalViewEditText();
    closeModalSaveTextAnyway();
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
    if (event.target == modalDeleteText) {
        closeModalDeleteText();
    }
    if (event.target == modalViewEditText) {
        closeModalViewEditText();
    }
    if (event.target == modalDoSaveTextAnyway) {
        closeModalSaveTextAnyway();
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
    clearAllErrors();
    modalDeleteAccount.style.display = "none";
}

function clearModalDeleteAccount() {
    $("#modal-form-delete-account-input-password").val("");
}

function submitDeleteAccount() {
    //Prevent form from submitting on its own and refreshing the page
    event.preventDefault();

    //Clear errors
    clearAllErrors();

    //Create variable to determine whether or not to make AJAX call
    var proceed = true;

    //Grab and verify input from user
    var password = $("#modal-form-delete-account-input-password").val().trim();
    if (password.length < 1) {
        proceed = false;
        const message = "Please enter your password.";
        $("modal-delete-account-div-errors").text(message);
    }

    //make AJAX call
    if (proceed) {
        $.ajax({
            type: 'POST',
            url: '/account/delete',
            data: {
                password: password
            },
            success: function(data, status) {
                window.location="/home";
                alert("Your account has been deleted");
            },
            error: function(xhr, status, error) {
                var err = eval("(" + xhr.responseText + ")");
                clearModalDeleteAccount();
                $("#modal-delete-account-div-errors").text(err.message);
                $("#modal-form-delete-account-input-password").focus();
            }
        });        
    }
}

/*Delete Text ================================================== */
const modalDeleteText = document.getElementById("modal-delete-text");

function displayModalDeleteText(textId, textTitle) {
    closeAllModals();
    clearAllErrors();
    $("#modal-delete-text-hidden-input-id").val(textId);
    $("#modal-delete-text-span-title").text(textTitle);
    modalDeleteText.style.display = "block";
}

function closeModalDeleteText() {
    modalDeleteText.style.display = "none";
}

function submitDeleteText() {
    //Prevent form from submitting on its own and refreshing the page
    event.preventDefault();

    //Clear errors
    clearAllErrors();

    //Grab text id
    var textId = $("#modal-delete-text-hidden-input-id").val();
    console.log("TextID: " + textId);

    //make AJAX call
    if (true) {
        $.ajax({
            type: 'POST',
            url: '/text/delete/' + textId,
            success: function(data, status) {
                window.location="/account/view";
            },
            error: function(xhr, status, error) {
                const msg = "Cannot fulfil that request right now.  Please try again later.";
                $("#modal-delete-text-div-errors").text(msg);
            }
        });
    }

}


/*Edit Text ================================================== */
const modalViewEditText = document.getElementById("modal-view-edit-text");
var originalTitle = "";
var originalContent = "";

function resetOriginals() {
    originalTitle = "";
    originalContent = "";
}

var placeholderId = "";
var palceholderTitle = "";
var placeholderContent = "";

function resetPlaceholders() {
    placeholderId = "";
    palceholderTitle = "";
    placeholderContent = "";
}

function displayModalViewEditText(textId) {
    closeAllModals();
    clearAllErrors();

    //ajax call for content
    $.ajax({
        type: 'GET',
        url: '/text/get/' + textId,
        success: function(text, status) {
            //fill values
            $("#modal-form-view-edit-text-input-id").val(textId);
            $("#modal-form-view-edit-text-input-title").val(text.title);
            $("#modal-form-view-edit-text-input-content").val(text.content);

            //set originals
            originalTitle = text.title;
            originalContent = text.content;
        },
        error: function(xhr, status, error) {
            clearAllErrors();
            var err = eval("(" + xhr.responseText + ")");
            $("#modal-view-edit-text-div-errors").text(err.message);
        }
    });    

    modalViewEditText.style.display = "block";
}

function closeModalViewEditText() {
    clearModalViewEditText();
    clearAllErrors();
    resetOriginals();
    modalViewEditText.style.display = "none";
}

function clearModalViewEditText() {
    $("#modal-form-view-edit-text-input-id").val("");
    $("#modal-form-view-edit-text-input-title").val("");
    $("#modal-form-view-edit-text-input-title").val("");
}

function submitViewEditText(checkTitles) {
    //Prevent form from submitting on its own and refreshing the page
    event.preventDefault();

    //Clear errors
    clearAllErrors();

    //Create variable to determine whether or not to make AJAX call
    var proceed = true;

    if (checkTitles == false) {
        id = placeholderId;
        title = palceholderTitle;
        content = placeholderContent;
    } else {

        //Grab input from user
        var id = $("#modal-form-view-edit-text-input-id").val();
        var title = $("#modal-form-view-edit-text-input-title").val().trim();
        var content = $("#modal-form-view-edit-text-input-content").val().trim();

        //if no changes, break out of method
        if ((title == originalTitle) && (content == originalContent)) {
            proceed = false;
            closeModalViewEditText();
        }
        
        //validate input from user
        var titleReturn = validateTitle(title);
        if (titleReturn != "good") {
            proceed = false;
            $("#modal-form-view-edit-text-input-title-errors").text(titleReturn);
        }

        var contentReturn = validateContent(content);
        if (contentReturn != "good") {
            proceed = false;
            $("#modal-form-view-edit-text-input-content-errors").text(contentReturn);
        }

        //If the title hasn't changed, don't flag it as already in use in the backend
        if (title == originalTitle) {
            checkTitles = false;
        }

        //Set placeholders for if title is taken
        placeholderId = id;
        palceholderTitle = title;
        placeholderContent = content;

    }
    
    //make AJAX call
    if (proceed) {
        $.ajax({
            type: 'POST',
            url: '/text/edit/' + id,
            data: {
                textTitle: title,
                textContent: content,
                checkTitles: checkTitles
            },
            success: function(data, status) {
                resetPlaceholders();
                window.location="/account/view";
            },
            error: function(xhr, status, error) {
                clearAllErrors();
                var err = eval("(" + xhr.responseText + ")");
                if (xhr.status == 400) {
                    displayModalDoSaveTextAnyway(err.message);
                } else {
                    resetPlaceholders();
                    $("#modal-view-edit-text-div-errors").text(err.message);
                }
            }
        });        
    }

}

const modalDoSaveTextAnyway = document.getElementById("modal-save-text-anyway");

function displayModalDoSaveTextAnyway(message) {
    $("#modal-save-text-anyway-error-display").text(message);
    modalDoSaveTextAnyway.style.display = "block";
}

function closeModalSaveTextAnyway() {
    resetPlaceholders();
    clearAllErrors();
    modalDoSaveTextAnyway.style.display = "none";
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

function validateTitle(title) {
    const maxLength = 50;
    
    if (title.length < 1 || title.length > maxLength) {
        return "Please enter a title that is fewer than " + maxLength + " characters long.";
    }

    return "good";
}

function validateContent(content) {
    const maxLength = 5000;
    
    if (content.length < 1 || content.length > maxLength) {
        return "Content must be fewer than " + maxLength + " characters long.";
    }

    return "good";
}