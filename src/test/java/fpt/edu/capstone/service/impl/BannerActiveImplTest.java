package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.recruiter.BannerPositionDetailResponse;
import fpt.edu.capstone.entity.BannerActive;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.BannerActiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BannerActiveImplTest {

    @Mock
    private BannerActiveRepository mockBannerActiveRepository;

    private BannerActiveImpl bannerActiveImplUnderTest;

    @Before
    public void setUp() throws Exception {
        bannerActiveImplUnderTest = new BannerActiveImpl(mockBannerActiveRepository);
    }
    
    private BannerActive bannerActive (){
        BannerActive bannerActive = new BannerActive(0L, "imageUrl", 0L, "displayPosition", false,
                "approvalStatus", LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        return bannerActive;
    }
    @Test
    public void testGetBannersByPosition() {
        final List<BannerActive> bannerActives = Arrays.asList(bannerActive());
        when(mockBannerActiveRepository.getBannerByPosition("displayPosition", "status")).thenReturn(bannerActives);
        final List<BannerActive> result = bannerActiveImplUnderTest.getBannersByPosition("displayPosition");
    }

    @Test
    public void testGetBannersByPosition_BannerActiveRepositoryReturnsNoItems() {
        when(mockBannerActiveRepository.getBannerByPosition("displayPosition", "status"))
                .thenReturn(Collections.emptyList());
        final List<BannerActive> result = bannerActiveImplUnderTest.getBannersByPosition("displayPosition");
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testGetAllByPaymentId() {
        final List<BannerActive> bannerActives = Arrays.asList(bannerActive());
        when(mockBannerActiveRepository.getAllByPaymentId(0L)).thenReturn(bannerActives);
        final List<BannerActive> result = bannerActiveImplUnderTest.getAllByPaymentId(0L);
    }

    @Test
    public void testGetAllByPaymentId_BannerActiveRepositoryReturnsNoItems() {
        when(mockBannerActiveRepository.getAllByPaymentId(0L)).thenReturn(Collections.emptyList());
        final List<BannerActive> result = bannerActiveImplUnderTest.getAllByPaymentId(0L);
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testGetAllBannerByPaymentId() {
        when(mockBannerActiveRepository.getBannerActiveByPaymentId(0L)).thenReturn(Arrays.asList());
        final List<BannerPositionDetailResponse> result = bannerActiveImplUnderTest.getAllBannerByPaymentId(0L);
    }

    @Test
    public void testGetAllBannerByPaymentId_BannerActiveRepositoryReturnsNoItems() {
        when(mockBannerActiveRepository.getBannerActiveByPaymentId(0L)).thenReturn(Collections.emptyList());
        final List<BannerPositionDetailResponse> result = bannerActiveImplUnderTest.getAllBannerByPaymentId(0L);
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testFindByPaymentIdAndPosition() {
        final BannerActive bannerActive = new BannerActive(0L, "imageUrl", 0L, "displayPosition", false,
                "approvalStatus", LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockBannerActiveRepository.findByPaymentIdAndDisplayPosition(0L, "position")).thenReturn(bannerActive);
        final BannerActive result = bannerActiveImplUnderTest.findByPaymentIdAndPosition(0L, "position");
    }

    @Test
    public void testGetAllBannerForApproval() {
        final Page<BannerActive> bannerActives = new PageImpl<>(Arrays.asList(bannerActive()));
        when(mockBannerActiveRepository.getAllBannerForApproval(any(Pageable.class))).thenReturn(bannerActives);
        final Page<BannerActive> result = bannerActiveImplUnderTest.getAllBannerForApproval(PageRequest.of(0, 1));
    }

    @Test
    public void testGetAllBannerForApproval_BannerActiveRepositoryReturnsNoItems() {
        when(mockBannerActiveRepository.getAllBannerForApproval(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));
        final Page<BannerActive> result = bannerActiveImplUnderTest.getAllBannerForApproval(PageRequest.of(0, 1));
    }

    @Test
    public void testFindById() throws Exception {
        final Optional<BannerActive> bannerActive = Optional.of(bannerActive());
        when(mockBannerActiveRepository.findById(0L)).thenReturn(bannerActive);
        final BannerActive result = bannerActiveImplUnderTest.findById(0L);
    }

    @Test
    public void testFindById_BannerActiveRepositoryReturnsAbsent() {
        when(mockBannerActiveRepository.findById(0L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bannerActiveImplUnderTest.findById(0L)).isInstanceOf(HiveConnectException.class);
    }
}