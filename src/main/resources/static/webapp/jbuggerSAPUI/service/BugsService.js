sap.ui.define([], function () {
    "use strict";

    return {

        getBugsList: function (fnDone, fnFail) {
            let sUrl = "http://localhost:8080/bugs";
            jQuery.ajax({
                url: sUrl,
                type: "get",
                async: true
            }).done(function (oData) {
                fnDone(oData);
            }).fail(function (oJqXHR) {
                fnFail(oJqXHR);
            });
        }

        // addNewDog: function(sName,sOwnerName, fnDone){
        //     let sUrl = "http://localhost:8080/dogs/dog"

        //     jQuery.ajax({
        //         url: sUrl,
        //         type: "post",
        //         async: true,
        //         contentType:"application/json",
        //         data: JSON.stringify({
        //             name: sName,
        //             ownerName: sOwnerName
        //         })
        //     }).done(function(oData){
        //         fnDone(oData);
        //     }).fail(function(oJqXHR){
            
        //     })
        // }

    }
});