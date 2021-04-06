package com.example.demo.resource;

import com.example.demo.ApplicationTest;
import com.example.demo.dto.AccountDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = { ApplicationTest.class })
@RunWith(SpringRunner.class)
public class AccountResourceTest {

    /*Тестирую на локальной базе, потому что на домашнем компьютере стоит windows 7
    и нет возможности поставить Doker, и следовательно не могу развернуть PostgreSQLContainer*/

    private final AccountDto accountDto =
            AccountDto.builder().accountNumber("11111").bic("222").amount(100.20).build();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MockMvc mvc;

    @Before
    public void before() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void add() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .post("/accounts/add").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(accountDto)).header("userInfo", "Anton"))
                .andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
        AccountDto res = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), AccountDto.class);
        accountComparison(res);
        cleanBase();
    }

    @Test
    public void findByAccountNumber() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/accounts/add").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(accountDto)).header("userInfo", "Anton"));
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .get("/accounts/get/{accountNumber}", accountDto.getAccountNumber())
                .header("userInfo", "Anton")).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
        AccountDto res = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), AccountDto.class);
        accountComparison(res);
        cleanBase();
    }

    @Test
    public void changeBalance() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/accounts/add").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(accountDto)).header("userInfo", "Anton"));
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .put("/accounts/change").contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("id", "1").param("diff", "10.45").header("userInfo", "Anton"))
                .andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
        AccountDto res = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), AccountDto.class);
        assertThat(res.getAmount()).isEqualTo(110.65);
        cleanBase();
    }

    private void accountComparison(AccountDto res){
        assertThat(res.getId()).isEqualTo(1L);
        assertThat(res.getAccountNumber()).isEqualTo(accountDto.getAccountNumber());
        assertThat(res.getBic()).isEqualTo(accountDto.getBic());
        assertThat(res.getAmount()).isEqualTo(accountDto.getAmount());
    }

    private void cleanBase(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "account");
        jdbcTemplate.execute("ALTER SEQUENCE account_id_seq RESTART WITH 1;");
    }
}
