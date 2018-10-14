sap.ui.define([
    "sap/ui/model/json/JSONModel"
], function (JSONModel) {
    "use strict";

    return {

        getCreateBugModel: function () {
            return new JSONModel({
                bugTitle: "",
                bugTitleState: "None",
                bugDescriptionState: "None",
                bugDescription: "",
                bugSeverity: undefined,
                UsersWithNameAndUsername: [],
                assignedToUsername: "",
                bugTargetDate: "",
                attachmentsNames: []
            });
        },
    };
});