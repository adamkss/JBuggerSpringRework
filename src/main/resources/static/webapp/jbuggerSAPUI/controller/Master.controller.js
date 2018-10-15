sap.ui.define([
	'jquery.sap.global',
	"sap/ui/model/json/JSONModel",
	"sap/ui/core/mvc/Controller",
	"sap/ui/model/Filter",
	"sap/ui/model/FilterOperator",
	'sap/ui/model/Sorter',
	'sap/m/MessageBox',
	'jbuggerSAPUI/model/ViewBugsModel',
	'jbuggerSAPUI/service/BugsService'
], function (jQuery, JSONModel, Controller, Filter, FilterOperator, Sorter, MessageBox, ViewBugsModel, BugsService) {
	"use strict";

	return Controller.extend("jbuggerSAPUI.controller.Master", {
		onInit: function () {
			this.oRouter = this.getOwnerComponent().getRouter();
			this._bDescendingSort = false;
			this._updateTable("");
		},

		_updateTable: function (sFilter) {
			BugsService.getBugsList(
				sFilter,
				(data) => {
					var bugsJsonModel = ViewBugsModel.getBugsModel();
					bugsJsonModel.setProperty("/bugs", data);
					this.getOwnerComponent().setModel(bugsJsonModel, "viewBugsModel");
				},
				(err) => {
					console.log(err);
				}
			)
		},

		onListItemPress: function (oEvent) {
			var oNextUIState = this.getOwnerComponent().getHelper().getNextUIState(1),
				bugPath = oEvent.getParameter("listItem").getBindingContext("viewBugsModel").getPath(),
				bugPathComponents = bugPath.split("/");
			this.oRouter.navTo("detail", { layout: oNextUIState.layout, bug: bugPathComponents[bugPathComponents.length-1] });
		},

		onCreateNewBugButtonPress: function () {
			this.getOwnerComponent().getRouter().navTo("createBug");
		},

		onSearch: function (oEvent) {
			this._updateTable(oEvent.getParameter("query"));
		}
	});
}, true);
