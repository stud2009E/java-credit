package ru.sber.edu;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.*;
import ru.sber.edu.entity.Bank;
import ru.sber.edu.entity.Credit;
import ru.sber.edu.entity.FavoriteCredit;
import ru.sber.edu.entity.auth.User;
import ru.sber.edu.repository.CreditFavoriteRepository;
import ru.sber.edu.repository.CreditOfferRepository;
import ru.sber.edu.repository.CreditRepository;
import ru.sber.edu.service.BankService;
import ru.sber.edu.service.CreditService;
import ru.sber.edu.service.UserService;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class CreditServiceTest {
    @Mock
    private CreditFavoriteRepository creditFavoriteRepository;
    @Mock
    private CreditOfferRepository creditOfferRepository;
    @Mock
    private CreditRepository creditRepository;
    @Mock
    private BankService bankService;
    @Mock
    private UserService userService;

    private CreditService creditService;

    @BeforeEach
    void setUp() {
        creditService = new CreditService();

        creditService.setCreditFavoriteRepository(creditFavoriteRepository);
        creditService.setCreditOfferRepository(creditOfferRepository);
        creditService.setCreditRepository(creditRepository);
        creditService.setBankService(bankService);
        creditService.setUserService(userService);
    }

    @AfterEach
    void tearDown() { }

    @Test
    public void findByMyBankTest(){

        Bank bank = new Bank();
        bank.setBankId(1L);

        Credit credit = new Credit();
        credit.setBankId(bank.getBankId());

        Page<Credit> credits = new PageImpl<>(List.of(credit)) ;

        Pageable pageable = PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC, "creditId"));

        when(bankService.getMyBank()).thenReturn(bank);
        when(creditRepository.findByBankId(bank.getBankId(), pageable)).thenReturn(credits);

        Page<Credit> creditsTest = creditService.findByMyBank(pageable);

        Assertions.assertEquals(credits, creditsTest);
    }

    @Test
    public void findByNameAndMyBankTest(){

        Bank bank = new Bank();
        bank.setBankId(1L);

        Credit credit = new Credit();
        credit.setBankId(bank.getBankId());
        credit.setName("Credit name");

        Page<Credit> credits = new PageImpl<>(List.of(credit)) ;

        Pageable pageable = PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC, "creditId"));

        when(bankService.getMyBank()).thenReturn(bank);
        when(creditRepository.findByBankIdAndNameContainingIgnoreCase(bank.getBankId(), credit.getName(), pageable)).thenReturn(credits);

        Page<Credit> creditsTest = creditService.findByNameAndMyBank(credit.getName(), pageable);

        Assertions.assertEquals(credits, creditsTest);
    }

    @Test
    public void findFavoriteCreditByUserTest(){

        User user = new User();

        Bank bank = new Bank();
        bank.setBankId(1L);

        FavoriteCredit favoriteCredit = new FavoriteCredit();

        Page<FavoriteCredit> favoriteCredits = new PageImpl<>(List.of(favoriteCredit));

        Pageable pageable = PageRequest.of(1, 10);
        when(creditFavoriteRepository.findByUser(user, pageable)).thenReturn(favoriteCredits);

        Page<FavoriteCredit> favoriteCreditsTest = creditService.findFavoriteCreditByUser(user, pageable);

        Assertions.assertEquals(favoriteCredits, favoriteCreditsTest);

    }

}
