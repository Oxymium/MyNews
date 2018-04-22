package com.raspberyl.mynews;

import android.content.Context;
import android.content.SharedPreferences;

import com.raspberyl.mynews.utils.SharedPreferencesUtils;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class SharedPreferencesTest {

    @Mock
    SharedPreferencesUtils sharedPreferencesUtils;
    @Mock
    Context context;


    @Test
    public void SharedPreferencesSaveStringTest () {

        String KEY = "KEY";
        String input = "Test";
        String expected = "Test";

        sharedPreferencesUtils.saveString(context, KEY, input);
        String output = sharedPreferencesUtils.loadString(context, KEY, input);

        assertEquals(expected, output);

    }

}


