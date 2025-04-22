package ru.sibsutis.shop.api.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.sibsutis.shop.api.dto.OrderDetailDto;
import ru.sibsutis.shop.api.dto.OrderRequestDto;
import ru.sibsutis.shop.api.dto.OrderResponseDto;
import ru.sibsutis.shop.api.dto.QuantityDto;
import ru.sibsutis.shop.core.model.entity.Item;
import ru.sibsutis.shop.core.model.entity.Order;
import ru.sibsutis.shop.core.model.entity.OrderDetail;
import ru.sibsutis.shop.core.model.entity.payment.Payment;
import ru.sibsutis.shop.core.model.entity.user.Customer;
import ru.sibsutis.shop.core.model.measurement.Quantity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-08T23:07:24+0700",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.13.jar, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order toEntity(OrderRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Order order = new Order();

        order.setOrderDetails( orderDetailDtoListToOrderDetailList( dto.getOrderDetails() ) );

        order.setDate( java.time.LocalDateTime.now() );
        order.setStatus( "CREATED" );

        return order;
    }

    @Override
    public OrderDetail toEntity(OrderDetailDto dto) {
        if ( dto == null ) {
            return null;
        }

        OrderDetail orderDetail = new OrderDetail();

        orderDetail.setItem( orderDetailDtoToItem( dto ) );
        orderDetail.setQuantity( quantityDtoToQuantity( dto.getQuantity() ) );
        orderDetail.setTaxStatus( dto.getTaxStatus() );

        return orderDetail;
    }

    @Override
    public OrderDetailDto toDto(OrderDetail detail) {
        if ( detail == null ) {
            return null;
        }

        OrderDetailDto orderDetailDto = new OrderDetailDto();

        orderDetailDto.setQuantity( quantityToQuantityDto( detail.getQuantity() ) );
        orderDetailDto.setItemId( detailItemId( detail ) );
        orderDetailDto.setTaxStatus( detail.getTaxStatus() );

        return orderDetailDto;
    }

    @Override
    public OrderResponseDto toDto(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderResponseDto orderResponseDto = new OrderResponseDto();

        orderResponseDto.setUserId( orderCustomerId( order ) );
        orderResponseDto.setPaymentId( orderPaymentId( order ) );
        orderResponseDto.setId( order.getId() );
        orderResponseDto.setDate( order.getDate() );
        orderResponseDto.setStatus( order.getStatus() );
        orderResponseDto.setOrderDetails( orderDetailListToOrderDetailDtoList( order.getOrderDetails() ) );

        return orderResponseDto;
    }

    @Override
    public List<OrderResponseDto> toDto(List<Order> order) {
        if ( order == null ) {
            return null;
        }

        List<OrderResponseDto> list = new ArrayList<OrderResponseDto>( order.size() );
        for ( Order order1 : order ) {
            list.add( toDto( order1 ) );
        }

        return list;
    }

    protected List<OrderDetail> orderDetailDtoListToOrderDetailList(List<OrderDetailDto> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderDetail> list1 = new ArrayList<OrderDetail>( list.size() );
        for ( OrderDetailDto orderDetailDto : list ) {
            list1.add( toEntity( orderDetailDto ) );
        }

        return list1;
    }

    protected Item orderDetailDtoToItem(OrderDetailDto orderDetailDto) {
        if ( orderDetailDto == null ) {
            return null;
        }

        Item item = new Item();

        item.setId( orderDetailDto.getItemId() );

        return item;
    }

    protected Quantity quantityDtoToQuantity(QuantityDto quantityDto) {
        if ( quantityDto == null ) {
            return null;
        }

        Quantity quantity = new Quantity();

        quantity.setName( quantityDto.getName() );
        quantity.setSymbol( quantityDto.getSymbol() );
        quantity.setValue( quantityDto.getValue() );

        return quantity;
    }

    protected QuantityDto quantityToQuantityDto(Quantity quantity) {
        if ( quantity == null ) {
            return null;
        }

        Integer value = null;
        String name = null;
        String symbol = null;

        value = quantity.getValue();
        name = quantity.getName();
        symbol = quantity.getSymbol();

        QuantityDto quantityDto = new QuantityDto( value, name, symbol );

        return quantityDto;
    }

    private Long detailItemId(OrderDetail orderDetail) {
        Item item = orderDetail.getItem();
        if ( item == null ) {
            return null;
        }
        return item.getId();
    }

    private Long orderCustomerId(Order order) {
        Customer customer = order.getCustomer();
        if ( customer == null ) {
            return null;
        }
        return customer.getId();
    }

    private Long orderPaymentId(Order order) {
        Payment payment = order.getPayment();
        if ( payment == null ) {
            return null;
        }
        return payment.getId();
    }

    protected List<OrderDetailDto> orderDetailListToOrderDetailDtoList(List<OrderDetail> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderDetailDto> list1 = new ArrayList<OrderDetailDto>( list.size() );
        for ( OrderDetail orderDetail : list ) {
            list1.add( toDto( orderDetail ) );
        }

        return list1;
    }
}
