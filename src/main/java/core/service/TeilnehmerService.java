package core.service;

import core.model.Teilnehmer;
import dao.GenericDAO;

public class TeilnehmerService extends GenericDAO<Teilnehmer> implements ITeilnehmerService<Teilnehmer> {
    @Override
    public boolean delete(long entityId) {
        return false;
    }

    @Override
    public void save(Teilnehmer entity) {
        super.save(entity);
    }

    @Override
    public Teilnehmer update(Teilnehmer entity) {
        return super.update(entity);
    }

    @Override
    public Teilnehmer find(long entityId) {
        return super.find(entityId);
    }

    @Override
    public java.util.List<Teilnehmer> findAll() {
        return super.findAll();
    }

    public TeilnehmerService() {
        super(Teilnehmer.class);
    }
}
