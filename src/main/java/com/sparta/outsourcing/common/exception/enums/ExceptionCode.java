package com.sparta.outsourcing.common.exception.enums;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    //----------MEMBER----------
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다"),
    NAME_TOO_LONG(HttpStatus.BAD_REQUEST, "이름은 최대 4글자까지 가능합니다"),
    USERNAME_REQUIRED(HttpStatus.BAD_REQUEST, "이름이 누락되었습니다"),
    MEMBER_ALREADY_DELETED(HttpStatus.NOT_FOUND, "이미 삭제된 회원입니다"),
    INVALID_POINT_VALUE(HttpStatus.NOT_FOUND, "0보다 큰 정수를 입력하세요"),
    ADDRESS_REQUIRED(HttpStatus.BAD_REQUEST, "주소가 누락되었습니다"),

    //이메일
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "해당 이메일이 존재합니다"),
    EMAIL_REQUIRED(HttpStatus.BAD_REQUEST, "이메일이 누락되었습니다"),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "유효하지 않은 이메일입니다"),

    //패스워드
    PASSWORD_REQUIRED(HttpStatus.BAD_REQUEST, "패스워드가 누락되었습니다"),
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 맞지 않습니다"),
    SAME_BEFORE_PASSWORD(HttpStatus.BAD_REQUEST, "변경된 비밀번호가 이전과 같습니다"),

    //----------가게----------
    STORE_OUT_OF_BUSINESS(HttpStatus.FORBIDDEN, "폐업한 가게입니다"),
    CANNOT_MODIFY_STORE_ID(HttpStatus.BAD_REQUEST, "가게 아이디는 수정할 수 없습니다"),
    NOT_FOUND_STORE(HttpStatus.NOT_FOUND, "해당 가게가 없습니다"),
    CANNOT_EXCEED_STORE_LIMIT(HttpStatus.FORBIDDEN, "최대 가게 3개만 소유 가능합니다."),

    //----------메뉴----------
    NOT_FOUND_MENU(HttpStatus.NOT_FOUND, "해당 메뉴가 없습니다"),
    MENU_ALREADY_DELETED(HttpStatus.NOT_FOUND, "이미 삭제된 메뉴입니다"),
    NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND, "해당 카테고리가 없습니다"),
    NOT_FOUND_OPTION(HttpStatus.NOT_FOUND, "해당 옵션이 없습니다"),
    OPTION_NOT_BELONG_TO_MENU(HttpStatus.NOT_FOUND, "해당 옵션은 선택한 메뉴에 포함되어 있지 않습니다"),


    //----------주문----------
    STORE_CLOSED(HttpStatus.GONE, "영업 시간이 아닙니다"),
    LOWER_THAN_MIN_ORDER(HttpStatus.BAD_REQUEST, "주문 금액이 최소 주문 금액보다 작습니다"),
    INSUFFICIENT_POINTS(HttpStatus.BAD_REQUEST, "포인트가 부족합니다"),
    NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, "해당 주문이 없습니다"),

    CANNOT_CHANGE_TO_PENDING(HttpStatus.FORBIDDEN, "대기 상태로 변경할 수 없습니다"),
    ACCEPT_ONLY_PENDING(HttpStatus.FORBIDDEN, "대기 상태일때만 수락 가능합니다"),
    REJECTED_ONLY_PENDING(HttpStatus.FORBIDDEN, "대기 상태일때만 거절 가능합니다"),
    COMPLETED_ONLY_ACCEPT(HttpStatus.FORBIDDEN, "수락 상태일때만 완료 가능합니다"),
    ONLY_ORDER_ALLOWED(HttpStatus.FORBIDDEN, "주문한 사람만 수정 가능합니다"),
    CANCEL_ONLY_PENDING(HttpStatus.FORBIDDEN, "대기 상태일때만 취소 가능합니다"),
    ORDER_NOT_DELIVERED(HttpStatus.FORBIDDEN, "주문이 배달 완료 상태가 아닙니다"),
    STORE_CLOSED_BY_OWER(HttpStatus.GONE,"해당 가게는 개인사정으로 문을 닫았습니다"),
    NOT_ORDER_NOW(HttpStatus.BAD_REQUEST,"선택한 메뉴는 현재 주문할 수 없습니다."),
    NOT_REJECTED_ACCEPT(HttpStatus.BAD_REQUEST,"완료된 주문은 취소할 수 없습니다."),


    //----------리뷰----------
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND, "해당 리뷰가 없습니다"),
    SCORE_OUT_OF_RANGE(HttpStatus.BAD_REQUEST, "점수는 1 에서 5 사이 숫자에서 골라주세요"),
    REVIEW_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "동일한 주문에 대해 이미 리뷰가 작성되었습니다"),
    NOT_ORDER_STATUS_COMPLETED(HttpStatus.FORBIDDEN, "주문이 배달 완료 상태가 아닙니다."),

    //권한
    ONLY_OWNER_ALLOWED(HttpStatus.FORBIDDEN, "사장님만 가능합니다"),
    HAS_NOT_PERMISSION(HttpStatus.FORBIDDEN, "권한이 없습니다"),
    ROLE_REQUIRED(HttpStatus.BAD_REQUEST, "OWNER과 USER중 하나를 입력하세요"),

    //----------즐겨찾기----------
    ALREADY_LIKE_EXISTS(HttpStatus.BAD_REQUEST, "이미 즐겨찾기한 가게입니다"),
    NOT_FOUND_LIKE(HttpStatus.NOT_FOUND, "이미 즐겨찾기한 가게입니다"),

    //광고등록
    ALREADY_ADVERTISEMENT_EXISTS(HttpStatus.BAD_REQUEST, "이미 광고등록이 된 가게입니다."),
    NOT_FOUND_ADVERTISEMENT(HttpStatus.NOT_FOUND, "이미 광고등록이 된 가게입니다."),

    //토큰
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "인증 토큰이 잘못되었거나 누락되었습니다"),

    HAS_NOT_COOKIE(HttpStatus.BAD_REQUEST, "Request has not cookie"),
    NOT_SUPPORT_ENCODING_COOKIE(HttpStatus.BAD_REQUEST, "Not support encoding cookie"),
    HAS_NOT_TOKEN(HttpStatus.BAD_REQUEST, "Request has not token"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "Token is expired"),
    NOT_SUPPORT_TOKEN(HttpStatus.UNAUTHORIZED, "Is not support token")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    ExceptionCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
