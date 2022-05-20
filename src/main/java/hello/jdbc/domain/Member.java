package hello.jdbc.domain;

import lombok.Data;

@Data // equals 와 hashcode Method 자동생성 해줌
public class Member {

    private String memberId;
    private int money;

    public Member() {
    }

    public Member(String memberId, int money) {
        this.memberId = memberId;
        this.money = money;
    }
}
