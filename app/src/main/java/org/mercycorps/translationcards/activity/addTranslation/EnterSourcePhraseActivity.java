package org.mercycorps.translationcards.activity.addTranslation;

import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.mercycorps.translationcards.R;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class EnterSourcePhraseActivity extends AddTranslationActivity {

    @Bind(R.id.source_phrase_field)TextView sourcePhraseTextView;
    @Bind(R.id.source_phrase_title)TextView sourcePhraseTitle;
    @Bind(R.id.activity_enter_source_phrase_next_label)LinearLayout nextButton;
    @Bind(R.id.activity_enter_source_phrase_next_text)TextView nextButtonText;
    @Bind(R.id.activity_enter_source_phrase_next_image)ImageView nextButtonImage;

    @Override
    protected void setActivityTitle() {
        sourcePhraseTitle.setText(String.format(getString(R.string.enter_source_phrase_activity_titile), getContextFromIntent().getDictionary().getLabel()));
    }

    @Override
    public void inflateView() {
        setContentView(R.layout.activity_enter_source_phrase);
    }

    @Override
    protected void initStates() {
        setSourcePhrase();
    }

    private void setSourcePhrase() {
        sourcePhraseTextView.setText(getContextFromIntent().getTranslation().getLabel());
    }

    @Override
    public void setBitmapsForActivity() {
        setBitmap(R.id.enter_source_phrase_image, R.drawable.enter_phrase_image);
    }

    @OnClick(R.id.enter_source_phrase_activity_back_label)
    public void cancelButtonClick() {
        String userEnteredSourcePhrase = sourcePhraseTextView.getText().toString();
        updateContextWithSourceText();
        startNextActivity(EnterSourcePhraseActivity.this, GetStartedActivity.class);
    }

    @OnTextChanged(R.id.source_phrase_field)
    public void sourcePhraseTextChanged() {
        String userEnteredSourcePhrase = sourcePhraseTextView.getText().toString();
        nextButton.setClickable(!userEnteredSourcePhrase.isEmpty());
        updateNextButtonState(userEnteredSourcePhrase);
    }

    protected void updateNextButtonState(String userEnteredSourcePhrase) {
        int backgroundResource = userEnteredSourcePhrase.isEmpty() ? R.drawable.forward_arrow_40p : R.drawable.forward_arrow;
        int buttonColor = userEnteredSourcePhrase.isEmpty() ? R.color.textDisabled : R.color.primaryTextColor;
        nextButtonText.setTextColor(ContextCompat.getColor(this, buttonColor));
        nextButtonImage.setBackgroundResource(backgroundResource);
    }

    @OnClick(R.id.activity_enter_source_phrase_next_label)
    public void enterSourcePhraseNextLabelClicked(){
        if (!nextButton.isClickable()) return;
        updateContextWithSourceText();
        startNextActivity(EnterSourcePhraseActivity.this, EnterTranslatedPhraseActivity.class);
    }

    protected void updateContextWithSourceText() {
        String userEnteredSourcePhrase = sourcePhraseTextView.getText().toString();
        getContextFromIntent().setSourceText(userEnteredSourcePhrase);
    }

}