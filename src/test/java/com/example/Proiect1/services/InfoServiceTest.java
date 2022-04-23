package com.example.Proiect1.services;

import com.example.Proiect1.domain.*;
import com.example.Proiect1.exceptions.ResourceNotFoundException;
import com.example.Proiect1.repositories.InfoRepository;
import com.example.Proiect1.repositories.ListenerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InfoServiceTest {

    @Mock
    InfoRepository infoRepository;

    @InjectMocks
    InfoServiceImpl infoService;

    public Info createTestInfo() {

        Listener listener = new Listener();
        listener.setId(1L);
        listener.setName("ListenerServiceTest");

        Info info = new Info();
        info.setId(1L);
        info.setFirstName("InfoFirstNameServiceTest");
        info.setLastName("InfoLastNameServiceTest");
        info.setListener(listener);

        listener.setInfo(info);

        return info;

    }

    @Test
    public void findById() {

        Info info = createTestInfo();
        Long id = info.getId();

        when(infoRepository.findById(id)).thenReturn(Optional.of(info));

        Info result = infoService.findById(id);

        assertEquals(info, result);
        verify(infoRepository, times(1)).findById(id);

    }

    @Test
    public void findByIdNotFound() {

        Info info = createTestInfo();
        Long id = info.getId();
        ResourceNotFoundException exception = new ResourceNotFoundException("Info with id " + id + " not found!");

        when(infoRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> infoService.findById(id));

        assertEquals(exception.getMessage(), result.getMessage());

    }

    @Test
    public void findByListenerId() {

        Info info = createTestInfo();
        Long listenerId = info.getListener().getId();

        when(infoRepository.findByListenerId(listenerId)).thenReturn(Optional.of(info));

        Info result = infoService.findByListenerId(listenerId);

        assertEquals(info, result);
        verify(infoRepository, times(1)).findByListenerId(listenerId);

    }

    @Test
    public void findByListenerIdNotFound() {

        Info info = createTestInfo();
        Long listenerId = info.getListener().getId();
        ResourceNotFoundException exception = new ResourceNotFoundException("Info with listener id " + listenerId + " not found!");

        when(infoRepository.findByListenerId(listenerId)).thenReturn(Optional.empty());

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> infoService.findByListenerId(listenerId));

        assertEquals(exception.getMessage(), result.getMessage());

    }

    @Test
    public void updateById() {

        Info info = createTestInfo();
        Long id = info.getId();
        String firstName = "InfoFirstNameServiceTestUpdated";
        String lastName = "InfoLastNameServiceTestUpdated";
        Listener listener = new Listener();
        listener.setId(1L);
        listener.setName("ListenerServiceTest");
        Info infoUpdated = createTestInfo();
        infoUpdated.setFirstName(firstName);
        infoUpdated.setLastName(lastName);
        infoUpdated.setListener(listener);

        when(infoRepository.findById(id)).thenReturn(Optional.of(info));
        when(infoRepository.findById(id)).thenReturn(Optional.of(infoUpdated));

        Info result = infoService.update(infoUpdated);

        assertEquals(infoUpdated, result);
        verify(infoRepository, times(2)).findById(id);
        verify(infoRepository, times(1)).updateById(id, firstName, lastName, listener);

    }

    @Test
    public void updateByIdNotFound() {

        Info info = createTestInfo();
        Long id = info.getId();
        String firstName = "InfoFirstNameServiceTestUpdated";
        String lastName = "InfoLastNameServiceTestUpdated";
        Listener listener = new Listener();
        listener.setId(1L);
        listener.setName("ListenerServiceTest");
        Info infoUpdated = createTestInfo();
        infoUpdated.setFirstName(firstName);
        infoUpdated.setLastName(lastName);;
        infoUpdated.setListener(listener);
        ResourceNotFoundException exception = new ResourceNotFoundException("Info with id " + id + " not found!");

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> infoService.update(infoUpdated));

        assertEquals(exception.getMessage(), result.getMessage());

    }

    @Test
    public void deleteById() {

        Info info = createTestInfo();
        Long id = info.getId();

        when(infoRepository.findById(id)).thenReturn(Optional.of(info));

        infoService.deleteById(id);

        assertThat(infoRepository.count()).isEqualTo(0);
        verify(infoRepository, times(1)).findById(id);
        verify(infoRepository, times(1)).deleteById(id);

    }

    @Test
    public void deleteByIdNotFound() {

        Info info = createTestInfo();
        Long id = info.getId();
        ResourceNotFoundException exception = new ResourceNotFoundException("Info with id " + id + " not found!");

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> infoService.deleteById(id));

        assertEquals(exception.getMessage(), result.getMessage());

    }

    @Test
    public void deleteByListenerId() {

        Info info = createTestInfo();
        Long listenerId = info.getListener().getId();

        when(infoRepository.findByListenerId(listenerId)).thenReturn(Optional.of(info));

        infoService.deleteByListenerId(listenerId);

        assertThat(infoRepository.count()).isEqualTo(0);
        verify(infoRepository, times(1)).findByListenerId(listenerId);
        verify(infoRepository, times(1)).deleteByListenerId(listenerId);

    }

    @Test
    public void deleteByListenerIdNotFound() {

        Info info = createTestInfo();
        Long listenerId = info.getListener().getId();
        List<Info> infos = new ArrayList<>();
        ResourceNotFoundException exception = new ResourceNotFoundException("No info with listener id " + listenerId + " found!");

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> infoService.deleteByListenerId(listenerId));

        assertEquals(exception.getMessage(), result.getMessage());

    }

}
