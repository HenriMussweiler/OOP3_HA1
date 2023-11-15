package core.service;

import core.model.SharingStandort;
import dao.GenericDAO;

public class SharingStandortService extends GenericDAO<SharingStandort> implements ISharingStandortService<SharingStandort> {

    @Override
    public boolean delete(long SharingStandortId) {
        return false;
    }

    @Override
    public void save(SharingStandort SharingStandort) {
        super.save(SharingStandort);
    }

    @Override
    public SharingStandort update(SharingStandort SharingStandort) {
        return super.update(SharingStandort);
    }

    @Override
    public SharingStandort find(long sharingStandortId) {
        return super.find(sharingStandortId);
    }

    @Override
    public java.util.List<SharingStandort> findAll() {
        return super.findAll();
    }

    public SharingStandortService() {
        super(SharingStandort.class);
    }
}
