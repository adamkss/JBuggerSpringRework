sap.ui.define([
	"sap/ui/model/json/JSONModel",
	"sap/ui/core/mvc/Controller",
	"jbuggerSAPUI/service/BugsService"
], function (JSONModel, Controller, BugsService) {
	"use strict";

	return Controller.extend("jbuggerSAPUI.controller.Detail", {
		onInit: function () {
			this.oRouter = this.getOwnerComponent().getRouter();
			this.oModel = this.getOwnerComponent().getModel();

			this.oRouter.getRoute("viewBugsMaster").attachPatternMatched(this._onProductMatched, this);
			this.oRouter.getRoute("detail").attachPatternMatched(this._onProductMatched, this);
			this.oRouter.getRoute("detailDetail").attachPatternMatched(this._onProductMatched, this);
		},
		handleItemPress: function (oEvent) {
			var oNextUIState = this.getOwnerComponent().getHelper().getNextUIState(2),
				supplierPath = oEvent.getSource().getBindingContext("products").getPath(),
				supplier = supplierPath.split("/").slice(-1).pop();

			this.oRouter.navTo("detailDetail", { layout: oNextUIState.layout, supplier: supplier });
		},
		handleFullScreen: function () {
			var sNextLayout = this.oModel.getProperty("/actionButtonsInfo/midColumn/fullScreen");
			this.oRouter.navTo("detail", { layout: sNextLayout, bug: this._bug });
		},
		handleExitFullScreen: function () {
			var sNextLayout = this.oModel.getProperty("/actionButtonsInfo/midColumn/exitFullScreen");
			this.oRouter.navTo("detail", { layout: sNextLayout, bug: this._bug });
		},
		handleClose: function () {
			var sNextLayout = this.oModel.getProperty("/actionButtonsInfo/midColumn/closeColumn");
			this.oRouter.navTo("viewBugsMaster", { layout: sNextLayout });
		},
		_onProductMatched: function (oEvent) {
			this._bug = oEvent.getParameter("arguments").bug || this._bug || "0";
			BugsService.getBugById(
				this._bug,
				(bug) => {
					this.getView().setModel(
						new JSONModel(bug),
						"selectedBugModel"
					)
				},
				(err) => {
					console.log(err)
				}
			)
		}
	});
}, true);
