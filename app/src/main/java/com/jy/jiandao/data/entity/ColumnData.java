package com.jy.jiandao.data.entity;

import java.util.List;

/*
 * created by Cherry on 2020-01-08
 **/
public class ColumnData {

    private ColumnList list;


    public ColumnList getList() {
        return list;
    }

    public void setList(ColumnList list) {
        this.list = list;
    }

    public class ColumnList{

        private List<Column> my_column;
        private List<Column> more_column;


        public List<Column> getMyColumn() {
            return my_column;
        }

        public void setMyColumn(List<Column> myColumn) {
            this.my_column = myColumn;
        }

        public List<Column> getMore_column() {
            return more_column;
        }

        public void setMore_column(List<Column> more_column) {
            this.more_column = more_column;
        }
    }


    public class Column{

        private String id;
        private String name;
        private String back_color;
        private int type;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBack_color() {
            return back_color;
        }

        public void setBack_color(String back_color) {
            this.back_color = back_color;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
