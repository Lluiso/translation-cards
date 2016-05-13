package org.mercycorps.translationcards.activity.addDeck;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mercycorps.translationcards.BuildConfig;
import org.mercycorps.translationcards.R;
import org.mercycorps.translationcards.util.TestAddDeckActivityHelper;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mercycorps.translationcards.util.TestAddDeckActivityHelper.createActivityToTest;
import static org.mercycorps.translationcards.util.TestAddDeckActivityHelper.createActivityToTestWithContext;
import static org.mercycorps.translationcards.util.TestAddTranslationCardActivityHelper.click;
import static org.mercycorps.translationcards.util.TestAddTranslationCardActivityHelper.findImageView;
import static org.mercycorps.translationcards.util.TestAddTranslationCardActivityHelper.setText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)
public class EnterDeckSourceLanguageActivityTest {

    @Test
    public void shouldReturnToEnterDeckTitleActivityWhenBackButtonIsClicked(){
        Activity activity = createActivityToTest(EnterDeckSourceLanguageActivity.class);
        click(activity, R.id.deck_source_language_back_arrow);
        assertEquals(EnterDeckTitleActivity.class.getName(), shadowOf(activity).getNextStartedActivity().getComponent().getClassName());
    }

    @Test
    public void shouldStartDeckDestinationLanguagesActivityWhenNextButtonIsClicked(){
        Activity activity = createActivityToTest(EnterDeckSourceLanguageActivity.class);
        click(activity, R.id.deck_source_language_next_label);
        assertEquals(EnterDeckDestinationLanguagesActivity.class.getName(), shadowOf(activity).getNextStartedActivity().getComponent().getClassName());
    }

    @Test
    public void shouldInflateEnterSourceLanguageImageWhenActivityIsCreated() {
        Activity activity = createActivityToTest(EnterDeckSourceLanguageActivity.class);
        ImageView imageView = findImageView(activity, R.id.deck_source_language_image);
        assertEquals(R.drawable.enter_phrase_image, shadowOf(imageView.getDrawable()).getCreatedFromResId());
    }

    @Test
    public void shouldSaveSourceLanguageToContextWhenNextButtonIsClicked() {
        NewDeckContext newDeckContext = mock(NewDeckContext.class);
        Activity activity = createActivityToTestWithContext(EnterDeckSourceLanguageActivity.class, newDeckContext);
        setText(activity, R.id.deck_source_language_text, TestAddDeckActivityHelper.DEFAULT_SOURCE_LANGUAGE);
        click(activity, R.id.deck_source_language_next_label);
        verify(newDeckContext).setSourceLanguageIso(TestAddDeckActivityHelper.DEFAULT_SOURCE_LANGUAGE_ISO);
    }

    @Test
    public void shouldSetSourceLanguagePickerAsClickable() {
        Activity activity = createActivityToTest(EnterDeckSourceLanguageActivity.class);
        View sourceLanguagePicker = activity.findViewById(R.id.deck_source_language_picker);
        assertTrue(sourceLanguagePicker.isClickable());
    }
}