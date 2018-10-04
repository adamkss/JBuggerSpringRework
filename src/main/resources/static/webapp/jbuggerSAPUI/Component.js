sap.ui.define(['sap/ui/core/UIComponent'],
	function(UIComponent) {
	"use strict";

	var Component = UIComponent.extend("jbuggerSAPUI.Component", {

		metadata : {
			rootView : {
				"viewName": "jbuggerSAPUI.view.ManageBugs",
				"type": "XML",
				"async": true
			},
			dependencies : {
				libs : [
					"sap.m"
				]
			},
			config : {
				sample : {
					stretch : true,
					files : [
						"ManageBugs.view.xml"
					]
				}
			}
		}
	});

	return Component;

});
