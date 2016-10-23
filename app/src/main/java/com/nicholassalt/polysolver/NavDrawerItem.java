package com.nicholassalt.polysolver;

/**
 * Created by Nick on 2016-02-16.
 */

class NavDrawerItem {
    private String title;
    private int icon;
    

    NavDrawerItem(String title, int icon){
        this.title = title;
        this.icon = icon;
    }


    public String getTitle(){
        return this.title;
    }

    public int getIcon(){
        return this.icon;
    }

}
