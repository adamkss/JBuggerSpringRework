sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/m/MessageBox",
    "sap/m/MessageToast",
    "sap/ui/model/json/JSONModel",
    "jbuggerSAPUI/service/BugsService",
    "jbuggerSAPUI/service/UsersService"
], function (Controller, MessageBox, MessageToast, JSONModel, BugsService, UsersService) {
    "use strict";

    return Controller.extend("jbuggerSAPUI.controller.CreateBug", {

        /**
         * Executed when controller is instantiated
         * @public
         */
        onInit: function () {
            this._wizard = this.byId("CreateBugWizard");
            this._oNavContainer = this.byId("wizardNavContainer");
            this._oWizardContentPage = this.byId("wizardContentPage");
            this._oWizardReviewPage = sap.ui.xmlfragment("jbuggerSAPUI.view.CreateBugWizardReviewPage", this);
            this._bugTitleInput = this.byId("bugTitle");
            this._bugDescriptionInput = this.byId("bugDescription");
            this._bugSeveritySelect = this.byId("bugSeveritySelect");
            this._assignedToComboBox = this.byId("assignedToComboBox");
            this._targetDatePicker = this.byId("targetDatePicker");
            this._assignedToSkipButton = this.byId("assignedToSkipButton");
            this._targetDateSkipButton = this.byId("targetDateSkipButton");
            this._attachmentsIds = [];
            this._oNavContainer.addPage(this._oWizardReviewPage);
            this._fileUploaders = [this.byId("fileUploader")];
            this._noOfFilesToUpload = 0;
            this._noOfFilesUploaded = 0;
            this._attachmentsNames = [];

            var oModel = new JSONModel({
                bugTitle: "",
                bugTitleState: "None",
                bugDescriptionState: "None",
                bugDescription: "",
                bugSeverity: undefined,
                UsersWithNameAndUsername: [],
                assignedToUsername: "",
                bugTargetDate: "",
                attachmentsNames: []
            });
            this._oModel = oModel;
            this.getView().setModel(oModel);

        },

        generalInfoValidation: function () {
            var title = this._bugTitleInput.getValue();
            var description = this._bugDescriptionInput.getValue();

            if (title.length < 3 && title !== "") {
                this._oModel.setProperty("/bugTitleState", "Error");
            } else {
                this._oModel.setProperty("/bugTitleState", "None");
            }

            if (description.length < 10 && description !== "") {
                this._oModel.setProperty("/bugDescriptionState", "Error");
            } else {
                this._oModel.setProperty("/bugDescriptionState", "None");
            }

            if (description.length >= 10 && title.length >= 3) {
                this._wizard.validateStep(this.byId("GeneralInfoStep"));
            } else {
                this._wizard.invalidateStep(this.byId("GeneralInfoStep"));
            }
        },

        onGeneralInfoStepComplete: function () {
            this._bugTitleInput.setEnabled(false);
            this._bugDescriptionInput.setEnabled(false);
        },

        onSeverityStepActivation: function () {

        },

        onSeveritySelectionChange: function (oEvent) {
            this._wizard.validateStep(this.byId("SeverityStep"));
        },

        onSeverityStepComplete: function () {
            this._bugSeveritySelect.setEnabled(false);
        },

        onAssignedToStepActivation: function () {
            UsersService.getUsersWithNameAndUsernameAndId(
                (oData) => {
                    this.getView().getModel().setProperty("/UsersWithNameAndUsername", oData);
                }
            )
        },

        onAssignedToComboboxSelectionChange: function () {
            let sAssignedToUsername = this.getView().getModel().getProperty("/assignedToUsername");
            if (sAssignedToUsername !== "") {
                this._wizard.validateStep(this.byId("AssignedToStep"));
            } else {
                this._wizard.invalidateStep(this.byId("AssignedToStep"));
            }
        },

        onAssignedToSkipPressed: function () {
            this._wizard.nextStep();
        },

        onAssignedToStepComplete: function () {
            this._assignedToComboBox.setEnabled(false);
            this._assignedToSkipButton.setVisible(false);
            this._assignedToSkipButton.setEnabled(false);
        },

        onTargetDateStepActivation: function () {

        },

        onTargetDateValueChange: function (oEvent) {
            let sDateValue = oEvent.getParameter("value");
            if (sDateValue !== "") {
                this._wizard.validateStep(this.byId("TargetDateStep"));
            } else {
                this._wizard.invalidateStep(this.byId("TargetDateStep"));
            }
        },

        onTargetDateSkipPressed: function () {
            this._wizard.nextStep();
        },

        onTargetDateStepComplete: function () {
            this._targetDatePicker.setEnabled(false);
            this._targetDateSkipButton.setVisible(false);
            this._targetDateSkipButton.setEnabled(false);
            if (this._targetDatePicker.getValue() === "")
                console.log("empty")
        },

        wizardCompletedHandler: function () {
            this._fileUploaders.forEach(fileUploader => {
                if (fileUploader.getValue() !== "") {
                    this._attachmentsNames.push(
                        {
                            name: fileUploader.getValue()
                        }
                    );
                }
            })
            this.getView().getModel().setProperty("/attachmentsNames", this._attachmentsNames);
            this._oNavContainer.to(this._oWizardReviewPage);
        },

        onAddOneMoreFileUploaderPress: function () {
            var oNewFileUploader = new sap.ui.unified.FileUploader(
                {
                    name: "fileUpload",
                    sendXHR: true,
                    buttonText: "Select file...",
                    uploadUrl: "http://localhost:8080/attachments",
                    uploadComplete: this.handleUploadComplete.bind(this)
                });
            this._fileUploaders.push(oNewFileUploader);

            this.byId("verticalLayoutForUploads").addContent(oNewFileUploader);
        },

        onRemoveOneFileUploaderPress: function () {
            this.byId("verticalLayoutForUploads").removeContent(this._fileUploaders.pop());
        },

        handleUploadComplete: function (oEvent) {
            var attachmentId = oEvent.getParameter("responseRaw");
            this._attachmentsIds.push(attachmentId);
            this._noOfFilesUploaded++;
            if (this._noOfFilesToUpload === this._noOfFilesUploaded) {
                this._saveNewBug();
            }
        },

        onFileChanged: function (oEvent) {


        },

        _saveNewBug: function () {
            var oModel = this.getView().getModel().getData();
            BugsService.createBug(
                oModel.bugTitle,
                oModel.bugDescription,
                oModel.bugSeverity,
                oModel.bugTargetDate,
                oModel.assignedToUsername,
                this._attachmentsIds,
                () => { MessageToast.show("Success") }
            );
        },

        onFormSubmission: function () {
            this._fileUploaders.forEach(fileUploader => {
                if (fileUploader.getValue() !== "") {
                    this._noOfFilesToUpload++;
                    fileUploader.upload()
                }
            })

            if (this._noOfFilesToUpload === 0) {
                this._saveNewBug();
            }//else wait for _saveNewBug to be called at the end of the uploads
        },
        backToWizardContent : function () {
			this._oNavContainer.backToPage(this._oWizardContentPage.getId());
        },
        
        _handleNavigationToStep : function (iStepNumber) {
			var fnAfterNavigate = function () {
				this._wizard.goToStep(this._wizard.getSteps()[iStepNumber]);
				this._oNavContainer.detachAfterNavigate(fnAfterNavigate);
			}.bind(this);

			this._oNavContainer.attachAfterNavigate(fnAfterNavigate);
			this.backToWizardContent();
        },
        
        _handleMessageBoxOpen : function (sMessage, sMessageBoxType) {
			MessageBox[sMessageBoxType](sMessage, {
				actions: [MessageBox.Action.YES, MessageBox.Action.NO],
				onClose: function (oAction) {
					if (oAction === MessageBox.Action.YES) {
						this._handleNavigationToStep(0);
						this._wizard.discardProgress(this._wizard.getSteps()[0]);
					}
				}.bind(this)
			});
        },
        
        handleWizardCancel : function () {
			this._handleMessageBoxOpen("Are you sure you want to cancel your report?", "warning");
		}
    });
});