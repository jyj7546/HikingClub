package hikingclub.model1.mybatis.service;

import java.util.List;

import hikingclub.model1.mybatis.model.Mountain;
import hikingclub.model1.retrofit.model.MountainInfo;

public interface MountainService {
    
    public Mountain getMountainItem(Mountain input) throws Exception;
    
    public List<Mountain> getMountainList(Mountain input) throws Exception;
    
    public int getMountainCount(Mountain input) throws Exception;
    
    public int addMountain(Mountain input) throws Exception;
    
    public int editMountain(Mountain input) throws Exception;
    
    public int deleteMountain(Mountain input) throws Exception;
    
}