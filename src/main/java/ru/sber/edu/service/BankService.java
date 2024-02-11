package ru.sber.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.edu.entity.Bank;
import ru.sber.edu.repository.BankRepository;

import java.util.Optional;

@Service
public class BankService {

    @Autowired
    private BankRepository bankRepository;

    public Bank findById(Long bankId) throws NullPointerException{

        Optional<Bank> bank = bankRepository.findById(bankId);

        if (bank.isEmpty()){
            throw new NullPointerException("There is no bank with ID " + bankId);
        }

        return bank.get();
    }

}
