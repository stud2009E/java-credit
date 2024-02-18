package ru.sber.edu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.sber.edu.controller.bank.BankController;
import ru.sber.edu.entity.Bank;
import ru.sber.edu.entity.Credit;
import ru.sber.edu.entity.CreditOffer;
import ru.sber.edu.entity.CreditOfferStatus;
import ru.sber.edu.service.BankService;
import ru.sber.edu.service.CreditOfferService;
import ru.sber.edu.service.CreditService;
import ru.sber.edu.ui.DisplayMode;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = BankController.class)

@AutoConfigureMockMvc
@WithMockUser(username = "bank1", roles = "BANK",
              password = "$2a$10$1CaDfjxloSDzWKUbXQDd1emJxub6QOG3fIR8L7ZXsjan1Owsyhfy")
public class BankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreditService creditService;

    @MockBean
    private BankService bankService;

    @MockBean
    private CreditOfferService creditOfferService;

    @Test
    void showCreditsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bank/credit/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("bank/credits"))
                .andExpect(MockMvcResultMatchers.model().attribute("pageSize", 10))
                .andExpect(MockMvcResultMatchers.model().attribute("sortBy", "creditId"))
                .andExpect(MockMvcResultMatchers.model().attribute("order", "acs"));
    }

    @Test
    void showCreditTest() throws Exception {

        Bank bank = new Bank();
        bank.setBankId(1L);

        Credit credit = new Credit();

        when(bankService.getMyBank()).thenReturn(bank);
        when(creditService.findByCreditIdAndBankId(1L,bankService.getMyBank().getBankId())).thenReturn(credit);

        mockMvc.perform(MockMvcRequestBuilders.get("/bank/credit/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("bank/creditShow"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("credit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("mode"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("action"));
    }

    @Test
    void searchEmptyCreditTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/bank/credit/search")
                        .param("name",""))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/bank/credit/all"));
    }

    @Test
    void creditEditTest() throws Exception{

        Bank bank = new Bank();
        bank.setBankId(1L);

        Credit credit = new Credit();

        when(bankService.getMyBank()).thenReturn(bank);
        when(creditService.findByCreditIdAndBankId(1L,bankService.getMyBank().getBankId())).thenReturn(credit);

        mockMvc.perform(MockMvcRequestBuilders.get("/bank/credit/edit/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("bank/creditEdit"))
                .andExpect(MockMvcResultMatchers.model().attribute("credit", credit))
                .andExpect(MockMvcResultMatchers.model().attribute("mode", DisplayMode.EDIT.toString()));
    }

    @Test
    void getCreateCreditTest() throws Exception{

        Bank bank = new Bank();
        bank.setBankId(1L);
        when(bankService.getMyBank()).thenReturn(bank);

        mockMvc.perform(MockMvcRequestBuilders.get("/bank/credit/create"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("bank/creditCreate"))
                .andExpect(MockMvcResultMatchers.model().attribute("mode", DisplayMode.CREATE.toString()));
    }

    @Test
    void creditOfferTest() throws Exception{

        Bank bank = new Bank();
        bank.setBankId(1L);

        Credit credit = new Credit();

        CreditOffer creditOffer = new CreditOffer();
        creditOffer.setCreditOfferStatus(new CreditOfferStatus(CreditOfferStatus.StatusType.REQUEST));

        Optional<CreditOffer> creditOfferOptional = Optional.of(creditOffer);

        when(bankService.getMyBank()).thenReturn(bank);
        when(creditService.findByCreditIdAndBankId(1L,bankService.getMyBank().getBankId())).thenReturn(credit);
        when(creditOfferService.findById(1L,1L)).thenReturn(creditOfferOptional);

        mockMvc.perform(MockMvcRequestBuilders.get("/bank/creditOffer/credit/1/user/1")
                        .param("creditId", "1" )
                        .param("userId", "1" )
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("bank/creditOffer"))
                .andExpect(MockMvcResultMatchers.model().attribute("mode", DisplayMode.EDIT.toString()));
    }

}
