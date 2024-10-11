package jpabook.jpashop.section7.web;

import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.section3.domain.Address;
import jpabook.jpashop.section3.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberForm {

    private Long id;

    @NotEmpty(message = "이름은 비어있을 수 없습니다.")
    private String name;

    private String city;
    private String street;
    private String zipCode;

    public MemberForm(Member member) {
        Address address = member.getAddress();
        this.id = member.getId();
        this.name = member.getName();
        this.city = address.getCity();
        this.street = address.getStreet();
        this.zipCode = address.getZipCode();
    }

    @Override
    public String toString() {
        return "MemberForm{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
