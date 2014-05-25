package com.dashtricks.pakistan.app.General;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Donohue on 5/7/14.
 */
public class Facility {
    private String name;
    private int facId;
    private String subdis;
    private double currentCapacity; // no direct set. Updated by adding refrigerators
    private double requiredCapacity; // Should ONLY be set with a value returned by the Calculator
    private double amountShortBy;
    private double percentDeficient;
    private int population;
    private Set<PowerSource> powerSources;
    private Set<Refrigerator> refrigerators;

    // All these things 
    public Facility(String name, int facId, Set<PowerSource> ps, SQLiteDatabase db) {
	    this.name = name;
	    this.facId = facId;
	    this.powerSources = ps;
        refrigerators = new HashSet<Refrigerator>();
        refrigerators = populateRefrigerators(db);
        for(Refrigerator r : refrigerators) {
            if(r.isWorking()){
                currentCapacity += r.getVolume();
            }
        }
    }

// Read from the database
    private Set<Refrigerator> populateRefrigerators(SQLiteDatabase db) {
        Set<Refrigerator> refrigerators = new HashSet<Refrigerator>();
        String query = "SELECT * FROM Refrigerators WHERE FacilityID=" + facId;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Refrigerator r = new Refrigerator();
                r.setUniqueId(Integer.parseInt(cursor.getString(0)));
                r.setAge(Integer.parseInt(cursor.getString(4)) - Refrigerator.CURRENT_YEAR);
                // Sum of +4 volume and -20 volume. Each refrigerator should have at most one nonzero of the two
                r.setVolume(Integer.parseInt(cursor.getString(14)) + Integer.parseInt(cursor.getString(15)));
                // Avoiding lookup table. 1 is working, 2 and 3 are not working
                r.setWorking(Integer.parseInt(cursor.getString(6)) == 1);
                r.setPs(PowerSource.ELECTRICITY); // needs two more layers of lookup, and this only
                                                  // matters for refrigerators we ourselves allocate
                // Adding NameVO to list
                refrigerators.add(r);
            } while (cursor.moveToNext());
        }
    return refrigerators;
    }

    public String getName() {
	return name;
    }
    
    public int getFacId() {
	return facId;
    }

    public boolean canUseSource(PowerSource p) {
	return powerSources.contains(p);
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void addRefrigerator(Refrigerator refrigerator) {
        refrigerators.add(refrigerator);
        if(refrigerator.isWorking()){
            currentCapacity += refrigerator.getVolume();
        }
    }

    public int getPopulation() {
        return population;
    }

    // No setter method because this is calculated based on refrigerators
    public double getCurrentCapacity() {
	    return currentCapacity;
    }

    // Done because Calculator imports Facility, and circular dependencies are ugly
    public void setRequiredCapacity(double rc){
	    requiredCapacity = rc;
        amountShortBy = rc - currentCapacity;
        percentDeficient = (1 - currentCapacity/requiredCapacity) * 100;
    }

    public double getRequiredCapacity() {
	return requiredCapacity;
    }

    public String getSubdis() {
        return subdis;
    }

}
