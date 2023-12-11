package rimgosu.cbthub.repository;

import lombok.Getter;
import lombok.Setter;
import rimgosu.cbthub.domain.OrderStatus;

@Getter @Setter
public class OrderSearch {

    private String memberName; // 회원의 이름
    private OrderStatus orderStatus; // 주문상태[ ORDER, CANCEL ]



}
