package core.service;

import java.util.List;

public interface IRechnungService<Rechnung> {
    void save(Rechnung entity);
    Rechnung update(Rechnung entity);
    boolean delete(long entityId);
    Rechnung find(long entityId);
    List<Rechnung> findAll();
}

