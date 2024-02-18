package ru.sber.edu.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.edu.entity.Bank;
import ru.sber.edu.entity.BankUser;
import ru.sber.edu.entity.auth.User;
import ru.sber.edu.exception.CreditBaseException;
import ru.sber.edu.repository.BankRepository;
import ru.sber.edu.repository.BankUserRepository;

import java.util.Optional;

@Data
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

    public Bank getMyBank(){
         User user = userService.getUser();
         Optional<BankUser> bankByUser = bankUserRepository.findById(user.getUserId());

         if (bankByUser.isEmpty()){
            throw new CreditBaseException("Unable to find bank");
         }

         Optional<Bank> optionalBank = bankRepository.findById(bankByUser.get().getBankId());

         if (optionalBank.isEmpty()){
             throw new CreditBaseException("Unable to find bank");
         }

         return optionalBank.get();
    }

}
