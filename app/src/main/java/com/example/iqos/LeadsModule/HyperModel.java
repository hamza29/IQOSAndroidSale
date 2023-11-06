package com.example.iqos.LeadsModule;

import java.util.ArrayList;
import java.util.List;

public class HyperModel {
    String title;
   List<ActivityHyperCareLeads.Day1> day1s = new ArrayList<ActivityHyperCareLeads.Day1>();

    public HyperModel(String title,  List<ActivityHyperCareLeads.Day1> day1s) {
        this.title = title;
        this.day1s = day1s;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ActivityHyperCareLeads.Day1> getDay1s() {
        return day1s;
    }

    public void setDay1s(List<ActivityHyperCareLeads.Day1> day1s) {
        this.day1s = day1s;
    }
}
