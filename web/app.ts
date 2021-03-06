/// <reference path="ts/EditEntryForm.ts"/>
/// <reference path="ts/NewEntryForm.ts"/>
/// <reference path="ts/ElementList.ts"/>
/// <reference path="ts/Navbar.ts"/>
/// <reference path="ts/LikeButton.ts"/>

// Prevent compiler errors when using jQuery.  "$" will be given a type of 
// "any", so that we can use it anywhere, and assume it has any fields or
// methods, without the compiler producing an error.
let $: any;

// Prevent compiler errors when using Handlebars
let Handlebars: any;

// a global for the EditEntryForm of the program.  See newEntryForm for 
// explanation
//let editEntryForm: EditEntryForm;

// Run some configuration code when the web page loads
$(document).ready(function () {
    Navbar.refresh();
    NewEntryForm.refresh();
    ElementList.refresh();
    EditEntryForm.refresh();

    // Create the object that controls the "Edit Entry" form
    //editEntryForm = new EditEntryForm();
    
    // set up initial UI state
    //$("#editElement").hide();
});