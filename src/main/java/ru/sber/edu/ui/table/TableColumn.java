package ru.sber.edu.ui.table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TableColumn implements UiColumn{
    String field;

    String title;

    UiFieldType type;

    public TableColumn(String field){
        this.field = field;
        this.title = field;
        this.type = UiFieldType.TEXT;
    }

    public TableColumn(String field, String title){
        this.field = field;
        this.title = title;
        this.type = UiFieldType.TEXT;
    }

    public String getType(){
        return type.name().toString();
    }
}