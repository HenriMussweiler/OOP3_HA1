package core.service;

import core.model.Rechnung;
import dao.GenericDAO;

public class RechnungService extends GenericDAO<Rechnung> implements IRechnungService<Rechnung> {

    @Override
    public boolean delete(long rechnungId) {
        try {
            super.delete(rechnungId, Rechnung.class);
            return true; // Erfolgreich gelöscht
        } catch (Exception e) {
            System.out.println("Fehler beim Löschen der Rechnung mit ID: " + rechnungId);
            return false; // Fehler beim Löschen
        }
    }


    @Override
    public void save(Rechnung Rechnung) {
        super.save(Rechnung);
    }

    @Override
    public Rechnung update(Rechnung Rechnung) {
        return super.update(Rechnung);
    }

    @Override
    public Rechnung find(long rechnungId) {
        return super.find(rechnungId);
    }

    @Override
    public java.util.List<Rechnung> findAll() {
        return super.findAll();
    }

    public RechnungService() {
        super(Rechnung.class);
    }
}
