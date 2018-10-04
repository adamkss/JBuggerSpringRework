sap.ui.define([
	'sap/ui/core/UIComponent',
	'sap/f/FlexibleColumnLayoutSemanticHelper'],
function(UIComponent,FlexibleColumnLayoutSemanticHelper) {
	"use strict";
	
	var Component = UIComponent.extend("jbuggerSAPUI.Component", {
		metadata: {
			manifest: "json"
		},
		
		init: function () {
			UIComponent.prototype.init.apply(this, arguments);
			this.getRouter().initialize();
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
