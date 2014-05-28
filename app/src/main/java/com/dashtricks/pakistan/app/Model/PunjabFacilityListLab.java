package com.dashtricks.pakistan.app.Model;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dan on 5/19/2014.
 */
public class PunjabFacilityListLab {
    private HashMap<String, List<ListTypeFacility>> mFacilities;

    private static PunjabFacilityListLab fLab;
    private Context mAppContext;

    private PunjabFacilityListLab(Context appContext){
        mFacilities = new HashMap<String, List<ListTypeFacility>>();
        DistrictToFacilitySetup();
    }

    public static PunjabFacilityListLab get(Context c) {
        if(fLab == null) {
            fLab = new PunjabFacilityListLab(c.getApplicationContext());
        }
        return fLab;
    }

    public HashMap<String, List<ListTypeFacility>> getFacilitiesList() {
        return mFacilities;
    }

    private void DistrictToFacilitySetup() {
        String[] districts = {"Attock", "Bahawalnagar", "Bahawalpur", "Bhakkar", "Chakwal", "Chiniot", "Dera Ghazi Khan",
                "Faisalabad", "Gujranwala", "Gujrat", "Hafizabad", "Jhang", "Jhelum", "Kasur", "Khanewal", "Khushab",
                "Lahore", "Layyah", "Lodhran", "Mandi Bahauddin", "Mianwali", "Multan", "Muzaffargarh", "Narowal",
                "Nankana Sahib", "Okara", "Pakpattan", "Rahim Yar Khan", "Rajanpur", "Rawalpindi", "Sahiwal", "Sargodha",
                "Sheikhupura", "Sialkot", "Toba Tek Singh", "Vehari"};

        for(String d: districts) {
            List<ListTypeFacility> temp = new ArrayList<ListTypeFacility>();
            for(int i = 0; i < 100; i++) {
                ListTypeFacility ltf = new ListTypeFacility(i);
                temp.add(ltf);
            }
            mFacilities.put(d, temp);
        }
    }

}
