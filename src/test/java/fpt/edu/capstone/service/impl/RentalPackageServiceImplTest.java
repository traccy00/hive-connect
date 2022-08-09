package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.RentalPackage;
import fpt.edu.capstone.entity.Role;
import fpt.edu.capstone.repository.RentalPackageServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
public class RentalPackageServiceImplTest {
    @InjectMocks
    RentalPackageServiceImpl rentalPackageService;

    @Mock
    RentalPackageServiceRepository rentalPackageServiceRepository;

    @Test
    void findAllTest(){
        List<RentalPackage> list = new ArrayList<>();
        RentalPackage rentalPackage = new RentalPackage();
        rentalPackage.setId(111L);
        rentalPackage.setPackageGroup("Banner");
        list.add(rentalPackage);
        Mockito.when(rentalPackageServiceRepository.findAll()).thenReturn(list);
        List <RentalPackage> rentalPackageList = rentalPackageService.findAll();
        assertEquals(1, rentalPackageList.size());
    }

    @Test
    void existByIdTest(){
        RentalPackage rentalPackage = new RentalPackage();
        rentalPackage.setId(1L);
        rentalPackage.setPackageGroup("Banner");
        Mockito.when(rentalPackageServiceRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
        assertEquals(true, rentalPackageService.existById(1L));
    }

    @Test
    void existByNameTest(){
        RentalPackage rentalPackage = new RentalPackage();
        rentalPackage.setId(1L);
        rentalPackage.setPackageGroup("Banner");
        Mockito.when(rentalPackageServiceRepository.existsByPackageGroup(ArgumentMatchers.anyString())).thenReturn(true);
        assertEquals(true, rentalPackageService.existByName("Banner"));
    }

    @Test
    void saveRentalPackage(){
        RentalPackage rentalPackage = new RentalPackage();
        rentalPackage.setId(1L);
        rentalPackage.setPackageGroup("Banner");
        rentalPackageService.saveRentalPackage(rentalPackage);
    }

    @Test
    void findByIdTest(){
        RentalPackage rentalPackage = new RentalPackage();
        rentalPackage.setId(1L);
        rentalPackage.setPackageGroup("Banner");
        Mockito.when(rentalPackageServiceRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(rentalPackage));
        Optional<RentalPackage> optional = rentalPackageService.findById(1L);
        assertEquals(1L,optional.get().getId() );
    }

    @Test
    void getRentalPackageNameTest(){
        RentalPackage rentalPackage = new RentalPackage();
        rentalPackage.setId(1L);
        rentalPackage.setPackageGroup("Banner");
        Mockito.when(rentalPackageServiceRepository.getRentalPackageName(ArgumentMatchers.anyLong())).thenReturn("Banner");
        assertEquals("Banner", rentalPackageService.getRentalPackageName(1L));
    }
}
