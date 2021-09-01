package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page object of Note section in home.html for Selenium test.
 */
public class NoteSection {

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "noteModal")
    private  WebElement noteModal;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "noteSave" )
    private WebElement noteSaveButton;

    @FindBy(id = "noteAdd")
    private WebElement noteAddButton;

    @FindBy(id = "noteEdit")
    private WebElement noteEditButton;

    @FindBy(id = "noteDelete")
    private WebElement noteDeleteButton;

    public NoteSection(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public void goToNoteTab() {
        notesTab.click();
    }

    public void fillNoteForm(String noteTitle, String noteDescription) {
        this.noteTitle.sendKeys(noteTitle);
        this.noteDescription.sendKeys(noteDescription);
    }

    public void clickSaveNote() {
        noteSaveButton.click();
    }

    public void clickAddNote() {
        noteAddButton.click();
    }

    public void clickEditNote() {
        noteEditButton.click();
    }

    public void clickDeleteNote() {
        noteDeleteButton.click();
    }

    public String getNoteTitle() {
        return noteTitle.getAttribute("value");
    }

    public String getNoteDescription() {
        return noteDescription.getAttribute("value");
    }

}
