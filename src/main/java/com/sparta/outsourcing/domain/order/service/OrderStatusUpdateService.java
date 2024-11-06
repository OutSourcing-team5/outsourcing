package com.sparta.outsourcing.domain.order.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderStatusUpdateService {
	private final JavaMailSender javaMailSender;

	public void sendOrderStatusUpdateEmail(Long orderId, String newStatus, String memberEmail) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(memberEmail);
		message.setSubject("주문 상태가 변경되었습니다.");
		message.setText("고객님의 주문번호 "+orderId+" 이(가) "+newStatus +" 상태로 변경되었습니다." );
		javaMailSender.send(message);
	}
}
