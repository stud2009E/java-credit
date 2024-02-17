package ru.sber.edu.ui.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class TableUtil <T>{

    private Page<T> page;
    private List<UiColumn> headers;

    private Map<String, Object> convertUsingReflection(Object object) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field: fields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(object));
        }

        return map;
    }

    private List<Map<String,Object>> transformData(Page<T> page){
        return page.stream().map(
                obj -> {
                    try {
                        return convertUsingReflection(obj);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
        ).toList();
    }

    public void addTableDataToModel(Model model) {
        model.addAttribute("headers", headers);
        model.addAttribute("rows", transformData(page));
    }

    public void addPagingDataToModel(Model model) {
        model.addAttribute("pageable", page.getPageable());
        model.addAttribute("page", page);
    }
}
