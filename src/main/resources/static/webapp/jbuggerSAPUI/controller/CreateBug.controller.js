sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/m/MessageBox",
    "sap/m/MessageToast",
    "sap/ui/model/json/JSONModel",
    "jbuggerSAPUI/service/BugsService"
], function (Controller, MessageBox, MessageToast, JSONModel, BugsService) {
    "use strict";

    return Controller.extend("jbuggerSAPUI.controller.CreateBug", {

        /**
         * Executed when controller is instantiated
         * @public
         */
        onInit: function () {
        }
    });
});