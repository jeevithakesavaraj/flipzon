package com.ideas2it.flipzon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.DeliveryDao;
import com.ideas2it.flipzon.model.Delivery;

/**
 * <p>
 * This service class represents CRUD operations for delivery partners
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Service
public class DeliveryService {

    @Autowired
    private DeliveryDao deliveryDao;

    /**
     * <p>
     * Add Delivery person to the database
     * </p>
     *
     * @param delivery  {@link Delivery}
     */
    public Delivery createDelivery(Delivery delivery) {
        return deliveryDao.save(delivery);
    }
}
