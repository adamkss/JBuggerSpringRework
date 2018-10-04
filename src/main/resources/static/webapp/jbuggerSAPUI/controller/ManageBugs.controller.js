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
            // this.navigateToViewBugs();
        },

        onItemSelect: function (oEvent) {
            var item = oEvent.getParameter('item');
            switch (item.getText()) {
                case "View bugs":{
                    this.navigateToViewBugs(oEvent);
                    break;
                }
                case "Create bug": {
                    this.navigateToCreateBug();
                    break;
                }
            }
        },

        // navigateToViewBugs: function () {
        //     if (!this._viewBugsView) {
        //         this._viewBugsView = sap.ui.xmlview("jbuggerSAPUI.view.ViewBugs");
        //     }

        //     this.getView().byId("pageContainer").addPage(this._viewBugsView);
        //     sap.ui.getCore().byId(this.getView().getId() + "--pageContainer").to(this._viewBugsView.sId);
        // },

        navigateToCreateBug: function () {
            if (!this._createBugView) {
                this._createBugView = sap.ui.xmlview("jbuggerSAPUI.view.CreateBug");
            }

            this.getView().byId("pageContainer").addPage(this._createBugView);
            sap.ui.getCore().byId(this.getView().getId() + "--pageContainer").to(this._createBugView.sId);
        },

        navigateToViewBugs: function(oEvent){
            var item = oEvent.getParameter('item');
			var viewId = this.getView().getId();
            sap.ui.getCore().byId(viewId + "--pageContainer").to(viewId + "--" + item.getKey());
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
        }
    });
});