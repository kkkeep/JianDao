package com.mr.k.libmvp.base;


import java.util.List;

public interface OnItemClickListener<T> {

    void onNewsClick(List<T> news, int position);
}
