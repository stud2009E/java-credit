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
import ru.sber.edu.entity.auth.Role;
import ru.sber.edu.entity.auth.User;
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
    public String home(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                       @RequestParam(value = "size", defaultValue = "20") int pageSize,
                       @RequestParam(defaultValue = "creditId") String sortBy,
                       @RequestParam(defaultValue = "acs") String order,
                       Model model) {


        if (!userService.isBankUser()){
            Sort sorting = Sort.by(sortBy);
            sorting = order.equals("acs") ? sorting.ascending() : sorting.descending();
            Pageable pageable = PageRequest.of(--pageNumber, pageSize, sorting);

            TableUtil util = new TableUtil(Credit.getColumns(), creditService.findAll(pageable));
            util.fill(model);
        }else {
            model.addAttribute("bank", bankService.getMyBank());
        }

        return "home";
    }
}