package core.service;

import java.util.List;

public interface ISharingStandortService<SharingStandort> {
    void save(SharingStandort entity);
    SharingStandort update(SharingStandort entity);
    boolean delete(long entityId);
    SharingStandort find(long entityId);
    List<SharingStandort> findAll();
}

