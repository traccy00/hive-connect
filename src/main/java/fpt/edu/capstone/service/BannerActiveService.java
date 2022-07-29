package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.BannerActive;

import java.util.List;

public interface BannerActiveService {

    List<BannerActive> getBannersByPosition(String displayPosition);
}
