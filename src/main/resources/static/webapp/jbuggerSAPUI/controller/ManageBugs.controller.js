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
            this.oRouter = this.getOwnerComponent().getRouter();
			this.oRouter.attachRouteMatched(this.onRouteMatched, this);
			this.oRouter.attachBeforeRouteMatched(this.onBeforeRouteMatched, this);
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
        },

        onBeforeRouteMatched: function(oEvent) {

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

			// this._updateUIElements();

			// Save the current route name
			this.currentRouteName = sRouteName;
			this.currentProduct = oArguments.product;
			this.currentSupplier = oArguments.supplier;
		},

		onStateChanged: function (oEvent) {
			var bIsNavigationArrow = oEvent.getParameter("isNavigationArrow"),
				sLayout = oEvent.getParameter("layout");

			// this._updateUIElements();

			// Replace the URL with the new layout if a navigation arrow was used
			if (bIsNavigationArrow) {
				this.oRouter.navTo(this.currentRouteName, {layout: sLayout, product: this.currentProduct, supplier: this.currentSupplier}, true);
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