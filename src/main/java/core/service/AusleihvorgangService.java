package core.service;

import core.model.Ausleihvorgang;
import dao.GenericDAO;

import java.util.List;

public class AusleihvorgangService extends GenericDAO<Ausleihvorgang> implements IAusleihvorgangService<Ausleihvorgang> {

    public AusleihvorgangService() {
        super(Ausleihvorgang.class);
    }

    @Override
    public void save(Ausleihvorgang entity) {
        super.save(entity);
    }

    @Override
    public Ausleihvorgang update(Ausleihvorgang entity) {
        return super.update(entity);
    }

    @Override
    public boolean delete(long entityId) {
        return super.delete(entityId, Ausleihvorgang.class);
    }

    @Override
    public Ausleihvorgang find(long entityId) {
        return super.find(entityId);
    }

    @Override
    public List<Ausleihvorgang> findAll() {
        return super.findAll();
    }
}
