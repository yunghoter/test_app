package com.mobile.controller;

import com.mobile.entity.Tariff; 
import com.mobile.service.TariffService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TariffControllerTest {
    @Mock
    private TariffService tariffService;

    @Mock
    private Model model;

    @InjectMocks
    private TariffController tariffController;

    @Test
    void testListTariffs() {
        List<Tariff> tariffs = List.of(new Tariff(), new Tariff());

        when(tariffService.findAll()).thenReturn(tariffs);

        String viewName = tariffController.listTariffs(model);

        verify(tariffService, times(1)).findAll();
        verify(model, times(1)).addAttribute("tariffs", tariffs);
        assertEquals("tariffs/list", viewName);
    }
}
