package org.mercycorps.translationcards.activity.addDeck;


import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.mercycorps.translationcards.R;
import org.mercycorps.translationcards.activity.MyDecksActivity;
import org.mercycorps.translationcards.data.Dictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static org.mercycorps.translationcards.ui.LanguageDisplayUtil.getDestLanguageListDisplay;

public class ReviewAndSaveActivity extends AddDeckActivity {
    @Bind(R.id.deck_name)TextView deckName;
    @Bind(R.id.deck_information)TextView deckInformation;
    @Bind(R.id.lock_icon)FrameLayout lockIcon;
    @Bind(R.id.translation_languages)TextView translationLanguagesTextView;
    @Bind(R.id.deck_menu)FrameLayout deckMenu;


    @Override
    public void inflateView() {
        setContentView(R.layout.activity_deck_review_and_save);

    }


    @Override
    public void initStates() {
        deckName.setText(getContextFromIntent().getDeckLabel());
        deckInformation.setText(getContextFromIntent().getDeckInformation());
        disableDeckCopyingAndLockIconIfUnlocked();
        fillLanguagesListTextView();
        deckMenu.setVisibility(View.GONE);
    }

    @OnClick(R.id.deck_review_and_save_button)
    protected void saveButtonClicked() {
        getContextFromIntent().save();
        startNextActivity(this, MyDecksActivity.class);
    }

    @OnClick(R.id.deck_review_and_save_back)
    public void backButtonClicked(){
        startNextActivity(this, EnterAuthorActivity.class);
    }

    @Override
    public void setBitmapsForActivity() {
        setBitmap(R.id.enter_source_language_image, R.drawable.enter_source_language_image);
    }

    private void disableDeckCopyingAndLockIconIfUnlocked() {

        int deckVisible = (getContextFromIntent().isDeckLocked()) ? View.VISIBLE : View.GONE;
        lockIcon.setVisibility(deckVisible);
        deckInformation.setPadding(getPaddingInPx(16), 0, getPaddingInPx(16), getPaddingInPx(20));


    }

    private int getPaddingInPx(int padding) {
        final float scale = translationLanguagesTextView.getResources().getDisplayMetrics().density;
        return (int) (padding* scale + 0.5f);
    }

    private void fillLanguagesListTextView() {
        String languagesInput = getContextFromIntent().getLanguagesInput();
        if (languagesInput != null) {
            String[] languagesList = languagesInput.split(",");
            List<Dictionary> dictionaries = new ArrayList<>();
            Integer itemIndex = 0;
            for (String language : languagesList) {
                dictionaries.add(new Dictionary(language.trim()));
            }
            translationLanguagesTextView.setText(getDestLanguageListDisplay(dictionaries, "  "));
        }
    }
}