package org.mercycorps.translationcards.data;

import org.mercycorps.translationcards.MainApplication;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Contains information about a collection of phrases in one or more languages.
 *
 * @author nick.c.worden@gmail.com (Nick Worden)
 */
public class Deck implements Serializable {

    private String label;
    private String publisher;
    private String externalId;
    private long dbId;
    private long timestamp;
    private boolean locked;
    private String sourceLanguageIso;
    // The dictionaries list is lazily initialized.
    private Dictionary[] dictionaries;

    public Deck(String label, String publisher, String externalId, long dbId, long timestamp,
                boolean locked, String sourceLanguageIso) {
        this.label = label;
        this.publisher = publisher;
        this.externalId = externalId;
        this.dbId = dbId;
        this.timestamp = timestamp;
        this.locked = locked;
        this.sourceLanguageIso = sourceLanguageIso;
        dictionaries = null;
    }

    public Deck(String label, String publisher, String externalId, long timestamp, boolean locked,
                String sourceLanguageIso) {
        this(label, publisher, externalId, -1, timestamp, locked, sourceLanguageIso);
    }

    public Deck() {

    }

    public String getLabel() {
        return label;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getExternalId() {
        return externalId;
    }

    public long getDbId() {
        return dbId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getCreationDateString() {
        Date date = new Date(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.US);
        return dateFormat.format(date);
    }

    public boolean isLocked() {
        return locked;
    }

    public String getSourceLanguageIso() {
        return sourceLanguageIso;
    }

    public Dictionary[] getDictionaries() {
        if (dictionaries == null) {
            dictionaries = ((MainApplication) MainApplication.getContextFromMainApp()).getDbManager().getAllDictionariesForDeck(dbId);
        }
        return dictionaries;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long save() {
        return ((MainApplication) MainApplication.getContextFromMainApp()).getDbManager().addDeck(label, publisher, timestamp, externalId, "", locked, sourceLanguageIso);
    }

    public void delete() {
        ((MainApplication) MainApplication.getContextFromMainApp()).getDbManager().deleteDeck(dbId);
    }

    public void update() {

    }

    public void setSourceLanguageIso(String sourceLanguageIso){
        this.sourceLanguageIso = sourceLanguageIso;

    }

    public String getDisplayLanguage() {
        for (Locale locale : Locale.getAvailableLocales()) {
            if (locale.getLanguage().equals(this.sourceLanguageIso)) {
                return locale.getDisplayLanguage();
            }
        }
        return "English";
    }

}
