

package com.mobile.service;

import com.mobile.entity.Call;
import com.mobile.repository.CallRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Collections; // Добавленный импорт



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CallServiceTest {
    @Mock
    private CallRepository callRepository;
    
    @InjectMocks
    private CallService callService;
    
    @Test
    void testFindBySubscriberId() {
        Call call1 = new Call();
        Call call2 = new Call();
        when(callRepository.findBySubscriberId(1L)).thenReturn(Arrays.asList(call1, call2));
        
        List<Call> result = callService.findBySubscriberId(1L);
        assertEquals(2, result.size());
        verify(callRepository, times(1)).findBySubscriberId(1L);
    }
    
    @Test
    void testSaveCall() {
        Call call = new Call();
        when(callRepository.save(call)).thenReturn(call);
        
        Call result = callService.save(call);
        assertNotNull(result);
        verify(callRepository, times(1)).save(call);
    }
	 @Test
void deleteAll_ShouldCallRepository() {
    callService.deleteAll();
    verify(callRepository, times(1)).deleteAll();
}
@Test
void findBySubscriberId_WhenNoCalls_ShouldReturnEmptyList() {
    when(callRepository.findBySubscriberId(1L)).thenReturn(Collections.emptyList());
    List<Call> result = callService.findBySubscriberId(1L);
    assertTrue(result.isEmpty());
}
}