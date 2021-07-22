package com.openclassrooms.realestatemanager;

import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.openclassrooms.realestatemanager.database.EstateDataBase;
import com.openclassrooms.realestatemanager.models.Estate;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class EstateDAOTest {

    //For Data
    private EstateDataBase estateDatabase;
    //DATA SET for test
    private static String estateID = "1";


    private static Estate ESTATE_HOUSE = new Estate("1","house", 50, 3, 2, 1, 150000.00, "Beautiful house", "6 rue des roches", 77131, "Touquin", true, false,
            false, true, false, "24/06/21", "", "David Bowie");

    private static Estate ESTATE_FLAT = new Estate("2","flat", 80, 2, 1, 1, 50000.00, "Very nice flat", "4 rue des roches", 77131, "Touquin", false, true,
            true, true, false, "22/06/21", "", "Mick Jagger");


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        try {
            this.estateDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getTargetContext(),
                    EstateDataBase.class)
                    .setTransactionExecutor(Executors.newSingleThreadExecutor())
                    .allowMainThreadQueries()
                    .build();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @After
    public void closeDb() throws Exception {
        estateDatabase.close();
    }


    @Test
    public void insertAndGetEstate() throws InterruptedException {
        //adding demo
        this.estateDatabase.estateDAO().insertEstate(ESTATE_HOUSE);
        this.estateDatabase.estateDAO().insertEstate(ESTATE_FLAT);
        //test
        List<Estate> estateList = LiveDataTestUtil.getValue(this.estateDatabase.estateDAO().getEstates());
        assertEquals(2, estateList.size());
    }


    @Test
    public void getEstateWhenNoEstateInserted() throws InterruptedException {
        //test
        List<Estate> estatesList = LiveDataTestUtil.getValue(this.estateDatabase.estateDAO().getEstates());
        assertTrue(estatesList.isEmpty());
    }


    @Test
    public void insertAndUpdateEstate()throws InterruptedException{
        this.estateDatabase.estateDAO().insertEstate(ESTATE_HOUSE);
        Estate estate = LiveDataTestUtil.getValue(this.estateDatabase.estateDAO().getEstates()).get(0);
        estate.setStores(true);
        this.estateDatabase.estateDAO().updateEstate(estate);

        List<Estate> estates = LiveDataTestUtil.getValue(this.estateDatabase.estateDAO().getEstates());
        assertTrue(estates.size() == 1 && estates.get(0).getStores());
    }



    @Test
    public void insertAndDeleteEstate()throws InterruptedException{
        this.estateDatabase.estateDAO().insertEstate(ESTATE_HOUSE);
        Estate estate = LiveDataTestUtil.getValue(this.estateDatabase.estateDAO().getEstate(estateID));
        this.estateDatabase.estateDAO().deleteEstate(Long.parseLong(estate.getEstateID()));

        List<Estate> estates = LiveDataTestUtil.getValue(this.estateDatabase.estateDAO().getEstates());
        assertTrue(estates.isEmpty());

    }



}
