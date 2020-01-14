package com.mr.k.banner;

/*
 * created by Cherry on 2020-01-14
 **/
public interface Indicator {

    void setHeight(int height);

    void setCount(int count);

    void setCurrentItem(int position, int index);

    void onItemSelect(int position);


    void setId(int id);

    int getId();


}
