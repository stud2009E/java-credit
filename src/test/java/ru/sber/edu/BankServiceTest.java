package ru.sber.edu;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import ru.sber.edu.controller.bank.BankController;
import ru.sber.edu.entity.Bank;
import ru.sber.edu.entity.BankUser;
import ru.sber.edu.entity.auth.User;
import ru.sber.edu.repository.BankRepository;
import ru.sber.edu.repository.BankUserRepository;
import ru.sber.edu.service.BankService;
import ru.sber.edu.service.UserService;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public final class BankServiceTest {

    @Mock
    private BankRepository repository;

    private BankService service;

    @Mock
    private UserService userService;

    @Mock
    private BankUserRepository bankUserRepository;

    @BeforeEach
    void setUp() {
        service = new BankService();
        service.setBankRepository(repository);
        service.setUserService(userService);
        service.setBankUserRepository(bankUserRepository);
    }

    @AfterEach
    void tearDown() { }

    @Test
    public void findByIdTest() throws Exception{

        Bank bank = new Bank();
        bank.setBankId(1L);
        bank.setName("Альфа");

        Optional<Bank> bankOptional = Optional.of(bank);

        when(repository.findById(1L)).thenReturn(bankOptional);

        Optional<Bank> bankOptionalTest = service.findById(1L);

        Assertions.assertEquals(bankOptional, bankOptionalTest);

    }

    @Test
    public void getMyBankTest() throws Exception{

        Bank bank = new Bank();
        bank.setBankId(1L);
        bank.setName("Альфа");
        Optional<Bank> optionalBank = Optional.of(bank);


        User user = new User();
        user.setUserId(1L);

        BankUser bankUser = new BankUser();
        bankUser.setBankId(user.getUserId());

        Optional<BankUser> bankByUserOptional = Optional.of(bankUser);

        Optional<Bank> bankOptional = Optional.of(bank);

        when(repository.findById(1L)).thenReturn(bankOptional);
        when(userService.getUser()).thenReturn(user);
        when(bankUserRepository.findById(user.getUserId())).thenReturn(bankByUserOptional);
        when(repository.findById(bank.getBankId())).thenReturn(optionalBank);

        Bank bankTest = service.getMyBank();

        Assertions.assertEquals(bank, bankTest);

    }
}
