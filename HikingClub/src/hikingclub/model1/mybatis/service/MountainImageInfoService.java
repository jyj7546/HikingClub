package hikingclub.model1.mybatis.service;

import java.util.List;

import hikingclub.model1.mybatis.model.MountainImageInfo;

public interface MountainImageInfoService {
    
    public MountainImageInfo getMountainImageInfoItem(MountainImageInfo input) throws Exception;
    
    public List<MountainImageInfo> getMountainImageInfoList(MountainImageInfo input) throws Exception;
    
    public int getMountainImageInfoCount(MountainImageInfo input) throws Exception;
    
    public int addMountainImageInfo(MountainImageInfo input) throws Exception;
    
    public int editMountainImageInfo(MountainImageInfo input) throws Exception;
    
    public int deleteMountainImageInfo(MountainImageInfo input) throws Exception;
    
}