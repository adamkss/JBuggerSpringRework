sap.ui.define([
    "sap/ui/model/json/JSONModel"
], function (JSONModel) {
    "use strict";

    return {

        getBugsModel: function () {
            return new JSONModel({
                "bugs": []
            });
        },
    };
});