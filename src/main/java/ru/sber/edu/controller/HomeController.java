package ru.sber.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sber.edu.entity.Credit;
import ru.sber.edu.service.BankService;
import ru.sber.edu.service.CreditService;
import ru.sber.edu.service.UserService;
import ru.sber.edu.ui.table.TableUtil;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private CreditService creditService;

    @Autowired
    private BankService bankService;

    @GetMapping(value = "/")
    public String home(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
                       @RequestParam(value = "size", required = false, defaultValue = "10") int pageSize,
                       Model model) {

        if (!userService.isBankUser()){
            Pageable pageable = PageRequest.of(--pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "creditId"));

            TableUtil<Credit> util = new TableUtil<>(creditService.findAll(pageable), Credit.getColumns());
            util.addTableDataToModel(model);
            util.addPagingDataToModel(model);
        }else {
            model.addAttribute("bank", bankService.getMyBank());
        }

        return "home";
    }


    @GetMapping(path = {"/search"})
    public String searchCredit(@RequestParam(value = "value", required = false) String value,
                                @RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
                               @RequestParam(value = "size", required = false, defaultValue = "10") int pageSize,
                               Model model) {

        if (value != null && !value.isEmpty()) {
            Pageable pageable = PageRequest.of(--pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "creditId"));

            TableUtil<Credit> util = new TableUtil<>(creditService.findByNameContainingIgnoreCase(value, pageable), Credit.getColumns());
            util.addTableDataToModel(model);
            util.addPagingDataToModel(model);

            model.addAttribute("searchValue", value);

            return "home";
        } else {
            return "redirect:?page=" + pageNumber + "&size=" + pageSize;
        }
    }
}