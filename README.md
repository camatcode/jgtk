# jgtk

unoffical java bindings aimed to be compatible with GTK v4.
licensed under LGPL v2.1+

[Project Documentation](https://ccook.pages.gitlab.gnome.org/jgtk/)

### Implemented Widgets

* GtkWidget
    * [GtkActionBar](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkActionBar.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkActionBarTest.java)]
    * [GtkAppChooserButton](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkAppChooserButton.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkAppChooserButtonTest.java)]
    * [~GtkAppChooserWidget~](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkAppChooserWidget.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkAppChooserWidgetTest.java)]
    * [GtkAspectFrame](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkAspectFrame.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkAspectFrameTest.java)]
    * [GtkBox](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkBox.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkBoxTest.java)]
    * [GtkButton](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkButton.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkButtonTest.java)]
        * [GtkLinkButton](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkLinkButton.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkLinkButtonTest.java)]
        * [GtkLockButton](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkLockButton.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkLockButtonTest.java)]
        * [GtkToggleButton](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkToggleButton.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkToggleButtonTest.java)]
    * [GtkCalendar](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkCalendar.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkCalendarTest.java)]
    * GtkCellView
    * [GtkCenterBox](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkCenterBox.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkCenterBoxTest.java)]
    * [GtkCheckButton](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkCheckButton.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkCheckButtonTest.java)]
    * GtkColorButton
    * GtkColorChooserWidget
    * GtkColorDialogButton
    * GtkColumnView
    * GtkComboBox
    * GtkDragIcon
    * GtkDrawingArea
    * GtkDropDown
    * [GtkEditableLabel](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkEditableLabel.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkEditableLabelTest.java)]
    * [GtkEntry](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkEntry.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkEntryTest.java)]
    * [GtkExpander](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkExpander.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkExpanderTest.java)]
    * GtkFileChooserWidget
    * [GtkFixed](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkFixed.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkFixedTest.java)]
    * [GtkFlowBox](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkFlowBox.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkFlowBoxTest.java)]
    * [GtkFlowBoxChild](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkFlowBoxChild.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkFlowBoxTest.java)]
    * GtkFontButton
    * GtkFontChooserWidget
    * GtkFontDialogButton
    * [GtkFrame](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkFrame.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkFrameTest.java)]
    * GtkGLArea
    * [GtkGrid](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkGrid.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkGridTest.java)]
    * [GtkHeaderBar](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkHeaderBar.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkHeaderBarTest.java)]
    * GtkIconView
    * [GtkImage](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkImage.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkImageTest.java)]
    * [~GtkInfoBar~](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkInfoBar.java) [[example](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkInfoBarTest.java)]
    * GtkInscription
    * [GtkLabel](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkLabel.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkLabelTest.java)]
        * [GtkLevelBar](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkLevelBar.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkLevelBarTest.java)]
    * [GtkListBase](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkListBase.java)
        * [GtkGridView](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkGridView.java)
        * [GtkListView](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkListView.java)
    * [GtkListBox](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkListBox.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkListBoxTest.java)]
    * [GtkListBoxRow](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkListBoxRow.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkListBoxRowTest.java)]
    * [GtkMediaControls](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkMediaControls.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkMediaControlsTest.java)]
    * [GtkMenuButton](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkMenuButton.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkMenuButtonTest.java)]
    * [GtkNotebook](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkNotebook.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkNotebookTest.java)]
    * [GtkOverlay](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkOverlay.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkOverlayTest.java)]
    * [GtkPaned](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkPaned.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkPanedTest.java)]
    * [GtkPasswordEntry](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkPasswordEntry.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkPasswordEntryTest.java)]
    * [GtkPicture](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkPicture.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkPictureTest.java)]
    * [GtkPopover](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkPopover.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkPopoverTest.java)]
        * [GtkEmojiChooser](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkEmojiChooser.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkEmojiChooserTest.java)]
        * [GtkPopoverMenu](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkPopoverMenu.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkPopoverMenuTest.java)]
    * [GtkPopoverMenuBar](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkPopoverMenuBar.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkPopoverMenuBarTest.java)]
    * [GtkProgressBar](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkProgressBar.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkProgressBarTest.java)]
    * [GtkRange](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkRange.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkRangeTest.java)]
        * [GtkScale](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkScale.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkScaleTest.java)]
    * [GtkRevealer](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkRevealer.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkRevealerTest.java)]
    * [GtkScaleButton](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkScaleButton.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkScaleButtonTest.java)]
        * [~GtkVolumeButton~](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkVolumeButton.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkVolumeButtonTest.java)]
    * [GtkScrollBar](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkScrollBar.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkScrollBarTest.java)]
    * [GtkScrolledWindow](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkScrolledWindow.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkScrolledWindowTest.java)]
    * [GtkSearchBar](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkSearchBar.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkSearchBarTest.java)]
    * [GtkSearchEntry](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkSearchEntry.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkSearchEntryTest.java)]
    * [GtkSeparator](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkSeparator.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkSeparatorTest.java)]
    * [GtkSpinButton](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkSpinButton.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkSpinButtonTest.java)]
    * [GtkSpinner](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkSpinner.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkSpinnerTest.java)]
    * [GtkStack](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkStack.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkStackTest.java)]
    * [GtkStackSidebar](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkStackSidebar.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkStackSidebarTest.java)]
    * [GtkStackSwitcher](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkStackSwitcher.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkStackSwitcherTest.java)]
    * [~GtkStatusbar~](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkStatusbar.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkStatusbarTest.java)]
    * [GtkSwitch](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkSwitch.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkSwitchTest.java)]
    * [GtkText](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkText.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkTextTest.java)]
    * [GtkTextView](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkTextView.java) [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkTextViewTest.java)]
    * [GtkTreeExpander](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkTreeExpander.java)
    * GtkTreeView
    * [GtkVideo](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkVideo.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkVideoTest.java)]
    * [GtkViewport](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkViewport.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkViewportTest.java)]
    * [GtkWindow](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkWindow.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkWindowTest.java)]
        * [GtkAboutDialog](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkAboutDialog.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkAboutDialogTest.java)]
        * [GtkApplicationWindow](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkApplicationWindow.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkApplicationWindowTest.java)]
        * [~GtkAssistant~](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkAssistant.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkAssistantTest.java)]
        * [~GtkDialog~](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkDialog.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkDialogTest.java)]
            * [~GtkAppChooserDialog~](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkAppChooserDialog.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkAppChooserDialogTest.java)]
            * [~GtkColorChooserDialog~](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkColorChooserDialog.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkColorChooserDialogTest.java)]
            * [~GtkFileChooserDialog~](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkFileChooserDialog.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkFileChooserDialogTest.java)]
            * [~GtkFontChooserDialog~](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkFontChooserDialog.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkFontChooserDialogTest.java)]
            * [~GtkMessageDialog~](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/main/java/com/gitlab/ccook/jgtk/gtk/GtkMessageDialog.java)  [[test](https://gitlab.gnome.org/ccook/jgtk/-/blob/main/src/test/java/com/gitlab/ccook/jgtk/gtk/GtkMessageDialogTest.java)]
            * GtkPageSetupUnixDialog
            * GtkPrintUnixDialog
        * GtkShortcutsWindow
    * GtkWindowControls
    * GtkWindowHandle

------

The GNOME logo and GNOME name are registered trademarks or trademarks of GNOME Foundation in the United States or other
countries. For more information, see GNOME foundation
pages: [Licensing Guidelines](https://wiki.gnome.org/FoundationBoard/Resources/LicensingGuidelines)
| [Logo and Trademarks](https://foundation.gnome.org/logo-and-trademarks/)
