package ru.sber.edu.ui.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Data
@AllArgsConstructor
public class TableUtil<T> {

    private Page<T> page;
    private List<UiColumn> headers;

    private Map<String, Object> convertUsingReflection(Object object) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(object));
        }

        return map;
    }


    public <P> List<Map<String, Object>> transformPlainData(Page<T> page, Function<T, P> fnPlainItem) {
        return page.stream()
                .map(fnPlainItem)
                .map(
                        obj -> {
                            try {
                                return convertUsingReflection(obj);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        }
                ).toList();
    }

    public <P> void addTableDataToModel(Model model, Function<T, P> fnPlainItem) {
        model.addAttribute("headers", headers);
        model.addAttribute("rows", transformPlainData(page, fnPlainItem));
    }

    public void addTableDataToModel(Model model) {
        model.addAttribute("headers", headers);
        model.addAttribute("rows", transformPlainData(page, Function.identity()));
    }

    public void addPagingDataToModel(Model model) {
        model.addAttribute("pageable", page.getPageable());
        model.addAttribute("page", page);
    }
}
