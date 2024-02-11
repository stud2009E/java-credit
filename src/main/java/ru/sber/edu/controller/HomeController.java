package ru.sber.edu.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sber.edu.entity.Credit;
import ru.sber.edu.service.CreditService;
import ru.sber.edu.service.UserService;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private CreditService creditService;

    @Autowired
    private ObjectMapper mapper;

    @GetMapping(value = "/")
    public String main(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                       @RequestParam(value = "size", defaultValue = "10") int pageSize,
                       @RequestParam(defaultValue = "creditId") String sortBy,
                       @RequestParam(defaultValue = "acs") String order,
                       Model model){

        Sort sorting = Sort.by(sortBy);
        sorting = order.equals("acs") ? sorting.ascending() : sorting.descending();

        Pageable pageable = PageRequest.of(--pageNumber, pageSize, sorting );

        Page<Credit> page = creditService.findAll(pageable);

        List<String> headers = Credit.getColumns();

        List<Map<String, Object>> rows = page.stream().map(
                credit -> mapper.convertValue(credit, new TypeReference<Map<String, Object>>() {})
        ).toList();

        model.addAttribute("headers", headers);
        model.addAttribute("rows", rows);

        return "home";
    }

}