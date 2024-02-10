package ru.sber.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.sber.edu.entity.Bank;
import ru.sber.edu.entity.Credit;
import ru.sber.edu.repository.BankRepository;
import ru.sber.edu.repository.CreditRepository;
import java.util.Optional;

@Service
public class CreditService {

    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private BankRepository bankRepository;

    public Page<Credit> findByBankId(Long bankId, int pageNumber, int pageSize, String sortedBy, String order) throws NullPointerException {

        Sort sorting = Sort.by(sortedBy);
        Pageable paging = PageRequest.of(--pageNumber, pageSize, order.equals("acs") ? sorting.ascending() : sorting.descending());

        return creditRepository.findByBankId(bankId, paging);
    }

    public Page<Credit> findByNameAndBankId(String name, Long bankId, int pageNumber, int pageSize, String sortedBy, String order) throws NullPointerException {

        Sort sorting = Sort.by(sortedBy);
        Pageable paging = PageRequest.of(--pageNumber, pageSize, order.equals("acs") ? sorting.ascending() : sorting.descending());

        return creditRepository.findByBankIdAndNameContainingIgnoreCase(bankId, name, paging);
    }

    public void createCredit(Credit credit){

        Optional<Bank> bank = bankRepository.findById(credit.getBankId());
        if (bank.isEmpty()){
            throw new NullPointerException("There is no bank "+credit.getBankId());
        }
        creditRepository.saveAndFlush(credit);
    }
    public Optional<Credit> findById(Long creditId){
        return creditRepository.findById(creditId);
    };
}