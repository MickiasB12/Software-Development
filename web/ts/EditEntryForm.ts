// a global for the EditEntryForm of the program.  See newEntryForm for 
// explanation
var editEntryForm: EditEntryForm;

// a global for the EditEntryForm of the program.  See newEntryForm for 
// explanation
//var editEntryForm: EditEntryForm;



class EditEntryForm {

    /**
     * The name of the DOM entry associated with EditEntryForm
     */
    private static readonly NAME = "EditEntryForm";

    /**
     * Track if the Singleton has been initialized
     */
    private static isInit = false;

    /**
     * Initialize the EditEntryForm by creating its element in the DOM and 
     * configuring its buttons.  This needs to be called from any public static 
     * method, to ensure that the Singleton is initialized before use
     */
    public static init() {
        if (!EditEntryForm.isInit) {
            
            //EditEntryForm.show();
            $("body").append(Handlebars.templates[EditEntryForm.NAME + ".hb"]());
            $("#" + EditEntryForm.NAME + "-OK").click(EditEntryForm.submitForm);
            $("#" + EditEntryForm.NAME + "-Close").click(EditEntryForm.hide);
            
            EditEntryForm.isInit = true;
        }
    }

    /**
     * Refresh() doesn't really have much meaning, but just like in sNavbar, we
     * have a refresh() method so that we don't have front-end code calling
     * init().
     */
    public static refresh() {
        EditEntryForm.init();
    }

    /**
     * Hide the EditEntryForm.  Be sure to clear its fields first
     */
    private static hide() {
        $("#" + EditEntryForm.NAME + "-title").val("");
        $("#" + EditEntryForm.NAME + "-message").val("");
        $("#" + EditEntryForm.NAME).modal("hide");
    }

    /**
     * Show the EditEntryForm.  Be sure to clear its fields, because there are
     * ways of making a Bootstrap modal disapper without clicking Close, and
     * we haven't set up the hooks to clear the fields on the events associated
     * with those ways of making the modal disappear.
     */
    public static show() {
        $("#" + EditEntryForm.NAME + "-title").val("");
        $("#" + EditEntryForm.NAME + "-message").val("");
        $("#" + EditEntryForm.NAME).modal("show");
    }



    public static config(data: any) {

        // $("#" + EditEntryForm.NAME + "-OK").click(EditEntryForm.submitForm(data.mData.mId));
        
        EditEntryForm.show();

        //$("#" + "EditEntryForm-title").val(id.mData.mSubject);
        $("#" + "EditEntryForm-title").val(data.mData.mSubject);
        $("#" + "EditEntryForm-message").val(data.mData.mMessage);
        $("#" + "EditEntryForm-id").val(data.mData.mId);

        
        /*
        $("#" + EditEntryForm.NAME + "-OK").click(EditEntryForm.submitForm);
        $("#" + EditEntryForm.NAME + "-Close").click(EditEntryForm.hide);
        $.ajax({
            type: "GET",
            url: "/messages" + id,
            dataType: "json",
            //data: JSON.stringify({ mSubject: title, mMessage: msg }),
            success: EditEntryForm.configHelper(id)
        });
        //$("body").append(Handlebars.templates[EditEntryForm.NAME + ".hb"]());
        
        //$("#" + EditEntryForm.NAME + "-OK").click(EditEntryForm.submitForm);
        //$("#" + EditEntryForm.NAME + "-Close").click(EditEntryForm.hide);
    }

    private static configHelper(id: any) {
        console.log(id);
        $("#" + "EditEntryForm-title").val(mData.mSubject);
        $("#" + "EditEntryForm-message").val(mData.mContent);*/
    }


    /**
     * Send data to submit the form only if the fields are both valid.  
     * Immediately hide the form when we send data, so that the user knows that 
     * their click was received.
     */
    private static submitForm() {
        // get the values of the three fields, force them to be strings, and check 
        // that neither is empty
        let title = "" + $("#" + EditEntryForm.NAME + "-title").val();
        let msg = "" + $("#" + EditEntryForm.NAME + "-message").val();
        let id = "" + $("#" + EditEntryForm.NAME + "-id").val();
        //let id = data;
        console.log(title, " ", msg, " ", id);
        if (title === "" || msg === "") {
            window.alert("Error: title or message is not valid");
            return;
        }
        EditEntryForm.hide();
        // set up an AJAX post.  When the server replies, the result will go to
        // onSubmitResponse
        $.ajax({
            type: "PUT",
            url: "/messages/" + id,
            dataType: "json",
            data: JSON.stringify({ mTitle: title, mMessage: msg }),
            success: EditEntryForm.onSubmitResponse
        });
    }

    /**
     * onSubmitResponse runs when the AJAX call in submitForm() returns a 
     * result.
     * 
     * @param data The object returned by the server
     */
    private static onSubmitResponse(data: any) {
        // If we get an "ok" message, clear the form and refresh the main 
        // listing of messages
        if (data.mStatus === "ok") {
            ElementList.refresh();
        }
        // Handle explicit errors with a detailed popup message
        else if (data.mStatus === "error") {
            window.alert("The server replied with an error:\n" + data.mMessage);
        }
        // Handle other errors with a less-detailed popup message
        else {
            window.alert("Unspecified error");
        }
    }
}