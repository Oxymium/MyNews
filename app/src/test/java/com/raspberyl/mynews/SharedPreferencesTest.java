package com.raspberyl.mynews;

import android.content.Context;
import android.content.SharedPreferences;

import com.raspberyl.mynews.utils.SharedPreferencesUtils;

import org.junit.Test;
import org.mockito.Mockito;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class SharedPreferencesTest {

    SharedPreferencesUtils sharedPreferencesUtils = mock(SharedPreferencesUtils.class);
    SharedPreferences sharedPreferences = mock(SharedPreferences.class);
    SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
    Context context = Mockito.mock(Context.class);

    String KEY = "KEY";
    String inputString = "INPUT TEST";
    String outputString;
    String expectedString = "INPUT TEST";

    @Test
    public void SharedPreferencesSaveStringTest() throws Exception {

        when(context.getString(anyString())).thenReturn("test");
        sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, string);
        editor.apply();

        SharedPreferencesUtils.saveString(context, KEY, inputString);
        outputString = SharedPreferencesUtils.loadString(context, KEY, inputString);


        assertEquals(outputString, expectedString);


    }
}


