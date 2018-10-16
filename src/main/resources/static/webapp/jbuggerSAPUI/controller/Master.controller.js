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
			this.oRouter.getRoute("viewBugsMaster").attachPatternMatched(this._onMainColumnMatched, this);
			this.oRouter.getRoute("detail").attachPatternMatched(this._onDetailColumnMatched, this);
			this._bDescendingSort = false;
			this._isTableLoaded = false;
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
				bugId = this.getOwnerComponent().getModel("viewBugsModel").getProperty(bugPath).id;
			this.oRouter.navTo("detail", { layout: oNextUIState.layout, bug: bugId });
		},

		onCreateNewBugButtonPress: function () {
			this.getOwnerComponent().getRouter().navTo("createBug");
		},

		onSearch: function (oEvent) {
			this._updateTable(oEvent.getParameter("query"));
		},

		_onMainColumnMatched: function (oEvent) {
			this._updateTable("");
			this._isTableLoaded = true;
		},

		_onDetailColumnMatched: function (oEvent) {
			if(!this._isTableLoaded){
				this._updateTable("");
			}
		}
	});
}, true);
