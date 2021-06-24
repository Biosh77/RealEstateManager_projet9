package com.openclassrooms.realestatemanager;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.openclassrooms.realestatemanager.contentProvider.EstateProvider;
import com.openclassrooms.realestatemanager.database.EstateDataBase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class EstateContentProviderTest {


    //for data
    private ContentResolver mContentResolver;

    //Data Set for test
    private static Integer estateID = 1;

    @Before
    public void setUp() {
        InstrumentationRegistry.getInstrumentation().getTargetContext().deleteDatabase("MyEstateDatabase.db");
        Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(), EstateDataBase.class)
                .allowMainThreadQueries()
                .build();
        mContentResolver = InstrumentationRegistry.getInstrumentation().getContext().getContentResolver();
    }

    @Test
    public void getEstateWhenNoEstateInserted() {
        final Cursor cursor =
                mContentResolver.query(ContentUris.withAppendedId(EstateProvider.URI_ESTATE, estateID),
                        null, null, null, null);
        assertThat(cursor,notNullValue());
        assertThat(cursor.getCount(), is(0));
        cursor.close();
    }

    @Test
    public void insertAndGetEstate() {
        //Before : Adding demo estate
        final Uri estateUri = mContentResolver.insert(EstateProvider.URI_ESTATE, generateEstate());
        //test
        final Cursor cursor =
                mContentResolver.query(ContentUris.withAppendedId(EstateProvider.URI_ESTATE, estateID),
                        null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(1));
        assertThat(cursor.moveToFirst(), is(true));
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("estateType")), is ("House"));
        mContentResolver.delete(Objects.requireNonNull(estateUri), null, null);
    }

    private ContentValues generateEstate() {
        final ContentValues values = new ContentValues();

        values.put("estateType", "House");
        values.put("surface", 50);
        values.put("rooms", 3);
        values.put("bedrooms", 1);
        values.put("bathrooms",1);
        values.put("price", 150000);
        values.put("description", "Beautiful house");
        values.put("address", "6 Rue des roches");
        values.put("postalCode", 77131);
        values.put("city", "Touquin");
        values.put("schools", true);
        values.put("stores", true);
        values.put("park", true);
        values.put("restaurants", false);
        values.put("entryDateEstate", "24/06/2021");
        values.put("soldDate", "");
        values.put("agentName", "David Bowie");

        return values;
    }
}
