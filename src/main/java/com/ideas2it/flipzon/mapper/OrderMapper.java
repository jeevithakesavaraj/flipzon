//package com.ideas2it.flipzon.mapper;
//
//import com.ideas2it.flipzon.dto.OrderDto;
//import com.ideas2it.flipzon.model.Cart;
//import com.ideas2it.flipzon.model.Order;
//
///**
// * <p>
// * Order Mapper has method for conversion of Dto to entity and entity to dto
// * </p>
// *
// * @author Jeevithakesavaraj
// */
//public class OrderMapper {
//
//    /**
//     * <p>
//     *  Converts OrderDto to Order
//     * </p>
//     *
//     * @param orderDto {@link OrderDto}
//     * @return Order {@link Order}
//     */
//    public static Order convertDtoToEntity(OrderDto orderDto) {
//        return Order.builder()
//                .cart(Cart.builder().id(orderDto.getCart_id()).build())
//                        .build())
//                .build();
//    }
//
//    /**
//     * <p>
//     * Converts Order to OrderDto
//     * </p>
//     *
//     * @param order {@link Order}
//     * @return OrderDto {@link OrderDto}
//     */
//    public static OrderDto convertEntityToDto(Order order) {
//        return OrderDto.builder()
//                .id(order.getId())
//                .cart_id(order.getCart().getId())
//                .payment_id(order.getPayment().getId())
//                .build();
//    }
//}
