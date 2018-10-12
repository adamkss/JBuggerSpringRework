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
        },

        getBugById: function (sId, fnDone, fnFail) {
            let sUrl = "http://localhost:8080/bugs/bug/" + sId;
            jQuery.ajax({
                url: sUrl,
                type: "get",
                async: false
            }).done(function (oData) {
                fnDone(oData);
            }).fail(function (oJqXHR) {
                fnFail(oJqXHR);
            });
        },

        createBug: function (sBugTitle, sBugDescription, sBugSeverity, sBugTargetDate, sBugAssignedToUsername, aAttachmentIds, fnDone) {
            let sUrl = "http://localhost:8080/bugs"

            jQuery.ajax({
                url: sUrl,
                type: "post",
                async: true,
                contentType: "application/json",
                data: JSON.stringify({
                    title: sBugTitle,
                    description: sBugDescription,
                    targetDate: sBugTargetDate,
                    severity: sBugSeverity,
                    createdByUsername: "admin",
                    assignedToUsername: sBugAssignedToUsername,
                    attachmentIds: aAttachmentIds.map(id => parseInt(id, 10))
                })
            }).done(function (oData) {
                fnDone(oData);
            }).fail(function (oJqXHR) {

            })
        }

    }
});