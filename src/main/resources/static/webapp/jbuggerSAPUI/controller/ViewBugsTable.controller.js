sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "jbuggerSAPUI/service/BugsService",
	"jbuggerSAPUI/model/ViewBugsModel",
	"sap/ui/model/json/JSONModel"
], function (Controller, BugsService, ViewBugsModel, JSONModel) {
    "use strict";

    return Controller.extend("jbuggerSAPUI.controller.ViewBugs", {

        onInit: function () {
			BugsService.getBugsList(
				(data) => {
					var model = new JSONModel({
						bugs : data
					})
					this.getView().setModel(model);
				},
				(err) => {
					console.log(err);
				}
			)
			this.oRouter = this.getOwnerComponent().getRouter();
		},

		onCreateBugButtonPress: function(){
			this.oRouter.navTo("createBug");
		}

    });
});