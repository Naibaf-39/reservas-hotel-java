package com.reservashotel.dao;

import com.reservashotel.model.Hotel;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class HotelDAO {

    public List<Hotel> buscarPorCiudad(String ciudad) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String queryString = "SELECT h FROM Hotel h WHERE LOWER(h.ciudad) LIKE LOWER(:ciudad)";
            TypedQuery<Hotel> query = em.createQuery(queryString, Hotel.class);
            query.setParameter("ciudad", "%" + ciudad + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Hotel findById(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Hotel> query = em.createQuery(
                "SELECT h FROM Hotel h LEFT JOIN FETCH h.habitaciones WHERE h.id = :id", Hotel.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}