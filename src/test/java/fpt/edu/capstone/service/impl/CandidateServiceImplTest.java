package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.common.user.GooglePojo;
import fpt.edu.capstone.dto.admin.user.CandidateManageResponse;
import fpt.edu.capstone.dto.candidate.CVBaseInformationRequest;
import fpt.edu.capstone.entity.Candidate;
import fpt.edu.capstone.entity.Users;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.CandidateRepository;
import org.junit.Before;
import org.junit.Test;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CandidateServiceImplTest {

    private CandidateServiceImpl candidateServiceImplUnderTest;

    @Before
    public void setUp() throws Exception {
        candidateServiceImplUnderTest = new CandidateServiceImpl();
        candidateServiceImplUnderTest.candidateRepository = mock(CandidateRepository.class);
    }
    private Candidate candidate(){
        Candidate candidate = new Candidate();
        candidate.setId(0L);
        candidate.setUserId(0L);
        candidate.setGender(false);
        candidate.setBirthDate(LocalDateTime.now());
        candidate.setSearchHistory("");
        candidate.setCountry("Việt Nam");
        candidate.setFullName("fullName");
        candidate.setAddress("address");
        return candidate;
    }
    @Test
    public void testGetAllCandidate() throws Exception {
        final List<Candidate> candidates = Arrays.asList(candidate());
        when(candidateServiceImplUnderTest.candidateRepository.findAll()).thenReturn(candidates);
        final List<Candidate> result = candidateServiceImplUnderTest.getAllCandidate();
    }

    @Test
    public void testGetAllCandidate_CandidateRepositoryReturnsNoItems() throws Exception {
        when(candidateServiceImplUnderTest.candidateRepository.findAll()).thenReturn(Collections.emptyList());
        final List<Candidate> result = candidateServiceImplUnderTest.getAllCandidate();
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testGetAllCandidate_ThrowsException() {
        final List<Candidate> candidates = Arrays.asList(candidate());
        when(candidateServiceImplUnderTest.candidateRepository.findAll()).thenReturn(candidates);
        assertThatThrownBy(() -> candidateServiceImplUnderTest.getAllCandidate()).isInstanceOf(Exception.class);
    }

    @Test
    public void testInsertCandidate() {
        candidateServiceImplUnderTest.insertCandidate(0L);
        verify(candidateServiceImplUnderTest.candidateRepository).insertCandidate(0L);
    }

    @Test
    public void testUpdateCandidate() {
        final Candidate newCandidate = candidate();
        candidateServiceImplUnderTest.updateCandidate(newCandidate, 0L);
    }

    @Test
    public void testFindById() throws Exception {
        final Optional<Candidate> candidate = Optional.of(candidate());
        when(candidateServiceImplUnderTest.candidateRepository.findById(0L)).thenReturn(candidate);
        final Optional<Candidate> result = candidateServiceImplUnderTest.findById(0L);
    }

    @Test
    public void testFindById_CandidateRepositoryReturnsAbsent() {
        when(candidateServiceImplUnderTest.candidateRepository.findById(0L)).thenReturn(Optional.empty());
        final Optional<Candidate> result = candidateServiceImplUnderTest.findById(0L);
        assertThat(result).isEmpty();
    }

    @Test
    public void testExistsById() {
        when(candidateServiceImplUnderTest.candidateRepository.existsById(0L)).thenReturn(false);
        final boolean result = candidateServiceImplUnderTest.existsById(0L);
        assertThat(result).isFalse();
    }

    @Test
    public void testGetCandidateById() {
        final Candidate candidate = candidate();
        when(candidateServiceImplUnderTest.candidateRepository.getCandidateById(0L)).thenReturn(candidate);
        final Candidate result = candidateServiceImplUnderTest.getCandidateById(0L);
    }

    @Test
    public void testFindCandidateByUserId() {
        final Optional<Candidate> candidate = Optional.of(candidate());
        when(candidateServiceImplUnderTest.candidateRepository.findCandidateByUserId(0L)).thenReturn(candidate);
        final Optional<Candidate> result = candidateServiceImplUnderTest.findCandidateByUserId(0L);
    }

    @Test
    public void testFindCandidateByUserId_CandidateRepositoryReturnsAbsent() {
        when(candidateServiceImplUnderTest.candidateRepository.findCandidateByUserId(0L)).thenReturn(Optional.empty());
        final Optional<Candidate> result = candidateServiceImplUnderTest.findCandidateByUserId(0L);
        assertThat(result).isEmpty();
    }

    @Test
    public void testUpdateCandidateInformation() {
        final Candidate updatedCandidate = candidate();
        candidateServiceImplUnderTest.updateCandidateInformation(updatedCandidate);
        verify(candidateServiceImplUnderTest.candidateRepository).updateCandidateInformation(false,
                LocalDateTime.now(), "country", "fullName", "address", "socialLink",
                "introduction", 0L);
    }

    @Test
    public void testUpdateCVInformation() {
        final CVBaseInformationRequest cvBaseInformationRequest = new CVBaseInformationRequest(0L, "name",
                "phoneNumber", false, LocalDateTime.now(), "country", "address", "socialLink");
        candidateServiceImplUnderTest.updateCVInformation(cvBaseInformationRequest);
        verify(candidateServiceImplUnderTest.candidateRepository).updateCVInformation(false,
                LocalDateTime.now(), "country", "name", "address", "socialLink", 0L);
    }

    @Test
    public void testUpdateIsNeedJob() {
        candidateServiceImplUnderTest.updateIsNeedJob(false, 0L);
        verify(candidateServiceImplUnderTest.candidateRepository).updateIsNeedJob(false, 0L);
    }

    @Test
    public void testSearchCandidatesForAdmin() {
        when(candidateServiceImplUnderTest.candidateRepository.searchCandidateForAdmin(any(Pageable.class),
                eq("username"), eq("email"))).thenReturn(new PageImpl<>(Arrays.asList()));
        final Page<CandidateManageResponse> result = candidateServiceImplUnderTest.searchCandidatesForAdmin(
                PageRequest.of(1, 10), "username", "email");
    }

    @Test
    public void testSearchCandidatesForAdmin_CandidateRepositoryReturnsNoItems() {
        when(candidateServiceImplUnderTest.candidateRepository.searchCandidateForAdmin(any(Pageable.class),
                eq("username"), eq("email"))).thenReturn(new PageImpl<>(Collections.emptyList()));
        final Page<CandidateManageResponse> result = candidateServiceImplUnderTest.searchCandidatesForAdmin(
                PageRequest.of(1, 10), "username", "email");
    }

    @Test
    public void testSave() {
        final Candidate candidate = candidate();
        final Candidate candidate1 = candidate();
        when(candidateServiceImplUnderTest.candidateRepository.save(any(Candidate.class))).thenReturn(candidate1);

        candidateServiceImplUnderTest.save(candidate);
        verify(candidateServiceImplUnderTest.candidateRepository).save(any(Candidate.class));
    }
    private Users users(){
        Users users = new Users();
        users.setId(0L);
        users.setUsername("username");
        users.setPassword("password");
        users.setEmail("email");
        users.setPhone("0967445450");
        users.setRoleId(0L);
        users.setIsDeleted(0);
        users.setLastLoginTime(LocalDateTime.now());
        users.setVerifiedEmail(false);
        users.setVerifiedPhone(false);
        users.setActive(true);
        users.setLocked(false);
        users.setAvatar("avatar");
        users.setResetPasswordToken("setResetPasswordToken");
        users.setGoogle(false);
        return users;
    }

    @Test
    public void testInsertGoogleCandidate() {
        final GooglePojo googlePojo = new GooglePojo();
        googlePojo.setEmail("email");
        googlePojo.setName("fullName");
        googlePojo.setPicture("avatarUrl");

        final Users user = users();
        final Candidate candidate = candidate();
        when(candidateServiceImplUnderTest.candidateRepository.save(any(Candidate.class))).thenReturn(candidate);
        final Optional<Candidate> candidate1 = Optional.of(candidate());
        when(candidateServiceImplUnderTest.candidateRepository.findById(0L)).thenReturn(candidate1);

        candidateServiceImplUnderTest.insertGoogleCandidate(googlePojo, user);
        verify(candidateServiceImplUnderTest.candidateRepository).save(any(Candidate.class));
    }

    @Test
    public void testInsertGoogleCandidate_CandidateRepositoryFindByIdReturnsAbsent() {
        final GooglePojo googlePojo = new GooglePojo();
        googlePojo.setEmail("email");
        googlePojo.setName("fullName");
        googlePojo.setPicture("avatarUrl");
        final Users user = users();
        final Candidate candidate = candidate();
        when(candidateServiceImplUnderTest.candidateRepository.save(any(Candidate.class))).thenReturn(candidate);
        when(candidateServiceImplUnderTest.candidateRepository.findById(0L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> candidateServiceImplUnderTest.insertGoogleCandidate(googlePojo, user))
                .isInstanceOf(HiveConnectException.class);
        verify(candidateServiceImplUnderTest.candidateRepository).save(any(Candidate.class));
    }
}
