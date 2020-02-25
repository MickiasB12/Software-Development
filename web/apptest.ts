var describe: any;
var it: any;
var expect: any;


describe("Tests of basic math functions", function() {
    it("Adding 1 should work", function() {
        var foo = 0;
        foo += 1;
        expect(foo).toEqual(1);
    });
    
    it("Subtracting 1 should work", function () {
        var foo = 0;
        foo -= 1;
        expect(foo).toEqual(-1);
    });
});

describe("Test of basic buttons", function() {
    it("UI Test: Add Button shows modal", function(){
        // click the button for showing the add button
        $("#"+Navbar.NAME + "-add").click(NewEntryForm.show);
        // expect that the add form is not hidden
        expect($("#" + NewEntryForm.NAME + ".modal.fade.in").length).toEqual(0);
        // reset the UI, so we don't mess up the next test
        $("#" + NewEntryForm.NAME + "-Close").click();        
    });
    it("UI Test: Edit Button shows modal", function(){
        //click the editbtn
        $("#"+ElementList.NAME + "-editbtn").click(ElementList.clickEdit);

        expect($("#" + EditEntryForm.NAME + ".modal.fade.in").length).toEqual(0);

        $("#" + EditEntryForm.NAME + "-Close").click();
    });
});