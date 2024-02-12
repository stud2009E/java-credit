package ru.sber.edu.service;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.sber.edu.entity.Bank;
import ru.sber.edu.projection.ClientOfBank;
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

    public Page<ClientOfBank> findClientsByBankId(Long bankId,
                                                  int pageNumber,
                                                  int pageSize,
                                                  String sortedBy,
                                                  String order)
    throws NullPointerException{

        Sort sorting = Sort.by(sortedBy);
        Pageable paging = PageRequest.of(--pageNumber, pageSize, order.equals("acs") ? sorting.ascending() : sorting.descending());

        Page<ClientOfBank> page = bankRepository.findClientsByBankId(bankId,paging);

        return page;

    }

}
