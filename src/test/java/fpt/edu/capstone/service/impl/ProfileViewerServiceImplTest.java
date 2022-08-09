package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.Payment;
import fpt.edu.capstone.entity.ProfileViewer;
import fpt.edu.capstone.repository.ProfileViewerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
public class ProfileViewerServiceImplTest {
    @InjectMocks
    ProfileViewerServiceImpl profileViewerService;

    @Mock
    ProfileViewerRepository profileViewerRepository;

    @Test
    void getProfileViewerOfCvTest(){
        ProfileViewer viewer = new ProfileViewer();
        Page <ProfileViewer> viewerPage = null;
        viewer.setId(1L);
        viewer.setCvId(1L);
        Pageable pageable = PageRequest.of(0,10);
//        Mockito.when(profileViewerRepository.getAllByCvId(pageable, ArgumentMatchers.anyLong())).thenReturn(viewerPage);
//        Page<ProfileViewer> page = profileViewerService.getProfileViewerOfCv(pageable, viewer.getCvId());
//        assertEquals(1, page.getTotalElements());
    }

    @Test
    void getByCvIdAndViewerIdTest(){
        ProfileViewer viewer = new ProfileViewer();
        viewer.setId(1L);
        viewer.setCvId(1L);
        viewer.setViewerId(1L);
        Mockito.when(profileViewerRepository.getByCvIdAndViewerId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(viewer);
        ProfileViewer pro = profileViewerService.getByCvIdAndViewerId(viewer.getCvId(), viewer.getViewerId());
        assertEquals(1L, pro.getId());
    }

    @Test
    void findAllTest(){
//        ProfileViewer viewer = new ProfileViewer();
//        PageRequest pageRequest = null;
//        viewer.setId(1L);
//        viewer.setCvId(1L);
//        viewer.setViewerId(1L);
//        Pageable pageable = PageRequest.of(0,10);
//        Page<ProfileViewer> viewerPage = null;
//        Mockito.when(profileViewerRepository.findAll(pageable)).thenReturn(viewerPage);
//        profileViewerService.findAll(pageRequest);
//        assertEquals();
    }

    @Test
    void getByCvIdAndViewerIdOptionalTest(){
        ProfileViewer viewer = new ProfileViewer();
        viewer.setId(1L);
        viewer.setCvId(1L);
        viewer.setViewerId(1L);
        Mockito.when(profileViewerRepository.
                getByCvIdAndViewerIdOptional(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(Optional.of(viewer));
        Optional <ProfileViewer> optional = profileViewerService.getByCvIdAndViewerIdOptional(viewer.getCvId(), viewer.getViewerId());
        assertEquals(1, optional.get().getId());
    }
}
