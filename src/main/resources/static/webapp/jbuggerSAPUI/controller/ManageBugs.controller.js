sap.ui.define([
    "sap/ui/core/mvc/Controller"
], function (Controller) {
    "use strict";

    return Controller.extend("jbuggerSAPUI.controller.ManageBugs", {

        /**
         * Executed when controller is instantiated
         * @public
         */
        onInit: function () {
            this._objectPageLayout = this.byId("ObjectPageLayout");
            this.oRouter = this.getOwnerComponent().getRouter();
            this.oRouter.attachRouteMatched(this.onRouteMatched, this);
            this.oRouter.attachBeforeRouteMatched(this.onBeforeRouteMatched, this);
            this._navigationList = this.getView().byId("navigationList");
            this._viewBugsNavListItem = this.getView().byId("viewBugsNavListItem");
            this._createBugNavListItem = this.getView().byId("createBugNavListItem");
            this._viewBugsTableNavListItem = this.getView().byId("viewBugsTableNavListItem");
        },


        onItemSelect: function (oEvent) {
            var item = oEvent.getParameter('item');
            switch (item.getText()) {
                case "View bugs": {
                    this.navigateToViewBugs();
                    break;
                }
                case "Create bug": {
                    this.navigateToCreateBug();
                    break;
                }
                case "View bugs table": {
                    this.navigateToViewBugsTable();
                    break;
                }
            }
        },

        navigateToCreateBug: function () {
            this.oRouter.navTo("createBug");
        },

        navigateToViewBugs: function () {
            var oNextUIState = this.getOwnerComponent().getHelper().getNextUIState(0);
            this.oRouter.navTo("viewBugsMaster", { layout: oNextUIState.layout });
        },

        navigateToViewBugsTable: function () {
            this.oRouter.navTo("viewBugsTable");
        },

        onSideNavButtonPress: function () {
            var viewId = this.getView().getId();
            var toolPage = sap.ui.getCore().byId(viewId + "--toolPage");
            var sideExpanded = toolPage.getSideExpanded();

            this._setToggleButtonTooltip(sideExpanded);

            toolPage.setSideExpanded(!toolPage.getSideExpanded());
        },

        _setToggleButtonTooltip: function (bLarge) {
            var toggleButton = this.byId('sideNavigationToggleButton');
            if (bLarge) {
                toggleButton.setTooltip('Large Size Navigation');
            } else {
                toggleButton.setTooltip('Small Size Navigation');
            }
        },

        onBeforeRouteMatched: function (oEvent) {

            var oModel = this.getOwnerComponent().getModel();

            var sLayout = oEvent.getParameters().arguments.layout;

            // If there is no layout parameter, query for the default level 0 layout (normally OneColumn)
            if (!sLayout) {
                var oNextUIState = this.getOwnerComponent().getHelper().getNextUIState(0);
                sLayout = oNextUIState.layout;
            }

            // Update the layout of the FlexibleColumnLayout
            if (sLayout) {
                oModel.setProperty("/layout", sLayout);
            }
        },

        onRouteMatched: function (oEvent) {
            var sRouteName = oEvent.getParameter("name"),
                oArguments = oEvent.getParameter("arguments");


            // Save the current route name
            this.currentRouteName = sRouteName;
            this.currentBug = oArguments.bug;
            this.currentSupplier = oArguments.supplier;
            this._updateUIElements();
            switch (sRouteName) {
                case "home":
                    var oNextUIState = this.getOwnerComponent().getHelper().getNextUIState(0);
                    this.oRouter.navTo("viewBugsMaster", { layout: oNextUIState.layout });
                    break;

                case "viewBugsMaster":
                case "detail":
                    var viewId = this.getView().getId();
                    sap.ui.getCore().byId(viewId + "--pageContainer").to(viewId + "--flexibleColumnLayout");

                    this._navigationList.setSelectedItem(this._viewBugsNavListItem);

                    break;

                case "createBug":
                    if (!this._createBugView) {
                        this._createBugView = sap.ui.xmlview("jbuggerSAPUI.view.CreateBug");
                    }
                    this.getView().byId("pageContainer").addPage(this._createBugView);
                    sap.ui.getCore().byId(this.getView().getId() + "--pageContainer").to(this._createBugView.sId);

                    this._navigationList.setSelectedItem(this._createBugNavListItem);

                    break;

                case "viewBugsTable":
                    if (!this._viewBugsTable) {
                        this._viewBugsTable = this.getOwnerComponent().runAsOwner( () => {
                            return sap.ui.xmlview("jbuggerSAPUI.view.ViewBugsTable")
                        });
                    }
                    this.getView().byId("pageContainer").addPage(this._viewBugsTable);
                    sap.ui.getCore().byId(this.getView().getId() + "--pageContainer").to(this._viewBugsTable.sId);

                    this._navigationList.setSelectedItem(this._viewBugsTableNavListItem);

                    break;
            }
        },

        onStateChanged: function (oEvent) {
            var bIsNavigationArrow = oEvent.getParameter("isNavigationArrow"),
                sLayout = oEvent.getParameter("layout");

            this._updateUIElements();

            // Replace the URL with the new layout if a navigation arrow was used
            if (bIsNavigationArrow) {
                this.oRouter.navTo(this.currentRouteName, { layout: sLayout, bug: this.currentBug, supplier: this.currentSupplier }, true);
            }
        },

        // Update the close/fullscreen buttons visibility
        _updateUIElements: function () {
            var oModel = this.getOwnerComponent().getModel();
            var oUIState = this.getOwnerComponent().getHelper().getCurrentUIState();
            oModel.setData(oUIState);
        },

        onExit: function () {
            this.oRouter.detachRouteMatched(this.onRouteMatched, this);
            this.oRouter.detachBeforeRouteMatched(this.onBeforeRouteMatched, this);
        }
    });
});