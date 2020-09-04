package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.Agenda;
import py.com.progweb.prueba.model.Persona;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless


public class PersonaDAO {
    @PersistenceContext(unitName = "pruebaPU")

    private EntityManager em;


    @Inject AgendaDAO agendaDao;

 /*
 Cada vez que querramos persistir una persona, persistimos tb su agenda.
 Por cada persona, le pasamos la agenda.
 * */
    public void agregar (Persona entidad){
        this.em.persist(entidad);
        for (Agenda a: entidad.getListaAgenda()){
            a.setPersona(entidad);
            agendaDao.agregar(a);

        }

    }

    public Object lista(){
        Query q=this.em.createQuery("select p.nombre,p.apellido,a.idAgenda, a.actividad,function('to_char',a.fecha,'yyyy-mm-dd')  from Persona p left join p.listaAgenda a where p.id_persona=a.persona.id_persona");

        return (List<Persona>) q.getResultList();
    }


}
