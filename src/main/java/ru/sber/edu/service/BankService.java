package ru.sber.edu.service;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.sber.edu.entity.Bank;
import ru.sber.edu.entity.BankUser;
import ru.sber.edu.entity.auth.User;
import ru.sber.edu.projection.ClientOfBank;
import ru.sber.edu.repository.BankRepository;
import ru.sber.edu.repository.BankUserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BankService {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private BankUserRepository bankUserRepository;

    @Autowired
    private UserService userService;

    public Optional<Bank> findById(Long bankId){
        return bankRepository.findById(bankId);
    }

    public Page<ClientOfBank> findClientsByBankId(Long bankId, int pageNumber,int pageSize,
                                                  String sortedBy, String order) throws NullPointerException{

        Sort sorting = Sort.by(sortedBy);
        Pageable paging = PageRequest.of(--pageNumber, pageSize, order.equals("acs") ? sorting.ascending() : sorting.descending());

        Page<ClientOfBank> page = bankRepository.findClientsByBankId(bankId,paging);

        return page;
    }

    public Bank getMyBank(){
         User user = userService.getUser();
         List<BankUser> bankByUser = bankUserRepository.findBankByUser(user);

         if (bankByUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find bank");
         }
         BankUser bankUser = bankByUser.get(0);
         return bankUser.getBank();

         return bankByUser.get(0).getBank();
    }

}
