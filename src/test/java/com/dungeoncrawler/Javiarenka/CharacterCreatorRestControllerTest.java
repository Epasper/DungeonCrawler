package com.dungeoncrawler.Javiarenka;

import com.dungeoncrawler.Javiarenka.characterCreator.CharacterCreatorRestController;
import com.dungeoncrawler.Javiarenka.characterCreator.CharacterCreatorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CharacterCreatorRestControllerTest {

    @Mock
    private CharacterCreatorRestController controller;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CharacterCreatorService service = new CharacterCreatorService();

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

    }

    @Test
    public void wrongClassGivenForRestApi() throws Exception {
        //todo
        mockMvc.perform(MockMvcRequestBuilders.get("/charClassToArmor/Warrior"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
