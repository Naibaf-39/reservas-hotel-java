package com.reservashotel.dao;

import com.reservashotel.model.Reserva;
import com.reservashotel.model.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ReservaDAO {

    public List<Reserva> findByUsuario(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String queryString = "SELECT r FROM Reserva r " +
                                 "JOIN FETCH r.habitacion h " +
                                 "JOIN FETCH h.hotel " +
                                 "WHERE r.usuario = :usuario ORDER BY r.fechaEntrada DESC";
            
            TypedQuery<Reserva> query = em.createQuery(queryString, Reserva.class);
            query.setParameter("usuario", usuario);
            
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void crear(Reserva reserva) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(reserva);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}