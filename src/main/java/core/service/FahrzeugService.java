package core.service;

import core.model.Fahrzeug;
import dao.GenericDAO;

import java.util.List;

public class FahrzeugService extends GenericDAO<Fahrzeug> implements IFahrzeugService<Fahrzeug> {

    public FahrzeugService() {
        super(Fahrzeug.class);
    }

    @Override
    public void save(Fahrzeug entity) {
        super.save(entity);
    }

    @Override
    public Fahrzeug update(Fahrzeug entity) {
        return super.update(entity);
    }

    @Override
    public boolean delete(long entityId) {
        return super.delete(entityId, Fahrzeug.class);
    }

    @Override
    public Fahrzeug find(long entityId) {
        return super.find(entityId);
    }

    @Override
    public List<Fahrzeug> findAll() {
        return super.findAll();
    }
}
