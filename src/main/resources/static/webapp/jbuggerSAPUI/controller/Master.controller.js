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
			BugsService.getBugsList(
				(data) => {
					var bugsJsonModel = ViewBugsModel.getBugsModel();
					bugsJsonModel.setProperty("/bugs", data);
					this.getView().setModel(bugsJsonModel);
				},
				(err) => {
					console.log(err);
				}
			)
		},

		onListItemPress: function(oEvent){
			var oNextUIState = this.getOwnerComponent().getHelper().getNextUIState(1),
				bugPath = oEvent.getParameter("listItem").getBindingContext().getPath(),
				bugId = this.getView().getModel().getProperty(bugPath).id;
				
			this.oRouter.navTo("detail", {layout: oNextUIState.layout, bug: bugId});
		}
	});
}, true);
