sap.ui.define([
	'sap/ui/core/UIComponent',
	'sap/f/FlexibleColumnLayoutSemanticHelper',
	'sap/ui/model/json/JSONModel'],
function(UIComponent,FlexibleColumnLayoutSemanticHelper,JSONModel) {
	"use strict";
	
	var Component = UIComponent.extend("jbuggerSAPUI.Component", {
		metadata: {
			manifest: "json"
		},
		
		init: function () {
			UIComponent.prototype.init.apply(this, arguments);
			var oModel = new JSONModel();
			this.setModel(oModel);
			this.getRouter().initialize();
		},
		createContent: function () {
			return sap.ui.view({
				viewName: "jbuggerSAPUI.view.ManageBugs",
				type: "XML"
			});
		},
		/**
		* Returns an instance of the semantic helper
		* @returns {sap.f.FlexibleColumnLayoutSemanticHelper} An instance of the semantic helper
		*/
		getHelper: function () {
			var oFCL = this.getRootControl().byId("flexibleColumnLayout"),
			oParams = jQuery.sap.getUriParameters(),
			oSettings = {
				defaultTwoColumnLayoutType: sap.f.LayoutType.TwoColumnsMidExpanded,
				defaultThreeColumnLayoutType: sap.f.LayoutType.ThreeColumnsMidExpanded,
				mode: oParams.get("mode"),
				initialColumnsCount: oParams.get("initial"),
				maxColumnsCount: oParams.get("max")
			};
			
			return FlexibleColumnLayoutSemanticHelper.getInstanceFor(oFCL, oSettings);
		}
	});
	return Component;
	
});
