package ru.sber.edu.ui.table;

import java.util.List;

public interface UiColumnList{

   static List<UiColumn> getColumns(){
       throw new UnsupportedOperationException("Override method");
   };
}