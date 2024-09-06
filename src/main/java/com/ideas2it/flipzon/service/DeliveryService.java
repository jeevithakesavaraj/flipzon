package com.ideas2it.flipzon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.DeliveryPersonDao;
import com.ideas2it.flipzon.model.DeliveryPerson;

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
    private DeliveryPersonDao deliveryPersonDao;

    /**
     * <p>
     * Add Delivery person to the database
     * </p>
     *
     * @param deliveryPerson  {@link DeliveryPerson}
     */
    public DeliveryPerson createDelivery(DeliveryPerson deliveryPerson) {
        return deliveryPersonDao.save(deliveryPerson);
    }
}
