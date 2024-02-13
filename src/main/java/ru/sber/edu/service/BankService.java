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

    public Optional<Bank> findById(Long bankId){
        return bankRepository.findById(bankId);
    }

}
